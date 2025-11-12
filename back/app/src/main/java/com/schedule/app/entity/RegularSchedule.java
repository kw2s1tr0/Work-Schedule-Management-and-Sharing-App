package com.schedule.app.entity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegularSchedule {
    private Integer id;
    private String userId;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private DayOfWeek dayOfWeek;
    private Integer intervalWeeks;
    private String workTypeId;

    public boolean checkId(RegularSchedule record){
        if (this.id != record.getId()) {
            throw new IllegalArgumentException("The ID does not match.");
        }
        return true;
    }

    public boolean checkUserId(RegularSchedule record){
        if (!this.userId.equals(record.getUserId())) {
            throw new IllegalArgumentException("The User ID does not match.");
        }
        return true;
    }
}
