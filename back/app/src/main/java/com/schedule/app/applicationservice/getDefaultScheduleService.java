package com.schedule.app.applicationservice;

import java.util.List;

import com.schedule.app.dto.DefaultScheduleDTO;
import com.schedule.app.form.SingleScheduleSearchForm;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.DefaultScheduleRecord;

public interface getDefaultScheduleService {
    List<DefaultScheduleDTO> defaultScheduleSearchService(SingleScheduleSearchForm form);

    ScheduleSearchRecord toScheduleRecord(SingleScheduleSearchForm form);

    List<DefaultScheduleRecord> readDefaultSchedule(ScheduleSearchRecord record);

    List<DefaultScheduleDTO> toScheduleDTOList(List<DefaultScheduleRecord> records);
}
