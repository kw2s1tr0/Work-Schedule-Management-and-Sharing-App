package com.schedule.app.applicationservice.impl;

import org.springframework.stereotype.Service;

import com.schedule.app.applicationservice.DeleteRegularScheduleService;
import com.schedule.app.domainservice.RegularScheduleService;
import com.schedule.app.repository.ScheduleDeleteMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeleteRegularScheduleServiceImpl implements DeleteRegularScheduleService {

    private final ScheduleDeleteMapper scheduleDeleteMapper;
    private final RegularScheduleService regularScheduleService;
    
    public void deleteRegularScheduleService(int scheduleId) {
        existRegularSchedule(scheduleId);
        deleteRegularSchedule(scheduleId);
    }

    public void existRegularSchedule(int scheduleId) {
        String userId = "00001";
        regularScheduleService.existRegularSchedule(scheduleId, userId);
    }

    public void deleteRegularSchedule(int scheduleId) {
        scheduleDeleteMapper.deleteRegularSchedule(scheduleId);
    }

}
