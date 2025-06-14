// user-tab.js
import { createActionColumn } from './create-action-column.js';
import { enterEditMode } from './edit-mode.js'
import { cancelEditMode } from './cancel-mode.js'
import { saveEdit } from './save-edit.js'
import { formatDate } from './common.js'
import { userHeaders } from './table-header-variable.js'


export function changeUserCount(delta) {
    const input = document.getElementById('userCount');
    let value = parseInt(input.value) || 1;
    value = Math.max(1, value + delta);
    input.value = value;
    generateUserFormsFromInput();
}

export function generateUserFormsFromInput() {
    const count = parseInt(document.getElementById('userCount').value) || 1;
    const container = document.getElementById('userFormsContainer');
    const template = document.getElementById('userFormTemplate');
    container.innerHTML = '';
    for (let i = 0; i < count; i++) {
        let html = template.innerHTML;
        html = html.replace(/#index#/g, i);

        const wrapper = document.createElement('div');
        wrapper.innerHTML = html;

        wrapper.querySelector('.form-index').textContent = i + 1;

        wrapper.querySelectorAll('input, select').forEach(input => {
            const name = input.getAttribute('name');
            if (!name)
                return;

            if (input.type === 'file') {
                input.setAttribute('name', 'avatars');
            } else {
                input.setAttribute('name', `adminUserDTOs[${i}].${name}`);
            }
        });

        container.appendChild(wrapper.firstElementChild);
    }
}

export function deleteUser(button) {
    const id = button.getAttribute("data-id");
    if (!confirm("Bạn có chắc chắn muốn xóa người dùng này?"))
        return;

    fetch('/gomap/admin/dashboard/users/delete', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: `ids=${encodeURIComponent(id)}`
    }).then(res => {
        if (res.redirected) {
            window.location.href = res.url;
        } else {
            alert("Xoá không thành công.");
        }
    }).catch(() => alert("Đã xảy ra lỗi khi xoá người dùng."));
}

export function initUserTab() {
    document.getElementById('userCount').value = 1;
    generateUserFormsFromInput();

    document.getElementById("selectAllUsers").addEventListener("change", function () {
        const checked = this.checked;
        document.querySelectorAll(".user-checkbox").forEach(cb => cb.checked = checked);
    });

    document.getElementById("deleteSelectedUsers").addEventListener("click", () => {
        const ids = Array.from(document.querySelectorAll(".user-checkbox:checked")).map(cb => cb.value);
        if (ids.length === 0) {
            alert("Vui lòng chọn ít nhất một người dùng để xoá.");
            return;
        }
        if (!confirm("Bạn có chắc chắn muốn xoá các người dùng đã chọn?"))
            return;

        const params = new URLSearchParams();
        ids.forEach(id => params.append('ids', id));
        fetch('/gomap/admin/dashboard/users/delete', {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: params.toString()
        }).then(res => {
            if (res.redirected) {
                window.location.href = res.url;
            } else {
                alert("Xoá không thành công.");
            }
        }).catch(() => alert("Đã xảy ra lỗi khi xoá người dùng."));
    });
}

export function loadUserPage(page) {
    const size = 10; // hoặc lấy từ input nếu cho phép người dùng chọn số dòng/trang

    fetch(`/gomap/admin/dashboard/users/page?page=${page}&size=${size}`)
            .then(response => {
                if (!response.ok)
                    throw new Error('Lỗi khi tải dữ liệu người dùng');
                return response.json();
            })
            .then(data => {
                updateUserTable(data.users);
                updateUserPagination(data.currentPage, data.totalPages);
            })
            .catch(error => {
                console.error('Lỗi khi tải trang người dùng:', error);
                alert('Không thể tải dữ liệu người dùng.');
            });
}

function updateUserTable(users) {
    const tbody = document.getElementById('userTableBody');
    tbody.innerHTML = '';

    users.forEach(user => {
        const row = document.createElement('tr');
        row.dataset.id = user.id; // Quan trọng để xử lý lưu

        row.innerHTML = `
            <td class="no-edit"><input type="checkbox" class="user-checkbox row-checkbox" value="${user.id}"></td>
            <td class="no-edit">${user.id}</td>
            <td>
                <span class="cell-text">${user.fullName}</span>
                <input name="fullName" class="cell-input d-none form-control form-control-sm" type="text" value="${user.fullName}">
            </td>
            <td>
                <span class="cell-text">${user.email}</span>
                <input name="email" class="cell-input d-none form-control form-control-sm" type="email" value="${user.email}">
            </td>
            <td>
                <span class="cell-text">${user.phone}</span>
                <input name="phone" class="cell-input d-none form-control form-control-sm" type="text" value="${user.phone}">
            </td>
            <td>
                <span class="no-edit">${user.username}</span>
                <input name="username" class="cell-input d-none form-control form-control-sm" type="text" value="${user.username}">
            </td>
            <td>
                <span class="cell-text">✿✿✿✿✿</span>
                <input name="password" class="cell-input d-none form-control form-control-sm" type="password">
            </td>
            <td>
                <span class="cell-text">${user.userRole}</span>
                <input name="userRole" class="cell-input d-none form-control form-control-sm" type="text" value="${user.userRole}">
            </td>
            <td>
                <span class="cell-text">${user.gender}</span>
                <select name="gender" class="cell-input d-none form-select form-select-sm">
                    <option value="Male" ${user.gender === 'Male' ? 'selected' : ''}>Male</option>
                    <option value="Female" ${user.gender === 'Female' ? 'selected' : ''}>Female</option>
                    <option value="Other" ${user.gender === 'Other' ? 'selected' : ''}>Other</option>
                </select>
            </td>
            <td>
                <span class="cell-text">${formatDate(user.birthday, 'yyyy-MM-dd')}</span>
                <input name="birthday" class="cell-input d-none form-control form-control-sm" type="date" value="${formatDate(user.birthday, 'yyyy-MM-dd')}">
            </td>
            <td>
                <img src="${user.avatar}" alt="Avatar" class="rounded-circle border" style="width: 48px; height: 48px; object-fit: cover;">
                <input name="avatarFile" type="file" class="cell-input d-none form-control form-control-sm" accept="image/*">
            </td>
            <td class="status-text">
                <span class="cell-text">${user.isActive ? 'Đang hoạt động' : 'Tạm dừng'}</span>
                <select name="isActive" class="cell-input form-select form-select-sm status-select d-none">
                    <option value="true" ${user.isActive ? 'selected' : ''}>Đang hoạt động</option>
                    <option value="false" ${!user.isActive ? 'selected' : ''}>Tạm dừng</option>
                </select>
            </td>
            <td class="no-edit">${formatDate(user.createdAt, 'yyyy-MM-dd HH:mm:ss')}</td>
        `;

        const actionTd = createActionColumn(user, enterEditMode, cancelEditMode, saveEdit, userHeaders);
        row.appendChild(actionTd);

        tbody.appendChild(row);
    });
}




function updateUserPagination(currentPage, totalPages) {
    const pagination = document.getElementById('userPagination');
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
        a.onclick = () => loadUserPage(page);

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

    // Hiển thị các trang từ startPage đến endPage
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


