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

    /**
     * イレギュラースケジュールを削除する
     * 
     * @param scheduleId スケジュールID
     * @param userId ユーザーID
     */
    @Override
    public void deleteIrregularScheduleService(int scheduleId, String userId) {
        existIrregularSchedule(scheduleId, userId);
        deleteIrregularSchedule(scheduleId);
    }

    /**
     * イレギュラースケジュールの存在チェック
     * 
     * @param scheduleId スケジュールID
     * @param userId ユーザーID
     */
    @Override
    public void existIrregularSchedule(int scheduleId, String userId) {
        irregularScheduleService.existIrregularSchedule(scheduleId, userId);
    }

    /**
     * イレギュラースケジュールを削除する
     * 
     * @param scheduleId スケジュールID
     */
    @Override
    public void deleteIrregularSchedule(int scheduleId) {
        scheduleDeleteMapper.deleteIrregularSchedule(scheduleId);
    }
    
}
