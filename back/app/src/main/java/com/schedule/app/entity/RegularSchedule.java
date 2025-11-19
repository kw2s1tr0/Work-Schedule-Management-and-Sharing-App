package com.schedule.app.entity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

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
    private DayOfWeek dayOfWeek;
    private Integer intervalWeeks;
    private String workTypeId;

    /**
     * レギュラースケジュールの重複チェック
     * 
     * @param record レギュラースケジュール出力レコード
     * @return 重複があればtrue
     */
    public boolean isOverlaps(String dayOfWeekStr) {
        if (!this.dayOfWeek.equals(DayOfWeek.valueOf(dayOfWeekStr))) {
            return false;
        }
        return true;
    }
}
