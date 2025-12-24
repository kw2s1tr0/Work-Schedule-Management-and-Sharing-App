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
   * @return 削除したID
   */
  @Override
  public Integer deleteDefaultScheduleService(int scheduleId, String userId) {
    existDefaultSchedule(scheduleId, userId);
    int id = deleteDefaultSchedule(scheduleId);
    return id;
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
   * @return 削除したID
   */
  @Override
  public Integer deleteDefaultSchedule(int scheduleId) {
    scheduleDeleteMapper.deleteDefaultSchedule(scheduleId);
    int id = scheduleId;
    return id;
  }
}
