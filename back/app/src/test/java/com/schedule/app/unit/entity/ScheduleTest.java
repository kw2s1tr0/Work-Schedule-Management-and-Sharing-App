package com.schedule.app.unit.entity;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;

import com.schedule.app.entity.Schedule;

public class ScheduleTest {
    private LocalDate date;
    private Schedule schedule;

    @BeforeEach
    void setUp() {
        date = LocalDate.of(2024, 6, 15);
        schedule = Schedule.builder()
                .date(date)
                .build();
    }

    
}
