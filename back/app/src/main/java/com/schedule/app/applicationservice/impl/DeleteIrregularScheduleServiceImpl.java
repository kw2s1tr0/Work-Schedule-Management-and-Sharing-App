package com.schedule.app.applicationservice.impl;

import com.schedule.app.applicationservice.DeleteIrregularScheduleService;
import com.schedule.app.domainservice.IrregularScheduleService;
import com.schedule.app.repository.ScheduleDeleteMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteIrregularScheduleServiceImpl implements DeleteIrregularScheduleService {
  private final ScheduleDeleteMapper scheduleDeleteMapper;
  private final IrregularScheduleService irregularScheduleService;

  /**
   * イレギュラースケジュールを削除する
   *
   * @param scheduleId スケジュールID
   * @param userId ユーザーID
   * @return 削除したID
   */
  @Override
  public Integer deleteIrregularScheduleService(int scheduleId, String userId) {
    existIrregularSchedule(scheduleId, userId);
    int id = deleteIrregularSchedule(scheduleId);
    return id;
  }

  /**
   * イレギュラースケジュールの存在チェック
   *
   * @param scheduleId スケジュールID
   * @param userId ユーザーID
   */
  @Override
  public void existIrregularSchedule(int scheduleId, String userId) {
    irregularScheduleService.existIrregularSchedule(scheduleId, userId);
  }

  /**
   * イレギュラースケジュールを削除する
   *
   * @param scheduleId スケジュールID
   * @return 削除したID
   */
  @Override
  public Integer deleteIrregularSchedule(int scheduleId) {
    scheduleDeleteMapper.deleteIrregularSchedule(scheduleId);
    return scheduleId;
  }
}
