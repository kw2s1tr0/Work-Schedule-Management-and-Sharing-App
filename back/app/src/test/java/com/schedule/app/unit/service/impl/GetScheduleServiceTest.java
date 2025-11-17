package com.schedule.app.unit.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.schedule.app.applicationservice.GetScheduleService;
import com.schedule.app.applicationservice.impl.GetScheduleServiceImpl;
import com.schedule.app.enums.ViewMode;
import com.schedule.app.form.ScheduleSearchForm;
import com.schedule.app.record.input.ScheduleSearchRecord;

public class GetScheduleServiceTest {
    GetScheduleService getScheduleService;

    @BeforeEach
    public void setUp() {
         getScheduleService = new GetScheduleServiceImpl(null, null, null, null);
    }

    @Test
    @DisplayName("toScheduleSearchRecord - viewModeがWeekの場合のテスト")
    public void testToScheduleSearchRecord_viewModeWeek() {
        ScheduleSearchForm form = ScheduleSearchForm.builder()
                .week("2025-W02")
                .viewMode(ViewMode.WEEK)
                .build();
        
        ScheduleSearchRecord record = getScheduleService.toScheduleSearchRecord(form);

        assertEquals(LocalDate.of(2025, 1, 6), record.from());
        assertEquals(LocalDate.of(2025, 1, 12), record.to());
    }

    @Test
    @DisplayName("toScheduleSearchRecord - viewModeがMonthの場合のテスト")
    public void testToScheduleSearchRecord_viewModeMonth() {
        ScheduleSearchForm form = ScheduleSearchForm.builder()
                .month("2025-03")
                .viewMode(ViewMode.MONTH)
                .build();
        
        ScheduleSearchRecord record = getScheduleService.toScheduleSearchRecord(form);

        assertEquals(LocalDate.of(2025, 3, 1), record.from());
        assertEquals(LocalDate.of(2025, 3, 31), record.to());
    }

    @Test
    @DisplayName("toScheduleSearchRecord - nameの分割テスト")
    public void testToScheduleSearchRecord_name(){
        ScheduleSearchForm form = ScheduleSearchForm.builder()
                .name("山田 太郎")
                .viewMode(ViewMode.MONTH)
                .month("2025-03")
                .build();

        ScheduleSearchRecord record = getScheduleService.toScheduleSearchRecord(form);

        assertEquals("山田", record.names().get(0));
        assertEquals("太郎", record.names().get(1));
    }

    @Test
    @DisplayName("toScheduleSearchRecord - userIdのテスト")
    public void testToScheduleSearchRecord_userId(){
        ScheduleSearchForm form = ScheduleSearchForm.builder()
                .userId("１２３45")
                .viewMode(ViewMode.MONTH)
                .month("2025-03")
                .build();

        ScheduleSearchRecord record = getScheduleService.toScheduleSearchRecord(form);

        assertEquals("12345", record.userId());
    }
}
