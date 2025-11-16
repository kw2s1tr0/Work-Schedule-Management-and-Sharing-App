package com.schedule.app.unit.entity.item;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.schedule.app.entity.Schedule;
import com.schedule.app.enums.DaysOfWeek;
import com.schedule.app.enums.ScheduleType;
import com.schedule.app.record.output.DefaultScheduleOutputRecord;
import com.schedule.app.record.output.IrregularScheduleOutputRecord;
import com.schedule.app.record.output.RegularScheduleOutputRecord;

class ScheduleTest {

    private Schedule schedule;
    private LocalDate testDate;

    @BeforeEach
    void setUp() {
        testDate = LocalDate.of(2025, 1, 6); // 月曜日
        schedule = Schedule.builder()
                .date(testDate)
                .build();
    }

    @Test
    @DisplayName("非定期スケジュール - 日付が一致する場合はtrueを返し、フィールドが設定される")
    void testIrregularScheduleMatches_DateMatches_ReturnsTrue() {
        // Given
        IrregularScheduleOutputRecord record = IrregularScheduleOutputRecord.builder()
                .userId("00001")
                .id(1)
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(18, 0))
                .date(testDate)
                .worktypeName("出社")
                .worktypeColor("#4CAF50")
                .build();

        // When
        boolean result = schedule.matches(record, ScheduleType.IRREGULAR);

        // Then
        assertTrue(result);
        assertEquals(1, schedule.getScheduleId());
        assertEquals(LocalTime.of(9, 0), schedule.getStartTime());
        assertEquals(LocalTime.of(18, 0), schedule.getEndTime());
        assertEquals("出社", schedule.getWorktypeName());
        assertEquals("#4CAF50", schedule.getWorktypeColor());
        assertEquals(ScheduleType.IRREGULAR, schedule.getScheduleType());
    }

    @Test
    @DisplayName("非定期スケジュール - 日付が一致しない場合はfalseを返す")
    void testIrregularScheduleMatches_DateDoesNotMatch_ReturnsFalse() {
        // Given
        IrregularScheduleOutputRecord record = IrregularScheduleOutputRecord.builder()
                .userId("00001")
                .id(1)
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(18, 0))
                .date(testDate.plusDays(1)) // 異なる日付
                .worktypeName("出社")
                .worktypeColor("#4CAF50")
                .build();

        // When
        boolean result = schedule.matches(record, ScheduleType.IRREGULAR);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("定期スケジュール - 条件が全て一致する場合はtrueを返し、フィールドが設定される")
    void testRegularScheduleMatches_AllConditionsMatch_ReturnsTrue() {
        // Given
        RegularScheduleOutputRecord record = RegularScheduleOutputRecord.builder()
                .userId("00001")
                .id(2)
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(19, 0))
                .startDate(LocalDate.of(2025, 1, 1))
                .endDate(LocalDate.of(2025, 12, 31))
                .daysOfWeek(DaysOfWeek.MONDAY.name())
                .intervalWeeks(1)
                .worktypeName("在宅勤務")
                .worktypeColor("#2196F3")
                .build();

        // When
        boolean result = schedule.matches(record, ScheduleType.REGULAR);

        // Then
        assertTrue(result);
        assertEquals(2, schedule.getScheduleId());
        assertEquals(LocalTime.of(10, 0), schedule.getStartTime());
        assertEquals(LocalTime.of(19, 0), schedule.getEndTime());
        assertEquals("在宅勤務", schedule.getWorktypeName());
        assertEquals("#2196F3", schedule.getWorktypeColor());
        assertEquals(ScheduleType.REGULAR, schedule.getScheduleType());
    }

    @Test
    @DisplayName("定期スケジュール - 日付が範囲外の場合はfalseを返す")
    void testRegularScheduleMatches_DateOutOfRange_ReturnsFalse() {
        // Given
        RegularScheduleOutputRecord record = RegularScheduleOutputRecord.builder()
                .userId("00001")
                .id(2)
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(19, 0))
                .startDate(LocalDate.of(2025, 2, 1)) // testDateより後の開始日
                .endDate(LocalDate.of(2025, 12, 31))
                .daysOfWeek(DaysOfWeek.MONDAY.name())
                .intervalWeeks(1)
                .worktypeName("在宅勤務")
                .worktypeColor("#2196F3")
                .build();

        // When
        boolean result = schedule.matches(record, ScheduleType.REGULAR);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("定期スケジュール - 終了日が過去の場合はfalseを返す")
    void testRegularScheduleMatches_EndDateInPast_ReturnsFalse() {
        // Given
        RegularScheduleOutputRecord record = RegularScheduleOutputRecord.builder()
                .userId("00001")
                .id(2)
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(19, 0))
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 12, 31)) // testDateより前の終了日
                .daysOfWeek(DaysOfWeek.MONDAY.name())
                .intervalWeeks(1)
                .worktypeName("在宅勤務")
                .worktypeColor("#2196F3")
                .build();

        // When
        boolean result = schedule.matches(record, ScheduleType.REGULAR);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("定期スケジュール - 曜日が一致しない場合はfalseを返す")
    void testRegularScheduleMatches_DayOfWeekDoesNotMatch_ReturnsFalse() {
        // Given
        RegularScheduleOutputRecord record = RegularScheduleOutputRecord.builder()
                .userId("00001")
                .id(2)
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(19, 0))
                .startDate(LocalDate.of(2025, 1, 1))
                .endDate(LocalDate.of(2025, 12, 31))
                .daysOfWeek(DaysOfWeek.TUESDAY.name()) // 月曜日のtestDateと一致しない
                .intervalWeeks(1)
                .worktypeName("在宅勤務")
                .worktypeColor("#2196F3")
                .build();

        // When
        boolean result = schedule.matches(record, ScheduleType.REGULAR);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("定期スケジュール - 2週間間隔で該当週の場合はtrueを返す")
    void testRegularScheduleMatches_BiweeklyValidWeek_ReturnsTrue() {
        // Given
        LocalDate startDate = LocalDate.of(2025, 1, 6); // 月曜日
        LocalDate targetDate = LocalDate.of(2025, 1, 20); // 2週間後の月曜日
        
        Schedule biweeklySchedule = Schedule.builder()
                .date(targetDate)
                .build();

        RegularScheduleOutputRecord record = RegularScheduleOutputRecord.builder()
                .userId("00001")
                .id(3)
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(17, 0))
                .startDate(startDate)
                .endDate(LocalDate.of(2025, 12, 31))
                .daysOfWeek(DaysOfWeek.MONDAY.name())
                .intervalWeeks(2) // 2週間間隔
                .worktypeName("出社")
                .worktypeColor("#4CAF50")
                .build();

        // When
        boolean result = biweeklySchedule.matches(record, ScheduleType.REGULAR);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("定期スケジュール - 2週間間隔で該当しない週の場合はfalseを返す")
    void testRegularScheduleMatches_BiweeklyInvalidWeek_ReturnsFalse() {
        // Given
        LocalDate startDate = LocalDate.of(2025, 1, 6); // 月曜日
        LocalDate targetDate = LocalDate.of(2025, 1, 13); // 1週間後の月曜日（該当しない）
        
        Schedule biweeklySchedule = Schedule.builder()
                .date(targetDate)
                .build();

        RegularScheduleOutputRecord record = RegularScheduleOutputRecord.builder()
                .userId("00001")
                .id(3)
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(17, 0))
                .startDate(startDate)
                .endDate(LocalDate.of(2025, 12, 31))
                .daysOfWeek(DaysOfWeek.MONDAY.name())
                .intervalWeeks(2) // 2週間間隔
                .worktypeName("出社")
                .worktypeColor("#4CAF50")
                .build();

        // When
        boolean result = biweeklySchedule.matches(record, ScheduleType.REGULAR);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("デフォルトスケジュール - 日付が範囲内の場合はtrueを返し、フィールドが設定される")
    void testDefaultScheduleMatches_DateInRange_ReturnsTrue() {
        // Given
        DefaultScheduleOutputRecord record = DefaultScheduleOutputRecord.builder()
                .userId("00001")
                .id(4)
                .startTime(LocalTime.of(8, 30))
                .endTime(LocalTime.of(17, 30))
                .startDate(LocalDate.of(2025, 1, 1))
                .endDate(LocalDate.of(2025, 12, 31))
                .worktypeName("通常勤務")
                .worktypeColor("#607D8B")
                .build();

        // When
        boolean result = schedule.matches(record, ScheduleType.DEFAULT);

        // Then
        assertTrue(result);
        assertEquals(4, schedule.getScheduleId());
        assertEquals(LocalTime.of(8, 30), schedule.getStartTime());
        assertEquals(LocalTime.of(17, 30), schedule.getEndTime());
        assertEquals("通常勤務", schedule.getWorktypeName());
        assertEquals("#607D8B", schedule.getWorktypeColor());
        assertEquals(ScheduleType.DEFAULT, schedule.getScheduleType());
    }

    @Test
    @DisplayName("デフォルトスケジュール - 日付が範囲外の場合はfalseを返す")
    void testDefaultScheduleMatches_DateOutOfRange_ReturnsFalse() {
        // Given
        DefaultScheduleOutputRecord record = DefaultScheduleOutputRecord.builder()
                .userId("00001")
                .id(4)
                .startTime(LocalTime.of(8, 30))
                .endTime(LocalTime.of(17, 30))
                .startDate(LocalDate.of(2025, 2, 1)) // testDateより後の開始日
                .endDate(LocalDate.of(2025, 12, 31))
                .worktypeName("通常勤務")
                .worktypeColor("#607D8B")
                .build();

        // When
        boolean result = schedule.matches(record, ScheduleType.DEFAULT);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("デフォルトスケジュール - 終了日が過去の場合はfalseを返す")
    void testDefaultScheduleMatches_EndDateInPast_ReturnsFalse() {
        // Given
        DefaultScheduleOutputRecord record = DefaultScheduleOutputRecord.builder()
                .userId("00001")
                .id(4)
                .startTime(LocalTime.of(8, 30))
                .endTime(LocalTime.of(17, 30))
                .startDate(LocalDate.of(2024, 1, 1))
                .endDate(LocalDate.of(2024, 12, 31)) // testDateより前の終了日
                .worktypeName("通常勤務")
                .worktypeColor("#607D8B")
                .build();

        // When
        boolean result = schedule.matches(record, ScheduleType.DEFAULT);

        // Then
        assertFalse(result);
    }
}
