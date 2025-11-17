package com.schedule.app.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import com.schedule.app.record.output.DefaultScheduleOutputRecord;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DefaultSchedule {
    private Integer id;
    private String userId;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private String workTypeId;

    /**
     * デフォルトスケジュールの重複チェック
     * 
     * @param record デフォルトスケジュール出力レコード
     * @return 重複があればtrue
     */
    public boolean isOverlaps(DefaultScheduleOutputRecord record) {
        if (record.getEndDate().isBefore(this.startDate) || record.getStartDate().isAfter(this.endDate)) {
            return false;
        }
        return true;
    }
}
