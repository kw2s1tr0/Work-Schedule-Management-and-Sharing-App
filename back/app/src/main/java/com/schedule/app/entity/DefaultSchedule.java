package com.schedule.app.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.schedule.app.record.output.DefaultScheduleRecord;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DefaultSchedule {
    private Integer id;
    private String userId;
    private LocalTime starTime;
    private LocalTime endTime;
    private LocalDate starDate;
    private LocalDate endDate;
    private String workTypeId;

    public boolean checkId(DefaultScheduleRecord record){
        if (this.id != record.getId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The ID does not match.");
        }
        return true;
    }

    public boolean checkUserId(DefaultScheduleRecord record){
        if (!this.userId.equals(record.getUserId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The User ID does not match.");
        }
        return true;
    }
}
