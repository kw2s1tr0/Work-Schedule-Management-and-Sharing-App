package com.schedule.app.applicationservice;

import java.util.List;

import com.schedule.app.dto.IrregularScheduleDTO;
import com.schedule.app.form.SingleScheduleSearchForm;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.IrregularScheduleRecord;

public interface IrregularScheduleSearchService {
    List<IrregularScheduleDTO> irregularScheduleSearchService(SingleScheduleSearchForm form);

    ScheduleSearchRecord toScheduleRecord(SingleScheduleSearchForm form);

    List<IrregularScheduleRecord> readIrregularSchedule(ScheduleSearchRecord record);

    List<IrregularScheduleDTO> toScheduleDTOList(List<IrregularScheduleRecord> records);
}
