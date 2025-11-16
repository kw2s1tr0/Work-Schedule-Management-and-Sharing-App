package com.schedule.app.applicationservice.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.schedule.app.applicationservice.getIrregularScheduleService;
import com.schedule.app.dto.IrregularScheduleDTO;
import com.schedule.app.form.SingleScheduleSearchForm;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.IrregularScheduleOutputRecord;
import com.schedule.app.repository.ScheduleSearchMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class getIrregularScheduleServiceImpl implements getIrregularScheduleService {

    private final ScheduleSearchMapper scheduleSearchMapper;

    @Override
    public List<IrregularScheduleDTO> irregularScheduleSearchService(SingleScheduleSearchForm form) {
        ScheduleSearchRecord record = toScheduleRecord(form);
        List<IrregularScheduleOutputRecord> records = readIrregularSchedule(record);
        List<IrregularScheduleDTO> dtos = toScheduleDTOList(records);
        return dtos;
    }

    @Override
    public ScheduleSearchRecord toScheduleRecord(SingleScheduleSearchForm form) {
        ScheduleSearchRecord record = ScheduleSearchRecord.builder()
                .from(form.from())
                .to(form.to())
                .build();
        return record;
    }

    @Override
    public List<IrregularScheduleOutputRecord> readIrregularSchedule(ScheduleSearchRecord record) {
        List<IrregularScheduleOutputRecord> records = scheduleSearchMapper.readIrregularScheduleRecord(record);
        return records;
    }

    @Override
    public List<IrregularScheduleDTO> toScheduleDTOList(List<IrregularScheduleOutputRecord> records) {
        List<IrregularScheduleDTO> dtos = new ArrayList<>();
        for (IrregularScheduleOutputRecord record : records) {
            IrregularScheduleDTO dto = IrregularScheduleDTO.builder()
                    .scheduleId(record.getId())
                    .startTime(record.getStartTime())
                    .endTime(record.getEndTime())
                    .worktypeName(record.getWorktypeName())
                    .worktypeColor(record.getWorktypeColor())
                    .build();
            dtos.add(dto);
        }
        return dtos;
    }
    
}
