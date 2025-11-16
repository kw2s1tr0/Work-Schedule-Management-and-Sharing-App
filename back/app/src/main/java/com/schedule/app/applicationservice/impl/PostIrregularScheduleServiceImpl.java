package com.schedule.app.applicationservice.impl;

import org.springframework.stereotype.Service;

import com.schedule.app.applicationservice.PostIrregularScheduleService;
import com.schedule.app.domainservice.IrregularScheduleService;
import com.schedule.app.form.IrregularScheduleForm;
import com.schedule.app.record.input.IrregularScheduleInputRecord;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.repository.ScheduleCreateMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostIrregularScheduleServiceImpl implements PostIrregularScheduleService{

    private final ScheduleCreateMapper scheduleCreateMapper;
    private final IrregularScheduleService irregularScheduleService;

    public void postIrregularScheduleService(IrregularScheduleForm form){
        IrregularScheduleInputRecord record = toIrregularScheduleRecord(form);
        postIrregularSchedule(record);
    }
    
    public IrregularScheduleInputRecord toIrregularScheduleRecord(IrregularScheduleForm form){

        ScheduleSearchRecord scheduleSearchRecord = ScheduleSearchRecord.builder()
                                        .from(form.date())
                                        .to(form.date())
                                        .build();

        irregularScheduleService.checkIrregularSchedule(scheduleSearchRecord);

        IrregularScheduleInputRecord record = IrregularScheduleInputRecord.builder()
                                                .userId("00001") //ログイン機能を使用するか仮に
                                                .startTime(form.startTime())
                                                .endTime(form.endTime())
                                                .date(form.date())
                                                .workTypeId(form.workTypeId())
                                                .build();

        return record;
    }

    public void postIrregularSchedule(IrregularScheduleInputRecord record) {
        scheduleCreateMapper.createIrregularSchedule(record);
    }
}