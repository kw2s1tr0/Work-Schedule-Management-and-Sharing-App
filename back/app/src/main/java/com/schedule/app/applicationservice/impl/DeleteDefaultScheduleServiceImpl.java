package com.schedule.app.applicationservice.impl;

import com.schedule.app.applicationservice.DeleteDefaultScheduleService;
import com.schedule.app.domainservice.DefaultScheduleService;
import com.schedule.app.repository.ScheduleDeleteMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteDefaultScheduleServiceImpl implements DeleteDefaultScheduleService {
  private final ScheduleDeleteMapper scheduleDeleteMapper;
  private final DefaultScheduleService defaultScheduleService;

  /**
   * デフォルトスケジュールを削除する
   *
   * @param scheduleId スケジュールID
   * @param userId ユーザーID
   */
  @Override
  public void deleteDefaultScheduleService(int scheduleId, String userId) {
    existDefaultSchedule(scheduleId, userId);
    deleteDefaultSchedule(scheduleId);
  }

  /**
   * デフォルトスケジュールの存在チェック
   *
   * @param scheduleId スケジュールID
   * @param userId ユーザーID
   */
  @Override
  public void existDefaultSchedule(int scheduleId, String userId) {
    defaultScheduleService.existDefaultSchedule(scheduleId, userId);
  }

  /**
   * デフォルトスケジュールを削除する
   *
   * @param scheduleId スケジュールID
   */
  @Override
  public void deleteDefaultSchedule(int scheduleId) {
    scheduleDeleteMapper.deleteDefaultSchedule(scheduleId);
  }
}
