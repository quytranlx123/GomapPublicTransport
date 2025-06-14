// tabs.js
import { createActionColumn }
from './create-action-column.js';
import { enterEditMode }
from './edit-mode.js';
import { cancelEditMode }
from './cancel-mode.js';
import { saveEdit }
from './save-edit.js';
import {
userHeaders,
        vehicleHeaders,
        routeHeaders,
        stationHeaders,
        scheduleHeaders,
        notificationHeaders,
        trafficReportHeaders,
        favoriteRouteHeaders,
        userNotificationHeaders,
        routeStationHeaders
        } from './table-header-variable.js';



export function initTabs() {
    document.querySelectorAll('.nav-tabs .nav-link').forEach(tab => {
        tab.addEventListener('click', function (e) {
            e.preventDefault();
            document.querySelectorAll('.nav-tabs .nav-link').forEach(t => t.classList.remove('active'));
            this.classList.add('active');
            document.querySelectorAll('.tab-pane').forEach(content => content.classList.remove('active'));
            const targetSelector = this.getAttribute('href');
            const target = document.querySelector(targetSelector);
            if (target) {
                target.classList.add('active');
            }
        });
    });


//    double click to copy
    document.addEventListener('dblclick', event => {
        const notyf = new Notyf();

        const target = event.target;

        // Chỉ xử lý nếu đang double click bên trong một bảng
        const table = target.closest('table');
        if (!table)
            return;

        // Bỏ qua nếu click vào input, checkbox, button hoặc nút chỉnh sửa
        if (target.matches('input, button, .edit-btn, .save-btn, .cancel-btn'))
            return;

        // Lấy text nếu có
        const text = target.innerText?.trim();
        if (!text)
            return;

        // Ghi vào clipboard
        navigator.clipboard.writeText(text)
                .then(() => notyf.success(`Đã copy: "${text}"`))
                .catch(() => notyf.error('Có lỗi xảy ra!'));

        // Xóa vùng chọn nếu có
        const selection = window.getSelection();
        if (selection)
            selection.removeAllRanges();
    });




//tích chọn khi bấm vào hàng
    document.body.addEventListener('click', (event) => {
        // ❌ Bỏ qua nếu click vào nút thao tác
        if (event.target.closest('.edit-btn, .cancel-btn, .save-btn'))
            return;

        // Tìm dòng <tr>
        let tr = event.target;
        while (tr && tr.tagName !== 'TR') {
            tr = tr.parentElement;
        }
        if (!tr || tr.classList.contains('editing'))
            return;

        const checkbox = tr.querySelector('input[type="checkbox"].row-checkbox');
        if (!checkbox)
            return;
        if (event.target === checkbox)
            return;

        // ✅ Toggle checkbox nếu click dòng
        checkbox.checked = !checkbox.checked;
    });


    document.querySelector('tbody').addEventListener('click', (event) => {
        if (event.target.classList.contains('edit-btn')) {
            enterEditMode(event.target.closest('tr'));
        } else if (event.target.classList.contains('cancel-btn')) {
            cancelEditMode(event.target.closest('tr'));
        } else if (event.target.classList.contains('save-btn')) {
            const tr = event.target.closest('tr');
            const entity = {id: tr.dataset.id};

            // Xác định tab hiện tại
            const activeTab = document.querySelector('.nav-tabs .nav-link.active');
            const tabId = activeTab?.getAttribute('href');

            // Chọn headers tương ứng
            let headers = null;
            switch (tabId) {
                case '#user':
                    headers = userHeaders;
                    break;
                case '#vehicle':
                    headers = vehicleHeaders;
                    break;
                case '#route':
                    headers = routeHeaders;
                    break;
                case '#station':
                    headers = stationHeaders;
                    break;
                case '#schedule':
                    headers = scheduleHeaders;
                    break;
                case '#notification':
                    headers = notificationHeaders;
                    break;
                case '#traffic-report':
                    headers = trafficReportHeaders;
                    break;
                case '#favorite-route':
                    headers = favoriteRouteHeaders;
                    break;
                case '#user-notification':
                    headers = userNotificationHeaders;
                    break;
                case '#route-station':
                    headers = routeStationHeaders;
                    break;
                default:
                    console.error('Không tìm thấy headers phù hợp cho tab:', tabId);
                    return;
            }

            saveEdit(tr, entity, headers);
        }
    });
}