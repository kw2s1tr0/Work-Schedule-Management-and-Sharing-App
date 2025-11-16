package com.schedule.app.applicationservice.impl;

import org.springframework.stereotype.Service;

import com.schedule.app.applicationservice.DeleteDefaultScheduleService;
import com.schedule.app.domainservice.DefaultScheduleService;
import com.schedule.app.repository.ScheduleDeleteMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeleteDefaultScheduleServiceImpl implements DeleteDefaultScheduleService{
    private final ScheduleDeleteMapper scheduleDeleteMapper;
    private final DefaultScheduleService defaultScheduleService;

    public void deleteDefaultScheduleService(int scheduleId) {
        existDefaultSchedule(scheduleId);
        deleteDefaultSchedule(scheduleId);
    }
    
    public void existDefaultSchedule(int scheduleId) {
        String userId = "00001";
        defaultScheduleService.existDefaultSchedule(scheduleId, userId);
    }

    public void deleteDefaultSchedule(int scheduleId) {
        scheduleDeleteMapper.deleteDefaultSchedule(scheduleId);
    }
}
