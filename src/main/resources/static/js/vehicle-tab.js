// vehicle-tab.js
import { createActionColumn } from './create-action-column.js';
import { enterEditMode } from './edit-mode.js'
import { cancelEditMode } from './cancel-mode.js'
import { saveEdit } from './save-edit.js'
import { formatDate, buildOptions } from './common.js'
import { vehicleHeaders } from './table-header-variable.js'

export function changeVehicleCount(delta) {
    const input = document.getElementById('vehicleCount');
    let value = parseInt(input.value) || 1;
    value = Math.max(1, value + delta);
    input.value = value;
    generateVehicleFormsFromInput();
}

export function generateVehicleFormsFromInput() {
    const count = parseInt(document.getElementById('vehicleCount').value) || 1;
    const container = document.getElementById('vehicleFormsContainer');
    const template = document.getElementById('vehicleFormTemplate');
    container.innerHTML = '';
    for (let i = 0; i < count; i++) {
        const clone = template.content.cloneNode(true);
        clone.querySelector('.form-index').textContent = i + 1;
        clone.querySelectorAll('[name]').forEach(input => {
            const baseName = input.getAttribute('name');
            input.name = `adminVehicleDTOs[${i}].${baseName}`;
        });
        container.appendChild(clone);
    }
}

export function deleteVehicle(button) {
    const id = button.getAttribute("data-id");
    if (!confirm("Bạn có chắc chắn muốn xoá phương tiện này?"))
        return;

    fetch('/gomap/admin/dashboard/vehicles/delete', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: `ids=${encodeURIComponent(id)}`
    }).then(res => {
        if (res.redirected) {
            window.location.href = res.url;
        } else {
            alert("Xoá không thành công.");
        }
    }).catch(() => alert("Đã xảy ra lỗi khi xoá phương tiện."));
}

export function initVehicleTab() {
    document.getElementById('vehicleCount').value = 1;
    generateVehicleFormsFromInput();

    document.getElementById("selectAllVehicles").addEventListener("change", function () {
        const isChecked = this.checked;
        document.querySelectorAll('.vehicle-checkbox').forEach(cb => cb.checked = isChecked);
    });

    document.getElementById("deleteSelectedVehicles").addEventListener("click", () => {
        const checkboxes = document.querySelectorAll('.vehicle-checkbox:checked');
        const ids = Array.from(checkboxes).map(cb => cb.value);
        if (ids.length === 0) {
            alert("Vui lòng chọn ít nhất một phương tiện để xoá.");
            return;
        }
        if (!confirm("Bạn có chắc chắn muốn xoá các phương tiện đã chọn?"))
            return;

        const params = new URLSearchParams();
        ids.forEach(id => params.append('ids', id));
        fetch('/gomap/admin/dashboard/vehicles/delete', {
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

export function loadVehiclePage(page) {
    const size = 10; // hoặc lấy từ input nếu cho phép người dùng chọn số dòng/trang

    fetch(`/gomap/admin/dashboard/vehicles/page?page=${page}&size=${size}`)
            .then(response => {
                if (!response.ok)
                    throw new Error('Lỗi khi tải dữ liệu phương tiện');
                return response.json();
            })
            .then(data => {
                updateVehicleTable(data.vehicles);
                updateVehiclePagination(data.currentPage, data.totalPages);
            })
            .catch(error => {
                console.error('Lỗi khi tải trang phương tiện:', error);
                alert('Không thể tải dữ liệu phương tiện.');
            });
}

function updateVehicleTable(vehicles) {
    const tbody = document.getElementById('vehicleTableBody');
    tbody.innerHTML = '';

    vehicles.forEach(vehicle => {
        const row = document.createElement('tr');
        row.classList.add('text-center');

        row.innerHTML = `
            <td class="no-edit">
                <input type="checkbox" class="vehicle-checkbox row-checkbox" value="${vehicle.id}">
            </td>
            <td class="no-edit">${vehicle.id}</td>
            <td>
                <span class="cell-text">${vehicle.licensePlate || ''}</span>
                <input name="licensePlate" class="cell-input d-none form-control form-control-sm" type="text" value="${vehicle.licensePlate || ''}">
            </td>
            <td>
                <span class="cell-text">${vehicle.vehicleType || ''}</span>
                <input name="vehicleType" class="cell-input d-none form-control form-control-sm" type="text" value="${vehicle.vehicleType || ''}">
            </td>
            <td>
                <span class="cell-text">${vehicle.driver || ''}</span>
                <input name="driver" class="cell-input d-none form-control form-control-sm" type="text" value="${vehicle.driver || ''}">
            </td>
            <td>
                <span class="cell-text">${vehicle.capacity != null ? vehicle.capacity : ''}</span>
                <input name="capacity" class="cell-input d-none form-control form-control-sm" type="number" value="${vehicle.capacity != null ? vehicle.capacity : ''}">
            </td>
            <td>
                <span class="cell-text">${vehicle.latitude != null ? vehicle.latitude : ''}</span>
                <input name="latitude" class="cell-input d-none form-control form-control-sm" type="number" step="any" value="${vehicle.latitude != null ? vehicle.latitude : ''}">
            </td>
            <td>
                <span class="cell-text">${vehicle.longitude != null ? vehicle.longitude : ''}</span>
                <input name="longitude" class="cell-input d-none form-control form-control-sm" type="number" step="any" value="${vehicle.longitude != null ? vehicle.longitude : ''}">
            </td>
            <td>
                <span class="cell-text">${vehicle.status || ''}</span>
                <input name="status" class="cell-input d-none form-control form-control-sm" type="text" value="${vehicle.status || ''}">
            </td>
            <td class="no-edit">${vehicle.createdAt ? formatDate(vehicle.createdAt, 'yyyy-MM-dd HH:mm:ss') : ''}</td>
            <td>
                <span class="cell-text">${vehicle.routeId != null ? vehicle.routeId : 'N/A'}</span>
                <select class="cell-input d-none form-control" name="routeId">
                    ${buildOptions(listRouteNames, vehicle.routeId)}
                </select>
            </td>
            <td class="status-text">
                <span class="cell-text">${vehicle.active}</span>
                <select name="active" class="cell-input d-none form-select form-select-sm">
                    <option value="true" ${vehicle.active ? 'selected' : ''}>Đang hoạt động</option>
                    <option value="false" ${!vehicle.active ? 'selected' : ''}>Không hoạt động</option>
                </select>
            </td>
        `;


        const actionTd = createActionColumn(vehicle, enterEditMode, cancelEditMode, saveEdit, vehicleHeaders);
        row.appendChild(actionTd);

        tbody.appendChild(row);
    });
}


function updateVehiclePagination(currentPage, totalPages) {
    const pagination = document.getElementById('vehiclePagination');
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
        a.onclick = () => loadVehiclePage(page);

        li.appendChild(a);
        return li;
    };

    pagination.appendChild(createPageItem('Trước', currentPage - 1, currentPage === 0));

    for (let i = 0; i < totalPages; i++) {
        pagination.appendChild(createPageItem(i + 1, i, false, i === currentPage));
    }

    pagination.appendChild(createPageItem('Sau', currentPage + 1, currentPage >= totalPages - 1));
}
