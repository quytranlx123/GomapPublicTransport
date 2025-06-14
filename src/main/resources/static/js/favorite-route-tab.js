
import { createActionColumn } from './create-action-column.js';
import { enterEditMode } from './edit-mode.js'
import { cancelEditMode } from './cancel-mode.js'
import { saveEdit } from './save-edit.js'
import { formatDate } from './common.js'
import { favoriteRouteHeaders } from './table-header-variable.js'
import { buildOptions } from './common.js'

export function changeFavoriteRouteCount(delta) {
    const input = document.getElementById('favoriteRouteCount');
    let value = parseInt(input.value) || 1;
    value += delta;
    if (value < 1)
        value = 1;
    input.value = value;
    generateFavoriteRouteFormsFromInput();
}

function generateFavoriteRouteFormsFromInput() {
    const count = parseInt(document.getElementById('favoriteRouteCount').value) || 1;
    const container = document.getElementById('favoriteRouteFormsContainer');
    const template = document.getElementById('favoriteRouteFormTemplate');
    container.innerHTML = '';
    for (let i = 0; i < count; i++) {
        const clone = template.content.cloneNode(true);
        clone.querySelector('.form-index').textContent = i + 1;

        const routeSelect = clone.querySelector('.route-select');
        const userSelect = clone.querySelector('.user-select');
        routeSelect.name = `adminFavoriteRouteDTOs[${i}].routeId`;
        userSelect.name = `adminFavoriteRouteDTOs[${i}].userId`;

        container.appendChild(clone);
    }
}

export function deleteFavoriteRoute(button) {
    const id = button.getAttribute("data-id");
    if (!confirm("Bạn có chắc chắn muốn xoá tuyến yêu thích này?"))
        return;
    fetch('/gomap/admin/dashboard/favorite-routes/delete', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: `ids=${id}`
    }).then(res => {
        if (res.redirected) {
            window.location.href = res.url;
        } else {
            alert("Xoá không thành công.");
        }
    });
}

export function initFavoriteRouteTab() {
    const countInput = document.getElementById('favoriteRouteCount');
    if (countInput) {
        countInput.value = 1;
        generateFavoriteRouteFormsFromInput();
    }

    const selectAll = document.getElementById("selectAllFavoriteRoutes");
    if (selectAll) {
        selectAll.addEventListener("change", function () {
            const isChecked = this.checked;
            document.querySelectorAll(".favorite-route-checkbox").forEach(cb => cb.checked = isChecked);
        });
    }

    const deleteSelected = document.getElementById("deleteSelectedFavoriteRoutes");
    if (deleteSelected) {
        deleteSelected.addEventListener("click", function () {
            const checkboxes = document.querySelectorAll('.favorite-route-checkbox:checked');
            const ids = Array.from(checkboxes).map(cb => cb.value);
            if (ids.length === 0) {
                alert("Vui lòng chọn ít nhất một tuyến để xoá.");
                return;
            }

            if (!confirm("Bạn có chắc chắn muốn xoá các tuyến đã chọn?"))
                return;

            fetch('/gomap/admin/dashboard/favorite-routes/delete', {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                body: new URLSearchParams(ids.map(id => ['ids', id]))
            }).then(res => {
                if (res.redirected) {
                    window.location.href = res.url;
                } else {
                    alert("Xoá không thành công.");
                }
            });
        });
    }
}
export function loadFavoriteRoutePage(page) {
    fetch(`/gomap/admin/dashboard/favorite-routes/page?page=${page}&size=10`)
            .then(res => res.json())
            .then(data => {
                updateFavoriteRouteTable(data.favoriteRoutes);
                updateFavoriteRoutePagination(data.currentPage, data.totalPages);
            })
            .catch(err => console.error("Lỗi khi tải danh sách tuyến yêu thích:", err));
}

function updateFavoriteRouteTable(favoriteRoutes) {
    const tbody = document.getElementById('favoriteRouteTableBody');
    tbody.innerHTML = '';

    favoriteRoutes.forEach(fr => {
        const row = document.createElement('tr');
        row.classList.add('text-center');

        row.innerHTML = `
            <td class="no-edit">
                <input type="checkbox" class="favorite-route-checkbox row-checkbox" value="${fr.id}">
            </td>
            <td class="no-edit">${fr.id}</td>
            <td>
                <span class="cell-text">${fr.routeId || 'N/A'}</span>
                <select class="cell-input d-none form-control" name="routeId">
                    ${buildOptions(listRouteNames, fr.routeId)}
                </select>
            </td>
            <td>
                <span class="cell-text">${fr.userId || 'N/A'}</span>
                <select class="cell-input d-none form-control" name="userId">
                    ${buildOptions(listUserFullnames, fr.userId)}
                </select>
            </td>
        
            <td class="no-edit">${formatDate(fr.createdAt, 'yyyy-MM-dd HH:mm:ss')}</td>
        `;

        const actionTd = createActionColumn(fr, enterEditMode, cancelEditMode, saveEdit, favoriteRouteHeaders);
        row.appendChild(actionTd);

        tbody.appendChild(row);
    });
}


function updateFavoriteRoutePagination(currentPage, totalPages) {
    const pagination = document.getElementById('favoriteRoutePagination');
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
        a.onclick = () => loadFavoriteRoutePage(page);

        li.appendChild(a);
        return li;
    };

    pagination.appendChild(createPageItem('Trước', currentPage - 1, currentPage === 0));

    for (let i = 0; i < totalPages; i++) {
        pagination.appendChild(createPageItem(i + 1, i, false, i === currentPage));
    }

    pagination.appendChild(createPageItem('Sau', currentPage + 1, currentPage >= totalPages - 1));
}

