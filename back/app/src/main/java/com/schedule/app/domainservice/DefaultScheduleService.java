package com.schedule.app.domainservice;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.schedule.app.repository.ScheduleExistMapper;
import com.schedule.app.repository.ScheduleFindMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DefaultScheduleService {
 
    private final ScheduleExistMapper scheduleExistMapper;
    private final ScheduleFindMapper scheduleFindMapper;

    /**
     * デフォルトスケジュールの存在確認
     * 
     * @param scheduleId スケジュールID
     * @param userId ユーザーID
     */
    public void existDefaultSchedule(int scheduleId, String userId) {
        // 存在しない場合は例外をスロー
        if (existDefaultScheduleCount(scheduleId, userId) == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Default schedule already exist for ID: " + scheduleId);
        }
    }
    /**
     * デフォルトスケジュールの存在確認カウント取得
     * 
     * @param scheduleId スケジュールID
     * @param userId ユーザーID
     * @return 存在カウント
     */
    public int existDefaultScheduleCount(int scheduleId,String userId) {
        int result = scheduleExistMapper.existDefaultSchedule(scheduleId, userId);
        return result;
    }

    /**
     * デフォルトスケジュールの重複チェック
     * 
     * @param scheduleSearchRecord スケジュール検索レコード
     * @param defaultSchedule デフォルトスケジュールエンティティ
     * @return 重複がなければtrue
     */
    public boolean checkDefaultSchedule(Integer id,String userId,LocalDate startDate,LocalDate endDate) {
        // 登録する期間に該当するデフォルトスケジュールを取得
        int result = findDefaultSchedule(id, userId, startDate, endDate);
        // 空でなければ重複チェックを行う
        if (result > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Default schedules already exist for the given criteria.");
            }
        return true;
    }

    /**
     * デフォルトスケジュールを読み取る
     * 
     * @param record スケジュール検索レコード
     * @return デフォルトスケジュール出力レコードリスト
     */
    public int findDefaultSchedule(Integer id,String userId,LocalDate startDate,LocalDate endDate) {
        int result = scheduleFindMapper.findDefaultSchedule(id,userId,startDate,endDate);
        return result;
    }
}