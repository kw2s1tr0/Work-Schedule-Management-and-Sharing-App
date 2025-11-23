package com.schedule.app.unit.entity;

import static org.junit.jupiter.api.Assertions.assertFalse;

import com.schedule.app.entity.Schedule;
import com.schedule.app.enums.ScheduleType;
import com.schedule.app.record.output.DefaultScheduleOutputRecord;
import com.schedule.app.record.output.IrregularScheduleOutputRecord;
import com.schedule.app.record.output.RegularScheduleOutputRecord;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ScheduleTest {
  private LocalDate date;
  private Schedule schedule;

  @BeforeEach
  public void setUp() {
    date = LocalDate.now();
    schedule = Schedule.builder().date(date).build();
  }

  @Test
  @DisplayName("matches - Irregularの日付が異なる際のテスト")
  public void matches_Irregular_date() {
    IrregularScheduleOutputRecord record =
        IrregularScheduleOutputRecord.builder().date(date.minusDays(1)).build();

    boolean result = schedule.matches(record, ScheduleType.IRREGULAR);

    assertFalse(result);
  }

  @Test
  @DisplayName("matches - Regularの期間が異なる際のテスト")
  public void matches_Regular_startDate() {
    RegularScheduleOutputRecord record =
        RegularScheduleOutputRecord.builder()
            .startDate(date.plusDays(1))
            .endDate(date.plusDays(1))
            .build();

    boolean result = schedule.matches(record, ScheduleType.REGULAR);

    assertFalse(result);
  }

  @Test
  @DisplayName("matches - Regularの期間が異なる際のテスト")
  public void matches_Regular_endDate() {
    RegularScheduleOutputRecord record =
        RegularScheduleOutputRecord.builder()
            .startDate(date.minusDays(1))
            .endDate(date.minusDays(1))
            .build();

    boolean result = schedule.matches(record, ScheduleType.REGULAR);

    assertFalse(result);
  }

  @Test
  @DisplayName("matches - Regularの曜日が異なる際のテスト")
  public void matches_Regular_dayOfWeek() {
    RegularScheduleOutputRecord record =
        RegularScheduleOutputRecord.builder()
            .startDate(date)
            .endDate(date.plusDays(1))
            .daysOfWeek(date.plusDays(1).getDayOfWeek().name())
            .build();

    boolean result = schedule.matches(record, ScheduleType.REGULAR);

    assertFalse(result);
  }

  @Test
  @DisplayName("matches - Defaultの期間が異なる際のテスト")
  public void matches_Default_startDate() {
    DefaultScheduleOutputRecord record =
        DefaultScheduleOutputRecord.builder()
            .startDate(date.plusDays(1))
            .endDate(date.plusDays(1))
            .build();

    boolean result = schedule.matches(record, ScheduleType.REGULAR);

    assertFalse(result);
  }

  @Test
  @DisplayName("matches - Defaultの期間が異なる際のテスト")
  public void matches_Default_endDate() {
    DefaultScheduleOutputRecord record =
        DefaultScheduleOutputRecord.builder()
            .startDate(date.minusDays(1))
            .endDate(date.minusDays(1))
            .build();

    boolean result = schedule.matches(record, ScheduleType.REGULAR);

    assertFalse(result);
  }
}
