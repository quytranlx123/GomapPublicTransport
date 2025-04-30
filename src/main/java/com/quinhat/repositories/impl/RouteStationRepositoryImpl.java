package com.quinhat.repositories.impl;

import com.quinhat.pojo.RouteStation;

import com.quinhat.repositories.RouteStationRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class RouteStationRepositoryImpl implements RouteStationRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<RouteStation> getAllRouteStations() {
        Session s = this.factory.getObject().getCurrentSession();
        return s.createQuery("FROM RouteStation", RouteStation.class).getResultList();
    }

    @Override
    public void save(RouteStation routeStation) {
        Session s = this.factory.getObject().getCurrentSession();
        if (routeStation.getId() == null) {
            s.persist(routeStation);
        } else {
            s.merge(routeStation);
        }
    }

    @Override
    public List<List<RouteStation>> findStationsWithPossibleTransfer(int departureStationId, int arrivalStationId) {
        Session session = this.factory.getObject().getCurrentSession();

        // 1. Lấy tất cả RouteStation
        Query<RouteStation> allRouteStationsQuery = session.createQuery("FROM RouteStation", RouteStation.class);
        List<RouteStation> allRouteStations = allRouteStationsQuery.getResultList();

        // 2. Gom các RouteStation theo Route
        Map<Integer, List<RouteStation>> routeStationsByRoute = new HashMap<>();
        for (RouteStation rs : allRouteStations) {
            int routeId = rs.getRouteId().getId();
            routeStationsByRoute.computeIfAbsent(routeId, k -> new ArrayList<>()).add(rs);
        }

        // 3. Tìm các tuyến có chứa departure và arrival
        Set<Integer> departureRoutes = new HashSet<>();
        Set<Integer> arrivalRoutes = new HashSet<>();

        for (Map.Entry<Integer, List<RouteStation>> entry : routeStationsByRoute.entrySet()) {
            for (RouteStation rs : entry.getValue()) {
                if (rs.getStationId().getId() == departureStationId) {
                    departureRoutes.add(entry.getKey());
                }
                if (rs.getStationId().getId() == arrivalStationId) {
                    arrivalRoutes.add(entry.getKey());
                }
            }
        }

        List<List<RouteStation>> result = new ArrayList<>();

        // 4. Các tuyến trực tiếp
        for (Integer routeId : departureRoutes) {
            if (arrivalRoutes.contains(routeId)) {
                List<RouteStation> stations = routeStationsByRoute.get(routeId);
                stations.sort(Comparator.comparingInt(RouteStation::getOrderStation));

                int departureIndex = -1, arrivalIndex = -1;
                for (int i = 0; i < stations.size(); i++) {
                    if (stations.get(i).getStationId().getId() == departureStationId) {
                        departureIndex = i;
                    }
                    if (stations.get(i).getStationId().getId() == arrivalStationId) {
                        arrivalIndex = i;
                    }
                }

                // Nếu departure và arrival hợp lệ và theo thứ tự đúng
                if (departureIndex != -1 && arrivalIndex != -1 && departureIndex <= arrivalIndex) {
                    List<RouteStation> routeSegment = new ArrayList<>(stations.subList(departureIndex, arrivalIndex + 1));
                    result.add(routeSegment);
                }
            }
        }

        // 5. Tuyến có chuyển
        for (Integer depRouteId : departureRoutes) {
            for (Integer arrRouteId : arrivalRoutes) {
                if (depRouteId.equals(arrRouteId)) {
                    continue;
                }

                List<RouteStation> depStations = new ArrayList<>(routeStationsByRoute.get(depRouteId));
                List<RouteStation> arrStations = new ArrayList<>(routeStationsByRoute.get(arrRouteId));
                depStations.sort(Comparator.comparingInt(RouteStation::getOrderStation));
                arrStations.sort(Comparator.comparingInt(RouteStation::getOrderStation));

                Set<Integer> depStationIds = depStations.stream()
                        .map(rs -> rs.getStationId().getId()).collect(Collectors.toSet());
                Set<Integer> arrStationIds = arrStations.stream()
                        .map(rs -> rs.getStationId().getId()).collect(Collectors.toSet());

                depStationIds.retainAll(arrStationIds); // Giao các trạm có thể chuyển

                for (Integer transferStationId : depStationIds) {
                    int depIndex = -1, transferIndexDep = -1;
                    for (int i = 0; i < depStations.size(); i++) {
                        if (depStations.get(i).getStationId().getId() == departureStationId) {
                            depIndex = i;
                        }
                        if (depStations.get(i).getStationId().getId() == transferStationId) {
                            transferIndexDep = i;
                        }
                    }

                    int transferIndexArr = -1, arrIndex = -1;
                    for (int i = 0; i < arrStations.size(); i++) {
                        if (arrStations.get(i).getStationId().getId() == transferStationId) {
                            transferIndexArr = i;
                        }
                        if (arrStations.get(i).getStationId().getId() == arrivalStationId) {
                            arrIndex = i;
                        }
                    }

                    if (depIndex != -1 && transferIndexDep != -1 && depIndex <= transferIndexDep
                            && transferIndexArr != -1 && arrIndex != -1 && transferIndexArr <= arrIndex) {

                        List<RouteStation> firstLeg = new ArrayList<>(depStations.subList(depIndex, transferIndexDep + 1));
                        List<RouteStation> secondLeg = new ArrayList<>(arrStations.subList(transferIndexArr, arrIndex + 1));

                        List<RouteStation> fullRoute = new ArrayList<>();
                        fullRoute.addAll(firstLeg);
                        fullRoute.addAll(secondLeg);

                        result.add(fullRoute);
                    }
                }
            }
        }

        // Sắp xếp lại các tuyến để đảm bảo trạm khởi hành là đầu tiên và trạm đến là cuối cùng
        for (List<RouteStation> routeSegment : result) {
            routeSegment.sort(Comparator.comparingInt(RouteStation::getOrderStation));
        }

        // Thêm bước đảm bảo trạm bắt đầu là "Bến xe Miền Đông" và trạm kết thúc là "Ngã 3 Phổ Quang"
        for (List<RouteStation> routeSegment : result) {
            // Nếu trạm bắt đầu không phải là "Bến xe Miền Đông", sửa lại thứ tự
            if (routeSegment.get(0).getStationId().getId() != departureStationId) {
                RouteStation firstStation = routeSegment.stream()
                        .filter(rs -> rs.getStationId().getId() == departureStationId)
                        .findFirst().orElse(null);
                if (firstStation != null) {
                    routeSegment.remove(firstStation);
                    routeSegment.add(0, firstStation);
                }
            }

            // Nếu trạm kết thúc không phải là "Ngã 3 Phổ Quang", sửa lại thứ tự
            if (routeSegment.get(routeSegment.size() - 1).getStationId().getId() != arrivalStationId) {
                RouteStation lastStation = routeSegment.stream()
                        .filter(rs -> rs.getStationId().getId() == arrivalStationId)
                        .findFirst().orElse(null);
                if (lastStation != null) {
                    routeSegment.remove(lastStation);
                    routeSegment.add(lastStation);
                }
            }
        }

        return result;
    }

}
