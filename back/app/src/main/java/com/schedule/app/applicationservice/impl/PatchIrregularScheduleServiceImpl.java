package com.schedule.app.applicationservice.impl;

import org.springframework.stereotype.Service;

import com.schedule.app.applicationservice.PatchIrregularScheduleService;
import com.schedule.app.domainservice.IrregularScheduleService;
import com.schedule.app.form.IrregularScheduleForm;
import com.schedule.app.record.input.IrregularScheduleInputRecord;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.repository.ScheduleUpdateMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatchIrregularScheduleServiceImpl implements PatchIrregularScheduleService{
    
    private final ScheduleUpdateMapper scheduleUpdateMapper;
    private final IrregularScheduleService irregularScheduleService;

    public void patchIrregularScheduleService(IrregularScheduleForm form){
        IrregularScheduleInputRecord record = toIrregularScheduleRecord(form);
        patchIrregularSchedule(record);
    }

    public IrregularScheduleInputRecord toIrregularScheduleRecord(IrregularScheduleForm form){

        String userId = "00001"; //ログイン機能を使用するか仮に

        irregularScheduleService.existIrregularSchedule(form.id(), userId);

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

    public void patchIrregularSchedule(IrregularScheduleInputRecord record) {
        scheduleUpdateMapper.updateIrregularSchedule(record);
    }
}
