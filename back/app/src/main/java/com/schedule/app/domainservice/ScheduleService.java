package com.schedule.app.domainservice;

import com.schedule.app.entity.Schedule;
import com.schedule.app.enums.ScheduleType;
import com.schedule.app.record.output.DefaultScheduleOutputRecord;
import com.schedule.app.record.output.IrregularScheduleOutputRecord;
import com.schedule.app.record.output.RegularScheduleOutputRecord;
import com.schedule.app.record.output.UserRecord;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

  /**
   * スケジュールエンティティに適切な予定を適合する
   *
   * @param defaultRecords
   * @param regularRecords
   * @param irregularRecords
   * @param commonDefaultScheduleRecords
   * @param commonRegularScheduleRecords
   * @param commonIrregularScheduleRecords
   * @param schedule
   * @param userRecord
   * @return ユーザーエンティティ
   */
  public Schedule scheduleService(
      List<DefaultScheduleOutputRecord> defaultRecords,
      List<RegularScheduleOutputRecord> regularRecords,
      List<IrregularScheduleOutputRecord> irregularRecords,
      List<DefaultScheduleOutputRecord> commonDefaultScheduleRecords,
      List<RegularScheduleOutputRecord> commonRegularScheduleRecords,
      List<IrregularScheduleOutputRecord> commonIrregularScheduleRecords,
      Schedule schedule,
      UserRecord userRecord) {

    // スケジュールの優先順位は以下の通り
    // 1. 非定期スケジュール
    // 2. 定期スケジュール
    // 3. 共通非定期スケジュール
    // 4. 共通定期スケジュール
    // 5. デフォルトスケジュール
    // 6. 共通デフォルトスケジュール
    // 理由はREADMEを参照されたし

    // 各スケジュールリストをチェックし、該当するスケジュールがあればリストに追加して次の日付へ
    // 個別の場合はユーザーID照合
    for (IrregularScheduleOutputRecord record : irregularRecords) {
      if (!record.getUserId().equals(userRecord.getUserId())) {
        continue;
      }
      if (schedule.matches(record, ScheduleType.IRREGULAR)) {
        return schedule;
      }
    }

    for (RegularScheduleOutputRecord record : regularRecords) {
      if (!record.getUserId().equals(userRecord.getUserId())) {
        continue;
      }
      if (schedule.matches(record, ScheduleType.REGULAR)) {
        return schedule;
      }
    }

    for (IrregularScheduleOutputRecord record : commonIrregularScheduleRecords) {
      if (schedule.matches(record, ScheduleType.COMMON_IRREGULAR)) {
        return schedule;
      }
    }

    for (RegularScheduleOutputRecord record : commonRegularScheduleRecords) {
      if (schedule.matches(record, ScheduleType.COMMON_REGULAR)) {
        return schedule;
      }
    }

    for (DefaultScheduleOutputRecord record : defaultRecords) {
      if (!record.getUserId().equals(userRecord.getUserId())) {
        continue;
      }
      if (schedule.matches(record, ScheduleType.DEFAULT)) {
        return schedule;
      }
    }

    for (DefaultScheduleOutputRecord record : commonDefaultScheduleRecords) {
      if (schedule.matches(record, ScheduleType.COMMON_DEFAULT)) {
        return schedule;
      }
    }

    // スケジュールが何も該当しなかった場合は日付だけセットしたスケジュールを追加
    return schedule;
  }
}
