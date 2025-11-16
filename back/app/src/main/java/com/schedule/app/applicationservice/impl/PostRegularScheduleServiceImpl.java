package com.schedule.app.applicationservice.impl;

import org.springframework.stereotype.Service;

import com.schedule.app.applicationservice.PostRegularScheduleService;
import com.schedule.app.domainservice.RegularScheduleService;
import com.schedule.app.entity.RegularSchedule;
import com.schedule.app.form.RegularScheduleForm;
import com.schedule.app.record.input.RegularScheduleInputRecord;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.repository.ScheduleCreateMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostRegularScheduleServiceImpl implements PostRegularScheduleService{
    private final ScheduleCreateMapper scheduleCreateMapper;
    private final RegularScheduleService regularScheduleService;

    public void postRegularScheduleService(RegularScheduleForm form){
        RegularSchedule regularSchedule = toRegularScheduleEntity(form);
        RegularScheduleInputRecord record = toRegularScheduleRecord(regularSchedule);
        postRegularSchedule(record);
    }

    public RegularSchedule toRegularScheduleEntity(RegularScheduleForm form){
        RegularSchedule entity = RegularSchedule.builder()
                                        .userId("00001") //ログイン機能を使用するか仮に
                                        .startTime(form.startTime())
                                        .endTime(form.endTime())
                                        .startDate(form.startDate())
                                        .endDate(form.endDate())
                                        .dayOfWeek(form.dayOfWeek().name())
                                        .intervalWeeks(form.intervalWeeks())
                                        .workTypeId(form.workTypeId())
                                        .build();
        return entity;
    }
    
    public RegularScheduleInputRecord toRegularScheduleRecord(RegularSchedule regularSchedule){

        ScheduleSearchRecord scheduleSearchRecord = ScheduleSearchRecord.builder()
                                        .from(regularSchedule.getStartDate())
                                        .to(regularSchedule.getEndDate())
                                        .build();

        regularScheduleService.checkRegularSchedule(scheduleSearchRecord,regularSchedule);

        RegularScheduleInputRecord record = RegularScheduleInputRecord.builder()
                                                .userId("00001") //ログイン機能を使用するか仮に
                                                .startTime(regularSchedule.getStartTime())
                                                .endTime(regularSchedule.getEndTime())
                                                .startDate(regularSchedule.getStartDate())
                                                .endDate(regularSchedule.getEndDate())
                                                .dayOfWeek(regularSchedule.getDayOfWeek())
                                                .intervalWeeks(regularSchedule.getIntervalWeeks())
                                                .workTypeId(regularSchedule.getWorkTypeId())
                                                .build();

        return record;
    }

    public void postRegularSchedule(RegularScheduleInputRecord record) {
        scheduleCreateMapper.createRegularSchedule(record);
    }
}
