package com.quinhat.services.impl;

import com.quinhat.pojo.Station;
import com.quinhat.repositories.StationRepository;
import com.quinhat.services.StationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationServiceImpl implements StationService {

    @Autowired
    private StationRepository stationRepo;

    @Override
    public List<Station> getAllStations() {
        return stationRepo.getAllStations();
    }
}
