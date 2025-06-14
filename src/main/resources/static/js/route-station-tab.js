import { createActionColumn } from './create-action-column.js';
import { enterEditMode } from './edit-mode.js'
import { cancelEditMode } from './cancel-mode.js'
import { saveEdit } from './save-edit.js'
import { formatDate, buildOptions } from './common.js'
import { routeStationHeaders } from './table-header-variable.js'


export function changeRouteStationCount(delta) {
    const input = document.getElementById('routeStationCount');
    let value = parseInt(input.value) || 1;
    value += delta;
    if (value < 1)
        value = 1;
    input.value = value;
    generateRouteStationFormsFromInput();
}

function generateRouteStationFormsFromInput() {
    const count = parseInt(document.getElementById('routeStationCount').value) || 1;
    const container = document.getElementById('routeStationFormsContainer');
    const template = document.getElementById('routeStationFormTemplate');
    container.innerHTML = '';
    for (let i = 0; i < count; i++) {
        let html = template.innerHTML.replace(/#index#/g, i); // Replace #index# with i
        const wrapper = document.createElement('div');
        wrapper.innerHTML = html;

        wrapper.querySelector('.form-index').textContent = i + 1;
        container.appendChild(wrapper.firstElementChild);
    }
}

export function deleteRouteStation(button) {
    const id = button.getAttribute("data-id");
    if (!confirm("Bạn có chắc chắn muốn xóa trạm tuyến này?"))
        return;

    fetch('/gomap/admin/dashboard/route-stations/delete', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: `ids=${encodeURIComponent(id)}`
    }).then(res => {
        if (res.redirected)
            window.location.href = res.url;
        else
            alert("Xoá không thành công.");
    }).catch(() => alert("Đã xảy ra lỗi khi xoá trạm tuyến."));
}

export function initRouteStationTab() {
    const countInput = document.getElementById('routeStationCount');
    if (countInput) {
        countInput.value = 1;
        generateRouteStationFormsFromInput();
    }

    const selectAll = document.getElementById("selectAllRouteStations");
    if (selectAll) {
        selectAll.addEventListener("change", function () {
            const checked = this.checked;
            document.querySelectorAll(".route-station-checkbox").forEach(cb => cb.checked = checked);
        });
    }

    const deleteSelected = document.getElementById("deleteSelectedRouteStations");
    if (deleteSelected) {
        deleteSelected.addEventListener("click", function () {
            const ids = Array.from(document.querySelectorAll(".route-station-checkbox:checked")).map(cb => cb.value);
            if (ids.length === 0) {
                alert("Vui lòng chọn ít nhất một trạm tuyến để xoá.");
                return;
            }

            if (!confirm("Bạn có chắc chắn muốn xoá các trạm tuyến đã chọn?"))
                return;

            const params = new URLSearchParams();
            ids.forEach(id => params.append('ids', id));

            fetch('/gomap/admin/dashboard/route-stations/delete', {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                body: params.toString()
            }).then(res => {
                if (res.redirected)
                    window.location.href = res.url;
                else
                    alert("Xoá không thành công.");
            }).catch(() => alert("Đã xảy ra lỗi khi xoá trạm tuyến."));
        });
    }
}
export function loadRouteStationPage(page) {
    fetch(`/gomap/admin/dashboard/route-stations/page?page=${page}&size=10`)
            .then(res => res.json())
            .then(data => {
                updateRouteStationTable(data.routeStations);
                updateRouteStationPagination(data.currentPage, data.totalPages);
            })
            .catch(err => console.error("Lỗi khi tải danh sách trạm tuyến:", err));
}

function updateRouteStationTable(routeStations) {
    const tbody = document.getElementById('routeStationTableBody');
    tbody.innerHTML = '';

    routeStations.forEach(rs => {
        const row = document.createElement('tr');
        row.classList.add('text-center');

        row.innerHTML = `
            <td class="no-edit">
                <input type="checkbox" class="route-station-checkbox row-checkbox" value="${rs.id}">
            </td>
        
            <td class="no-edit">${rs.id}</td>
        
            <td>
                <span class="cell-text">${rs.orderStation}</span>
                <input name="orderStation" class="cell-input d-none form-control form-control-sm" type="number" value="${rs.orderStation || 0}">
            </td>
        
            <td>
                <span class="cell-text">${rs.distance}</span>
                <input name="distance" class="cell-input d-none form-control form-control-sm" type="number" value="${rs.distance || 0}">
            </td>
        
            <td>
                <span class="cell-text">${rs.duration}</span>
                <input name="duration" class="cell-input d-none form-control form-control-sm" type="number" value="${rs.duration || 0}">
            </td>
            <td>
                <span class="cell-text">${rs.routeId != null ? rs.routeId : 'N/A'}</span>
                <select class="cell-input d-none form-control" name="routeId">
                    ${buildOptions(listRouteNames, rs.routeId)}
                </select>
            </td>
            <td>
                <span class="cell-text">${rs.stationId != null ? rs.stationId : 'N/A'}</span>
                <select class="cell-input d-none form-control" name="routeId">
                    ${buildOptions(listStationNames, rs.stationId)}
                </select>
            </td>
        `;


        const actionTd = createActionColumn(rs, enterEditMode, cancelEditMode, saveEdit, routeStationHeaders);
        row.appendChild(actionTd);

        tbody.appendChild(row);
    });
}


function updateRouteStationPagination(currentPage, totalPages) {
    const pagination = document.getElementById('routeStationPagination');
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
        a.onclick = () => loadRouteStationPage(page);

        li.appendChild(a);
        return li;
    };

    pagination.appendChild(createPageItem('Trước', currentPage - 1, currentPage === 0));

    for (let i = 0; i < totalPages; i++) {
        pagination.appendChild(createPageItem(i + 1, i, false, i === currentPage));
    }

    pagination.appendChild(createPageItem('Sau', currentPage + 1, currentPage >= totalPages - 1));
}
