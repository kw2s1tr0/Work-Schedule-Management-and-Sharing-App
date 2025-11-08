package com.schedule.app.unit.entity.item;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.schedule.app.entity.item.Schedule;
import com.schedule.app.enums.DaysOfWeek;
import com.schedule.app.enums.ScheduleType;
import com.schedule.app.record.output.item.DefaultScheduleRecord;
import com.schedule.app.record.output.item.IrregularScheduleRecord;
import com.schedule.app.record.output.item.RegularScheduleRecord;

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
        IrregularScheduleRecord record = new IrregularScheduleRecord(
                1,
                LocalTime.of(9, 0),
                LocalTime.of(18, 0),
                testDate,
                "出社",
                "#4CAF50"
        );

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
        IrregularScheduleRecord record = new IrregularScheduleRecord(
                1,
                LocalTime.of(9, 0),
                LocalTime.of(18, 0),
                testDate.plusDays(1), // 異なる日付
                "出社",
                "#4CAF50"
        );

        // When
        boolean result = schedule.matches(record, ScheduleType.IRREGULAR);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("定期スケジュール - 条件が全て一致する場合はtrueを返し、フィールドが設定される")
    void testRegularScheduleMatches_AllConditionsMatch_ReturnsTrue() {
        // Given
        RegularScheduleRecord record = new RegularScheduleRecord(
                2,
                LocalTime.of(10, 0),
                LocalTime.of(19, 0),
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31),
                DaysOfWeek.MONDAY,
                1,
                "在宅勤務",
                "#2196F3"
        );

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
        RegularScheduleRecord record = new RegularScheduleRecord(
                2,
                LocalTime.of(10, 0),
                LocalTime.of(19, 0),
                LocalDate.of(2025, 2, 1), // testDateより後の開始日
                LocalDate.of(2025, 12, 31),
                DaysOfWeek.MONDAY,
                1,
                "在宅勤務",
                "#2196F3"
        );

        // When
        boolean result = schedule.matches(record, ScheduleType.REGULAR);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("定期スケジュール - 終了日が過去の場合はfalseを返す")
    void testRegularScheduleMatches_EndDateInPast_ReturnsFalse() {
        // Given
        RegularScheduleRecord record = new RegularScheduleRecord(
                2,
                LocalTime.of(10, 0),
                LocalTime.of(19, 0),
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 12, 31), // testDateより前の終了日
                DaysOfWeek.MONDAY,
                1,
                "在宅勤務",
                "#2196F3"
        );

        // When
        boolean result = schedule.matches(record, ScheduleType.REGULAR);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("定期スケジュール - 曜日が一致しない場合はfalseを返す")
    void testRegularScheduleMatches_DayOfWeekDoesNotMatch_ReturnsFalse() {
        // Given
        RegularScheduleRecord record = new RegularScheduleRecord(
                2,
                LocalTime.of(10, 0),
                LocalTime.of(19, 0),
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31),
                DaysOfWeek.TUESDAY, // 月曜日のtestDateと一致しない
                1,
                "在宅勤務",
                "#2196F3"
        );

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

        RegularScheduleRecord record = new RegularScheduleRecord(
                3,
                LocalTime.of(9, 0),
                LocalTime.of(17, 0),
                startDate,
                LocalDate.of(2025, 12, 31),
                DaysOfWeek.MONDAY,
                2, // 2週間間隔
                "出社",
                "#4CAF50"
        );

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

        RegularScheduleRecord record = new RegularScheduleRecord(
                3,
                LocalTime.of(9, 0),
                LocalTime.of(17, 0),
                startDate,
                LocalDate.of(2025, 12, 31),
                DaysOfWeek.MONDAY,
                2, // 2週間間隔
                "出社",
                "#4CAF50"
        );

        // When
        boolean result = biweeklySchedule.matches(record, ScheduleType.REGULAR);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("デフォルトスケジュール - 日付が範囲内の場合はtrueを返し、フィールドが設定される")
    void testDefaultScheduleMatches_DateInRange_ReturnsTrue() {
        // Given
        DefaultScheduleRecord record = new DefaultScheduleRecord(
                4,
                LocalTime.of(8, 30),
                LocalTime.of(17, 30),
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31),
                "通常勤務",
                "#607D8B"
        );

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
        DefaultScheduleRecord record = new DefaultScheduleRecord(
                4,
                LocalTime.of(8, 30),
                LocalTime.of(17, 30),
                LocalDate.of(2025, 2, 1), // testDateより後の開始日
                LocalDate.of(2025, 12, 31),
                "通常勤務",
                "#607D8B"
        );

        // When
        boolean result = schedule.matches(record, ScheduleType.DEFAULT);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("デフォルトスケジュール - 終了日が過去の場合はfalseを返す")
    void testDefaultScheduleMatches_EndDateInPast_ReturnsFalse() {
        // Given
        DefaultScheduleRecord record = new DefaultScheduleRecord(
                4,
                LocalTime.of(8, 30),
                LocalTime.of(17, 30),
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 12, 31), // testDateより前の終了日
                "通常勤務",
                "#607D8B"
        );

        // When
        boolean result = schedule.matches(record, ScheduleType.DEFAULT);

        // Then
        assertFalse(result);
    }
}
