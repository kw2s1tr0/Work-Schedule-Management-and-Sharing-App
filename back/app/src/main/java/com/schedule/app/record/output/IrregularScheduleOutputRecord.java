package com.schedule.app.record.output;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IrregularScheduleOutputRecord {
    private String userId;
    private Integer id;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private String worktypeName;
    private String worktypeColor;
}
