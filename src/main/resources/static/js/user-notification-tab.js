// user-notification-tab.js
import { createActionColumn } from './create-action-column.js';
import { enterEditMode } from './edit-mode.js'
import { cancelEditMode } from './cancel-mode.js'
import { saveEdit } from './save-edit.js'
import { formatDate } from './common.js'
import { userNotificationHeaders } from './table-header-variable.js'
import { buildOptions } from './common.js'

export function changeUserNotificationCount(delta) {
    const input = document.getElementById('userNotificationCount');
    let value = parseInt(input.value) || 1;
    value = Math.max(1, value + delta);
    input.value = value;
    generateUserNotificationFormsFromInput();
}

export function generateUserNotificationFormsFromInput() {
    const count = parseInt(document.getElementById('userNotificationCount').value) || 1;
    const container = document.getElementById('userNotificationFormsContainer');
    const template = document.getElementById('userNotificationFormTemplate');
    container.innerHTML = '';

    for (let i = 0; i < count; i++) {
        const clone = template.content.cloneNode(true);
        clone.querySelector('.form-index').textContent = i + 1;
        clone.querySelectorAll('[name]').forEach(input => {
            const baseName = input.getAttribute('name');
            input.name = `adminUserNotificationDTOs[${i}].${baseName}`;
        });
        container.appendChild(clone);
    }
}

export function deleteUserNotification(button) {
    const id = button.getAttribute("data-id");
    if (!confirm("Bạn có chắc chắn muốn xoá thông báo người dùng này?"))
        return;

    fetch('/gomap/admin/dashboard/user-notifications/delete', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: `ids=${encodeURIComponent(id)}`
    }).then(res => {
        if (res.redirected) {
            window.location.href = res.url;
        } else {
            alert("Xoá không thành công.");
        }
    }).catch(() => alert("Đã xảy ra lỗi khi xoá thông báo người dùng."));
}

export function initUserNotificationTab() {
    document.getElementById('userNotificationCount').value = 1;
    generateUserNotificationFormsFromInput();

    const selectAllCheckbox = document.getElementById("selectAllUserNotifications");
    if (selectAllCheckbox) {
        selectAllCheckbox.addEventListener("change", function () {
            const checked = this.checked;
            document.querySelectorAll('.user-notification-checkbox').forEach(cb => cb.checked = checked);
        });
    }

    const deleteButton = document.getElementById("deleteSelectedUserNotifications");
    if (deleteButton) {
        deleteButton.addEventListener("click", () => {
            const ids = Array.from(document.querySelectorAll('.user-notification-checkbox:checked')).map(cb => cb.value);
            if (ids.length === 0) {
                alert("Vui lòng chọn ít nhất một thông báo người dùng để xoá.");
                return;
            }
            if (!confirm("Bạn có chắc chắn muốn xoá các thông báo người dùng đã chọn?"))
                return;

            const params = new URLSearchParams();
            ids.forEach(id => params.append('ids', id));
            fetch('/gomap/admin/dashboard/user-notifications/delete', {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                body: params.toString()
            }).then(res => {
                if (res.redirected) {
                    window.location.href = res.url;
                } else {
                    alert("Xoá không thành công.");
                }
            }).catch(() => alert("Đã xảy ra lỗi khi xoá."));
        });
    }

    document.getElementById('addUserNotificationForm').addEventListener('submit', function (e) {
        e.preventDefault(); // chặn submit mặc định

        const forms = document.querySelectorAll('.user-notification-form');
        const requests = [];

        forms.forEach(form => {
            const notificationId = form.querySelector('.notification-id-select').value;
            const selectedUsers = form.querySelector('.user-ids-select').selectedOptions;
            const userIds = Array.from(selectedUsers).map(opt => opt.value).filter(Boolean);

            if (notificationId && userIds.length > 0) {
                requests.push({
                    notificationId: notificationId,
                    userIds: userIds
                });
            }
        });

        if (requests.length === 0) {
            alert('Vui lòng điền đầy đủ thông tin!');
            return;
        }

        Promise.all(requests.map(req =>
            fetch('/gomap/admin/dashboard/user-notifications/save', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(req)
            })
        )).then(responses => {
            alert('Gửi thông báo thành công!');
            // Reset form sau khi gửi thành công
            document.getElementById('addUserNotificationForm').reset();
            generateUserNotificationFormsFromInput();
        }).catch(error => {
            console.error(error);
            alert('Có lỗi xảy ra!');
        });
    });

}


// user-notification-tab.js
export function loadUserNotificationPage(page) {
    fetch(`/gomap/admin/dashboard/user-notifications/page?page=${page}&size=10`)
            .then(res => res.json())
            .then(data => {
                updateUserNotificationTable(data.userNotifications);
                updateUserNotificationPagination(data.currentPage, data.totalPages);
            })
            .catch(err => console.error("Lỗi khi tải trang thông báo người dùng:", err));
}

function updateUserNotificationTable(userNotifications) {
    const tbody = document.getElementById('userNotificationTableBody');
    tbody.innerHTML = '';

    userNotifications.forEach(un => {
        const row = document.createElement('tr');

        row.innerHTML = `
                <td class="no-edit">
                    <input type="checkbox" class="row-checkbox user-notification-checkbox" value="${un.id}">
                </td>
                <td class="no-edit">${un.id}</td>
                <td>
                  <span>${formatDate(un.sendAt, 'yyyy-MM-dd HH:mm:ss')}</span>
                </td>
                <td>
                  <span class="cell-text">${un.isRead ? 'Read' : 'Unread'}</span>
                  <select class="cell-input d-none">
                    <option value="true" ${un.isRead ? 'selected' : ''}>Read</option>
                    <option value="false" ${!un.isRead ? 'selected' : ''}>Unread</option>
                  </select>
                </td>
                <td>
                    <span class="cell-text">${un.notificationId || 'N/A'}</span>
                    <select class="cell-input d-none form-control" name="notificationId">
                        ${buildOptions(listNotificationNames, un.notificationId)}
                    </select>
                </td>
                <td>
                    <span class="cell-text">${un.userId || 'N/A'}</span>
                    <select class="cell-input d-none form-control" name="userId">
                        ${buildOptions(listUserFullnames, un.userId)}
                    </select>
                </td>
            `;

        const actionTd = createActionColumn(un, enterEditMode, cancelEditMode, saveEdit, userNotificationHeaders);
        row.appendChild(actionTd);

        tbody.appendChild(row);
    });
}


function updateUserNotificationPagination(currentPage, totalPages) {
    const pagination = document.getElementById('userNotificationPagination');
    pagination.innerHTML = '';

    const createPageItem = (text, page, disabled = false, active = false) => {
        const li = document.createElement('li');
        li.className = 'page-item';
        if (disabled)
            li.classList.add('disabled');
        if (active)
            li.classList.add('active');

        const a = document.createElement('a');
        a.className = 'page-link';
        a.href = 'javascript:void(0);';
        a.textContent = text;
        a.onclick = () => loadUserNotificationPage(page);

        li.appendChild(a);
        return li;
    };

    const maxVisiblePages = 7;
    let startPage = Math.max(0, currentPage - Math.floor(maxVisiblePages / 2));
    let endPage = startPage + maxVisiblePages - 1;

    if (endPage >= totalPages) {
        endPage = totalPages - 1;
        startPage = Math.max(0, endPage - maxVisiblePages + 1);
    }

    pagination.appendChild(createPageItem('Trước', currentPage - 1, currentPage === 0));

    if (startPage > 0) {
        pagination.appendChild(createPageItem(1, 0));
        if (startPage > 1) {
            const li = document.createElement('li');
            li.className = 'page-item disabled';
            li.innerHTML = `<span class="page-link">...</span>`;
            pagination.appendChild(li);
        }
    }

    for (let i = startPage; i <= endPage; i++) {
        pagination.appendChild(createPageItem(i + 1, i, false, i === currentPage));
    }

    if (endPage < totalPages - 1) {
        if (endPage < totalPages - 2) {
            const li = document.createElement('li');
            li.className = 'page-item disabled';
            li.innerHTML = `<span class="page-link">...</span>`;
            pagination.appendChild(li);
        }
        pagination.appendChild(createPageItem(totalPages, totalPages - 1));
    }

    pagination.appendChild(createPageItem('Sau', currentPage + 1, currentPage >= totalPages - 1));
}



