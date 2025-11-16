package com.schedule.app.domainservice;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.schedule.app.entity.RegularSchedule;
import com.schedule.app.record.output.RegularScheduleOutputRecord;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.repository.ScheduleExistMapper;
import com.schedule.app.repository.ScheduleSearchMapper;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegularScheduleService {

    private final ScheduleExistMapper scheduleExistMapper;
    private final ScheduleSearchMapper scheduleSearchMapper;

    public void existRegularSchedule(int scheduleId, int userId) {
        if (!scheduleExistMapper.existRegularSchedule(scheduleId, userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Regular schedule already exist for ID: " + scheduleId);
        }
    }

    public boolean checkRegularSchedule(ScheduleSearchRecord scheduleSearchRecord,RegularSchedule regularSchedule) {
        List<RegularScheduleOutputRecord> records = readRegularSchedule(scheduleSearchRecord);
        if (!records.isEmpty()) {
            for (RegularScheduleOutputRecord record : records) {
                if(regularSchedule.isOverlaps(record)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No regular schedules found for the given criteria.");
                }
            }
        }
        return true;
    }

    public List<RegularScheduleOutputRecord> readRegularSchedule(ScheduleSearchRecord record){
        List<RegularScheduleOutputRecord> records = scheduleSearchMapper.readRegularScheduleRecord(record);
        return records;
    }
}
