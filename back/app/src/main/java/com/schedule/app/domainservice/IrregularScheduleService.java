package com.schedule.app.domainservice;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.IrregularScheduleOutputRecord;
import com.schedule.app.repository.ScheduleExistMapper;
import com.schedule.app.repository.ScheduleSearchMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class IrregularScheduleService {

    private final ScheduleExistMapper scheduleExistMapper;
    private final ScheduleSearchMapper scheduleSearchMapper;

    /**
     * イレギュラースケジュールの存在確認
     * 
     * @param scheduleId スケジュールID
     * @param userId ユーザーID
     */ 
    public boolean existIrregularSchedule(int scheduleId, String userId) {
        // 存在しない場合は例外をスロー
        if (!scheduleExistMapper.existIrregularSchedule(scheduleId, userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Irregular schedule already exist for ID: " + scheduleId);
        }
        return true;
    }

    /**
     * イレギュラースケジュールの重複チェック
     * 
     * @param record スケジュール検索レコード
     * @return 重複がなければtrue
     */
    public boolean checkIrregularSchedule(ScheduleSearchRecord record) {
        List<IrregularScheduleOutputRecord> records = readIrregularSchedule(record);
        // 空でなければ例外をスロー
        if (!records.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No irregular schedules found for the given criteria.");
        }
        return true;
    }

    /**
     * イレギュラースケジュールを読み取る
     * 
     * @param record スケジュール検索レコード
     * @return イレギュラースケジュール出力レコードリスト
     */
    public List<IrregularScheduleOutputRecord> readIrregularSchedule(ScheduleSearchRecord record){
        List<IrregularScheduleOutputRecord> records = scheduleSearchMapper.readIrregularScheduleRecord(record);
        return records;
    }
}
