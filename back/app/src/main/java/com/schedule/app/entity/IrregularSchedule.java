package com.schedule.app.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import com.schedule.app.record.output.IrregularScheduleRecord;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IrregularSchedule {
    private Integer id;
    private String userId;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private String workTypeId;

    public boolean checkId(IrregularScheduleRecord record){
        if (this.id != record.getId()) {
            throw new IllegalArgumentException("The ID does not match.");
        }
        return true;
    }

    public boolean checkUserId(IrregularScheduleRecord record){
        if (!this.userId.equals(record.getUserId())) {
            throw new IllegalArgumentException("The User ID does not match.");
        }
        return true;
    }
}
