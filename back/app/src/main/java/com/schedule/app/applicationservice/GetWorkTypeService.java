package com.schedule.app.applicationservice;

import com.schedule.app.dto.WorkTypeDTO;
import java.util.List;

public interface GetWorkTypeService {
  List<WorkTypeDTO> getWorkTypeList();

  List<WorkTypeDTO> toWorkTypeDTOList(List<com.schedule.app.record.output.WorkTypeRecord> records);

  List<com.schedule.app.record.output.WorkTypeRecord> readWorkTypeList();
}
