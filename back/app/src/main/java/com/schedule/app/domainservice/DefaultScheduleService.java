package com.schedule.app.domainservice;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.schedule.app.entity.DefaultSchedule;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.DefaultScheduleOutputRecord;
import com.schedule.app.repository.ScheduleExistMapper;
import com.schedule.app.repository.ScheduleSearchMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DefaultScheduleService {

    private final ScheduleExistMapper scheduleExistMapper;
    private final ScheduleSearchMapper scheduleSearchMapper;

    public void existDefaultSchedule(int scheduleId, int userId) {
        if (!scheduleExistMapper.existDefaultSchedule(scheduleId, userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Default schedule already exist for ID: " + scheduleId);
        }
    }

    public boolean checkDefaultSchedule(ScheduleSearchRecord scheduleSearchRecord, DefaultSchedule defaultSchedule) {
        List<DefaultScheduleOutputRecord> records = readDefaultSchedule(scheduleSearchRecord);
        if (!records.isEmpty()) {
            for (DefaultScheduleOutputRecord record : records) {
                if(defaultSchedule.isOverlaps(record)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No default schedules found for the given criteria.");
                }
            }
        }
        return true;
    }

    public List<DefaultScheduleOutputRecord> readDefaultSchedule(ScheduleSearchRecord record) {
        List<DefaultScheduleOutputRecord> records = scheduleSearchMapper.readDefaultScheduleRecord(record);
        return records;
    }
}