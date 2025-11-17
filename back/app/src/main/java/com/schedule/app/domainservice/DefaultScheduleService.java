package com.schedule.app.domainservice;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.schedule.app.entity.DefaultSchedule;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.DefaultScheduleOutputRecord;
import com.schedule.app.repository.ScheduleExistMapper;
import com.schedule.app.repository.ScheduleSearchMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DefaultScheduleService {

    private final ScheduleExistMapper scheduleExistMapper;
    private final ScheduleSearchMapper scheduleSearchMapper;

    /**
     * デフォルトスケジュールの存在確認
     * 
     * @param scheduleId スケジュールID
     * @param userId ユーザーID
     */
    public void existDefaultSchedule(int scheduleId, String userId) {
        // 存在しない場合は例外をスロー
        if (!scheduleExistMapper.existDefaultSchedule(scheduleId, userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Default schedule already exist for ID: " + scheduleId);
        }
    }

    /**
     * デフォルトスケジュールの重複チェック
     * 
     * @param scheduleSearchRecord スケジュール検索レコード
     * @param defaultSchedule デフォルトスケジュールエンティティ
     * @return 重複がなければtrue
     */
    public boolean checkDefaultSchedule(ScheduleSearchRecord scheduleSearchRecord, DefaultSchedule defaultSchedule) {
        List<DefaultScheduleOutputRecord> records = readDefaultSchedule(scheduleSearchRecord);
        // 空でなければ重複チェックを行う
        if (!records.isEmpty()) {
            for (DefaultScheduleOutputRecord record : records) {
                // 重複があれば例外をスロー
                if(defaultSchedule.isOverlaps(record)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No default schedules found for the given criteria.");
                }
            }
        }
        return true;
    }

    /**
     * デフォルトスケジュールを読み取る
     * 
     * @param record スケジュール検索レコード
     * @return デフォルトスケジュール出力レコードリスト
     */
    public List<DefaultScheduleOutputRecord> readDefaultSchedule(ScheduleSearchRecord record) {
        List<DefaultScheduleOutputRecord> records = scheduleSearchMapper.readDefaultScheduleRecord(record);
        return records;
    }
}