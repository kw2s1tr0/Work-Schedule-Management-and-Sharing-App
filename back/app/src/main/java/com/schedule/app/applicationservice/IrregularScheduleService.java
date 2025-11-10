package com.schedule.app.applicationservice;

import java.util.List;

import com.schedule.app.dto.IrregularScheduleDTO;
import com.schedule.app.form.IrregularScheduleSearchForm;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.IrregularScheduleRecord;

public interface IrregularScheduleService {
    List<IrregularScheduleDTO> irregularScheduleSearchService(IrregularScheduleSearchForm form);

    ScheduleSearchRecord toScheduleRecord(IrregularScheduleSearchForm form);

    List<IrregularScheduleRecord> readIrregularSchedule(ScheduleSearchRecord record);

    List<IrregularScheduleDTO> toScheduleDTOList(List<IrregularScheduleRecord> records);
}
