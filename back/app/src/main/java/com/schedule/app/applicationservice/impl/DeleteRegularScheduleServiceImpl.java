package com.schedule.app.applicationservice.impl;

import com.schedule.app.applicationservice.DeleteRegularScheduleService;
import com.schedule.app.domainservice.RegularScheduleService;
import com.schedule.app.repository.ScheduleDeleteMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteRegularScheduleServiceImpl implements DeleteRegularScheduleService {

  private final ScheduleDeleteMapper scheduleDeleteMapper;
  private final RegularScheduleService regularScheduleService;

  /**
   * レギュラースケジュールを削除する
   *
   * @param scheduleId スケジュールID
   * @param userId ユーザーID
   * @return 削除したID
   */
  @Override
  public Integer deleteRegularScheduleService(int scheduleId, String userId) {
    existRegularSchedule(scheduleId, userId);
    int id = deleteRegularSchedule(scheduleId);
    return id;
  }

  /**
   * レギュラースケジュールの存在チェック
   *
   * @param scheduleId スケジュールID
   * @param userId ユーザーID
   */
  @Override
  public void existRegularSchedule(int scheduleId, String userId) {
    regularScheduleService.existRegularSchedule(scheduleId, userId);
  }

  /**
   * レギュラースケジュールを削除する
   *
   * @param scheduleId スケジュールID
   * @return 削除したID
   */
  @Override
  public Integer deleteRegularSchedule(int scheduleId) {
    scheduleDeleteMapper.deleteRegularSchedule(scheduleId);
    int id = scheduleId;
    return id;
  }
}
