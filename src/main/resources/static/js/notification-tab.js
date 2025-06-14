//notification-tab.js
import { createActionColumn } from './create-action-column.js';
import { enterEditMode } from './edit-mode.js'
import { cancelEditMode } from './cancel-mode.js'
import { saveEdit } from './save-edit.js'
import { formatDate } from './common.js'
import { notificationHeaders } from './table-header-variable.js'

export function changeNotificationCount(delta) {
    const input = document.getElementById('notificationCount');
    let value = parseInt(input.value) || 1;
    value += delta;
    if (value < 1)
        value = 1;
    input.value = value;
    generateNotificationFormsFromInput();
}

export function generateNotificationFormsFromInput() {
    const count = parseInt(document.getElementById('notificationCount').value) || 1;
    const container = document.getElementById('notificationFormsContainer');
    const template = document.getElementById('notificationFormTemplate');
    container.innerHTML = '';
    for (let i = 0; i < count; i++) {
        const clone = template.content.cloneNode(true);
        clone.querySelector('.form-index').textContent = i + 1;
        clone.querySelectorAll('[name]').forEach(el => {
            const baseName = el.getAttribute('name');
            el.name = `adminNotificationDTOs[${i}].${baseName}`;
        });
        container.appendChild(clone);
    }
}

export function deleteNotification(button) {
    const id = button.getAttribute("data-id");
    if (!confirm("Bạn có chắc chắn muốn xoá thông báo này?"))
        return;

    fetch('/gomap/admin/dashboard/notifications/delete', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: `ids=${encodeURIComponent(id)}`
    }).then(res => {
        if (res.redirected) {
            window.location.href = res.url;
        } else {
            alert("Xoá không thành công.");
        }
    }).catch(() => alert("Đã xảy ra lỗi khi xoá thông báo."));
}

export function initNotificationTab() {
    const notificationCountInput = document.getElementById('notificationCount');
    if (notificationCountInput) {
        notificationCountInput.value = 1;
        generateNotificationFormsFromInput();
    }

    const selectAllCheckbox = document.getElementById("selectAllNotifications");
    if (selectAllCheckbox) {
        selectAllCheckbox.addEventListener("change", function () {
            const isChecked = this.checked;
            document.querySelectorAll('.notification-checkbox').forEach(cb => cb.checked = isChecked);
        });
    }

    const deleteSelectedBtn = document.getElementById("deleteSelectedNotifications");
    if (deleteSelectedBtn) {
        deleteSelectedBtn.addEventListener("click", function () {
            const checkboxes = document.querySelectorAll('.notification-checkbox:checked');
            const ids = Array.from(checkboxes).map(cb => cb.value);
            if (ids.length === 0) {
                alert("Vui lòng chọn ít nhất một thông báo để xoá.");
                return;
            }

            if (!confirm("Bạn có chắc chắn muốn xoá các thông báo đã chọn?"))
                return;

            const params = new URLSearchParams();
            ids.forEach(id => params.append('ids', id));

            fetch('/gomap/admin/dashboard/notifications/delete', {
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
}
export function loadNotificationPage(page) {
    fetch(`/gomap/admin/dashboard/notifications/page?page=${page}&size=10`)
            .then(res => res.json())
            .then(data => {
                updateNotificationTable(data.notifications);
                updateNotificationPagination(data.currentPage, data.totalPages);
            })
            .catch(err => console.error("Lỗi khi tải danh sách thông báo:", err));
}

function updateNotificationTable(notifications) {
    const tbody = document.getElementById('notificationTableBody');
    tbody.innerHTML = '';

    notifications.forEach(notification => {
        const row = document.createElement('tr');
        row.classList.add('text-center');
        row.dataset.type = "notification";

        row.innerHTML = `
            <td class="no-edit">
                <input type="checkbox" class="notification-checkbox row-checkbox" value="${notification.id}">
            </td>
        
            <td class="no-edit">${notification.id}</td>

            <td>
                <span class="cell-text">${notification.title}</span>
                <input type="text" class="form-control form-control-sm cell-input d-none" name="title" value="${notification.title}">
            </td>

            <td>
                <span class="cell-text">${notification.message}</span>
                <input type="text" class="form-control form-control-sm cell-input d-none" name="message" value="${notification.message}">
            </td>

            <td>
                <span class="cell-text">${notification.messageType}</span>
                <select class="form-select form-select-sm cell-input d-none" name="messageType">
                    <option value="INFO" ${notification.messageType === 'INFO' ? 'selected' : ''}>INFO</option>
                    <option value="WARNING" ${notification.messageType === 'WARNING' ? 'selected' : ''}>WARNING</option>
                    <option value="ERROR" ${notification.messageType === 'ERROR' ? 'selected' : ''}>ERROR</option>
                </select>
            </td>

            <td class="no-edit">${formatDateTime(notification.createdAt)}</td>
        `;

        const actionTd = createActionColumn(notification, enterEditMode, cancelEditMode, saveEdit, notificationHeaders);
        row.appendChild(actionTd);

        tbody.appendChild(row);
    });
}



function updateNotificationPagination(currentPage, totalPages) {
    const pagination = document.getElementById('notificationPagination');
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
        a.onclick = () => loadNotificationPage(page);

        li.appendChild(a);
        return li;
    };

    const maxVisiblePages = 7; // số trang tối đa hiển thị
    let startPage = Math.max(0, currentPage - Math.floor(maxVisiblePages / 2));
    let endPage = startPage + maxVisiblePages - 1;

    if (endPage >= totalPages) {
        endPage = totalPages - 1;
        startPage = Math.max(0, endPage - maxVisiblePages + 1);
    }

    // Nút "Trước"
    pagination.appendChild(createPageItem('Trước', currentPage - 1, currentPage === 0));

    // Luôn hiển thị trang đầu nếu startPage > 0
    if (startPage > 0) {
        pagination.appendChild(createPageItem(1, 0));
        if (startPage > 1) {
            const li = document.createElement('li');
            li.className = 'page-item disabled';
            li.innerHTML = `<span class="page-link">...</span>`;
            pagination.appendChild(li);
        }
    }

    // Hiển thị các trang trong khoảng startPage đến endPage
    for (let i = startPage; i <= endPage; i++) {
        pagination.appendChild(createPageItem(i + 1, i, false, i === currentPage));
    }

    // Luôn hiển thị trang cuối nếu endPage < totalPages - 1
    if (endPage < totalPages - 1) {
        if (endPage < totalPages - 2) {
            const li = document.createElement('li');
            li.className = 'page-item disabled';
            li.innerHTML = `<span class="page-link">...</span>`;
            pagination.appendChild(li);
        }
        pagination.appendChild(createPageItem(totalPages, totalPages - 1));
    }

    // Nút "Sau"
    pagination.appendChild(createPageItem('Sau', currentPage + 1, currentPage >= totalPages - 1));
}


function formatDateTime(dateTimeString) {
    const date = new Date(dateTimeString);
    return date.toLocaleString('vi-VN', {
        year: 'numeric', month: '2-digit', day: '2-digit',
        hour: '2-digit', minute: '2-digit', second: '2-digit'
    });
}
