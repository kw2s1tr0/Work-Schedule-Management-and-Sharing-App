package com.schedule.app.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import com.schedule.app.enums.ScheduleType;
import com.schedule.app.record.output.DefaultScheduleOutputRecord;
import com.schedule.app.record.output.IrregularScheduleOutputRecord;
import com.schedule.app.record.output.RegularScheduleOutputRecord;

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
    public boolean matches(IrregularScheduleOutputRecord record, ScheduleType scheduleType) {

        // 日付が一致しない場合は非該当
        if (!date.equals(record.getDate())) {
            return false;
        }

        this.scheduleId = record.getId();
        this.startTime = record.getStartTime();
        this.endTime = record.getEndTime();
        this.worktypeName = record.getWorktypeName();
        this.worktypeColor = record.getWorktypeColor();
        this.scheduleType = scheduleType;
        return true;
    }

    /**
     * 定期スケジュールレコードと照合し、該当する場合はフィールドをセットする
     * 
     * @return 照合結果(true:該当、false:非該当)
     */
    public boolean matches(RegularScheduleOutputRecord record, ScheduleType scheduleType) {

        if (date.isBefore(record.getStartDate()) || date.isAfter(record.getEndDate())) {
            return false;
        }

        // 曜日の一致確認
        if (!record.getDaysOfWeek().equals(date.getDayOfWeek().name())) {
            return false;
        }

        // 間隔週の確認（2週間間隔など）
        if (record.getIntervalWeeks() == 2) {
            LocalDate startDate = record.getStartDate();
            long weeksBetween = ChronoUnit.WEEKS.between(startDate, date);
            if (weeksBetween % 2 != 0) {
                return false;
            }
        }

        this.scheduleId = record.getId();
        this.startTime = record.getStartTime();
        this.endTime = record.getEndTime();
        this.worktypeName = record.getWorktypeName();
        this.worktypeColor = record.getWorktypeColor();
        this.scheduleType = scheduleType;
        
        return true;
    }

    /**
     * デフォルトスケジュールレコードと照合し、該当する場合はフィールドをセットする
     * 
     * @return 照合結果(true:該当、false:非該当)
     */
    public boolean matches(DefaultScheduleOutputRecord record, ScheduleType scheduleType) {

        // 日付が範囲外の場合は非該当
        if (date.isBefore(record.getStartDate()) || date.isAfter(record.getEndDate())) {
            return false;
        }

        this.scheduleId = record.getId();
        this.startTime = record.getStartTime();
        this.endTime = record.getEndTime();
        this.worktypeName = record.getWorktypeName();
        this.worktypeColor = record.getWorktypeColor();
        this.scheduleType = scheduleType;
        return true;
    }

    public boolean isOverlaps(RegularScheduleOutputRecord record) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isOverlaps'");
    }
}
