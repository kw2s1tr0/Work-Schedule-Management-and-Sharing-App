package com.schedule.app.applicationservice;

import java.util.List;

import com.schedule.app.dto.RegularScheduleDTO;
import com.schedule.app.form.SingleScheduleSearchForm;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.RegularScheduleRecord;

public interface RegularScheduleSearchService {
    List<RegularScheduleDTO> regularScheduleSearchService(SingleScheduleSearchForm form);

    ScheduleSearchRecord toScheduleRecord(SingleScheduleSearchForm form);

    List<RegularScheduleRecord> readRegularSchedule(ScheduleSearchRecord record);

    List<RegularScheduleDTO> toScheduleDTOList(List<RegularScheduleRecord> records);
}
