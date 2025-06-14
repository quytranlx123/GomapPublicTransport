export function renderStatisticsCharts() {
    fetch('/gomap/admin/dashboard/statistics/all')
            .then(res => res.json())
            .then(data => {
                // 1. Top 5 tuyến xe yêu thích
                const top5 = data.top5FavoriteRoutes;
                const labelsTop5 = top5.map(item => item.label);
                const valuesTop5 = top5.map(item => item.value);
                new Chart(document.getElementById('top5RouteFavorite'), {
                    type: 'bar',
                    data: {
                        labels: labelsTop5,
                        datasets: [{
                                label: 'Yêu thích',
                                data: valuesTop5,
                                backgroundColor: 'rgba(75, 192, 192, 0.6)'
                            }]
                    },
                    options: {responsive: true}
                });

                // 2. Thống kê phương tiện
                const vehicleStatus = data.vehicleStatus;
                new Chart(document.getElementById('vehicleStatusChart'), {
                    type: 'pie',
                    data: {
                        labels: Object.keys(vehicleStatus),
                        datasets: [{
                                data: Object.values(vehicleStatus),
                                backgroundColor: ['green', 'orange', 'red']
                            }]
                    },
                    options: {responsive: true}
                });

                // 3. Phản ánh giao thông
                const trafficReports = data.trafficReports;
                new Chart(document.getElementById('trafficReportChart'), {
                    type: 'doughnut',
                    data: {
                        labels: Object.keys(trafficReports),
                        datasets: [{
                                data: Object.values(trafficReports),
                                backgroundColor: ['blue', 'gray', 'purple']
                            }]
                    },
                    options: {responsive: true}
                });
            });
}
