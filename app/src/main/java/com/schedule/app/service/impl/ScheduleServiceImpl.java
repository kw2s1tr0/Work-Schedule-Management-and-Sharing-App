package com.schedule.app.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.schedule.app.dto.UserDTO;
import com.schedule.app.form.ScheduleSearchForm;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.DefaultUserRecord;
import com.schedule.app.record.output.IrregularUserRecord;
import com.schedule.app.record.output.RegularUserRecord;
import com.schedule.app.record.output.item.DefaultScheduleRecord;
import com.schedule.app.record.output.item.IrregularScheduleRecord;
import com.schedule.app.record.output.item.RegularScheduleRecord;
import com.schedule.app.service.ScheduleService;

@Service
public class ScheduleServiceImpl implements ScheduleService{
    public List<UserDTO> scheduleSearchService(ScheduleSearchForm form){
        return null;
    }
    public ScheduleSearchRecord toScheduleSearchRecord(ScheduleSearchForm form) {
        return null;
    }
    public List<DefaultUserRecord> readRegularSchedule(ScheduleSearchRecord record){
        return null;
    }
    public List<RegularUserRecord> readIrregularSchedule(ScheduleSearchRecord record){
        return null;
    }
    public List<IrregularUserRecord> readDefaultSchedule(ScheduleSearchRecord record){
        return null;
    }
    public List<DefaultScheduleRecord> readCommonDefaultScheduleRecord(ScheduleSearchRecord record){
        return null;
    }
    public List<RegularScheduleRecord> readCommonRegularUserRecords(ScheduleSearchRecord record ){
        return null;
    }
    public List<IrregularScheduleRecord> readCommonIrregularUserRecords(ScheduleSearchRecord record){
        return null;
    }
    public List<UserDTO> toUserDTOList(
        List<DefaultUserRecord> defaultUserRecords,
        List<RegularUserRecord> regularUserRecords,
        List<IrregularUserRecord> irregularUserRecords,
        List<DefaultScheduleRecord> defaultScheduleRecords,
        List<RegularScheduleRecord> regularScheduleRecords,
        List<IrregularScheduleRecord> irregularScheduleRecords) {
        return null;
    }
}
