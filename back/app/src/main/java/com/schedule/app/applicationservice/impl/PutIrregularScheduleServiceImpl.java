package com.schedule.app.applicationservice.impl;

import com.schedule.app.applicationservice.PutIrregularScheduleService;
import com.schedule.app.domainservice.IrregularScheduleService;
import com.schedule.app.form.IrregularScheduleUpdateForm;
import com.schedule.app.record.input.IrregularScheduleInputRecord;
import com.schedule.app.repository.ScheduleUpdateMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PutIrregularScheduleServiceImpl implements PutIrregularScheduleService {

  private final ScheduleUpdateMapper scheduleUpdateMapper;
  private final IrregularScheduleService irregularScheduleService;

  /**
   * イレギュラースケジュールを更新する
   *
   * @param form 画面入力フォーム
   * @param userId ユーザーID
   */
  @Override
  public void patchIrregularScheduleService(IrregularScheduleUpdateForm form, String userId) {
    IrregularScheduleInputRecord record = toIrregularScheduleRecord(form, userId);
    patchIrregularSchedule(record);
  }

  /**
   * フォームをレコードに変換する
   *
   * @param form 画面入力フォーム
   * @param userId ユーザーID
   * @return イレギュラースケジュール入力レコード
   */
  @Override
  public IrregularScheduleInputRecord toIrregularScheduleRecord(
      IrregularScheduleUpdateForm form, String userId) {

    // イレギュラースケジュールの存在チェック
    irregularScheduleService.existIrregularSchedule(form.id(), userId);

    // イレギュラースケジュールの重複チェック
    irregularScheduleService.checkIrregularSchedule(form.id(), userId, form.date(), form.date());

    IrregularScheduleInputRecord record =
        IrregularScheduleInputRecord.builder()
            .id(form.id())
            .userId(userId)
            .startTime(form.startTime())
            .endTime(form.endTime())
            .date(form.date())
            .workTypeId(form.workTypeId())
            .build();

    return record;
  }

  /**
   * イレギュラースケジュールを更新する
   *
   * @param record イレギュラースケジュール入力レコード
   */
  @Override
  public void patchIrregularSchedule(IrregularScheduleInputRecord record) {
    scheduleUpdateMapper.updateIrregularSchedule(record);
  }
}
