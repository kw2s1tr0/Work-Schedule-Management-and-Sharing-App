package com.schedule.app.service.impl;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public List<UserDTO> toUserDTOList(List<UserDefaultScheduleRecord> defaultUserRecords,
                                       List<UserRegularScheduleRecord> regularUserRecords,
                                       List<UserIrregularScheduleRecord> irregularUserRecords,
                                       List<DefaultScheduleRecord> commonDefaultScheduleRecords,
                                       List<RegularScheduleRecord> commonRegularScheduleRecords,
                                       List<IrregularScheduleRecord> commonIrregularScheduleRecords,
                                       List<UserRecord> userRecords,
                                       LocalDate from,
                                       LocalDate to) {
        
        if (userRecords ==null){
            List<UserDTO> emptyList = new ArrayList<>();
            return emptyList;
        }

        List<UserDTO> userDTOList = new ArrayList<>();

        outer:
        for (int i = 0; i < userRecords.size(); i++) {
            UserRecord userRecord = userRecords.get(i);
            List<DefaultScheduleRecord> defaultRecords = defaultUserRecords.get(i).defaultSchedules();
            List<RegularScheduleRecord> regularRecords = regularUserRecords.get(i).regularSchedules();
            List<IrregularScheduleRecord> irregularRecords = irregularUserRecords.get(i).irregularSchedules();

            List<ScheduleDTO> scheduleDTOs = new ArrayList<>();

            for (LocalDate date = from; !date.isAfter(to); date = date.plusDays(1)) {

                if (irregularRecords != null){
                    for (IrregularScheduleRecord record : irregularRecords) {
                        if (date.equals(record.date())) {
                            ScheduleDTO scheduleDTO = ScheduleDTO.builder()
                                .scheduleId(record.scheduleId())
                                .startTime(record.startTime())
                                .endTime(record.endTime())
                                .date(record.date())
                                .worktypeName(record.worktypeName())
                                .worktypeColor(record.worktypeColor())
                                .build();
                            scheduleDTOs.add(scheduleDTO);
                            continue outer;
                        }
                    }
                }

                if (regularRecords != null){
                    for (RegularScheduleRecord record : regularRecords) {
                        if (date.isAfter(record.startDate().plusDays(1))
                            && date.isBefore(record.endDate().plusDays(1))
                            && record.daysOfWeek().name().equals(date.getDayOfWeek().name())
                            ){
                            switch (record.intervalWeeks()) {
                                case 1:
                                    ScheduleDTO scheduleDTO = ScheduleDTO.builder()
                                        .scheduleId(record.scheduleId())
                                        .startTime(record.startTime())
                                        .endTime(record.endTime())
                                        .date(date)
                                        .worktypeName(record.worktypeName())
                                        .worktypeColor(record.worktypeColor())
                                        .build();
                                    scheduleDTOs.add(scheduleDTO);
                                    continue outer;
                                case 2:
                                    LocalDate startDate = record.startDate();
                                    long weeksBetween = java.time.temporal.ChronoUnit.WEEKS.between(
                                        startDate.with(java.time.DayOfWeek.MONDAY),
                                        date.with(java.time.DayOfWeek.MONDAY)
                                    );
                                    if (weeksBetween % 2 == 0) {
                                        ScheduleDTO scheduleDTO2 = ScheduleDTO.builder()
                                            .scheduleId(record.scheduleId())
                                            .startTime(record.startTime())
                                            .endTime(record.endTime())
                                            .date(date)
                                            .worktypeName(record.worktypeName())
                                            .worktypeColor(record.worktypeColor())
                                            .build();
                                        scheduleDTOs.add(scheduleDTO2);
                                        continue outer;
                                    }
                                    break;
                                default:
                                    break;
                            }
                            continue outer;
                        }
                    }
                }

                if (defaultRecords != null){
                    for (DefaultScheduleRecord record : defaultRecords) {
                        if (date.isAfter(record.startDate().plusDays(1))
                            && date.isBefore(record.endDate().plusDays(1))
                            ){
                            ScheduleDTO scheduleDTO = ScheduleDTO.builder()
                                .scheduleId(record.scheduleId())
                                .startTime(record.startTime())
                                .endTime(record.endTime())
                                .date(date)
                                .worktypeName(record.worktypeName())
                                .worktypeColor(record.worktypeColor())
                                .build();
                            scheduleDTOs.add(scheduleDTO);
                            continue outer;
                        }
                    }
                }

                if (commonIrregularScheduleRecords != null){
                    for (IrregularScheduleRecord record : commonIrregularScheduleRecords) {
                        if (date.getMonthValue() == record.date().getMonthValue()
                            && date.getDayOfMonth() == record.date().getDayOfMonth()) {
                            ScheduleDTO scheduleDTO = ScheduleDTO.builder()
                                .scheduleId(record.scheduleId())
                                .startTime(record.startTime())
                                .endTime(record.endTime())
                                .date(date)
                                .worktypeName(record.worktypeName())
                                .worktypeColor(record.worktypeColor())
                                .build();
                            scheduleDTOs.add(scheduleDTO);
                            continue outer;
                        }
                    }
                }

                if (commonRegularScheduleRecords != null){
                    for (RegularScheduleRecord record : commonRegularScheduleRecords) {
                        if (date.isAfter(record.startDate().plusDays(1))
                            && date.isBefore(record.endDate().plusDays(1))
                            && record.daysOfWeek().name().equals(date.getDayOfWeek().name())
                            ){
                            switch (record.intervalWeeks()) {
                                case 1:
                                    ScheduleDTO scheduleDTO = ScheduleDTO.builder()
                                        .scheduleId(record.scheduleId())
                                        .startTime(record.startTime())
                                        .endTime(record.endTime())
                                        .date(date)
                                        .worktypeName(record.worktypeName())
                                        .worktypeColor(record.worktypeColor())
                                        .build();
                                    scheduleDTOs.add(scheduleDTO);
                                    continue outer;
                                case 2:
                                    LocalDate startDate = record.startDate();
                                    long weeksBetween = java.time.temporal.ChronoUnit.WEEKS.between(
                                        startDate.with(java.time.DayOfWeek.MONDAY),
                                        date.with(java.time.DayOfWeek.MONDAY)
                                    );
                                    if (weeksBetween % 2 == 0) {
                                        ScheduleDTO scheduleDTO2 = ScheduleDTO.builder()
                                            .scheduleId(record.scheduleId())
                                            .startTime(record.startTime())
                                            .endTime(record.endTime())
                                            .date(date)
                                            .worktypeName(record.worktypeName())
                                            .worktypeColor(record.worktypeColor())
                                            .build();
                                        scheduleDTOs.add(scheduleDTO2);
                                        continue outer;
                                    }
                                    break;
                                default:
                                    break;
                            }
                            continue outer;
                        }
                    }
                }

                if (commonDefaultScheduleRecords != null){
                    for (DefaultScheduleRecord record : commonDefaultScheduleRecords) {
                        if (date.isAfter(record.startDate().plusDays(1))
                            && date.isBefore(record.endDate().plusDays(1))
                            ){
                            ScheduleDTO scheduleDTO = ScheduleDTO.builder()
                                .scheduleId(record.scheduleId())
                                .startTime(record.startTime())
                                .endTime(record.endTime())
                                .date(date)
                                .worktypeName(record.worktypeName())
                                .worktypeColor(record.worktypeColor())
                                .build();
                            scheduleDTOs.add(scheduleDTO);
                            continue outer;
                        }
                    }
                }
            }
            UserDTO userDTO = UserDTO.builder()
                .userName(userRecord.userName())
                .organizationName(userRecord.organizationName())
                .schedules(scheduleDTOs)
                .build();

            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

}
