package com.schedule.app.applicationservice;

import java.util.List;

import com.schedule.app.dto.IrregularScheduleDTO;
import com.schedule.app.form.SingleScheduleSearchForm;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.IrregularScheduleOutputRecord;

public interface GetIrregularScheduleService {
    List<IrregularScheduleDTO> irregularScheduleSearchService(SingleScheduleSearchForm form, String userId);

    ScheduleSearchRecord toScheduleRecord(SingleScheduleSearchForm form, String userId);

    List<IrregularScheduleOutputRecord> readIrregularSchedule(ScheduleSearchRecord record);

    List<IrregularScheduleDTO> toScheduleDTOList(List<IrregularScheduleOutputRecord> records);
}
