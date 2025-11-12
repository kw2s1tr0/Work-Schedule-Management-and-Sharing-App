package com.schedule.app.applicationservice.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.schedule.app.applicationservice.getRegularScheduleService;
import com.schedule.app.dto.RegularScheduleDTO;
import com.schedule.app.form.SingleScheduleSearchForm;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.RegularScheduleRecord;
import com.schedule.app.repository.ScheduleSearchMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class getRegularScheduleServiceImpl implements getRegularScheduleService {

    private final ScheduleSearchMapper scheduleSearchMapper;

    @Override
    public List<RegularScheduleDTO> regularScheduleSearchService(SingleScheduleSearchForm form) {
        ScheduleSearchRecord record = toScheduleRecord(form);
        List<RegularScheduleRecord> records = readRegularSchedule(record);
        List<RegularScheduleDTO> dtos = toScheduleDTOList(records);
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
    public List<RegularScheduleRecord> readRegularSchedule(ScheduleSearchRecord record) {
        List<RegularScheduleRecord> records = scheduleSearchMapper.readRegularScheduleRecord(record);
        return records;
    }

    @Override
    public List<RegularScheduleDTO> toScheduleDTOList(List<RegularScheduleRecord> records) {
        List<RegularScheduleDTO> dtos = new ArrayList<>();
        for (RegularScheduleRecord record : records) {
            RegularScheduleDTO dto = RegularScheduleDTO.builder()
                    .scheduleId(record.getId())
                    .startTime(record.getStartTime())
                    .endTime(record.getEndTime())
                    .worktypeName(record.getWorktypeName())
                    .worktypeColor(record.getWorktypeColor())
                    .daysOfWeek(record.getDaysOfWeek())
                    .build();
            dtos.add(dto);
        }
        return dtos;
    }    
}
