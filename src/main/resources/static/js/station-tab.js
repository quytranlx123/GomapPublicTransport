// station-tab.js
import { createActionColumn } from './create-action-column.js';
import { enterEditMode } from './edit-mode.js'
import { cancelEditMode } from './cancel-mode.js'
import { saveEdit } from './save-edit.js'
import { formatDate } from './common.js'
import { stationHeaders } from './table-header-variable.js'

// Thay đổi số lượng trạm
export function changeStationCount(delta) {
    const input = document.getElementById('stationCount');
    let value = parseInt(input.value) || 1;
    value += delta;
    if (value < 1)
        value = 1;
    input.value = value;
    generateStationFormsFromInput();
}

// Tạo form trạm với name chuẩn DTO: adminStationDTOs[i].field
export function generateStationFormsFromInput() {
    const count = parseInt(document.getElementById('stationCount').value) || 1;
    const container = document.getElementById('stationFormsContainer');
    const template = document.getElementById('stationFormTemplate');
    container.innerHTML = '';
    for (let i = 0; i < count; i++) {
        const clone = template.content.cloneNode(true);
        clone.querySelector('.form-index').textContent = i + 1;
        const inputs = clone.querySelectorAll('[name]');
        inputs.forEach(input => {
            const baseName = input.getAttribute('name');
            input.name = `adminStationDTOs[${i}].${baseName}`;
        });
        container.appendChild(clone);
    }
}

// Xóa một trạm
export function deleteStation(button) {
    const id = button.getAttribute("data-id");
    if (!confirm("Bạn có chắc chắn muốn xoá trạm này?"))
        return;
    fetch('/gomap/admin/dashboard/stations/delete', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: `ids=${encodeURIComponent(id)}`
    }).then(res => {
        if (res.redirected) {
            window.location.href = res.url;
        } else {
            alert("Xoá không thành công.");
        }
    }).catch(() => alert("Đã xảy ra lỗi khi xoá trạm."));
}

// Xóa nhiều trạm
export function deleteSelectedStations() {
    const checkboxes = document.querySelectorAll('.station-checkbox:checked');
    const ids = Array.from(checkboxes).map(cb => cb.value);

    if (ids.length === 0) {
        alert("Vui lòng chọn ít nhất một trạm để xoá.");
        return;
    }
    if (!confirm("Bạn có chắc chắn muốn xoá các trạm đã chọn?"))
        return;

    const params = new URLSearchParams();
    ids.forEach(id => params.append('ids', id));
    fetch('/gomap/admin/dashboard/stations/delete', {
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

// Init các event listener cho tab trạm
export function initStationTab() {
    const stationCountInput = document.getElementById('stationCount');
    if (stationCountInput) {
        stationCountInput.value = 1;
        generateStationFormsFromInput();
    }

    const selectAll = document.getElementById("selectAllStations");
    if (selectAll) {
        selectAll.addEventListener("change", function () {
            const checked = this.checked;
            document.querySelectorAll('.station-checkbox').forEach(cb => cb.checked = checked);
        });
    }

    const deleteSelectedBtn = document.getElementById("deleteSelectedStations");
    if (deleteSelectedBtn) {
        deleteSelectedBtn.addEventListener("click", deleteSelectedStations);
    }
}
export function loadStationPage(page) {
    fetch(`/gomap/admin/dashboard/stations/page?page=${page}&size=10`)
            .then(res => res.json())
            .then(data => {
                updateStationTable(data.stations);
                updateStationPagination(data.currentPage, data.totalPages);
            })
            .catch(err => console.error("Lỗi khi tải danh sách trạm:", err));
}

function updateStationTable(stations) {
    const tbody = document.getElementById('stationTableBody');
    tbody.innerHTML = '';

    stations.forEach(station => {
        const row = document.createElement('tr');
        row.classList.add('text-center');

        row.innerHTML = `
            <td class="no-edit">
                <input type="checkbox" class="row-checkbox station-checkbox" value="${station.id}">
            </td>
            <td class="no-edit">${station.id}</td>
            <td>
                <span class="cell-text">${station.name}</span>
                <input type="text" class="cell-input d-none form-control" name="name" value="${station.name}">
            </td>
            <td>
                <span class="cell-text">${station.latitude}</span>
                <input type="number" class="cell-input d-none form-control" name="latitude" value="${station.latitude}">
            </td>
            <td>
                <span class="cell-text">${station.longitude}</span>
                <input type="number" class="cell-input d-none form-control" name="longitude" value="${station.longitude}">
            </td>
            <td>
                <span class="cell-text">${station.address}</span>
                <input type="text" class="cell-input d-none form-control" name="address" value="${station.address}">
            </td>
            <td class="no-edit">${formatDateTime(station.createdAt)}</td>
        `;


        const actionTd = createActionColumn(station, enterEditMode, cancelEditMode, saveEdit, stationHeaders);
        row.appendChild(actionTd);

        tbody.appendChild(row);
    });
}


function updateStationPagination(currentPage, totalPages) {
    const pagination = document.getElementById('stationPagination');
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
        a.onclick = () => loadStationPage(page);

        li.appendChild(a);
        return li;
    };

    const maxVisiblePages = 7; // số trang tối đa hiển thị trên thanh phân trang
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
            // Dấu "..."
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
            // Dấu "..."
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
    const pad = n => n.toString().padStart(2, '0');
    return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())} ` +
            `${pad(date.getHours())}:${pad(date.getMinutes())}:${pad(date.getSeconds())}`;
}
