package com.schedule.app.applicationservice.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.schedule.app.applicationservice.GetIrregularScheduleService;
import com.schedule.app.dto.IrregularScheduleDTO;
import com.schedule.app.form.SingleScheduleSearchForm;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.IrregularScheduleOutputRecord;
import com.schedule.app.repository.ScheduleSearchMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GetIrregularScheduleServiceImpl implements GetIrregularScheduleService {

    private final ScheduleSearchMapper scheduleSearchMapper;

    /**
     * イレギュラースケジュールを取得する
     * 
     * @param form 画面入力フォーム
     * @param userId ユーザーID
     * @return イレギュラースケジュールDTOリスト
     */
    @Override
    public List<IrregularScheduleDTO> irregularScheduleSearchService(SingleScheduleSearchForm form, String userId) {
        ScheduleSearchRecord record = toScheduleRecord(form, userId);
        List<IrregularScheduleOutputRecord> records = readIrregularSchedule(record);
        List<IrregularScheduleDTO> dtos = toScheduleDTOList(records);
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
     * イレギュラースケジュールを読み取る
     * 
     * @param record スケジュール検索レコード
     * @return イレギュラースケジュール出力レコードリスト
     */
    @Override
    public List<IrregularScheduleOutputRecord> readIrregularSchedule(ScheduleSearchRecord record) {
        List<IrregularScheduleOutputRecord> records = scheduleSearchMapper.readIrregularScheduleRecord(record);
        return records;
    }

    /**
     * イレギュラースケジュール出力レコードリストをDTOリストに変換する
     * 
     * @param records イレギュラースケジュール出力レコードリスト
     * @return イレギュラースケジュールDTOリスト
     */
    @Override
    public List<IrregularScheduleDTO> toScheduleDTOList(List<IrregularScheduleOutputRecord> records) {
        List<IrregularScheduleDTO> dtos = new ArrayList<>();
        for (IrregularScheduleOutputRecord record : records) {
            IrregularScheduleDTO dto = IrregularScheduleDTO.builder()
                    .scheduleId(record.getId())
                    .startTime(record.getStartTime())
                    .endTime(record.getEndTime())
                    .worktypeName(record.getWorktypeName())
                    .worktypeColor(record.getWorktypeColor())
                    .build();
            dtos.add(dto);
        }
        return dtos;
    }
    
}
