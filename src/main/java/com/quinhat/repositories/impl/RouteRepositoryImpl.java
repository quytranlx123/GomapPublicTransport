package com.quinhat.repositories.impl;

import com.quinhat.dto.AdminRouteDTO;
import com.quinhat.mapper.AdminFavoriteRouteMapper;
import com.quinhat.mapper.AdminRouteMapper;
import com.quinhat.pojo.FavoriteRoute;
import com.quinhat.pojo.Notification;
import com.quinhat.pojo.Route;
import com.quinhat.pojo.RouteStation;
import com.quinhat.pojo.Station;
import com.quinhat.pojo.User;
import com.quinhat.repositories.RouteRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class RouteRepositoryImpl implements RouteRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    private static final int PAGE_SIZE = 8;

    @Override
    public List<AdminRouteDTO> getAllRoutes() {
        Session s = this.factory.getObject().getCurrentSession();
        List<Route> routes = s.createQuery("FROM Route r", Route.class).getResultList();
        return routes.stream()
                .map(AdminRouteMapper::toDTO)
                .toList();
    }

    @Override
    public Route getRouteById_Qui(int id) {
        Session session = factory.getObject().getCurrentSession();
        return session.get(Route.class, id);
    }

    @Override
    public List<Route> getRoutesByStartAndEndPoints(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery<Route> query = builder.createQuery(Route.class);
        Root<Route> root = query.from(Route.class);
        query.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String startPoint = params.get("startPoint");
            if (startPoint != null && !startPoint.isEmpty()) {
                predicates.add(builder.equal(root.get("startPoint"), startPoint));
            }

            String endPoint = params.get("endPoint");
            if (endPoint != null && !endPoint.isEmpty()) {
                predicates.add(builder.equal(root.get("endPoint"), endPoint));
            }

            query.where(predicates.toArray(new Predicate[0]));
        }

        Query<Route> queryExecution = s.createQuery(query);

        if (params != null && params.containsKey("page")) {
            int page = Integer.parseInt(params.getOrDefault("page", "1"));
            int start = (page - 1) * PAGE_SIZE;
            queryExecution.setFirstResult(start);
            queryExecution.setMaxResults(PAGE_SIZE);
        }

        return queryExecution.getResultList();
    }

    @Override
    public Route createRoute(Route route) {
        Session s = this.factory.getObject().getCurrentSession();
        s.persist(route);
        return route;
    }

    @Override
    public Route getRouteById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Route.class, id);
    }

    @Override
    public void deleteRoute(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Route route = this.getRouteById(id);
        s.remove(route);
    }

    @Override
    public Route updateRoute(Route route) {
        Session s = this.factory.getObject().getCurrentSession();
        s.merge(route);
        return route;
    }

    @Override
    public List<Station> getStationsByCoordinates(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery<Station> query = builder.createQuery(Station.class);
        Root<Station> root = query.from(Station.class);
        query.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String latitude = params.get("latitude");
            String longitude = params.get("longitude");
            if (latitude != null && longitude != null) {
                predicates.add(builder.equal(root.get("latitude"), Float.parseFloat(latitude)));
                predicates.add(builder.equal(root.get("longitude"), Float.parseFloat(longitude)));
            }

            query.where(predicates.toArray(new Predicate[0]));
        }

        Query<Station> queryExecution = s.createQuery(query);
        return queryExecution.getResultList();
    }

    @Override
    public List<RouteStation> getRouteStations(int routeId) {
        Session s = this.factory.getObject().getCurrentSession();
        Query<RouteStation> query = s.createQuery("FROM RouteStation rs WHERE rs.routeId.id = :routeId", RouteStation.class);

        query.setParameter("routeId", routeId);
        return query.getResultList();
    }

    @Override
    public List<Route> getRoutes(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Route> q = b.createQuery(Route.class);
        Root<Route> root = q.from(Route.class);
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            // Ví dụ lọc theo tên tuyến
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("name"), String.format("%%%s%%", kw)));
            }

            q.where(predicates.toArray(Predicate[]::new));
        }

        Query<Route> query = s.createQuery(q);

        if (params != null && params.containsKey("page")) {
            int page = Integer.parseInt(params.getOrDefault("page", "1"));
            int start = (page - 1) * PAGE_SIZE;
            query.setFirstResult(start);
            query.setMaxResults(PAGE_SIZE);
        }

        List<Route> routes = query.getResultList();

        // Eager load stations
        for (Route r : routes) {
            Hibernate.initialize(r.getRouteStationSet());
        }

        return routes;
    }

    @Override
    public List<RouteStation> getAllRouteStations() {
        Session s = this.factory.getObject().getCurrentSession();
        Query<RouteStation> query = s.createQuery("FROM RouteStation", RouteStation.class);
        return query.getResultList();
    }

    @Override
    public List<Route> getRoutesByIds(List<Integer> routeIds) {
        Session s = this.factory.getObject().getCurrentSession();
        Query<Route> query = s.createQuery("FROM Route WHERE id IN (:routeIds)", Route.class);
        query.setParameterList("routeIds", routeIds);
        return query.getResultList();
    }

    @Override
    public List<Route> findRoutesByStations(int departureStationId, int arrivalStationId) {
        Session session = this.factory.getObject().getCurrentSession();

        // 1. Tạo danh sách tất cả RouteStation từ cơ sở dữ liệu
        Query<RouteStation> allRouteStationsQuery = session.createQuery("FROM RouteStation", RouteStation.class);
        List<RouteStation> allRouteStations = allRouteStationsQuery.getResultList();

        // 2. Xây dựng đồ thị từ RouteStation
        Map<Integer, List<RouteEdge>> graph = new HashMap<>();
        Map<Integer, List<RouteStation>> routeStationsByRoute = new HashMap<>();

        // Tạo đồ thị các tuyến và các trạm trong từng tuyến
        for (RouteStation rs : allRouteStations) {
            int routeId = rs.getRouteId().getId();
            routeStationsByRoute.computeIfAbsent(routeId, k -> new ArrayList<>()).add(rs);
        }

        // Xây dựng các cạnh trong đồ thị
        for (List<RouteStation> routeStations : routeStationsByRoute.values()) {
            routeStations.sort(Comparator.comparingInt(RouteStation::getOrderStation));

            for (int i = 0; i < routeStations.size() - 1; i++) {
                RouteStation from = routeStations.get(i);
                RouteStation to = routeStations.get(i + 1);

                // Thêm cạnh cho cả chiều đi và chiều ngược lại nếu có
                graph.computeIfAbsent(from.getStationId().getId(), k -> new ArrayList<>())
                        .add(new RouteEdge(to.getStationId().getId(), from.getRouteId().getId()));

                graph.computeIfAbsent(to.getStationId().getId(), k -> new ArrayList<>())
                        .add(new RouteEdge(from.getStationId().getId(), from.getRouteId().getId()));
            }
        }

        // 3. BFS tìm đường đi có ít chuyển tuyến nhất
        PriorityQueue<BFSNode> queue = new PriorityQueue<>(Comparator.comparingInt(BFSNode::getTransferCount));
        Set<Integer> visited = new HashSet<>();
        queue.add(new BFSNode(departureStationId, new ArrayList<>(), -1, 0));

        while (!queue.isEmpty()) {
            BFSNode current = queue.poll();

            // Nếu đến được đích, xử lý kết quả
            if (current.stationId == arrivalStationId) {
                List<RouteStation> filteredRouteStations = new ArrayList<>();
                for (Integer routeId : current.routes) {
                    List<RouteStation> routeStations = routeStationsByRoute.get(routeId);
                    if (routeStations != null) {
                        boolean withinRoute = false;
                        for (RouteStation rs : routeStations) {
                            // Lọc trạm trong phạm vi tuyến từ departure đến arrival
                            if (rs.getStationId().getId() == departureStationId) {
                                withinRoute = true;
                            }
                            if (withinRoute) {
                                filteredRouteStations.add(rs);
                            }
                            if (rs.getStationId().getId() == arrivalStationId) {
                                break;
                            }
                        }
                    }
                }

                // Sau khi lọc, trả về danh sách các tuyến đường
                Query<Route> routeQuery = session.createQuery("FROM Route WHERE id IN (:routeIds)", Route.class);
                routeQuery.setParameterList("routeIds", current.routes);
                List<Route> resultRoutes = routeQuery.getResultList();

                // Gắn kết các RouteStation đã được lọc cho từng Route
                for (Route route : resultRoutes) {
                    Set<RouteStation> filteredRouteStationsSet = new HashSet<>(filteredRouteStations);
                    route.setRouteStationSet(filteredRouteStationsSet); // Cập nhật danh sách các trạm
                }

                return resultRoutes;
            }

            visited.add(current.stationId);

            // Duyệt các trạm lân cận
            List<RouteEdge> neighbors = graph.getOrDefault(current.stationId, Collections.emptyList());
            for (RouteEdge edge : neighbors) {
                if (!visited.contains(edge.toStationId)) {
                    List<Integer> newRoutes = new ArrayList<>(current.routes);

                    // Nếu đổi tuyến, thêm routeId vào danh sách
                    if (edge.routeId != current.lastRouteId) {
                        newRoutes.add(edge.routeId);
                    }

                    // Thêm vào queue với số lần chuyển tuyến
                    queue.add(new BFSNode(edge.toStationId, newRoutes, edge.routeId,
                            current.transferCount + (edge.routeId != current.lastRouteId ? 1 : 0)));
                }
            }
        }

        return Collections.emptyList(); // Không tìm thấy đường đi
    }

    @Override
    public void save(AdminRouteDTO dto) {
        Session session = this.factory.getObject().getCurrentSession();
        Route route = AdminRouteMapper.toEntity(dto);

        if (route.getCreatedAt() == null) {
            route.setCreatedAt(new java.util.Date());
        }

        session.persist(route);
    }

    @Override
    public void delete(List<Integer> ids) {
        Session session = this.factory.getObject().getCurrentSession();
        MutationQuery query = session.createMutationQuery("DELETE FROM Route u WHERE u.id IN :ids");
        query.setParameter("ids", ids);
        query.executeUpdate();
    }

    @Override
    public List<AdminRouteDTO> getRoutesPaginated(int page, int size) {
        Session s = this.factory.getObject().getCurrentSession();
        List<Route> list = s.createQuery("FROM Route r", Route.class)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
        return list.stream().map(AdminRouteMapper::toDTO).toList();
    }

    @Override
    public long countRoutes() {
        Session s = this.factory.getObject().getCurrentSession();
        return s.createQuery("SELECT COUNT(r.id) FROM Route r", Long.class).getSingleResult();
    }

    @Override
    public void update(Route r) {
        Session s = this.factory.getObject().getCurrentSession();
        s.merge(r);
    }

    @Override
    public Route findById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Route.class, id);
    }

//    @Override
//    public List<RouteStation> findRoutesByStations(int departureStationId, int arrivalStationId) {
//        Session session = this.factory.getObject().getCurrentSession();
//
//        // 1. Lấy tất cả RouteStation từ database
//        Query<RouteStation> allRouteStationsQuery = session.createQuery("FROM RouteStation", RouteStation.class);
//        List<RouteStation> allRouteStations = allRouteStationsQuery.getResultList();
//
//        // 2. Gom các RouteStation theo Route
//        Map<Integer, List<RouteStation>> routeStationsByRoute = new HashMap<>();
//
//        for (RouteStation rs : allRouteStations) {
//            int routeId = rs.getRouteId().getId();
//            routeStationsByRoute.computeIfAbsent(routeId, k -> new ArrayList<>()).add(rs);
//        }
//
//        // 3. Duyệt từng tuyến để tìm tuyến có chứa cả điểm đi và điểm đến theo đúng thứ tự
//        for (Map.Entry<Integer, List<RouteStation>> entry : routeStationsByRoute.entrySet()) {
//            List<RouteStation> routeStations = entry.getValue();
//            // Sắp xếp theo thứ tự trạm
//            routeStations.sort(Comparator.comparingInt(RouteStation::getOrderStation));
//
//            // Tìm vị trí của departureStation và arrivalStation
//            int departureIndex = -1, arrivalIndex = -1;
//            for (int i = 0; i < routeStations.size(); i++) {
//                if (routeStations.get(i).getStationId().getId() == departureStationId) {
//                    departureIndex = i;
//                }
//                if (routeStations.get(i).getStationId().getId() == arrivalStationId) {
//                    arrivalIndex = i;
//                }
//            }
//
//            // Nếu tìm thấy cả hai và departure đứng trước arrival
//            if (departureIndex != -1 && arrivalIndex != -1 && departureIndex <= arrivalIndex) {
//                // Trả về danh sách các trạm từ điểm đi đến điểm đến
//                return routeStations.subList(departureIndex, arrivalIndex + 1);
//            }
//        }
//
//        // Nếu không tìm thấy tuyến phù hợp
//        return Collections.emptyList();
//    }
//    @Override
//    public List<List<RouteStation>> findStationsWithPossibleTransfer(int departureStationId, int arrivalStationId) {
//        Session session = this.factory.getObject().getCurrentSession();
//
//        // 1. Lấy tất cả RouteStation
//        Query<RouteStation> allRouteStationsQuery = session.createQuery("FROM RouteStation", RouteStation.class);
//        List<RouteStation> allRouteStations = allRouteStationsQuery.getResultList();
//
//        // 2. Gom các RouteStation theo Route
//        Map<Integer, List<RouteStation>> routeStationsByRoute = new HashMap<>();
//        for (RouteStation rs : allRouteStations) {
//            int routeId = rs.getRouteId().getId();
//            routeStationsByRoute.computeIfAbsent(routeId, k -> new ArrayList<>()).add(rs);
//        }
//
//        // 3. Tìm các tuyến có chứa departure và arrival
//        Set<Integer> departureRoutes = new HashSet<>();
//        Set<Integer> arrivalRoutes = new HashSet<>();
//
//        for (Map.Entry<Integer, List<RouteStation>> entry : routeStationsByRoute.entrySet()) {
//            for (RouteStation rs : entry.getValue()) {
//                if (rs.getStationId().getId() == departureStationId) {
//                    departureRoutes.add(entry.getKey());
//                }
//                if (rs.getStationId().getId() == arrivalStationId) {
//                    arrivalRoutes.add(entry.getKey());
//                }
//            }
//        }
//
//        List<List<RouteStation>> result = new ArrayList<>();
//
//        // 4. Nếu có tuyến chung, đi luôn
//        for (Integer routeId : departureRoutes) {
//            if (arrivalRoutes.contains(routeId)) {
//                List<RouteStation> stations = routeStationsByRoute.get(routeId);
//                stations.sort(Comparator.comparingInt(RouteStation::getOrderStation));
//
//                int departureIndex = -1, arrivalIndex = -1;
//                for (int i = 0; i < stations.size(); i++) {
//                    if (stations.get(i).getStationId().getId() == departureStationId) {
//                        departureIndex = i;
//                    }
//                    if (stations.get(i).getStationId().getId() == arrivalStationId) {
//                        arrivalIndex = i;
//                    }
//                }
//                if (departureIndex != -1 && arrivalIndex != -1 && departureIndex <= arrivalIndex) {
//                    result.add(stations.subList(departureIndex, arrivalIndex + 1));
//                }
//            }
//        }
//
//        if (!result.isEmpty()) {
//            return result;
//        }
//
//        // 5. Nếu không có tuyến chung -> tìm chuyển tuyến
//        for (Integer depRouteId : departureRoutes) {
//            for (Integer arrRouteId : arrivalRoutes) {
//                if (depRouteId.equals(arrRouteId)) {
//                    continue; // đã xét ở trên
//                }
//                List<RouteStation> depStations = routeStationsByRoute.get(depRouteId);
//                List<RouteStation> arrStations = routeStationsByRoute.get(arrRouteId);
//
//                // Tìm trạm chung
//                Set<Integer> depStationIds = depStations.stream().map(rs -> rs.getStationId().getId()).collect(Collectors.toSet());
//                Set<Integer> arrStationIds = arrStations.stream().map(rs -> rs.getStationId().getId()).collect(Collectors.toSet());
//
//                depStationIds.retainAll(arrStationIds); // lấy giao nhau
//                if (!depStationIds.isEmpty()) {
//                    Integer transferStationId = depStationIds.iterator().next(); // lấy 1 trạm giao bất kỳ
//
//                    // Tìm đoạn từ departure đến transfer
//                    depStations.sort(Comparator.comparingInt(RouteStation::getOrderStation));
//                    int depIndex = -1, transferIndexDep = -1;
//                    for (int i = 0; i < depStations.size(); i++) {
//                        if (depStations.get(i).getStationId().getId() == departureStationId) {
//                            depIndex = i;
//                        }
//                        if (depStations.get(i).getStationId().getId() == transferStationId) {
//                            transferIndexDep = i;
//                        }
//                    }
//
//                    // Tìm đoạn từ transfer đến arrival
//                    arrStations.sort(Comparator.comparingInt(RouteStation::getOrderStation));
//                    int transferIndexArr = -1, arrIndex = -1;
//                    for (int i = 0; i < arrStations.size(); i++) {
//                        if (arrStations.get(i).getStationId().getId() == transferStationId) {
//                            transferIndexArr = i;
//                        }
//                        if (arrStations.get(i).getStationId().getId() == arrivalStationId) {
//                            arrIndex = i;
//                        }
//                    }
//
//                    if (depIndex != -1 && transferIndexDep != -1 && depIndex <= transferIndexDep
//                            && transferIndexArr != -1 && arrIndex != -1 && transferIndexArr <= arrIndex) {
//
//                        List<RouteStation> firstLeg = depStations.subList(depIndex, transferIndexDep + 1);
//                        List<RouteStation> secondLeg = arrStations.subList(transferIndexArr, arrIndex + 1);
//
//                        // Ghép lại
//                        List<RouteStation> fullRoute = new ArrayList<>();
//                        fullRoute.addAll(firstLeg);
//                        fullRoute.addAll(secondLeg);
//
//                        result.add(fullRoute);
//                    }
//                }
//            }
//        }
//
//        return result;
//    }
// Class phụ trợ
    private static class RouteEdge {

        int toStationId;
        int routeId;

        public RouteEdge(int toStationId, int routeId) {
            this.toStationId = toStationId;
            this.routeId = routeId;
        }
    }

    private static class BFSNode {

        int stationId;
        List<Integer> routes; // danh sách các routeIds đã đi qua
        int lastRouteId; // tuyến trước đó, để xác định chuyển tuyến
        int transferCount; // số lần chuyển tuyến

        public BFSNode(int stationId, List<Integer> routes, int lastRouteId, int transferCount) {
            this.stationId = stationId;
            this.routes = routes;
            this.lastRouteId = lastRouteId;
            this.transferCount = transferCount;
        }

        public int getTransferCount() {
            return transferCount;
        }
    }

}
