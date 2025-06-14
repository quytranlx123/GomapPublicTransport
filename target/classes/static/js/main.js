//main.js
import { initTabs } from './tabs.js';

import {
changeFavoriteRouteCount,
        deleteFavoriteRoute,
        initFavoriteRouteTab,
        loadFavoriteRoutePage
        } from './favorite-route-tab.js';

import {
changeNotificationCount,
        generateNotificationFormsFromInput,
        deleteNotification,
        initNotificationTab,
        loadNotificationPage
        } from './notification-tab.js';

import {
changeRouteStationCount,
        deleteRouteStation,
        initRouteStationTab,
        loadRouteStationPage
        } from './route-station-tab.js';

import {
changeRouteCount,
        deleteRoute,
        updateRouteNumberHidden,
        initRouteTab,
        loadRoutePage
        } from './route-tab.js';

import { changeScheduleCount,
        deleteSchedule,
        initScheduleTab,
        loadSchedulePage
} from './schedule-tab.js';


import { changeStationCount,
        deleteStation,
        initStationTab,
        loadStationPage
        } from './station-tab.js';

import {
changeTrafficReportCount,
        deleteTrafficReport,
        initTrafficReportTab,
        loadTrafficReportPage
        } from './traffic-report-tab.js';

import {
changeUserNotificationCount,
        deleteUserNotification,
        initUserNotificationTab,
        loadUserNotificationPage
        } from './user-notification-tab.js';

import {
changeUserCount,
        deleteUser,
        initUserTab,
        loadUserPage
        } from './user-tab.js';

import {
changeVehicleCount,
        deleteVehicle,
        initVehicleTab,
        loadVehiclePage
} from './vehicle-tab.js';

import { renderStatisticsCharts } from './statistical.js'

import { initDashboardCharts, changeMonth } from './dashboard-charts.js';

import { initSelectAllCheckboxes } from './select-all.js';
import { initCoordinateValidation } from './validator.js';
import { initSidebar } from './sidebar.js';


document.addEventListener('DOMContentLoaded', () => {
    // Lấy dữ liệu từ biến toàn cục window
    const top5FavoriteRoutesJson = window.top5FavoriteRoutesJson || '[]';
    const vehicleStatusStatsJson = window.vehicleStatusStatsJson || '{}';
    const trafficReportStatsJson = window.trafficReportStatsJson || '{}';

    // Khởi tạo tab trước để DOM tab đã sẵn sàng
    initTabs();

    // Khởi tạo các phần tab cụ thể
    initFavoriteRouteTab();
    initNotificationTab();
    initRouteStationTab();
    initRouteTab();
    initScheduleTab();
    initStationTab();
    initTrafficReportTab();
    initUserNotificationTab();
    initUserTab();
    initVehicleTab();
    initSidebar();

    renderStatisticsCharts();


    loadFavoriteRoutePage(0);
    loadNotificationPage(0);
    loadRouteStationPage(0);
    loadSchedulePage(0);
    loadStationPage(0);
    loadTrafficReportPage(0);
    loadRoutePage(0);
    loadUserNotificationPage(0);
    loadUserPage(0);
    loadVehiclePage(0);


    // Biểu đồ Dashboard
    initDashboardCharts(top5FavoriteRoutesJson, vehicleStatusStatsJson, trafficReportStatsJson);

    // Các tiện ích chung
    initSelectAllCheckboxes();
    initCoordinateValidation();

    // Gán các hàm cần thiết lên window để gọi từ HTML
    window.changeNotificationCount = changeNotificationCount;
    window.deleteNotification = deleteNotification;

    window.changeFavoriteRouteCount = changeFavoriteRouteCount;
    window.deleteFavoriteRoute = deleteFavoriteRoute;

    window.changeRouteStationCount = changeRouteStationCount;
    window.deleteRouteStation = deleteRouteStation;

    window.changeRouteCount = changeRouteCount;
    window.deleteRoute = deleteRoute;
    window.updateRouteNumberHidden = updateRouteNumberHidden;

    window.changeScheduleCount = changeScheduleCount;
    window.deleteSchedule = deleteSchedule;

    window.changeStationCount = changeStationCount;
    window.deleteStation = deleteStation;

    window.changeMonth = changeMonth;

    window.changeTrafficReportCount = changeTrafficReportCount;
    window.deleteTrafficReport = deleteTrafficReport;

    window.changeUserNotificationCount = changeUserNotificationCount;
    window.deleteUserNotification = deleteUserNotification;

    window.changeUserCount = changeUserCount;
    window.deleteUser = deleteUser;

    window.changeVehicleCount = changeVehicleCount;
    window.deleteVehicle = deleteVehicle;

});
