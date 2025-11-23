package com.schedule.app.integration.applicationservice.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.schedule.app.applicationservice.GetScheduleService;
import com.schedule.app.dto.UserDTO;
import com.schedule.app.enums.ScheduleType;
import com.schedule.app.enums.ViewMode;
import com.schedule.app.form.ScheduleSearchForm;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GetScheduleServiceTest {

  @Autowired private GetScheduleService getScheduleService;

  @Test
  @DisplayName("scheduleSearchService - 週表示モードで組織内全ユーザーのスケジュール検索が正常に動作する")
  public void scheduleSearchService() {
    ScheduleSearchForm form =
        ScheduleSearchForm.builder()
            .week("2025-W03")
            .viewMode(ViewMode.WEEK)
            .organizationCode("006")
            .build();

    List<UserDTO> users = getScheduleService.scheduleSearchService(form);

    assertAll(
        () -> {
          for (int u = 0; u < users.size(); u++) {
            switch (u) {
              case 0:
                assertEquals("山田太郎", users.get(u).userName());
                assertEquals("開発部", users.get(u).organizationName());

                for (int i = 0; i < users.get(u).schedules().size(); i++) {
                  switch (i) {
                    case 0:
                      assertEquals(
                          LocalDate.of(2025, 1, 13), users.get(u).schedules().get(i).date());
                      assertEquals("在宅勤務", users.get(u).schedules().get(i).worktypeName());
                      assertEquals(
                          ScheduleType.REGULAR, users.get(u).schedules().get(i).scheduleType());
                      assertEquals(LocalTime.of(9, 0), users.get(u).schedules().get(i).startTime());
                      assertEquals(LocalTime.of(18, 0), users.get(u).schedules().get(i).endTime());
                      assertEquals("#2196F3", users.get(u).schedules().get(i).worktypeColor());
                      break;
                    case 1:
                      assertEquals(
                          LocalDate.of(2025, 1, 14), users.get(u).schedules().get(i).date());
                      assertEquals("出社", users.get(u).schedules().get(i).worktypeName());
                      assertEquals(
                          ScheduleType.DEFAULT, users.get(u).schedules().get(i).scheduleType());
                      assertEquals(LocalTime.of(8, 0), users.get(u).schedules().get(i).startTime());
                      assertEquals(LocalTime.of(17, 0), users.get(u).schedules().get(i).endTime());
                      assertEquals("#4CAF50", users.get(u).schedules().get(i).worktypeColor());
                      break;
                    case 2:
                      assertEquals(
                          LocalDate.of(2025, 1, 15), users.get(u).schedules().get(i).date());
                      assertEquals("有給休暇", users.get(u).schedules().get(i).worktypeName());
                      assertEquals(
                          ScheduleType.IRREGULAR, users.get(u).schedules().get(i).scheduleType());
                      assertEquals(
                          LocalTime.of(9, 0),
                          users.get(u).schedules().get(i).startTime()); // 開始・終了時刻はnull（終日休暇のため）
                      assertEquals(
                          LocalTime.of(18, 0),
                          users.get(u).schedules().get(i).endTime()); // 開始・終了時刻はnull（終日休暇のため）
                      assertEquals("#FF9800", users.get(u).schedules().get(i).worktypeColor());
                      break;
                    case 3:
                      assertEquals(
                          LocalDate.of(2025, 1, 16), users.get(u).schedules().get(i).date());
                      assertEquals("出社", users.get(u).schedules().get(i).worktypeName());
                      assertEquals(
                          ScheduleType.DEFAULT, users.get(u).schedules().get(i).scheduleType());
                      assertEquals(LocalTime.of(8, 0), users.get(u).schedules().get(i).startTime());
                      assertEquals(LocalTime.of(17, 0), users.get(u).schedules().get(i).endTime());
                      assertEquals("#4CAF50", users.get(u).schedules().get(i).worktypeColor());
                      break;
                    case 4:
                      assertEquals(
                          LocalDate.of(2025, 1, 17), users.get(u).schedules().get(i).date());
                      assertEquals("出社", users.get(u).schedules().get(i).worktypeName());
                      assertEquals(
                          ScheduleType.DEFAULT, users.get(u).schedules().get(i).scheduleType());
                      assertEquals(LocalTime.of(8, 0), users.get(u).schedules().get(i).startTime());
                      assertEquals(LocalTime.of(17, 0), users.get(u).schedules().get(i).endTime());
                      assertEquals("#4CAF50", users.get(u).schedules().get(i).worktypeColor());
                      break;
                    case 5:
                      assertEquals(
                          LocalDate.of(2025, 1, 18), users.get(u).schedules().get(i).date());
                      assertEquals("休日", users.get(u).schedules().get(i).worktypeName());
                      assertEquals(
                          ScheduleType.COMMON_REGULAR,
                          users.get(u).schedules().get(i).scheduleType());
                      assertEquals(LocalTime.of(9, 0), users.get(u).schedules().get(i).startTime());
                      assertEquals(LocalTime.of(18, 0), users.get(u).schedules().get(i).endTime());
                      assertEquals("#E91E63", users.get(u).schedules().get(i).worktypeColor());
                      break;
                    case 6:
                      assertEquals(
                          LocalDate.of(2025, 1, 19), users.get(u).schedules().get(i).date());
                      assertEquals("休日", users.get(u).schedules().get(i).worktypeName());
                      assertEquals(
                          ScheduleType.COMMON_REGULAR,
                          users.get(u).schedules().get(i).scheduleType());
                      assertEquals(LocalTime.of(9, 0), users.get(u).schedules().get(i).startTime());
                      assertEquals(LocalTime.of(18, 0), users.get(u).schedules().get(i).endTime());
                      assertEquals("#E91E63", users.get(u).schedules().get(i).worktypeColor());
                      break;
                  }
                }
                break;
              case 1:
                assertEquals("佐藤花子", users.get(u).userName());
                assertEquals("開発部", users.get(u).organizationName());

                for (int i = 0; i < users.get(u).schedules().size(); i++) {
                  switch (i) {
                    case 0:
                      assertEquals(
                          LocalDate.of(2025, 1, 13), users.get(u).schedules().get(i).date());
                      assertEquals("在宅勤務", users.get(u).schedules().get(i).worktypeName());
                      assertEquals(
                          ScheduleType.REGULAR, users.get(u).schedules().get(i).scheduleType());
                      assertEquals(
                          LocalTime.of(10, 0), users.get(u).schedules().get(i).startTime());
                      assertEquals(LocalTime.of(19, 0), users.get(u).schedules().get(i).endTime());
                      assertEquals("#2196F3", users.get(u).schedules().get(i).worktypeColor());
                      break;
                    case 1:
                      assertEquals(
                          LocalDate.of(2025, 1, 14), users.get(u).schedules().get(i).date());
                      assertEquals("出社", users.get(u).schedules().get(i).worktypeName());
                      assertEquals(
                          ScheduleType.DEFAULT, users.get(u).schedules().get(i).scheduleType());
                      assertEquals(LocalTime.of(9, 0), users.get(u).schedules().get(i).startTime());
                      assertEquals(LocalTime.of(17, 30), users.get(u).schedules().get(i).endTime());
                      assertEquals("#4CAF50", users.get(u).schedules().get(i).worktypeColor());
                      break;
                    case 2:
                      assertEquals(
                          LocalDate.of(2025, 1, 15), users.get(u).schedules().get(i).date());
                      assertEquals("在宅勤務", users.get(u).schedules().get(i).worktypeName());
                      assertEquals(
                          ScheduleType.REGULAR, users.get(u).schedules().get(i).scheduleType());
                      assertEquals(
                          LocalTime.of(10, 0), users.get(u).schedules().get(i).startTime());
                      assertEquals(LocalTime.of(19, 0), users.get(u).schedules().get(i).endTime());
                      assertEquals("#2196F3", users.get(u).schedules().get(i).worktypeColor());
                      break;
                    case 3:
                      assertEquals(
                          LocalDate.of(2025, 1, 16), users.get(u).schedules().get(i).date());
                      assertEquals("出社", users.get(u).schedules().get(i).worktypeName());
                      assertEquals(
                          ScheduleType.DEFAULT, users.get(u).schedules().get(i).scheduleType());
                      assertEquals(LocalTime.of(9, 0), users.get(u).schedules().get(i).startTime());
                      assertEquals(LocalTime.of(17, 30), users.get(u).schedules().get(i).endTime());
                      assertEquals("#4CAF50", users.get(u).schedules().get(i).worktypeColor());
                      break;
                    case 4:
                      assertEquals(
                          LocalDate.of(2025, 1, 17), users.get(u).schedules().get(i).date());
                      assertEquals("在宅勤務", users.get(u).schedules().get(i).worktypeName());
                      assertEquals(
                          ScheduleType.REGULAR, users.get(u).schedules().get(i).scheduleType());
                      assertEquals(
                          LocalTime.of(10, 0), users.get(u).schedules().get(i).startTime());
                      assertEquals(LocalTime.of(19, 0), users.get(u).schedules().get(i).endTime());
                      assertEquals("#2196F3", users.get(u).schedules().get(i).worktypeColor());
                      break;
                    case 5:
                      assertEquals(
                          LocalDate.of(2025, 1, 18), users.get(u).schedules().get(i).date());
                      assertEquals("休日", users.get(u).schedules().get(i).worktypeName());
                      assertEquals(
                          ScheduleType.COMMON_REGULAR,
                          users.get(u).schedules().get(i).scheduleType());
                      assertEquals(LocalTime.of(9, 0), users.get(u).schedules().get(i).startTime());
                      assertEquals(LocalTime.of(18, 0), users.get(u).schedules().get(i).endTime());
                      assertEquals("#E91E63", users.get(u).schedules().get(i).worktypeColor());
                      break;
                    case 6:
                      assertEquals(
                          LocalDate.of(2025, 1, 19), users.get(u).schedules().get(i).date());
                      assertEquals("休日", users.get(u).schedules().get(i).worktypeName());
                      assertEquals(
                          ScheduleType.COMMON_REGULAR,
                          users.get(u).schedules().get(i).scheduleType());
                      assertEquals(LocalTime.of(9, 0), users.get(u).schedules().get(i).startTime());
                      assertEquals(LocalTime.of(18, 0), users.get(u).schedules().get(i).endTime());
                      assertEquals("#E91E63", users.get(u).schedules().get(i).worktypeColor());
                      break;
                  }
                }
                break;
            }
          }
        });
  }
}
