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
public class IrregularScheduleService {

    private final ScheduleExistMapper scheduleExistMapper;
    private final ScheduleFindMapper scheduleFindMapper;

    /**
     * イレギュラースケジュールの存在確認
     * 
     * @param scheduleId スケジュールID
     * @param userId ユーザーID
     */ 
    public boolean existIrregularSchedule(int scheduleId, String userId) {
        // 存在しない場合は例外をスロー
        if (scheduleExistMapper.existIrregularSchedule(scheduleId, userId) == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Irregular schedule already exist for ID: " + scheduleId);
        }
        return true;
    }

    /**
     * イレギュラースケジュールの存在確認カウント取得
     * 
     * @param scheduleId スケジュールID
     * @param userId ユーザーID
     * @return 存在カウント
     */
    public int existIrregularScheduleCount(int scheduleId,String userId) {
        int result = scheduleExistMapper.existIrregularSchedule(scheduleId, userId);
        return result;
    }

    /**
     * イレギュラースケジュールの重複チェック
     * 
     * @param record スケジュール検索レコード
     * @return 重複がなければtrue
     */
    public boolean checkIrregularSchedule(Integer id,String userId,LocalDate startDate,LocalDate endDate) {
        // 登録する期間に該当するイレギュラースケジュールを取得
        int result = findIrregularSchedule(id, userId, startDate, endDate);
        // 空でなければ例外をスロー
        if (result > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Irregular schedules already exist for the given criteria.");
        }
        return true;
    }

    /**
     * イレギュラースケジュールを読み取る
     * 
     * @param record スケジュール検索レコード
     * @return イレギュラースケジュール出力レコードリスト
     */
    public int findIrregularSchedule(Integer id,String userId,LocalDate startDate,LocalDate endDate){
        int result = scheduleFindMapper.findIrregularSchedule(id,userId,startDate,endDate);
        return result;
    }
}
