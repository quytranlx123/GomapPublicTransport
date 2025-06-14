//cancel-mode.js
export function cancelEditMode(tr) {
    const editBtn = tr.querySelector('.edit-btn');
    const cancelBtn = tr.querySelector('.cancel-btn');
    const statusCell = tr.querySelector('.status-cell');
    const saveBtn = tr.querySelector('.save-btn');

    if (editBtn)
        editBtn.classList.remove('d-none');
    if (cancelBtn)
        cancelBtn.classList.add('d-none');
    if (saveBtn)
        saveBtn.classList.add('d-none');

    // Hiển thị lại text, ẩn input/select
    tr.querySelectorAll('td').forEach(td => {
        const text = td.querySelector('.cell-text');
        const input = td.querySelector('.cell-input');

        if (text)
            text.classList.remove('d-none');
        if (input)
            input.classList.add('d-none');
    });

    // ✅ Bỏ check khi hủy
    const checkbox = tr.querySelector('input[type="checkbox"].row-checkbox');
    if (checkbox)
        checkbox.checked = false;

    // ✅ Gỡ class đang chỉnh sửa
    tr.classList.remove('editing');
}
