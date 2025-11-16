package com.schedule.app.applicationservice.impl;

import org.springframework.stereotype.Service;

import com.schedule.app.applicationservice.DeleteIrregularScheduleService;
import com.schedule.app.domainservice.IrregularScheduleService;
import com.schedule.app.repository.ScheduleDeleteMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeleteIrregularScheduleServiceImpl implements DeleteIrregularScheduleService{
    private final ScheduleDeleteMapper scheduleDeleteMapper;
    private final IrregularScheduleService irregularScheduleService;

    public void deleteIrregularScheduleService(int scheduleId) {
        existIrregularSchedule(scheduleId);
        deleteIrregularSchedule(scheduleId);
    }

    public void existIrregularSchedule(int scheduleId) {
        String userId = "00001";
        irregularScheduleService.existIrregularSchedule(scheduleId, userId);
    }

    public void deleteIrregularSchedule(int scheduleId) {
        scheduleDeleteMapper.deleteIrregularSchedule(scheduleId);
    }
    
}
