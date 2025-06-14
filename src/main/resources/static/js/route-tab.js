// route-tab.js
import { createActionColumn } from './create-action-column.js';
import { formatDate, formatTime } from './common.js'
import { enterEditMode } from './edit-mode.js'
import { cancelEditMode } from './cancel-mode.js'
import { saveEdit } from './save-edit.js'
import { routeHeaders } from './table-header-variable.js'
import { buildOptions } from './common.js'


// Thay đổi số lượng tuyến
export function changeRouteCount(delta) {
    const input = document.getElementById('routeCount');
    let value = parseInt(input.value) || 1;
    value += delta;
    if (value < 1)
        value = 1;
    input.value = value;
    generateRouteFormsFromInput();
}

// Tạo form tuyến có name chuẩn DTO: adminRouteDTOs[i].field
export function generateRouteFormsFromInput() {
    const count = parseInt(document.getElementById('routeCount').value) || 1;
    const container = document.getElementById('routeFormsContainer');
    const template = document.getElementById('routeFormTemplate');
    container.innerHTML = '';
    for (let i = 0; i < count; i++) {
        const clone = template.content.cloneNode(true);
        clone.querySelector('.form-index').textContent = i + 1;
        const inputs = clone.querySelectorAll('[name]');
        inputs.forEach(input => {
            const baseName = input.getAttribute('name');
            input.name = `adminRouteDTOs[${i}].${baseName}`;
        });
        container.appendChild(clone);
    }
}

// Cập nhật tên ẩn theo số tuyến
export function updateRouteNumberHidden(input) {
    const value = input.value || '';
    const hiddenInput = input.parentElement.querySelector('.route-name-hidden');
    hiddenInput.value = 'Tuyến số ' + value;
}

// Xóa 1 tuyến
export function deleteRoute(button) {
    const id = button.getAttribute("data-id");
    if (!confirm("Bạn có chắc chắn muốn xoá tuyến này?"))
        return;
    fetch('/gomap/admin/dashboard/routes/delete', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: `ids=${encodeURIComponent(id)}`
    }).then(res => {
        if (res.redirected) {
            window.location.href = res.url;
        } else {
            alert("Xoá không thành công.");
        }
    }).catch(() => alert("Đã xảy ra lỗi khi xoá tuyến."));
}

// Xóa nhiều tuyến
export function deleteSelectedRoutes() {
    const checkboxes = document.querySelectorAll('.route-checkbox:checked');
    const ids = Array.from(checkboxes).map(cb => cb.getAttribute('data-id'));
    if (ids.length === 0) {
        alert("Vui lòng chọn ít nhất một tuyến để xoá.");
        return;
    }
    if (!confirm("Bạn có chắc chắn muốn xoá các tuyến đã chọn?"))
        return;

    const params = new URLSearchParams();
    ids.forEach(id => params.append('ids', id));
    fetch('/gomap/admin/dashboard/routes/delete', {
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
}

// Xử lý chọn tất cả
export function initSelectAllRoutes() {
    const selectAll = document.getElementById("selectAllRoutes");
    if (!selectAll)
        return;
    selectAll.addEventListener("change", function () {
        const isChecked = this.checked;
        document.querySelectorAll('.route-checkbox').forEach(cb => cb.checked = isChecked);
    });
}

// Validate điểm bắt đầu và kết thúc không trùng nhau
function validateDifferent() {
    if (this.value && this === startPoint && this.value === endPoint.value) {
        alert("Điểm bắt đầu và điểm kết thúc không được trùng nhau.");
        this.value = '';
    } else if (this.value && this === endPoint && this.value === startPoint.value) {
        alert("Điểm bắt đầu và điểm kết thúc không được trùng nhau.");
        this.value = '';
    }
}

let startPoint = null;
let endPoint = null;

// Hàm khởi tạo
export function initRouteTab() {
    const routeCountInput = document.getElementById('routeCount');
    if (routeCountInput) {
        routeCountInput.value = 1;
        generateRouteFormsFromInput();
    }

    startPoint = document.getElementById('startPoint');
    endPoint = document.getElementById('endPoint');
    if (startPoint && endPoint) {
        startPoint.addEventListener('change', validateDifferent);
        endPoint.addEventListener('change', validateDifferent);
    }

    const deleteSelectedBtn = document.getElementById("deleteSelectedRoutes");
    if (deleteSelectedBtn) {
        deleteSelectedBtn.addEventListener("click", deleteSelectedRoutes);
    }

    initSelectAllRoutes();

    // Route number -> route name update
    const routeNumberInput = document.getElementById("routeNumber");
    const routeNameInput = document.getElementById("routName");
    if (routeNumberInput && routeNameInput) {
        routeNumberInput.addEventListener("input", () => {
            const number = routeNumberInput.value;
            routeNameInput.value = number ? `Tuyến số ${number}` : "";
        });
    }
}

export function loadRoutePage(page) {
    fetch(`/gomap/admin/dashboard/routes/page?page=${page}&size=10`)
            .then(res => res.json())
            .then(data => {
                updateRouteTable(data.routes);
                updateRoutePagination(data.currentPage, data.totalPages);
            })
            .catch(err => console.error("Lỗi khi tải danh sách tuyến:", err));
}

function updateRouteTable(routes) {
    const tbody = document.getElementById('routeTableBody');
    tbody.innerHTML = '';

    routes.forEach(route => {
        const row = document.createElement('tr');
        row.classList.add('text-center');

        row.innerHTML = `
            <td class="no-edit">
                <input type="checkbox" class="route-checkbox row-checkbox" data-id="${route.id}">
            </td>

            <td class="no-edit">${route.id}</td>

            <td>
                <span class="cell-text">${route.name || 'N/A'}</span>
                <input name="name" class="cell-input d-none form-control form-control-sm" type="text" value="${route.name || ''}">
            </td>

            <td>
                <span class="cell-text">${route.startPoint || 'N/A'}</span>
                <select name="startPoint" class="cell-input d-none form-control">
                    ${buildOptions(listStationNames, route.startPoint)}
                </select>
            </td>

            <td>
                <span class="cell-text">${route.endPoint || 'N/A'}</span>
                <select name="endPoint" class="cell-input d-none form-control">
                    ${buildOptions(listStationNames, route.endPoint)}
                </select>
            </td>

            <td>
                <span class="cell-text">${route.status || 'N/A'}</span>
                <select name="status" class="cell-input d-none form-control">
                    <option value="Active" ${route.status === 'Active' ? 'selected' : ''}>Active</option>
                    <option value="Inactive" ${route.status === 'Inactive' ? 'selected' : ''}>Inactive</option>
                </select>
            </td>

            <td>
                <span class="cell-text">${route.frequency || 'N/A'}</span>
                <input name="frequency" class="cell-input d-none form-control form-control-sm" type="text" value="${route.frequency || ''}">
            </td>

            <td>
                <span class="cell-text">${formatTime(route.startTime)}</span>
                <input name="startTime" class="cell-input d-none form-control form-control-sm" type="time" value="${formatTime(route.startTime)}">
            </td>

            <td>
                <span class="cell-text">${formatTime(route.endTime)}</span>
                <input name="endTime" class="cell-input d-none form-control form-control-sm" type="time" value="${formatTime(route.endTime)}">
            </td>

            <td>
                <span class="cell-text">${route.distance || 'N/A'}</span>
                <input name="distance" class="cell-input d-none form-control form-control-sm" type="text" value="${route.distance || ''}">
            </td>

            <td>
                <span class="cell-text">${route.duration || 'N/A'}</span>
                <input name="duration" class="cell-input d-none form-control form-control-sm" type="text" value="${route.duration || ''}">
            </td>

            <td class="no-edit">${formatDate(route.createdAt, 'yyyy-MM-dd HH:mm:ss')}</td>
        `;

        const actionTd = createActionColumn(route, enterEditMode, cancelEditMode, saveEdit, routeHeaders);
        row.appendChild(actionTd);
        tbody.appendChild(row);
    });
}



function updateRoutePagination(currentPage, totalPages) {
    const pagination = document.getElementById('routePagination');
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
        a.onclick = () => loadRoutePage(page);

        li.appendChild(a);
        return li;
    };

    pagination.appendChild(createPageItem('Trước', currentPage - 1, currentPage === 0));

    for (let i = 0; i < totalPages; i++) {
        pagination.appendChild(createPageItem(i + 1, i, false, i === currentPage));
    }

    pagination.appendChild(createPageItem('Sau', currentPage + 1, currentPage >= totalPages - 1));
}
