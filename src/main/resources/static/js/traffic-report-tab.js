// traffic-report-tab.js
import { createActionColumn } from './create-action-column.js';
import { enterEditMode } from './edit-mode.js'
import { cancelEditMode } from './cancel-mode.js'
import { saveEdit } from './save-edit.js'
import { formatDate, buildOptions } from './common.js'
import { trafficReportHeaders } from './table-header-variable.js'

export function changeTrafficReportCount(delta) {
    const input = document.getElementById('trafficReportCount');
    let value = parseInt(input.value) || 1;
    value = Math.max(1, value + delta);
    input.value = value;
    generateTrafficReportFormsFromInput();
}

export function generateTrafficReportFormsFromInput() {
    const count = parseInt(document.getElementById('trafficReportCount').value) || 1;
    const container = document.getElementById('trafficReportFormsContainer');
    const template = document.getElementById('trafficReportFormTemplate');
    container.innerHTML = '';

    for (let i = 0; i < count; i++) {
        const clone = template.content.cloneNode(true);
        clone.querySelector('.form-index').textContent = i + 1;
        clone.querySelectorAll('[name]').forEach(el => {
            const baseName = el.getAttribute('name');
            el.name = `adminTrafficReportDTOs[${i}].${baseName}`;
        });
        container.appendChild(clone);
    }
}

export function deleteTrafficReport(button) {
    const id = button.getAttribute("data-id");
    if (!confirm("Bạn có chắc chắn muốn xoá báo cáo giao thông này?"))
        return;

    fetch('/gomap/admin/dashboard/traffic-reports/delete', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: `ids=${encodeURIComponent(id)}`
    }).then(res => {
        if (res.redirected) {
            window.location.href = res.url;
        } else {
            alert("Xoá không thành công.");
        }
    }).catch(() => alert("Đã xảy ra lỗi khi xoá."));
}

export function initTrafficReportTab() {
    document.getElementById('trafficReportCount').value = 1;
    generateTrafficReportFormsFromInput();

    document.getElementById("selectAllTrafficReports").addEventListener("change", function () {
        const checked = this.checked;
        document.querySelectorAll(".traffic-report-checkbox").forEach(cb => cb.checked = checked);
    });

    document.getElementById("deleteSelectedTrafficReports").addEventListener("click", () => {
        const ids = Array.from(document.querySelectorAll(".traffic-report-checkbox:checked")).map(cb => cb.value);
        if (ids.length === 0) {
            alert("Vui lòng chọn ít nhất một báo cáo để xoá.");
            return;
        }
        if (!confirm("Bạn có chắc chắn muốn xoá các báo cáo đã chọn?"))
            return;

        const params = new URLSearchParams();
        ids.forEach(id => params.append('ids', id));
        fetch('/gomap/admin/dashboard/traffic-reports/delete', {
            method: 'POST',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            body: params.toString()
        }).then(res => {
            if (res.redirected) {
                window.location.href = res.url;
            } else {
                alert("Xoá không thành công.");
            }
        }).catch(() => alert("Đã xảy ra lỗi khi xoá báo cáo giao thông."));
    });
}


export function loadTrafficReportPage(page) {
    fetch(`/gomap/admin/dashboard/traffic-reports/page?page=${page}&size=10`)
            .then(res => res.json())
            .then(data => {
                updateTrafficReportTable(data.trafficReports);
                updateTrafficReportPagination(data.currentPage, data.totalPages);
            })
            .catch(err => console.error("Lỗi khi tải trang báo cáo giao thông:", err));
}

function updateTrafficReportTable(trafficReports) {
    const tbody = document.getElementById('trafficReportTableBody');
    tbody.innerHTML = '';

    trafficReports.forEach(report => {
        const row = document.createElement('tr');
        row.classList.add('text-center');

        row.innerHTML = `
            <td class="no-edit"><input type="checkbox" class="traffic-report-checkbox row-checkbox" value="${report.id}"></td>
            <td class="no-edit">${report.id}</td>
            <td>
                <span class="cell-text">${report.title}</span>
                <input name="title" class="cell-input d-none form-control form-control-sm" type="text" value="${report.title}">
            </td>
            <td>
                <span class="cell-text">${report.address}</span>
                <input name="address" class="cell-input d-none form-control form-control-sm" type="text" value="${report.address}">
            </td>
            <td>
                <span class="cell-text">${report.latitude}</span>
                <input name="latitude" class="cell-input d-none form-control form-control-sm" type="text" value="${report.latitude}">
            </td>
            <td>
                <span class="cell-text">${report.longitude}</span>
                <input name="longitude" class="cell-input d-none form-control form-control-sm" type="text" value="${report.longitude}">
            </td>
            <td>
                <img src="${report.image}" alt="Image" class="cell-text rounded-circle border" style="width: 48px; height: 48px; object-fit: cover;">
                <input name="imageFile" type="file" class="cell-input d-none form-control form-control-sm" accept="image/*">
            </td>
            <td>
                <span class="cell-text">${report.description}</span>
                <textarea name="description" class="cell-input d-none form-control form-control-sm">${report.description}</textarea>
            </td>
            <td class="no-edit">${formatDate(report.createdAt, 'yyyy-MM-dd HH:mm:ss')}</td>
        
            <td>
                <span class="cell-text">${report.verified}</span>
                <select name="verified" class="cell-input d-none form-select form-select-sm">
                    <option value="true" ${report.verified ? 'selected' : ''}>Đã xác thực</option>
                    <option value="false" ${!report.verified ? 'selected' : ''}>Chưa xác thực</option>
                </select>
            </td>

            <td>
                <span class="cell-text">${report.userId !== null ? report.userId : 'N/A'}</span>
                <select class="cell-input d-none form-control" name="userId">
                    ${buildOptions(listUserFullnames, report.userId)}
                </select>
            </td>
        
           <td>
                <span class="cell-text">${report.type}</span>
                <select name="type" class="cell-input d-none form-select form-select-sm">
                  <option value="REPORT" th:selected="${report.type} == 'REPORT'">Báo cáo</option>
                  <option value="RATING" th:selected="${report.type} == 'RATING'">Đánh giá dịch vụ</option>
                </select>
            </td>

        `;



        const actionTd = createActionColumn(report, enterEditMode, cancelEditMode, saveEdit, trafficReportHeaders);
        row.appendChild(actionTd);

        tbody.appendChild(row);
    });
}

function updateTrafficReportPagination(currentPage, totalPages) {
    const pagination = document.getElementById('trafficReportPagination');
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
        a.onclick = () => loadTrafficReportPage(page);

        li.appendChild(a);
        return li;
    };

    pagination.appendChild(createPageItem('Trước', currentPage - 1, currentPage === 0));

    for (let i = 0; i < totalPages; i++) {
        pagination.appendChild(createPageItem(i + 1, i, false, i === currentPage));
    }

    pagination.appendChild(createPageItem('Sau', currentPage + 1, currentPage >= totalPages - 1));
}

