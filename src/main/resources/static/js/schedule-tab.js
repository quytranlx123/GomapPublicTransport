// schedule-tab.js
import { createActionColumn } from './create-action-column.js';
import { enterEditMode } from './edit-mode.js'
import { cancelEditMode } from './cancel-mode.js'
import { saveEdit } from './save-edit.js'
import { formatDate, formatTime, buildOptions } from './common.js'
import { scheduleHeaders } from './table-header-variable.js'

// Thay đổi số lượng lịch trình
export function changeScheduleCount(delta) {
    const input = document.getElementById('scheduleCount');
    let value = parseInt(input.value) || 1;
    value += delta;
    if (value < 1)
        value = 1;
    input.value = value;
    generateScheduleFormsFromInput();
}

// Tạo form lịch trình với name: adminScheduleDTOs[i].field
export function generateScheduleFormsFromInput() {
    const count = parseInt(document.getElementById('scheduleCount').value) || 1;
    const container = document.getElementById('scheduleFormsContainer');
    const template = document.getElementById('scheduleFormTemplate');
    container.innerHTML = '';
    for (let i = 0; i < count; i++) {
        const clone = template.content.cloneNode(true);
        clone.querySelector('.form-index').textContent = i + 1;
        const inputs = clone.querySelectorAll('[name]');
        inputs.forEach(input => {
            const baseName = input.getAttribute('name');
            input.name = `adminScheduleDTOs[${i}].${baseName.split('.').pop()}`;
        });
        container.appendChild(clone);
    }
}

// Xóa một lịch trình
export function deleteSchedule(button) {
    const id = button.getAttribute("data-id");
    if (!confirm("Bạn có chắc chắn muốn xóa lịch trình này?"))
        return;
    fetch('/gomap/admin/dashboard/schedules/delete', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: `ids=${encodeURIComponent(id)}`
    }).then(res => {
        if (res.redirected) {
            window.location.href = res.url;
        } else {
            alert("Xoá không thành công.");
        }
    }).catch(() => alert("Đã xảy ra lỗi khi xoá lịch trình."));
}

// Xóa nhiều lịch trình
export function deleteSelectedSchedules() {
    const ids = Array.from(document.querySelectorAll(".schedule-checkbox:checked")).map(cb => cb.value);
    if (ids.length === 0) {
        alert("Vui lòng chọn ít nhất một lịch trình để xoá.");
        return;
    }
    if (!confirm("Bạn có chắc chắn muốn xoá các lịch trình đã chọn?"))
        return;

    const params = new URLSearchParams();
    ids.forEach(id => params.append('ids', id));
    fetch('/gomap/admin/dashboard/schedules/delete', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: params.toString()
    }).then(res => {
        if (res.redirected) {
            window.location.href = res.url;
        } else {
            alert("Xoá không thành công.");
        }
    }).catch(() => alert("Đã xảy ra lỗi khi xoá lịch trình."));
}

// Init các event listener cho tab lịch trình
export function initScheduleTab() {
    const scheduleCountInput = document.getElementById('scheduleCount');
    if (scheduleCountInput) {
        scheduleCountInput.value = 1;
        generateScheduleFormsFromInput();
    }

    const selectAll = document.getElementById("selectAllSchedules");
    if (selectAll) {
        selectAll.addEventListener("change", function () {
            const checked = this.checked;
            document.querySelectorAll(".schedule-checkbox").forEach(cb => cb.checked = checked);
        });
    }

    const deleteSelectedBtn = document.getElementById("deleteSelectedSchedules");
    if (deleteSelectedBtn) {
        deleteSelectedBtn.addEventListener("click", deleteSelectedSchedules);
    }
}
export function loadSchedulePage(page) {
    fetch(`/gomap/admin/dashboard/schedules/page?page=${page}&size=10`)
            .then(res => res.json())
            .then(data => {
                updateScheduleTable(data.schedules);
                updateSchedulePagination(data.currentPage, data.totalPages);
            })
            .catch(err => console.error("Lỗi khi tải danh sách lịch trình:", err));
}

function updateScheduleTable(schedules) {
    const tbody = document.getElementById('scheduleTableBody');
    tbody.innerHTML = '';

    schedules.forEach(s => {
        const row = document.createElement('tr');
        row.classList.add('text-center');

        row.innerHTML = `
            <td class="no-edit">
                <input type="checkbox" class="schedule-checkbox row-checkbox" value="${s.id}">
            </td>
            <td class="no-edit">${s.id}</td>
            <td>
                <span class="cell-text">${formatTime(s.departureTime)}</span>
                <input type="time" class="cell-input d-none" name="departureTime" value="${formatTime(s.departureTime)}" />
            </td>
            <td>
                <span class="cell-text">${formatTime(s.arrivalTime)}</span>
                <input type="time" class="cell-input d-none" name="arrivalTime" value="${formatTime(s.arrivalTime)}" />
            </td>
            <td>
                <span class="cell-text">${s.vehicleId || 'N/A'}</span>
                <select class="cell-input d-none form-control" name="vehicleId">
                    ${buildOptions(listVehicleNames, s.vehicleId)}
                </select>
            </td>
        `;


        const actionTd = createActionColumn(s, enterEditMode, cancelEditMode, saveEdit, scheduleHeaders);
        row.appendChild(actionTd);

        tbody.appendChild(row);
    });
}

function updateSchedulePagination(currentPage, totalPages) {
    const pagination = document.getElementById('schedulePagination');
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
        a.onclick = () => loadSchedulePage(page);

        li.appendChild(a);
        return li;
    };

    pagination.appendChild(createPageItem('Trước', currentPage - 1, currentPage === 0));

    for (let i = 0; i < totalPages; i++) {
        pagination.appendChild(createPageItem(i + 1, i, false, i === currentPage));
    }

    pagination.appendChild(createPageItem('Sau', currentPage + 1, currentPage >= totalPages - 1));
}