package com.schedule.app.domainservice;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.schedule.app.entity.RegularSchedule;
import com.schedule.app.record.output.RegularScheduleOutputRecord;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.repository.ScheduleExistMapper;
import com.schedule.app.repository.ScheduleSearchMapper;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegularScheduleService {

    private final ScheduleExistMapper scheduleExistMapper;
    private final ScheduleSearchMapper scheduleSearchMapper;

    /**
     * レギュラースケジュールの存在確認
     * 
     * @param scheduleId スケジュールID
     * @param userId ユーザーID
     */
    public void existRegularSchedule(int scheduleId, String userId) {
        // 存在しない場合は例外をスロー
        if (!scheduleExistMapper.existRegularSchedule(scheduleId, userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Regular schedule already exist for ID: " + scheduleId);
        }
    }

    /**
     * レギュラースケジュールの重複チェック
     * 
     * @param scheduleSearchRecord スケジュール検索レコード
     * @param regularSchedule レギュラースケジュールエンティティ
     * @return 重複がなければtrue
     */
    public boolean checkRegularSchedule(ScheduleSearchRecord scheduleSearchRecord,RegularSchedule regularSchedule) {
        List<RegularScheduleOutputRecord> records = readRegularSchedule(scheduleSearchRecord);
        // 空でなければ重複チェックを行う
        if (!records.isEmpty()) {
            for (RegularScheduleOutputRecord record : records) {
                // 重複があれば例外をスロー
                if(regularSchedule.isOverlaps(record)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No regular schedules found for the given criteria.");
                }
            }
        }
        return true;
    }

    /**
     * レギュラースケジュールを読み取る
     * 
     * @param record スケジュール検索レコード
     * @return レギュラースケジュール出力レコードリスト
     */
    public List<RegularScheduleOutputRecord> readRegularSchedule(ScheduleSearchRecord record){
        List<RegularScheduleOutputRecord> records = scheduleSearchMapper.readRegularScheduleRecord(record);
        return records;
    }
}
