package com.quinhat.services.impl;

import com.quinhat.dto.AdminRouteDTO;
import com.quinhat.mapper.AdminRouteMapper;
import com.quinhat.pojo.Route;
import com.quinhat.pojo.RouteStation;
import com.quinhat.pojo.Station;

import com.quinhat.repositories.RouteRepository;
import com.quinhat.services.RouteService;
import com.quinhat.utils.Common;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Override
    public List<Route> getRoutesByStartAndEndPoints(Map<String, String> params) {
        return routeRepository.getRoutesByStartAndEndPoints(params);

    }

    @Override
    public Route createRoute(Map<String, String> params) {
        Route route = new Route();
        route.setStartPoint(params.get("startPoint"));
        route.setEndPoint(params.get("endPoint"));
        route.setName(params.get("name"));
        route.setStatus(params.get("status"));
        route.setFrequency(params.get("frequency"));
        route.setDistance(Float.parseFloat(params.get("distance")));
        route.setDuration(Integer.parseInt(params.get("duration")));
        return routeRepository.createRoute(route);
    }

    @Override
    public Route updateRoute(int id, Map<String, String> params) {
        Route route = routeRepository.getRouteById(id);
        if (route != null) {
            route.setStartPoint(params.get("startPoint"));
            route.setEndPoint(params.get("endPoint"));
            route.setName(params.get("name"));
            route.setStatus(params.get("status"));
            route.setFrequency(params.get("frequency"));
            route.setDistance(Float.parseFloat(params.get("distance")));
            route.setDuration(Integer.parseInt(params.get("duration")));
            return routeRepository.updateRoute(route);
        }
        return null;
    }

    @Override
    public Route getRouteById(int id) {
        return routeRepository.getRouteById(id);
    }

    @Override
    public void deleteRoute(int id) {
        routeRepository.deleteRoute(id);
    }

    @Override
    public List<Station> getStationsByCoordinates(Map<String, String> params) {
        return routeRepository.getStationsByCoordinates(params);
    }

    @Override
    public List<RouteStation> getRouteStations(int routeId) {
        return routeRepository.getRouteStations(routeId);
    }

    @Override
    public List<Route> getRoutes(Map<String, String> params) {
        return this.routeRepository.getRoutes(params);
    }

    @Override
    public List<Route> findRoutesByStations(int departureStationId, int arrivalStationId) {
        return routeRepository.findRoutesByStations(departureStationId, arrivalStationId);
    }

    @Override
    public List<AdminRouteDTO> getAllRoutes() {
        return routeRepository.getAllRoutes();
    }

    @Override
    public void save(AdminRouteDTO dto) {
        routeRepository.save(dto);
    }

    @Override
    public void delete(List<Integer> ids) {
        routeRepository.delete(ids);
    }

    @Override
    public List<AdminRouteDTO> getRoutesPaginated(int page, int size) {
        return routeRepository.getRoutesPaginated(page, size);
    }

    @Override
    public long countRoutes() {
        return routeRepository.countRoutes();
    }

    @Override
    public Route findById(int id) {
        return routeRepository.findById(id);
    }

    @Override
    public AdminRouteDTO update(AdminRouteDTO dto) {
        Route existing = routeRepository.findById(dto.getId());
        if (existing == null) {
            throw new IllegalArgumentException("Route not found");
        }

        if (dto.getStartPoint() != null) {
            existing.setStartPoint(dto.getStartPoint());
        }
        if (dto.getEndPoint() != null) {
            existing.setEndPoint(dto.getEndPoint());
        }
        if (dto.getName() != null) {
            existing.setName(dto.getName());
        }
        if (dto.getStatus() != null) {
            existing.setStatus(Common.toLowerCase(dto.getStatus()));
        }
        if (dto.getFrequency() != null) {
            existing.setFrequency(dto.getFrequency());
        }
        if (dto.getStartTime() != null) {
            existing.setStartTime(dto.getStartTime());
        }
        if (dto.getEndTime() != null) {
            existing.setEndTime(dto.getEndTime());
        }
        if (dto.getDistance() > 0) {
            existing.setDistance(dto.getDistance());
        }
        if (dto.getDuration() > 0) {
            existing.setDuration(dto.getDuration());
        }
        // Không cập nhật createdAt vì đây là thời điểm tạo ban đầu

        routeRepository.update(existing);
        return AdminRouteMapper.toDTO(existing);
    }

}
