package com.schedule.app.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import com.schedule.app.record.output.RegularScheduleOutputRecord;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegularSchedule {
    private Integer id;
    private String userId;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate startDate;
    private LocalDate endDate;
    private String dayOfWeek;
    private Integer intervalWeeks;
    private String workTypeId;

    /**
     * レギュラースケジュールの重複チェック
     * 
     * @param record レギュラースケジュール出力レコード
     * @return 重複があればtrue
     */
    public boolean isOverlaps(RegularScheduleOutputRecord record) {
        if (record.getEndDate().isBefore(this.startDate) || record.getStartDate().isAfter(this.endDate)) {
            return false;
        }
        if (!this.dayOfWeek.equals(record.getDaysOfWeek())) {
            return false;
        }
        if (this.intervalWeeks == 2) {
            // 2週間ごとの場合、開始週と対象週の週番号の偶奇が一致しない場合のみ重複しない
            long weeksBetween = java.time.temporal.ChronoUnit.WEEKS.between(this.startDate, record.getStartDate());
            if (weeksBetween % 2 != 0) {
                return false;
            }
        }
        return true;
    }
}
