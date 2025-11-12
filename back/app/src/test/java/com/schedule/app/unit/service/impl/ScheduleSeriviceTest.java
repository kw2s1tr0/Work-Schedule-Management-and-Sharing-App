package com.schedule.app.unit.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.schedule.app.applicationservice.impl.getScheduleServiceImpl;
import com.schedule.app.dto.ScheduleDTO;
import com.schedule.app.dto.UserDTO;
import com.schedule.app.entity.Schedule;
import com.schedule.app.entity.User;
import com.schedule.app.enums.DaysOfWeek;
import com.schedule.app.enums.ScheduleType;
import com.schedule.app.enums.ViewMode;
import com.schedule.app.form.ScheduleSearchForm;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.DefaultScheduleRecord;
import com.schedule.app.record.output.IrregularScheduleRecord;
import com.schedule.app.record.output.RegularScheduleRecord;
import com.schedule.app.record.output.UserRecord;

class ScheduleServiceImplTest {
    
    private getScheduleServiceImpl scheduleService;

    @BeforeEach
    void setUp() {
        scheduleService = new getScheduleServiceImpl(null, null, null);
    }

    @Test
    @DisplayName("toScheduleSearchRecord - 週表示モードの場合、正しい週の開始日と終了日が設定される")
    void testToScheduleSearchRecord_WeekMode_CalculatesCorrectDates() {
        // Given
        ScheduleSearchForm form = new ScheduleSearchForm(
                "00001",
                "2025-W02", // 2025年第2週
                null,
                ViewMode.WEEK,
                "山田太郎",
                "001"
        );

        // When
        ScheduleSearchRecord result = scheduleService.toScheduleSearchRecord(form);

        // Then
        assertEquals("00001", result.userId());
        assertEquals(LocalDate.of(2025, 1, 6), result.from()); // 第2週の月曜日
        assertEquals(LocalDate.of(2025, 1, 12), result.to()); // 第2週の日曜日
        assertEquals(Arrays.asList("山田太郎"), result.names());
        assertEquals("001", result.organizationCode());
    }

    @Test
    @DisplayName("toScheduleSearchRecord - 月表示モードの場合、正しい月の開始日と終了日が設定される")
    void testToScheduleSearchRecord_MonthMode_CalculatesCorrectDates() {
        // Given
        ScheduleSearchForm form = new ScheduleSearchForm(
                "00001",
                null,
                "2025-02", // 2025年2月
                ViewMode.MONTH,
                "佐藤花子",
                "002"
        );

        // When
        ScheduleSearchRecord result = scheduleService.toScheduleSearchRecord(form);

        // Then
        assertEquals("00001", result.userId());
        assertEquals(LocalDate.of(2025, 2, 1), result.from());
        assertEquals(LocalDate.of(2025, 2, 28), result.to());
        assertEquals(Arrays.asList("佐藤花子"), result.names());
        assertEquals("002", result.organizationCode());
    }

    @Test
    @DisplayName("toScheduleSearchRecord - 名前がスペース区切りの場合、正しくリストに分割される")
    void testToScheduleSearchRecord_NameWithSpaces_SplitsCorrectly() {
        // Given
        ScheduleSearchForm form = new ScheduleSearchForm(
                "00001",
                "2025-W01",
                null,
                ViewMode.WEEK,
                "山田　太郎　三郎", // 全角スペース混在
                "001"
        );

        // When
        ScheduleSearchRecord result = scheduleService.toScheduleSearchRecord(form);

        // Then
        assertEquals(Arrays.asList("山田", "太郎", "三郎"), result.names());
    }

    @Test
    @DisplayName("toScheduleSearchRecord - 名前が空またはnullの場合、nullが設定される")
    void testToScheduleSearchRecord_EmptyOrNullName_ReturnsNull() {
        // Given - 空文字の場合
        ScheduleSearchForm formEmpty = new ScheduleSearchForm(
                "00001",
                "2025-W01",
                null,
                ViewMode.WEEK,
                "",
                "001"
        );

        // When
        ScheduleSearchRecord resultEmpty = scheduleService.toScheduleSearchRecord(formEmpty);

        // Then
        assertNull(resultEmpty.names());

        // Given - nullの場合
        ScheduleSearchForm formNull = new ScheduleSearchForm(
                "00001",
                "2025-W01",
                null,
                ViewMode.WEEK,
                null,
                "001"
        );

        // When
        ScheduleSearchRecord resultNull = scheduleService.toScheduleSearchRecord(formNull);

        // Then
        assertNull(resultNull.names());
    }

    @Test
    @DisplayName("toUserEntityList - スケジュール優先順位に従ってスケジュールが設定される")
    void testToUserEntityList_SchedulePriority_SetsSchedulesCorrectly() {
        // Given
        LocalDate from = LocalDate.of(2025, 1, 6); // 月曜日
        LocalDate to = LocalDate.of(2025, 1, 6); // 同じ日
        
        // ユーザーレコード
        List<UserRecord> userRecords = Arrays.asList(
                UserRecord.builder()
                        .userId("00001")
                        .name("山田太郎")
                        .organizationName("開発部")
                        .build()
        );

        // 非定期スケジュール（最優先）
        List<IrregularScheduleRecord> irregularRecords = Arrays.asList(
                IrregularScheduleRecord.builder()
                        .userId("00001")
                        .id(1)
                        .startTime(LocalTime.of(10, 0))
                        .endTime(LocalTime.of(15, 0))
                        .date(from)
                        .worktypeName("有給休暇")
                        .worktypeColor("#FF9800")
                        .build()
        );

        // 定期スケジュール
        List<RegularScheduleRecord> regularRecords = Arrays.asList(
                RegularScheduleRecord.builder()
                        .userId("00001")
                        .id(2)
                        .startTime(LocalTime.of(9, 0))
                        .endTime(LocalTime.of(18, 0))
                        .startDate(from)
                        .endDate(from)
                        .daysOfWeek(DaysOfWeek.MONDAY.name())
                        .intervalWeeks(1)
                        .worktypeName("出社")
                        .worktypeColor("#4CAF50")
                        .build()
        );

        // デフォルトスケジュール
        List<DefaultScheduleRecord> defaultRecords = Arrays.asList(
                DefaultScheduleRecord.builder()
                        .userId("00001")
                        .id(3)
                        .startTime(LocalTime.of(8, 30))
                        .endTime(LocalTime.of(17, 30))
                        .startDate(from)
                        .endDate(from)
                        .worktypeName("通常勤務")
                        .worktypeColor("#607D8B")
                        .build()
        );

        // When
        List<User> result = scheduleService.toUserEntityList(
                defaultRecords, regularRecords, irregularRecords,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                from, to, userRecords
        );

        // Then
        assertEquals(1, result.size());
        User user = result.get(0);
        assertEquals("山田太郎", user.getUserName());
        assertEquals("開発部", user.getOrganizationName());
        assertEquals(1, user.getSchedules().size());
        
        Schedule schedule = user.getSchedules().get(0);
        assertEquals(1, schedule.getScheduleId()); // 非定期スケジュールが優先される
        assertEquals("有給休暇", schedule.getWorktypeName());
        assertEquals(ScheduleType.IRREGULAR, schedule.getScheduleType());
    }

    @Test
    @DisplayName("toUserEntityList - ユーザーレコードが空の場合、空のリストを返す")
    void testToUserEntityList_EmptyUserRecords_ReturnsEmptyList() {
        // Given
        LocalDate from = LocalDate.of(2025, 1, 6);
        LocalDate to = LocalDate.of(2025, 1, 6);
        List<UserRecord> emptyUserRecords = new ArrayList<>();

        // When
        List<User> result = scheduleService.toUserEntityList(
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                from, to, emptyUserRecords
        );

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("toUserEntityList - 該当スケジュールがない場合、日付だけ設定されたスケジュールが追加される")
    void testToUserEntityList_NoMatchingSchedule_AddsScheduleWithDateOnly() {
        // Given
        LocalDate from = LocalDate.of(2025, 1, 6);
        LocalDate to = LocalDate.of(2025, 1, 6);
        
        List<UserRecord> userRecords = Arrays.asList(
                UserRecord.builder()
                        .userId("00001")
                        .name("山田太郎")
                        .organizationName("開発部")
                        .build()
        );

        // 空のスケジュールリスト
        List<DefaultScheduleRecord> defaultRecords = new ArrayList<>();
        List<RegularScheduleRecord> regularRecords = new ArrayList<>();
        List<IrregularScheduleRecord> irregularRecords = new ArrayList<>();

        // When
        List<User> result = scheduleService.toUserEntityList(
                defaultRecords, regularRecords, irregularRecords,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                from, to, userRecords
        );

        // Then
        assertEquals(1, result.size());
        User user = result.get(0);
        assertEquals(1, user.getSchedules().size());
        
        Schedule schedule = user.getSchedules().get(0);
        assertEquals(from, schedule.getDate());
        assertEquals(0, schedule.getScheduleId()); // デフォルト値
        assertNull(schedule.getStartTime());
        assertNull(schedule.getEndTime());
        assertNull(schedule.getWorktypeName());
        assertNull(schedule.getWorktypeColor());
        assertNull(schedule.getScheduleType());
    }

    @Test
    @DisplayName("toUserDTOList - ユーザーエンティティリストが空の場合、空のリストを返す")
    void testToUserDTOList_EmptyUserList_ReturnsEmptyList() {
        // Given
        List<User> emptyUserList = new ArrayList<>();

        // When
        List<UserDTO> result = scheduleService.toUserDTOList(emptyUserList);

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("toUserDTOList - ユーザーエンティティリストを正しくDTOリストに変換する")
    void testToUserDTOList_ValidUserList_ConvertsToDTO() {
        // Given
        Schedule schedule = Schedule.builder()
                .scheduleId(1)
                .date(LocalDate.of(2025, 1, 6))
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(18, 0))
                .worktypeName("出社")
                .worktypeColor("#4CAF50")
                .scheduleType(ScheduleType.REGULAR)
                .build();

        User user = User.builder()
                .userName("山田太郎")
                .organizationName("開発部")
                .schedules(Arrays.asList(schedule))
                .build();

        List<User> userList = Arrays.asList(user);

        // When
        List<UserDTO> result = scheduleService.toUserDTOList(userList);

        // Then
        assertEquals(1, result.size());
        UserDTO userDTO = result.get(0);
        assertEquals("山田太郎", userDTO.userName());
        assertEquals("開発部", userDTO.organizationName());
        assertEquals(1, userDTO.schedules().size());
        
        ScheduleDTO scheduleDTO = userDTO.schedules().get(0);
        assertEquals(1, scheduleDTO.scheduleId());
        assertEquals(LocalDate.of(2025, 1, 6), scheduleDTO.date());
        assertEquals(LocalTime.of(9, 0), scheduleDTO.startTime());
        assertEquals(LocalTime.of(18, 0), scheduleDTO.endTime());
        assertEquals("出社", scheduleDTO.worktypeName());
        assertEquals("#4CAF50", scheduleDTO.worktypeColor());
        assertEquals(ScheduleType.REGULAR, scheduleDTO.scheduleType());
    }

    @Test
    @DisplayName("toUserEntityList - 複数日のスケジュールが正しく生成される")
    void testToUserEntityList_MultipleDays_CreatesSchedulesCorrectly() {
        // Given
        LocalDate from = LocalDate.of(2025, 1, 6); // 月曜日
        LocalDate to = LocalDate.of(2025, 1, 8);   // 水曜日
        
        List<UserRecord> userRecords = Arrays.asList(
                UserRecord.builder()
                        .userId("00001")
                        .name("山田太郎")
                        .organizationName("開発部")
                        .build()
        );

        // 月水金のスケジュール
        List<RegularScheduleRecord> regularRecords = Arrays.asList(
                RegularScheduleRecord.builder()
                        .userId("00001")
                        .id(1)
                        .startTime(LocalTime.of(9, 0))
                        .endTime(LocalTime.of(18, 0))
                        .startDate(from)
                        .endDate(to)
                        .daysOfWeek(DaysOfWeek.MONDAY.name())
                        .intervalWeeks(1)
                        .worktypeName("出社")
                        .worktypeColor("#4CAF50")
                        .build(),
                RegularScheduleRecord.builder()
                        .userId("00001")
                        .id(2)
                        .startTime(LocalTime.of(9, 0))
                        .endTime(LocalTime.of(18, 0))
                        .startDate(from)
                        .endDate(to)
                        .daysOfWeek(DaysOfWeek.WEDNESDAY.name())
                        .intervalWeeks(1)
                        .worktypeName("在宅")
                        .worktypeColor("#2196F3")
                        .build()
        );

        // When
        List<User> result = scheduleService.toUserEntityList(
                new ArrayList<>(), regularRecords, new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                from, to, userRecords
        );

        // Then
        assertEquals(1, result.size());
        User user = result.get(0);
        assertEquals(3, user.getSchedules().size()); // 3日分

        // 月曜日（出社）
        Schedule mondaySchedule = user.getSchedules().get(0);
        assertEquals("出社", mondaySchedule.getWorktypeName());
        assertEquals(ScheduleType.REGULAR, mondaySchedule.getScheduleType());

        // 火曜日（スケジュールなし）
        Schedule tuesdaySchedule = user.getSchedules().get(1);
        assertEquals(LocalDate.of(2025, 1, 7), tuesdaySchedule.getDate());
        assertNull(tuesdaySchedule.getWorktypeName());

        // 水曜日（在宅）
        Schedule wednesdaySchedule = user.getSchedules().get(2);
        assertEquals("在宅", wednesdaySchedule.getWorktypeName());
        assertEquals(ScheduleType.REGULAR, wednesdaySchedule.getScheduleType());
    }
}
