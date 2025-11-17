package com.schedule.app.applicationservice;

import java.util.List;

import com.schedule.app.dto.WorkTypeDTO;

public interface GetWorkTypeService {
    List<WorkTypeDTO> getWorkTypeList();
    List<WorkTypeDTO> toWorkTypeDTOList(List<com.schedule.app.record.output.WorkTypeRecord> records);
    List<com.schedule.app.record.output.WorkTypeRecord> readWorkTypeList();
}
