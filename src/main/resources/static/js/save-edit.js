import { cancelEditMode } from './cancel-mode.js';
import { formatDate, formatTime, buildOptions } from './common.js';
import { showOverlayWithProgress } from './loading.js';

export async function saveEdit(tr, entity, headers) {
    const notyf = new Notyf();
    const formData = new FormData();

    const fileInputs = tr.querySelectorAll('input[type="file"]');
    fileInputs.forEach(input => {
        if (input.files.length > 0) {
            formData.append(input.name, input.files[0]);
        }
    });

    const inputs = tr.querySelectorAll('.cell-input');
    inputs.forEach(input => {
        const name = input.name || input.dataset.column;
        if (name && input.type !== 'file') {
            formData.append(name, input.value);
        }
    });

    formData.append('id', entity.id);

    try {
        await showOverlayWithProgress(async () => {
            const response = await fetch(headers.updateUrl, {
                method: 'POST',
                body: formData,
            });

            if (!response.ok) {
                throw new Error(`Lỗi mạng: ${response.status}`);
            }

            const result = await response.json();
            updateRowView(tr, result, headers);
            notyf.success('Cập nhật thành công');
            cancelEditMode(tr);
        });
    } catch (error) {
        notyf.error('Lỗi khi cập nhật: ' + error.message);
    }
}


const optionMap = {
    notificationId: listNotificationNames,
    userId: listUserFullnames,
    routeId: listRouteNames,
    stationId: listStationNames,
    vehicleId: listVehicleNames,
};

function updateRowView(tr, result, headers) {
    const {fieldMappings, formatters = {}} = headers;

    for (const field in fieldMappings) {
        const colIndex = fieldMappings[field];
        const td = tr.querySelector(`td:nth-child(${colIndex})`);
        if (!td)
            continue;

        const value = result[field];
        const formatted = formatters[field] ? formatters[field](value) : value;

        if (field === 'avatar' || field === 'image') {
            const img = td.querySelector('img');
            if (img)
                img.src = value;
        } else {
            // Cập nhật .cell-text
            const span = td.querySelector('.cell-text');
            if (span)
                span.textContent = formatted;

            // Cập nhật giá trị mặc định trong .cell-input
            const input = td.querySelector('.cell-input');
            if (input) {
                if (input.tagName === 'SELECT' && optionMap && optionMap[field]) {
                    input.innerHTML = buildOptions(optionMap[field], value);
                } else if (input.type === 'time') {
                    input.value = formatTime(value);
                } else {
                    input.value = value;
                }
            }
        }
    }
}







