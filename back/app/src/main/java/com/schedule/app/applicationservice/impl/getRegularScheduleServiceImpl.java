package com.schedule.app.applicationservice.impl;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.schedule.app.applicationservice.GetRegularScheduleService;
import com.schedule.app.dto.RegularScheduleDTO;
import com.schedule.app.form.SingleScheduleSearchForm;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.RegularScheduleOutputRecord;
import com.schedule.app.repository.ScheduleSearchMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GetRegularScheduleServiceImpl implements GetRegularScheduleService {

    private final ScheduleSearchMapper scheduleSearchMapper;

    /**
     * レギュラースケジュールを取得する
     * 
     * @param form 画面入力フォーム
     * @param userId ユーザーID
     * @return レギュラースケジュールDTOリスト
     */
    @Override
    public List<RegularScheduleDTO> regularScheduleSearchService(SingleScheduleSearchForm form, String userId) {
        ScheduleSearchRecord record = toScheduleRecord(form, userId);
        List<RegularScheduleOutputRecord> records = readRegularSchedule(record);
        List<RegularScheduleDTO> dtos = toScheduleDTOList(records);
        return dtos;
    }

    /**
     * フォームをレコードに変換する
     * 
     * @param form 画面入力フォーム
     * @param userId ユーザーID
     * @return スケジュール検索レコード
     */
    @Override
    public ScheduleSearchRecord toScheduleRecord(SingleScheduleSearchForm form, String userId) {
        ScheduleSearchRecord record = ScheduleSearchRecord.builder()
                .userId(userId)
                .from(form.from())
                .to(form.to())
                .build();
        return record;
    }

    /**
     * レギュラースケジュールを読み取る
     * 
     * @param record スケジュール検索レコード
     * @return レギュラースケジュール出力レコードリスト
     */
    @Override
    public List<RegularScheduleOutputRecord> readRegularSchedule(ScheduleSearchRecord record) {
        List<RegularScheduleOutputRecord> records = scheduleSearchMapper.readRegularScheduleRecord(record);
        return records;
    }

    /**
     * レギュラースケジュール出力レコードリストをDTOリストに変換する
     * 
     * @param records レギュラースケジュール出力レコードリスト
     * @return レギュラースケジュールDTOリスト
     */
    @Override
    public List<RegularScheduleDTO> toScheduleDTOList(List<RegularScheduleOutputRecord> records) {
        List<RegularScheduleDTO> dtos = new ArrayList<>();
        for (RegularScheduleOutputRecord record : records) {
            RegularScheduleDTO dto = RegularScheduleDTO.builder()
                    .scheduleId(record.getId())
                    .startTime(record.getStartTime())
                    .endTime(record.getEndTime())
                    .startDate(record.getStartDate())
                    .endDate(record.getEndDate())
                    .worktypeName(record.getWorktypeName())
                    .worktypeColor(record.getWorktypeColor())
                    .daysOfWeek(DayOfWeek.valueOf(record.getDaysOfWeek()))
                    .build();
            dtos.add(dto);
        }
        return dtos;
    }    
}
