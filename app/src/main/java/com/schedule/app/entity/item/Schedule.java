package com.schedule.app.entity.item;

import java.time.LocalDate;
import java.time.LocalTime;

import com.schedule.app.enums.ScheduleType;
import com.schedule.app.record.output.item.DefaultScheduleRecord;
import com.schedule.app.record.output.item.IrregularScheduleRecord;
import com.schedule.app.record.output.item.RegularScheduleRecord;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Schedule {
    private int scheduleId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String worktypeName;
    private String worktypeColor;
    private ScheduleType scheduleType;

    public boolean matches(IrregularScheduleRecord record, ScheduleType scheduleType){
        if (!date.equals(record.date())) {
            return false;
        }

        this.scheduleId = record.scheduleId();
        this.startTime = record.startTime();
        this.endTime = record.endTime();
        this.worktypeName = record.worktypeName();
        this.worktypeColor = record.worktypeColor();
        this.scheduleType = scheduleType;
        return true;
    }

    public boolean matches(RegularScheduleRecord record, ScheduleType scheduleType){
        if (date.isBefore(record.startDate()) || date.isAfter(record.endDate())) {
            return false;
        }

        if(!record.daysOfWeek().name().equals(date.getDayOfWeek().name())) {
            return false;
        }

        if (record.intervalWeeks() == 2) {
            LocalDate startDate = record.startDate();
            long weeksBetween = java.time.temporal.ChronoUnit.WEEKS.between(
            startDate.with(java.time.DayOfWeek.MONDAY),
            date.with(java.time.DayOfWeek.MONDAY));
            if (weeksBetween % 2 != 0) {
                return false;
            }
        }

        this.scheduleId = record.scheduleId();
        this.startTime = record.startTime();
        this.endTime = record.endTime();
        this.worktypeName = record.worktypeName();
        this.worktypeColor = record.worktypeColor();
        this.scheduleType = scheduleType;
        return true;
    }

    public boolean matches(DefaultScheduleRecord record, ScheduleType scheduleType){
        if (date.isBefore(record.startDate()) || date.isAfter(record.endDate())) {
            return false;
        }

        this.scheduleId = record.scheduleId();
        this.startTime = record.startTime();
        this.endTime = record.endTime();
        this.worktypeName = record.worktypeName();
        this.worktypeColor = record.worktypeColor();
        this.scheduleType = scheduleType;
        return true;
    }
}
