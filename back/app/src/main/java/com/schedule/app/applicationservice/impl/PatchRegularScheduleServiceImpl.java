package com.schedule.app.applicationservice.impl;

import org.springframework.stereotype.Service;

import com.schedule.app.applicationservice.PatchRegularScheduleService;
import com.schedule.app.domainservice.RegularScheduleService;
import com.schedule.app.entity.RegularSchedule;
import com.schedule.app.form.RegularScheduleForm;
import com.schedule.app.record.input.RegularScheduleInputRecord;
import com.schedule.app.repository.ScheduleUpdateMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatchRegularScheduleServiceImpl implements PatchRegularScheduleService{
    private final ScheduleUpdateMapper scheduleUpdateMapper;
    private final RegularScheduleService regularScheduleService;

    public void patchRegularScheduleService(RegularScheduleForm form){
        RegularSchedule regularSchedule = toRegularScheduleEntity(form);
        RegularScheduleInputRecord record = toRegularScheduleRecord(regularSchedule);
        patchRegularSchedule(record);
    }

    public RegularSchedule toRegularScheduleEntity(RegularScheduleForm form){
        RegularSchedule entity = RegularSchedule.builder()
                                        .userId("00001") //ログイン機能を使用するか仮に
                                        .startTime(form.startTime())
                                        .endTime(form.endTime())
                                        .startDate(form.startDate())
                                        .endDate(form.endDate())
                                        .workTypeId(form.workTypeId())
                                        .build();
        return entity;
    }

    public RegularScheduleInputRecord toRegularScheduleRecord(RegularSchedule regularSchedule){

        String userId = "00001"; //ログイン機能を使用するか仮に

        regularScheduleService.existRegularSchedule(regularSchedule.getId(), userId);

        RegularScheduleInputRecord record = RegularScheduleInputRecord.builder()
                                                .userId("00001") //ログイン機能を使用するか仮に
                                                .startTime(regularSchedule.getStartTime())
                                                .endTime(regularSchedule.getEndTime())
                                                .startDate(regularSchedule.getStartDate())
                                                .endDate(regularSchedule.getEndDate())
                                                .workTypeId(regularSchedule.getWorkTypeId())
                                                .build();
        return record;
    }

    public void patchRegularSchedule(RegularScheduleInputRecord record){
        scheduleUpdateMapper.updateRegularSchedule(record);
    }
}
