package com.schedule.app.entity.item;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import com.schedule.app.enums.ScheduleType;
import com.schedule.app.record.output.item.DefaultScheduleRecord;
import com.schedule.app.record.output.item.IrregularScheduleRecord;
import com.schedule.app.record.output.item.RegularScheduleRecord;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Schedule {
    private int scheduleId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String worktypeName;
    private String worktypeColor;
    private ScheduleType scheduleType;

    /**
     * 非定期スケジュールレコードと照合し、該当する場合はフィールドをセットする
     * 
     * @return 照合結果(true:該当、false:非該当)
     */
    public boolean matches(IrregularScheduleRecord record, ScheduleType scheduleType) {

        // 日付が一致しない場合は非該当
        if (!date.equals(record.date())) {
            return false;
        }

        this.scheduleId = record.scheduleId();
        this.startTime = record.startTime();
        this.endTime = record.endTime();
        this.worktypeName = record.worktypeName();
        this.worktypeColor = record.worktypeColor();
        this.scheduleType = scheduleType;
        return true;
    }

    /**
     * 定期スケジュールレコードと照合し、該当する場合はフィールドをセットする
     * 
     * @return 照合結果(true:該当、false:非該当)
     */
    public boolean matches(RegularScheduleRecord record, ScheduleType scheduleType) {

        if (date.isBefore(record.startDate()) || date.isAfter(record.endDate())) {
            return false;
        }

        // 曜日の一致確認
        if (!record.daysOfWeek().name().equals(date.getDayOfWeek().name())) {
            return false;
        }

        // 間隔週の確認（2週間間隔など）
        if (record.intervalWeeks() == 2) {
            LocalDate startDate = record.startDate();
            long weeksBetween = ChronoUnit.WEEKS.between(startDate, date);
            if (weeksBetween % 2 != 0) {
                return false;
            }
        }

        this.scheduleId = record.scheduleId();
        this.startTime = record.startTime();
        this.endTime = record.endTime();
        this.worktypeName = record.worktypeName();
        this.worktypeColor = record.worktypeColor();
        this.scheduleType = scheduleType;
        
        return true;
    }

    /**
     * デフォルトスケジュールレコードと照合し、該当する場合はフィールドをセットする
     * 
     * @return 照合結果(true:該当、false:非該当)
     */
    public boolean matches(DefaultScheduleRecord record, ScheduleType scheduleType) {

        // 日付が範囲外の場合は非該当
        if (date.isBefore(record.startDate()) || date.isAfter(record.endDate())) {
            return false;
        }

        this.scheduleId = record.scheduleId();
        this.startTime = record.startTime();
        this.endTime = record.endTime();
        this.worktypeName = record.worktypeName();
        this.worktypeColor = record.worktypeColor();
        this.scheduleType = scheduleType;
        return true;
    }
}
