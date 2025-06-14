export function initSelectAllCheckboxes() {
    const tabs = [
        {selectAllId: 'selectAllUsers', checkboxClass: 'user-checkbox'},
        {selectAllId: 'selectAllRoutes', checkboxClass: 'route-checkbox'},
        {selectAllId: 'selectAllStations', checkboxClass: 'station-checkbox'},
        {selectAllId: 'selectAllVehicles', checkboxClass: 'vehicle-checkbox'},
        {selectAllId: 'selectAllNotifications', checkboxClass: 'notification-checkbox'},
        {selectAllId: 'selectAllUserNotifications', checkboxClass: 'user-notification-checkbox'},
        {selectAllId: 'selectAllTrafficReports', checkboxClass: 'traffic-report-checkbox'},
        {selectAllId: 'selectAllFavoriteRoutes', checkboxClass: 'favorite-route-checkbox'},
        {selectAllId: 'selectAllSchedules', checkboxClass: 'schedule-checkbox'},
        {selectAllId: 'selectAllRouteStations', checkboxClass: 'route-station-checkbox'}
    ];

    tabs.forEach(tab => {
        const selectAllCheckbox = document.getElementById(tab.selectAllId);
        if (selectAllCheckbox) {
            selectAllCheckbox.addEventListener('change', function () {
                const checkboxes = document.querySelectorAll(`.${tab.checkboxClass}`);
                checkboxes.forEach(checkbox => {
                    checkbox.checked = selectAllCheckbox.checked;
                });
            });
        }
    });
}
