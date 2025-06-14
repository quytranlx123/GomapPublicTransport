package com.quinhat.repositories.impl;

import com.quinhat.dto.AdminRouteStationDTO;
import com.quinhat.mapper.AdminRouteStationMapper;
import com.quinhat.pojo.Route;
import com.quinhat.pojo.RouteStation;
import com.quinhat.pojo.Station;

import com.quinhat.repositories.RouteStationRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.hibernate.Session;
import org.hibernate.query.MutationQuery;
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
    public List<AdminRouteStationDTO> getAllRouteStations() {
        Session s = this.factory.getObject().getCurrentSession();
        List<RouteStation> routeStations = s.createQuery("FROM RouteStation rt", RouteStation.class).getResultList();
        return routeStations.stream()
                .map(AdminRouteStationMapper::toDTO)
                .toList();
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

        // 3. Tìm các routeId chứa departure và arrival (tìm những tuyến có chứa trạm đi và trạm đến)
        Set<Integer> departureRoutes = new HashSet<>();
        Set<Integer> arrivalRoutes = new HashSet<>();

        for (Map.Entry<Integer, List<RouteStation>> entry : routeStationsByRoute.entrySet()) {
            for (RouteStation rs : entry.getValue()) {
                int stationId = rs.getStationId().getId();
                if (stationId == departureStationId) {
                    departureRoutes.add(entry.getKey());
                }
                if (stationId == arrivalStationId) {
                    arrivalRoutes.add(entry.getKey());
                }
            }
        }

        // 4. Nếu có route chứa cả điểm đi và điểm đến (tuyến đi thẳng)
        for (Integer routeId : departureRoutes) {
            if (arrivalRoutes.contains(routeId)) {
                List<RouteStation> routeStations = routeStationsByRoute.get(routeId)
                        .stream()
                        .sorted(Comparator.comparingInt(RouteStation::getOrderStation))
                        .collect(Collectors.toList());

                int startIdx = -1, endIdx = -1;
                for (int i = 0; i < routeStations.size(); i++) {
                    int stationId = routeStations.get(i).getStationId().getId();
                    if (stationId == departureStationId) {
                        startIdx = i;
                    }
                    if (stationId == arrivalStationId) {
                        endIdx = i;
                    }
                }

                if (startIdx != -1 && endIdx != -1 && startIdx != endIdx) {
                    List<RouteStation> directSegment;
                    if (startIdx < endIdx) {
                        // Đi thuận chiều
                        directSegment = new ArrayList<>(routeStations.subList(startIdx, endIdx + 1));
                    } else {
                        // Đi ngược chiều
                        directSegment = new ArrayList<>();
                        for (int i = startIdx; i >= endIdx; i--) {
                            directSegment.add(routeStations.get(i));
                        }
                    }
                    return List.of(directSegment);
                }
            }
        }

        // 5. Nếu không có tuyến đi thẳng, tìm tuyến có trạm trung gian
        // 5.1. Duyệt từng cặp tuyến (tuyến có trạm đi, tuyến có trạm đến)
        List<List<RouteStation>> resultList = new ArrayList<>();
        for (Integer depRouteId : departureRoutes) {
            List<RouteStation> depRouteStations = routeStationsByRoute.get(depRouteId)
                    .stream()
                    .sorted(Comparator.comparingInt(RouteStation::getOrderStation))
                    .collect(Collectors.toList());

            Set<Integer> depStationIds = depRouteStations.stream()
                    .map(rs -> rs.getStationId().getId())
                    .collect(Collectors.toSet());

            for (Integer arrRouteId : arrivalRoutes) {
                List<RouteStation> arrRouteStations = routeStationsByRoute.get(arrRouteId)
                        .stream()
                        .sorted(Comparator.comparingInt(RouteStation::getOrderStation))
                        .collect(Collectors.toList());

                Set<Integer> arrStationIds = arrRouteStations.stream()
                        .map(rs -> rs.getStationId().getId())
                        .collect(Collectors.toSet());

                // Tìm trạm trung gian chung
                Set<Integer> commonStations = new HashSet<>(depStationIds);
                commonStations.retainAll(arrStationIds);

                for (Integer transferStationId : commonStations) {
                    // segment 1: departure -> transfer
                    int depIndex = -1;
                    int transferInDepIndex = -1;

                    for (int i = 0; i < depRouteStations.size(); i++) {
                        int stationId = depRouteStations.get(i).getStationId().getId();
                        if (stationId == departureStationId) {
                            depIndex = i;
                        }
                        if (stationId == transferStationId) {
                            transferInDepIndex = i;
                        }
                    }

                    if (depIndex == -1 || transferInDepIndex == -1 || depIndex == transferInDepIndex) {
                        continue;
                    }

                    // segment 2: transfer -> arrival
                    int transferInArrIndex = -1;
                    int arrIndex = -1;

                    for (int i = 0; i < arrRouteStations.size(); i++) {
                        int stationId = arrRouteStations.get(i).getStationId().getId();
                        if (stationId == transferStationId) {
                            transferInArrIndex = i;
                        }
                        if (stationId == arrivalStationId) {
                            arrIndex = i;
                        }
                    }

                    if (transferInArrIndex == -1 || arrIndex == -1 || transferInArrIndex == arrIndex) {
                        continue;
                    }

                    // Xây dựng segment1: departure -> transfer (đoạn tuyến từ trạm đi đến trạm trung chuyển)
                    List<RouteStation> segment1 = new ArrayList<>();
                    if (depIndex < transferInDepIndex) {
                        // Đi thuận chiều
                        for (int i = depIndex; i <= transferInDepIndex; i++) {
                            segment1.add(depRouteStations.get(i));
                        }
                    } else {
                        // Đi ngược chiều
                        for (int i = depIndex; i >= transferInDepIndex; i--) {
                            segment1.add(depRouteStations.get(i));
                        }
                    }

                    // Xây dựng segment2: transfer -> arrival (đoạn tuyến từ trạm trung chuyển và trạm đến)
                    List<RouteStation> segment2 = new ArrayList<>();
                    if (transferInArrIndex < arrIndex) {
                        // Đi thuận chiều
                        for (int i = transferInArrIndex; i <= arrIndex; i++) {
                            segment2.add(arrRouteStations.get(i));
                        }
                    } else {
                        // Đi ngược chiều
                        for (int i = transferInArrIndex; i >= arrIndex; i--) {
                            segment2.add(arrRouteStations.get(i));
                        }
                    }

                    resultList.add(segment1);
                    resultList.add(segment2);
                    return resultList; // Trả về route 2 chặng đầu tiên tìm thấy
                }
            }
        }

        // 6. Không tìm thấy tuyến phù hợp
        return new ArrayList<>();
    }

    @Override
    public void save(AdminRouteStationDTO dto) {
        Session s = this.factory.getObject().getCurrentSession();

        Route route = s.get(Route.class, dto.getRouteId());
        Station station = s.get(Station.class, dto.getStationId());

        if (route == null || station == null) {
            throw new IllegalArgumentException("Tuyến hoặc Trạm dừng không tồn tại");
        }

        RouteStation routeStation = AdminRouteStationMapper.toEntity(dto, route, station);
        s.persist(routeStation);
    }

    @Override
    public void delete(List<Integer> ids) {
        Session session = this.factory.getObject().getCurrentSession();
        MutationQuery query = session.createMutationQuery("DELETE FROM RouteStation u WHERE u.id IN :ids");
        query.setParameter("ids", ids);
        query.executeUpdate();
    }

    @Override
    public List<AdminRouteStationDTO> getRouteStationsPaginated(int page, int size) {
        Session s = this.factory.getObject().getCurrentSession();
        List<RouteStation> list = s.createQuery("FROM RouteStation rs", RouteStation.class)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
        return list.stream().map(AdminRouteStationMapper::toDTO).toList();
    }

    @Override
    public long countRouteStations() {
        Session s = this.factory.getObject().getCurrentSession();
        return s.createQuery("SELECT COUNT(rs.id) FROM RouteStation rs", Long.class).getSingleResult();
    }

    @Override
    public void update(RouteStation r) {
        Session s = this.factory.getObject().getCurrentSession();
        s.merge(r);
    }

    @Override
    public RouteStation findById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(RouteStation.class, id);
    }
}
