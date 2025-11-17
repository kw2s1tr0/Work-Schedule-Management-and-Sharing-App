package com.schedule.app.applicationservice.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.schedule.app.applicationservice.GetWorkTypeService;
import com.schedule.app.dto.WorkTypeDTO;
import com.schedule.app.record.output.WorkTypeRecord;
import com.schedule.app.repository.WorkTypeSearchMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GetWorkTypeServiceImpl implements GetWorkTypeService {

    private final WorkTypeSearchMapper workTypeSearchMapper;

    /**
     * 勤怠種別一覧を取得する（休日を除く）
     * 
     * @return 勤怠種別DTOリスト
     */
    @Override
    public List<WorkTypeDTO> getWorkTypeList() {
        List<WorkTypeRecord> records = readWorkTypeList();
        List<WorkTypeDTO> dtos = toWorkTypeDTOList(records);
        return dtos;
    }

    /**
     * 勤怠種別一覧を読み取る
     * 
     * @return 勤怠種別レコードリスト
     */
    @Override
    public List<WorkTypeRecord> readWorkTypeList() {
        List<WorkTypeRecord> records = workTypeSearchMapper.readWorkTypeRecord();
        return records;
    }

    /**
     * 勤怠種別レコードをDTOリストに変換する
     * 
     * @param records 勤怠種別レコードリスト
     * @return 勤怠種別DTOリスト
     */
    @Override
    public List<WorkTypeDTO> toWorkTypeDTOList(List<WorkTypeRecord> records) {
        List<WorkTypeDTO> workTypeDTOs = new ArrayList<>();
        for (WorkTypeRecord record : records) {
            WorkTypeDTO dto = WorkTypeDTO.builder()
                    .id(record.getId()) 
                    .workTypeName(record.getWorkTypeName())
                    .workTypeColor(record.getWorkTypeColor())
                    .build();
            workTypeDTOs.add(dto);
        }
        return workTypeDTOs;
    }
}