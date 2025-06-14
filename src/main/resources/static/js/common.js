//yyyy-mm-dd và yyyy-MM-dd HH:mm:ss
export function formatDate(dateStr, format) {
    if (!dateStr)
        return '';
    const date = new Date(dateStr);
    if (isNaN(date))
        return '';

    const pad = (n) => n.toString().padStart(2, '0');

    if (format === 'yyyy-MM-dd') {
        return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())}`;
    } else if (format === 'yyyy-MM-dd HH:mm:ss') {
        return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())} ${pad(date.getHours())}:${pad(date.getMinutes())}:${pad(date.getSeconds())}`;
    }
    return dateStr;
}


//HH:MM
export function formatTime(dateTimeStr) {
    if (!dateTimeStr)
        return '';

    const pad = n => n.toString().padStart(2, '0');

    // Nếu là chuỗi giờ thuần túy: "12:24:00"
    if (/^\d{2}:\d{2}(:\d{2})?$/.test(dateTimeStr)) {
        const [hh, mm] = dateTimeStr.split(':');
        return `${pad(hh)}:${pad(mm)}`;
    }

    // Nếu là ISO string hoặc dạng hợp lệ của Date
    const date = new Date(dateTimeStr);
    if (isNaN(date))
        return '';

    return `${pad(date.getHours())}:${pad(date.getMinutes())}`;
}

export function buildOptions(options, selectedValue) {
    let html = `<option value="" ${selectedValue === '' || selectedValue == null ? 'selected' : ''}>Không chọn</option>`;
    Object.entries(options).forEach(([key, val]) => {
        const selected = key == selectedValue ? 'selected' : '';
        html += `<option value="${key}" ${selected}>${val}</option>`;
    });
    return html;
}