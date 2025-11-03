package com.schedule.app.service.impl;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.List;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.schedule.app.dto.UserDTO;
import com.schedule.app.dto.item.ScheduleDTO;
import com.schedule.app.form.ScheduleSearchForm;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.UserDefaultScheduleRecord;
import com.schedule.app.record.output.UserIrregularScheduleRecord;
import com.schedule.app.record.output.UserRecord;
import com.schedule.app.record.output.UserRegularScheduleRecord;
import com.schedule.app.record.output.item.DefaultScheduleRecord;
import com.schedule.app.record.output.item.IrregularScheduleRecord;
import com.schedule.app.record.output.item.RegularScheduleRecord;
import com.schedule.app.repository.CommonScheduleSearchMapper;
import com.schedule.app.repository.ScheduleSearchMapper;
import com.schedule.app.repository.UserSearchMapper;
import com.schedule.app.service.ScheduleService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleSearchMapper scheduleSearchMapper;
    private final CommonScheduleSearchMapper commonScheduleSearchMapper;
    private final UserSearchMapper userSearchMapper;

    @Override
    public List<UserDTO> scheduleSearchService(ScheduleSearchForm form){
        return null;
    }

    @Override
    public ScheduleSearchRecord toScheduleSearchRecord(ScheduleSearchForm form) {

        WeekFields weekFields = WeekFields.ISO;
        int year;
        int week;
        int month;
        LocalDate from;
        LocalDate to;
        switch (form.viewMode()) {
            case WEEK:
                year = Integer.parseInt(form.week().substring(0, 4));
                week = Integer.parseInt(form.week().substring(6));
                from = LocalDate
                        .ofYearDay(year,1)
                        .with(weekFields.weekOfYear(), week)
                        .with(weekFields.dayOfWeek(), 1);
                to = from.plusDays(6);
                break;
            case MONTH:
                year = Integer.parseInt(form.month().substring(0, 4));
                month = Integer.parseInt(form.month().substring(5));
                from = LocalDate
                        .of(year, month, 1);
                to = from.plusMonths(1).minusDays(1);
                break;
            default:
                throw new IllegalArgumentException("Invalid view mode: " + form.viewMode());
        }

        List<String> names;
        if (form.name() == null || form.name().isBlank()) {
            names = null;
        } else {
            names = Arrays.asList(form.name().trim().split("\\s+|　+"));
        }

        String userId = Normalizer.normalize(form.userId(), Normalizer.Form.NFKC);

        ScheduleSearchRecord record = ScheduleSearchRecord.builder()
            .userId(userId)
            .from(from)
            .to(to)
            .names(names)
            .organizationCode(form.organizationCode())
            .build();
        return record;
    }

    @Override
    public List<UserRegularScheduleRecord> readRegularSchedule(ScheduleSearchRecord record){
        List<UserRegularScheduleRecord> userRecords = scheduleSearchMapper.readRegularScheduleRecord(record);
        return userRecords;
    }

    @Override
    public List<UserIrregularScheduleRecord> readIrregularSchedule(ScheduleSearchRecord record){
        List<UserIrregularScheduleRecord> userRecords = scheduleSearchMapper.readIrregularScheduleRecord(record);
        return userRecords;
    }

    @Override
    public List<UserDefaultScheduleRecord> readDefaultSchedule(ScheduleSearchRecord record){
        List<UserDefaultScheduleRecord> userRecords = scheduleSearchMapper.readDefaultScheduleRecord(record);
        return userRecords;
    }

    @Override
    public List<DefaultScheduleRecord> readCommonDefaultScheduleRecord(ScheduleSearchRecord record){
        List<DefaultScheduleRecord> defaultScheduleRecords = commonScheduleSearchMapper.readDefaultScheduleRecord(record);
        return defaultScheduleRecords;
    }

    @Override
    public List<RegularScheduleRecord> readCommonRegularUserRecords(ScheduleSearchRecord record ){
        List<RegularScheduleRecord> regularScheduleRecords = commonScheduleSearchMapper.readRegularScheduleRecord(record);
        return regularScheduleRecords;
    }

    @Override
    public List<IrregularScheduleRecord> readCommonIrregularUserRecords(ScheduleSearchRecord record){
        List<IrregularScheduleRecord> irregularScheduleRecords = commonScheduleSearchMapper.readIrregularScheduleRecord(record);
        return irregularScheduleRecords;
    }

    @Override
    public List<UserRecord> readUserRecords(ScheduleSearchRecord record) {
        List<UserRecord> userRecords = userSearchMapper.readUserRecord(record);
        return userRecords;
    }

    @Override
    public List<UserDTO> toUserDTOList(
        List<UserDefaultRecord> defaultUserRecords,
        List<UserRegularRecord> regularUserRecords,
        List<UserIrregularRecord> irregularUserRecords,
        List<DefaultScheduleRecord> defaultScheduleRecords,
        List<RegularScheduleRecord> regularScheduleRecords,
        List<IrregularScheduleRecord> irregularScheduleRecords,
        List<UserRecord> userRecords,
        LocalDate from,
        LocalDate to) {
        List<UserDTO> userDTOList = new ArrayList<>();
        for (int i = 0; i < userRecords.size(); i++) {
            UserRecord userRecord = userRecords.get(i);
            DefaultUserRecord defaultUserRecord = defaultUserRecords.get(i);
            RegularUserRecord regularUserRecord = regularUserRecords.get(i);
            IrregularUserRecord irregularUserRecord = irregularUserRecords.get(i);
            List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
            for (LocalDate date = from; !date.isAfter(to); date = date.plusDays(1)) {
                ScheduleDTO scheduleDTO = null;
                scheduleDTOs.add(scheduleDTO);
            }
            UserDTO userDTO = UserDTO.builder()
                .userId(userRecord.userId())
                .name(userRecord.organizationName())
                .schedules(scheduleDTOs)
                .build();
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

}
