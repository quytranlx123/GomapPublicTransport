package com.quinhat.services.impl;

import com.quinhat.pojo.Schedule;
import com.quinhat.repositories.ScheduleRepository;
import com.quinhat.services.ScheduleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepo;

    @Override
    public List<Schedule> getAllSchedules() {
        return scheduleRepo.getAllSchedules();
    }

    @Override
    public void save(Schedule schedule) {
        scheduleRepo.save(schedule);
    }
}
