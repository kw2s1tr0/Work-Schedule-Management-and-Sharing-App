package com.schedule.app.integration.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.schedule.app.applicationservice.ScheduleService;
import com.schedule.app.dto.UserDTO;
import com.schedule.app.dto.item.ScheduleDTO;
import com.schedule.app.enums.ScheduleType;
import com.schedule.app.enums.ViewMode;
import com.schedule.app.form.ScheduleSearchForm;

@SpringBootTest
@Transactional
public class ScheduleServiceImplIntegrationTest {

    @Autowired
    private ScheduleService scheduleService;

    @Test
    @DisplayName("scheduleSearchService - 週表示モードでユーザー固有のスケジュール検索が正常に動作する")
    void testScheduleSearchService_WeekMode_UserSpecificSearch_ReturnsCorrectSchedules() {
        // Given - 2025年第2週（1月6日〜1月12日）の山田太郎のスケジュールを検索
        ScheduleSearchForm form = new ScheduleSearchForm(
                "00001",        // 山田太郎のユーザーID
                "2025-W02",     // 2025年第2週
                null,
                ViewMode.WEEK,
                "山田太郎",
                null
        );

        // When
        List<UserDTO> result = scheduleService.scheduleSearchService(form);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());

        UserDTO userDTO = result.get(0);
        assertEquals("山田太郎", userDTO.userName());
        assertEquals("開発部", userDTO.organizationName());
        assertEquals(7, userDTO.schedules().size()); // 週7日分

        // 平日（月〜金）は定期スケジュールが適用される
        for (int i = 0; i < 5; i++) { // 月曜日から金曜日
            ScheduleDTO schedule = userDTO.schedules().get(i);
            assertEquals(LocalDate.of(2025, 1, 6).plusDays(i), schedule.date());
            assertEquals(LocalTime.of(9, 0), schedule.startTime());
            assertEquals(LocalTime.of(18, 0), schedule.endTime());
            assertEquals("在宅勤務", schedule.worktypeName());
            assertEquals("#2196F3", schedule.worktypeColor());
            assertEquals(ScheduleType.REGULAR, schedule.scheduleType());
        }

        // 土日は共通定期スケジュール（休日）が適用される
        for (int i = 5; i < 7; i++) { // 土曜日、日曜日
            ScheduleDTO schedule = userDTO.schedules().get(i);
            assertEquals(LocalDate.of(2025, 1, 6).plusDays(i), schedule.date());
            assertNull(schedule.startTime());
            assertNull(schedule.endTime());
            assertEquals("休日", schedule.worktypeName()); 
            assertEquals("#E91E63", schedule.worktypeColor());
            assertEquals(ScheduleType.COMMON_REGULAR, schedule.scheduleType());
        }
    }

    @Test
    @DisplayName("scheduleSearchService - 月表示モードで組織検索が正常に動作する")
    void testScheduleSearchService_MonthMode_OrganizationSearch_ReturnsCorrectSchedules() {
        // Given - 2025年1月の開発部（006）のスケジュールを検索
        ScheduleSearchForm form = new ScheduleSearchForm(
                null,
                null,
                "2025-01",      // 2025年1月
                ViewMode.MONTH,
                null,
                "006"           // 開発部
        );

        // When
        List<UserDTO> result = scheduleService.scheduleSearchService(form);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size()); // 山田太郎と佐藤花子

        // 山田太郎のスケジュール確認
        UserDTO yamadaUser = result.stream()
                .filter(user -> "山田太郎".equals(user.userName()))
                .findFirst()
                .orElse(null);
        assertNotNull(yamadaUser);
        assertEquals("開発部", yamadaUser.organizationName());
        assertEquals(31, yamadaUser.schedules().size()); // 1月は31日

        // 佐藤花子のスケジュール確認（2週間間隔の定期スケジュール）
        UserDTO satoUser = result.stream()
                .filter(user -> "佐藤花子".equals(user.userName()))
                .findFirst()
                .orElse(null);
        assertNotNull(satoUser);
        assertEquals("開発部", satoUser.organizationName());
        assertEquals(31, satoUser.schedules().size()); // 1月は31日
    }
}
