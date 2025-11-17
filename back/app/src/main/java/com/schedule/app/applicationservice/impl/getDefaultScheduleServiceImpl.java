package com.schedule.app.applicationservice.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.schedule.app.applicationservice.GetDefaultScheduleService;
import com.schedule.app.dto.DefaultScheduleDTO;
import com.schedule.app.form.SingleScheduleSearchForm;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.DefaultScheduleOutputRecord;
import com.schedule.app.repository.ScheduleSearchMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GetDefaultScheduleServiceImpl implements GetDefaultScheduleService {

    private final ScheduleSearchMapper scheduleSearchMapper;

    /**
     * デフォルトスケジュールを取得する
     * 
     * @param form 画面入力フォーム
     * @param userId ユーザーID
     * @return デフォルトスケジュールDTOリスト
     */
    @Override
    public List<DefaultScheduleDTO> defaultScheduleSearchService(SingleScheduleSearchForm form, String userId) {
        ScheduleSearchRecord record = toScheduleRecord(form, userId);
        List<DefaultScheduleOutputRecord> records = readDefaultSchedule(record);
        List<DefaultScheduleDTO> dtos = toScheduleDTOList(records);
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
     * デフォルトスケジュールを読み取る
     * 
     * @param record スケジュール検索レコード
     * @return デフォルトスケジュール出力レコードリスト
     */
    @Override
    public List<DefaultScheduleOutputRecord> readDefaultSchedule(ScheduleSearchRecord record) {
        List<DefaultScheduleOutputRecord> records = scheduleSearchMapper.readDefaultScheduleRecord(record);
        return records;
    }

    /**
     * デフォルトスケジュール出力レコードリストをDTOリストに変換する
     * 
     * @param records デフォルトスケジュール出力レコードリスト
     * @return デフォルトスケジュールDTOリスト
     */
    @Override
    public List<DefaultScheduleDTO> toScheduleDTOList(List<DefaultScheduleOutputRecord> records) {
        List<DefaultScheduleDTO> dtos = new ArrayList<>();
        for (DefaultScheduleOutputRecord record : records) {
            DefaultScheduleDTO dto = DefaultScheduleDTO.builder()
                    .scheduleId(record.getId())
                    .startTime(record.getStartTime())
                    .endTime(record.getEndTime())
                    .startDate(record.getStartDate())
                    .endDate(record.getEndDate())
                    .worktypeName(record.getWorktypeName())
                    .worktypeColor(record.getWorktypeColor())
                    .build();
            dtos.add(dto);
        }
        return dtos;
    }

}