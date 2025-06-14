//edit-mode.js
export function enterEditMode(tr) {
    const editBtn = tr.querySelector('.edit-btn');
    const cancelBtn = tr.querySelector('.cancel-btn');
    const saveBtn = tr.querySelector('.save-btn');

    if (editBtn)
        editBtn.classList.add('d-none');
    if (cancelBtn)
        cancelBtn.classList.remove('d-none');
    if (saveBtn)
        saveBtn.classList.remove('d-none');

    // Ẩn text, hiện input/select
    tr.querySelectorAll('td').forEach(td => {
        const text = td.querySelector('.cell-text');
        const input = td.querySelector('.cell-input');

        if (text)
            text.classList.add('d-none');
        if (input)
            input.classList.remove('d-none');
    });

    // Tự động check checkbox nếu có
    const checkbox = tr.querySelector('input[type="checkbox"].row-checkbox');
    if (checkbox)
        checkbox.checked = true;

    // Đánh dấu đang chỉnh sửa
    tr.classList.add('editing');
}
