package com.schedule.app.applicationservice.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.schedule.app.applicationservice.DefaultScheduleSearchService;
import com.schedule.app.dto.DefaultScheduleDTO;
import com.schedule.app.form.SingleScheduleSearchForm;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.DefaultScheduleRecord;
import com.schedule.app.repository.ScheduleSearchMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DefaultScheduleSearchServiceImpl implements DefaultScheduleSearchService {

    private final ScheduleSearchMapper scheduleSearchMapper;

    @Override
    public List<DefaultScheduleDTO> defaultScheduleSearchService(SingleScheduleSearchForm form) {
        ScheduleSearchRecord record = toScheduleRecord(form);
        List<DefaultScheduleRecord> records = readDefaultSchedule(record);
        List<DefaultScheduleDTO> dtos = toScheduleDTOList(records);
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
    public List<DefaultScheduleRecord> readDefaultSchedule(ScheduleSearchRecord record) {
        List<DefaultScheduleRecord> records = scheduleSearchMapper.readDefaultScheduleRecord(record);
        return records;
    }

    @Override
    public List<DefaultScheduleDTO> toScheduleDTOList(List<DefaultScheduleRecord> records) {
        List<DefaultScheduleDTO> dtos = new ArrayList<>();
        for (DefaultScheduleRecord record : records) {
            DefaultScheduleDTO dto = DefaultScheduleDTO.builder()
                    .scheduleId(record.getId())
                    .startTime(record.getStartTime())
                    .endTime(record.getEndTime())
                    .startDate(record.getStartDate())
                    .endDate(record.getEndDate())
                    .worktypeName(record.getWorktypeName())
                    .worktypeColor(record.getWorktypeColor())
                    .build();
            dtos.add(dto);
        }
        return dtos;
    }

}