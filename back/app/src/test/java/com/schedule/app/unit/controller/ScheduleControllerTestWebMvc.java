package com.schedule.app.unit.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.schedule.app.applicationservice.ScheduleService;
import com.schedule.app.controller.ScheduleController;
import com.schedule.app.dto.UserDTO;
import com.schedule.app.dto.item.ScheduleDTO;
import com.schedule.app.enums.ScheduleType;

@WebMvcTest(ScheduleController.class)
class ScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScheduleService scheduleService;

    private List<UserDTO> expectedUserDTOs;

    @BeforeEach
    void setUp() {
        // テスト用のScheduleDTO
        ScheduleDTO scheduleDTO = ScheduleDTO.builder()
                .scheduleId(1)
                .date(LocalDate.of(2025, 1, 6))
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(18, 0))
                .worktypeName("出社")
                .worktypeColor("#4CAF50")
                .scheduleType(ScheduleType.REGULAR)
                .build();

        // テスト用のUserDTO
        UserDTO userDTO = UserDTO.builder()
                .userName("山田太郎")
                .organizationName("開発部")
                .schedules(Arrays.asList(scheduleDTO))
                .build();

        expectedUserDTOs = Arrays.asList(userDTO);
    }

    @Test
    @DisplayName("GET /api/schedule - 正常なパラメータでスケジュールを取得")
    void testGetSchedule_ValidParameters_ReturnsSchedules() throws Exception {
        // Given
        when(scheduleService.scheduleSearchService(any())).thenReturn(expectedUserDTOs);

        // When & Then
        mockMvc.perform(get("/api/schedule")
                .param("userId", "00001")
                .param("week", "2025-W02")
                .param("viewMode", "WEEK")
                .param("name", "山田太郎")
                .param("organizationCode", "001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].userName").value("山田太郎"))
                .andExpect(jsonPath("$[0].organizationName").value("開発部"))
                .andExpect(jsonPath("$[0].schedules[0].scheduleId").value(1))
                .andExpect(jsonPath("$[0].schedules[0].worktypeName").value("出社"))
                .andExpect(jsonPath("$[0].schedules[0].worktypeColor").value("#4CAF50"));

        verify(scheduleService, times(1)).scheduleSearchService(any());
    }

    @Test
    @DisplayName("GET /api/schedule - 月表示モードでスケジュールを取得")
    void testGetSchedule_MonthMode_ReturnsSchedules() throws Exception {
        // Given
        when(scheduleService.scheduleSearchService(any())).thenReturn(expectedUserDTOs);

        // When & Then
        mockMvc.perform(get("/api/schedule")
                .param("userId", "00002")
                .param("month", "2025-02")
                .param("viewMode", "MONTH")
                .param("name", "佐藤花子")
                .param("organizationCode", "002"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].userName").value("山田太郎"));

        verify(scheduleService, times(1)).scheduleSearchService(any());
    }

    @Test
    @DisplayName("GET /api/schedule - サービスが空リストを返す場合")
    void testGetSchedule_EmptyResult_ReturnsEmptyArray() throws Exception {
        // Given
        when(scheduleService.scheduleSearchService(any())).thenReturn(Arrays.asList());

        // When & Then
        mockMvc.perform(get("/api/schedule")
                .param("userId", "00001")
                .param("week", "2025-W02")
                .param("viewMode", "WEEK"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(scheduleService, times(1)).scheduleSearchService(any());
    }

    @Test
    @DisplayName("GET /api/schedule - 不正なweekフォーマットでバリデーションエラー")
    void testGetSchedule_InvalidWeekFormat_ReturnsBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/schedule")
                .param("userId", "00001")
                .param("week", "2025-W60") // 無効な週番号
                .param("viewMode", "WEEK"))
                .andExpect(status().isBadRequest());

        verify(scheduleService, never()).scheduleSearchService(any());
    }

    @Test
    @DisplayName("GET /api/schedule - 不正なmonthフォーマットでバリデーションエラー")
    void testGetSchedule_InvalidMonthFormat_ReturnsBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/schedule")
                .param("userId", "00001")
                .param("month", "2025-13") // 無効な月
                .param("viewMode", "MONTH"))
                .andExpect(status().isBadRequest());

        verify(scheduleService, never()).scheduleSearchService(any());
    }

    @Test
    @DisplayName("GET /api/schedule - 不正なuserIdフォーマットでバリデーションエラー")
    void testGetSchedule_InvalidUserIdFormat_ReturnsBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/schedule")
                .param("userId", "ABCDEF") // 数字以外
                .param("week", "2025-W02")
                .param("viewMode", "WEEK"))
                .andExpect(status().isBadRequest());

        verify(scheduleService, never()).scheduleSearchService(any());
    }

    @Test
    @DisplayName("GET /api/schedule - viewModeが必須パラメータ不足でバリデーションエラー")
    void testGetSchedule_MissingViewMode_ReturnsBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/schedule")
                .param("userId", "00001")
                .param("week", "2025-W02"))
                // viewMode未指定
                .andExpect(status().isBadRequest());

        verify(scheduleService, never()).scheduleSearchService(any());
    }

    @Test
    @DisplayName("GET /api/schedule - weekとmonthの両方指定でバリデーションエラー")
    void testGetSchedule_BothWeekAndMonth_ReturnsBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/schedule")
                .param("userId", "00001")
                .param("week", "2025-W02")
                .param("month", "2025-02") // 両方指定
                .param("viewMode", "WEEK"))
                .andExpect(status().isBadRequest());

        verify(scheduleService, never()).scheduleSearchService(any());
    }

    @Test
    @DisplayName("GET /api/schedule - 名前が長すぎる場合のバリデーションエラー")
    void testGetSchedule_NameTooLong_ReturnsBadRequest() throws Exception {
        // Given
        String longName = "a".repeat(51); // 51文字（制限は50文字）

        // When & Then
        mockMvc.perform(get("/api/schedule")
                .param("userId", "00001")
                .param("week", "2025-W02")
                .param("viewMode", "WEEK")
                .param("name", longName))
                .andExpect(status().isBadRequest());

        verify(scheduleService, never()).scheduleSearchService(any());
    }
}