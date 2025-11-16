package com.schedule.app.applicationservice.impl;

import org.springframework.stereotype.Service;

import com.schedule.app.applicationservice.PatchDefaultScheduleService;
import com.schedule.app.domainservice.DefaultScheduleService;
import com.schedule.app.entity.DefaultSchedule;
import com.schedule.app.form.DefaultScheduleForm;
import com.schedule.app.record.input.DefaultScheduleInputRecord;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.repository.ScheduleUpdateMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatchDefaultScheduleServiceImpl implements PatchDefaultScheduleService{
    private final ScheduleUpdateMapper scheduleUpdateMapper;
    private final DefaultScheduleService defaultScheduleService;

    public void patchDefaultScheduleService(DefaultScheduleForm form){
        DefaultSchedule DefaultSchedule = toDefaultScheduleEintity(form);
        DefaultScheduleInputRecord record = toDefaultScheduleRecord(DefaultSchedule);
        patchDefaultSchedule(record);
    }

    public DefaultSchedule toDefaultScheduleEintity(DefaultScheduleForm form){
        DefaultSchedule entity = DefaultSchedule.builder()
                                        .userId("00001") //ログイン機能を使用するか仮に
                                        .startTime(form.startTime())
                                        .endTime(form.endTime())
                                        .startDate(form.startDate())
                                        .endDate(form.endDate())
                                        .workTypeId(form.workTypeId())
                                        .build();
        return entity;
    }

    public DefaultScheduleInputRecord toDefaultScheduleRecord(DefaultSchedule DefaultSchedule){

        String userId = "00001"; //ログイン機能を使用するか仮に

        defaultScheduleService.existDefaultSchedule(DefaultSchedule.getId(), userId);

        ScheduleSearchRecord scheduleSearchRecord = ScheduleSearchRecord.builder()
                                        .from(DefaultSchedule.getStartDate())
                                        .to(DefaultSchedule.getEndDate())
                                        .build();
        
        defaultScheduleService.checkDefaultSchedule(scheduleSearchRecord,DefaultSchedule);

        DefaultScheduleInputRecord record = DefaultScheduleInputRecord.builder()
                                                .userId("00001") //ログイン機能を使用するか仮に
                                                .startTime(DefaultSchedule.getStartTime())
                                                .endTime(DefaultSchedule.getEndTime())
                                                .startDate(DefaultSchedule.getStartDate())
                                                .endDate(DefaultSchedule.getEndDate())
                                                .workTypeId(DefaultSchedule.getWorkTypeId())
                                                .build();

        return record;
    }

    public void patchDefaultSchedule(DefaultScheduleInputRecord record) {
        scheduleUpdateMapper.updateDefaultSchedule(record);
    }
}
