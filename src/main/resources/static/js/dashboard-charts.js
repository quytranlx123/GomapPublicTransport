export function initDashboardCharts(top5FavoriteRoutesJson, vehicleStatusStatsJson, trafficReportStatsJson) {
    // Top 5 tuyến xe yêu thích
    let top5FavoriteRoutes = [];
    try {
        top5FavoriteRoutes = JSON.parse(top5FavoriteRoutesJson);
    } catch (e) {
        console.error("Dữ liệu top5FavoriteRoutes không hợp lệ:", e);
    }
    if (!Array.isArray(top5FavoriteRoutes)) {
        console.error("Dữ liệu top5FavoriteRoutes không phải là mảng");
        return;
    }
    const labels = top5FavoriteRoutes.map(route => route.routeName);
    const data = top5FavoriteRoutes.map(route => route.count);
    const ctx = document.getElementById('top5RouteFavorite').getContext('2d');
    new Chart(ctx, {
        type: 'bar',
        data: {
            labels,
            datasets: [{
                    label: 'Top 5 Tuyến Xe Yêu Thích',
                    data,
                    backgroundColor: ['#FF5733', '#33FF57', '#3357FF', '#FF33A1', '#33FFF0'],
                    borderColor: ['#fff', '#fff', '#fff', '#fff', '#fff'],
                    borderWidth: 1
                }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {display: false},
                tooltip: {
                    callbacks: {
                        label: (tooltipItem) => `${tooltipItem.label}: ${tooltipItem.raw} lượt yêu thích`
                    }
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    title: {display: true, text: 'Lượt yêu thích'}
                },
                x: {
                    title: {display: true, text: 'Tên tuyến'}
                }
            }
        }
    });

    // Thống kê trạng thái phương tiện
    const vehicleStatusStats = JSON.parse(vehicleStatusStatsJson || '{}');
    const statusLabels = Object.keys(vehicleStatusStats);
    const statusData = Object.values(vehicleStatusStats);
    const statusCtx = document.getElementById('vehicleStatusChart').getContext('2d');
    new Chart(statusCtx, {
        type: 'pie',
        data: {
            labels: statusLabels,
            datasets: [{
                    label: 'Trạng thái phương tiện',
                    data: statusData,
                    backgroundColor: ['#36A2EB', '#FF6384'],
                    borderColor: ['#fff', '#fff'],
                    borderWidth: 1
                }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {position: 'top'},
                tooltip: {
                    callbacks: {
                        label: (tooltipItem) => `${tooltipItem.label}: ${tooltipItem.raw} xe`
                    }
                }
            }
        }
    });

    // Phản ánh giao thông
    const trafficReportStats = JSON.parse(trafficReportStatsJson || '{}');
    const trafficLabels = Object.keys(trafficReportStats);
    const trafficData = Object.values(trafficReportStats);
    const trafficCtx = document.getElementById('trafficReportChart').getContext('2d');
    new Chart(trafficCtx, {
        type: 'pie',
        data: {
            labels: trafficLabels,
            datasets: [{
                    label: 'Số lượng phản ánh',
                    data: trafficData,
                    backgroundColor: ['#36A2EB', '#FF6384', '#FFCE56', '#4BC0C0', '#9966FF', '#FF9F40'],
                    borderColor: '#ffffff',
                    borderWidth: 1
                }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {position: 'top'},
                tooltip: {
                    callbacks: {
                        label: (tooltipItem) => `${tooltipItem.label}: ${tooltipItem.raw} phản ánh`
                    }
                }
            }
        }
    });
}

// Hàm xử lý khi đổi tháng select
export function changeMonth(selectElement) {
    const selectedMonth = selectElement.value;
    const url = new URL(window.location.href);
    url.searchParams.set("month", selectedMonth);
    window.location.href = url.toString();
}
