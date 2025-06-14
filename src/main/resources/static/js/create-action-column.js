export function createActionColumn(entity, onEditClick = null, onCancelClick = null, onSaveClick = null, headers) {
    const td = document.createElement('td');
    console.log(headers.updateUrl);
    if (!headers) {
        throw new Error('Headers is undefined in createActionColumn');
    }
    td.className = 'no-edit action-button';
    td.style.width = '80px';
    td.style.minWidth = '80px';
    td.style.maxWidth = '80px';
    td.style.height = '50px';
    td.style.padding = '0';

    // Nút Sửa
    const editButton = document.createElement('button');
    editButton.type = 'button';
    editButton.className = 'btn btn-primary btn-sm rounded-0 edit-btn';
    editButton.style.width = '100%';
    editButton.style.height = '100%';
    editButton.style.display = 'block';
    editButton.style.borderRadius = '0';
    editButton.style.padding = '0';
    editButton.style.whiteSpace = 'nowrap';
    editButton.setAttribute('title', 'Sửa');
    editButton.dataset.id = entity.id;
    if (typeof onEditClick === 'function') {
        editButton.addEventListener('click', (e) => {
            const tr = e.target.closest('tr');
            onEditClick(tr);
        });
    }
    editButton.innerHTML = `<i class="bi bi-pencil-square me-1"></i> Sửa`;

    // Nút Lưu (ẩn mặc định)
    const saveButton = document.createElement('button');
    saveButton.type = 'button';
    saveButton.className = 'btn btn-success btn-sm rounded-0 save-btn d-none';
    saveButton.style.width = '100%';
    saveButton.style.height = '50%';
    saveButton.style.display = 'block';
    saveButton.style.borderRadius = '0';
    saveButton.style.padding = '0';
    saveButton.style.whiteSpace = 'nowrap';
    saveButton.setAttribute('title', 'Lưu');
    saveButton.dataset.id = entity.id;

    if (typeof onSaveClick === 'function') {
        saveButton.addEventListener('click', (e) => {
            const tr = e.target.closest('tr');
            console.log("Headers nhận vào:", headers);
            onSaveClick(tr, entity, headers);  // truyền entity luôn

        });
    }

    saveButton.innerHTML = `<i class="bi bi-check-circle me-1"></i> Lưu`;

    // Nút Hủy (ẩn mặc định)
    const cancelButton = document.createElement('button');
    cancelButton.type = 'button';
    cancelButton.className = 'btn btn-secondary btn-sm rounded-0 cancel-btn d-none';
    cancelButton.style.width = '100%';
    cancelButton.style.height = '50%';
    cancelButton.style.display = 'block';
    cancelButton.style.borderRadius = '0';
    cancelButton.style.padding = '0';
    cancelButton.style.whiteSpace = 'nowrap';
    cancelButton.setAttribute('title', 'Hủy');
    cancelButton.dataset.id = entity.id;
    if (typeof onCancelClick === 'function') {
        cancelButton.addEventListener('click', (e) => {
            const tr = e.target.closest('tr');
            onCancelClick(tr);
        });
    }
    cancelButton.innerHTML = `<i class="bi bi-x-circle me-1"></i> Hủy`;

    // Khi edit: hiển thị Lưu + Hủy, ẩn Sửa
    // Khi cancel/save: ẩn Lưu + Hủy, hiện Sửa
    td.appendChild(editButton);
    td.appendChild(saveButton);
    td.appendChild(cancelButton);

    return td;
}
