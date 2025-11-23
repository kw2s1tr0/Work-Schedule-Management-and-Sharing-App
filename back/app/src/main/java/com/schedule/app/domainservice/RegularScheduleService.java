package com.schedule.app.domainservice;

import com.schedule.app.entity.RegularSchedule;
import com.schedule.app.enums.DomainError;
import com.schedule.app.exception.DomainException;
import com.schedule.app.repository.ScheduleExistMapper;
import com.schedule.app.repository.ScheduleFindMapper;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegularScheduleService {

  private final ScheduleExistMapper scheduleExistMapper;
  private final ScheduleFindMapper scheduleFindMapper;

  /**
   * レギュラースケジュールの存在確認
   *
   * @param scheduleId スケジュールID
   * @param userId ユーザーID
   */
  public void existRegularSchedule(int scheduleId, String userId) {
    // 存在しない場合は例外をスロー
    if (existRegularScheduleCount(scheduleId, userId) == 0) {
      throw new DomainException(
          DomainError.NOT_FOUND, "Regular schedule does not exist for ID: " + scheduleId);
    }
  }

  public int existRegularScheduleCount(int scheduleId, String userId) {
    int result = scheduleExistMapper.existRegularSchedule(scheduleId, userId);
    return result;
  }

  /**
   * レギュラースケジュールの重複チェック
   *
   * @param scheduleSearchRecord スケジュール検索レコード
   * @param regularSchedule レギュラースケジュールエンティティ
   * @return 重複がなければtrue
   */
  public boolean checkRegularSchedule(
      Integer id,
      String userId,
      LocalDate startDate,
      LocalDate endDate,
      RegularSchedule regularSchedule) {
    List<String> DayOfWeeks = readRegularSchedule(id, userId, startDate, endDate);
    // 空でなければ重複チェックを行う
    if (!DayOfWeeks.isEmpty()) {
      for (String dayOfWeek : DayOfWeeks) {
        // 重複があれば例外をスロー
        if (regularSchedule.isOverlaps(dayOfWeek)) {
          throw new DomainException(
              DomainError.CONFLICT, "Regular schedules already exist for the given criteria.");
        }
      }
    }
    return true;
  }

  /**
   * レギュラースケジュールを読み取る
   *
   * @param record スケジュール検索レコード
   * @return レギュラースケジュールの曜日リスト（重複チェック用）
   */
  public List<String> readRegularSchedule(
      Integer id, String userId, LocalDate startDate, LocalDate endDate) {
    List<String> DayOfWeeks =
        scheduleFindMapper.findRegularSchedule(id, userId, startDate, endDate);
    return DayOfWeeks;
  }
}
