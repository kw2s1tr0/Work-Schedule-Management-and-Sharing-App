package com.schedule.app.domainservice;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.IrregularScheduleOutputRecord;
import com.schedule.app.repository.ScheduleExistMapper;
import com.schedule.app.repository.ScheduleSearchMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class IrregularScheduleService {

    private final ScheduleExistMapper scheduleExistMapper;
    private final ScheduleSearchMapper scheduleSearchMapper;

    public boolean existIrregularSchedule(int scheduleId, String userId) {
        if (!scheduleExistMapper.existIrregularSchedule(scheduleId, userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Irregular schedule already exist for ID: " + scheduleId);
        }
        return true;
    }

    public boolean checkIrregularSchedule(ScheduleSearchRecord record) {
        List<IrregularScheduleOutputRecord> records = readIrregularSchedule(record);
        if (!records.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No irregular schedules found for the given criteria.");
        }
        return true;
    }

    public List<IrregularScheduleOutputRecord> readIrregularSchedule(ScheduleSearchRecord record){
        List<IrregularScheduleOutputRecord> records = scheduleSearchMapper.readIrregularScheduleRecord(record);
        return records;
    }
}
