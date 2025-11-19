package com.schedule.app.unit.entity;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.schedule.app.entity.RegularSchedule;

public class RegularScheduleTest {
    
    private LocalDate date;
    private RegularSchedule regularSchedule;

    @BeforeEach
    public void setUp() {
        date = LocalDate.now();
        regularSchedule = RegularSchedule.builder()
                .startDate(date)
                .endDate(date)
                .dayOfWeek(date.getDayOfWeek())
                .intervalWeeks(2)
                .build();
    }

    @Test
    @DisplayName("isOverlaps - Regularの曜日が異なる際のテスト")
    public void isOverlaps_dayOfWeek() {
        String dayOfWeekStr = date.plusDays(1).getDayOfWeek().name();
        
        boolean result = regularSchedule.isOverlaps(dayOfWeekStr);

        assertFalse(result);
    }
}
