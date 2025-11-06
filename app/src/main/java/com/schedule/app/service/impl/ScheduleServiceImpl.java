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
import com.schedule.app.entity.User;
import com.schedule.app.entity.item.Schedule;
import com.schedule.app.enums.ScheduleType;
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
        ScheduleSearchRecord record = toScheduleSearchRecord(form);
        List<UserRegularScheduleRecord> regularSchedules = readRegularSchedule(record);
        List<UserIrregularScheduleRecord> irregularSchedules = readIrregularSchedule(record);
        List<UserDefaultScheduleRecord> defaultSchedules = readDefaultSchedule(record);
        List<DefaultScheduleRecord> commonDefaultScheduleRecords = readCommonDefaultScheduleRecord(record);
        List<RegularScheduleRecord> commonRegularScheduleRecords = readCommonRegularUserRecords(record);
        List<IrregularScheduleRecord> commonIrregularScheduleRecords = readCommonIrregularUserRecords(record);
        List<UserRecord> userRecords = readUserRecords(record);
        List<User> userList = toUserEntityList(defaultSchedules,
                                                regularSchedules,
                                                irregularSchedules,
                                                commonDefaultScheduleRecords,
                                                commonRegularScheduleRecords,       
                                                commonIrregularScheduleRecords,
                                                record.from(),
                                                record.to(),
                                                userRecords);
        List<UserDTO> userDTOList = toUserDTOList(userList);
        return userDTOList;
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
    public List<User> toUserEntityList(List<UserDefaultScheduleRecord> defaultUserRecords,
            List<UserRegularScheduleRecord> regularUserRecords, List<UserIrregularScheduleRecord> irregularUserRecords,
            List<DefaultScheduleRecord> commonDefaultScheduleRecords,
            List<RegularScheduleRecord> commonRegularScheduleRecords,
            List<IrregularScheduleRecord> commonIrregularScheduleRecords, LocalDate from, LocalDate to,
            List<UserRecord> userRecords) {

        if (userRecords.isEmpty()) {
            List<User> emptyList = new ArrayList<>();
            return emptyList;
        }

        List<User> userList = new ArrayList<>();

        
        for (int i = 0; i < userRecords.size(); i++) {
            UserRecord userRecord = userRecords.get(i);
            List<DefaultScheduleRecord> defaultRecords = defaultUserRecords.get(i).defaultSchedules();
            List<RegularScheduleRecord> regularRecords = regularUserRecords.get(i).regularSchedules();
            List<IrregularScheduleRecord> irregularRecords = irregularUserRecords.get(i).irregularSchedules();

            List<Schedule> schedules = new ArrayList<>();

            outer:
            for (LocalDate date = from; !date.isAfter(to); date = date.plusDays(1)) {

                Schedule schedule = Schedule.builder()
                                            .date(date)
                                            .build();               
                if (!irregularRecords.isEmpty()){
                    for (IrregularScheduleRecord record : irregularRecords) {
                        if (schedule.matches(record, ScheduleType.IRREGULAR)) {
                            schedules.add(schedule);
                            continue outer;
                        }
                    }
                }

                if (!regularRecords.isEmpty()){
                    for (RegularScheduleRecord record : regularRecords) {
                        if (schedule.matches(record, ScheduleType.REGULAR)) {
                            schedules.add(schedule);
                            continue outer;
                        }
                    }
                }

                if (!commonIrregularScheduleRecords.isEmpty()){
                    for (IrregularScheduleRecord record : commonIrregularScheduleRecords) {
                        if (date.equals(record.date())) {
                            schedules.add(schedule);
                            continue outer;
                        }
                    }
                }


                if (!commonRegularScheduleRecords.isEmpty()){
                    for (RegularScheduleRecord record : commonRegularScheduleRecords) {
                        if (schedule.matches(record, ScheduleType.COMMON_REGULAR)) {
                            schedules.add(schedule);
                            continue outer;
                        }
                    }
                }

                if (!defaultRecords.isEmpty()){
                    for (DefaultScheduleRecord record : defaultRecords) {
                        if (schedule.matches(record, ScheduleType.DEFAULT)) {
                            schedules.add(schedule);
                            continue outer;
                        }
                    }
                }

                if (!commonDefaultScheduleRecords.isEmpty()){
                    for (DefaultScheduleRecord record : commonDefaultScheduleRecords) {
                        if (schedule.matches(record, ScheduleType.COMMON_DEFAULT)) {
                            schedules.add(schedule);
                            continue outer;
                        }
                    }
                }
            }
            User user = User.builder()
                .userName(userRecord.userName())
                .organizationName(userRecord.organizationName())
                .schedules(schedules)
                .build();

            userList.add(user);
        }
        return userList;
    }

    @Override
    public List<UserDTO> toUserDTOList(List<User> userList) {
        
        if (userList.isEmpty()) {
            List<UserDTO> emptyList = new ArrayList<>();
            return emptyList;
        }

        List<UserDTO> userDTOList = new ArrayList<>();

        for (User user : userList) {
            List<ScheduleDTO> scheduleDTOs = new ArrayList<>();

            for (Schedule schedule : user.getSchedules()) {
                ScheduleDTO scheduleDTO = ScheduleDTO.builder()
                    .scheduleId(schedule.getScheduleId())
                    .date(schedule.getDate())
                    .startTime(schedule.getStartTime())
                    .endTime(schedule.getEndTime())
                    .worktypeName(schedule.getWorktypeName())
                    .worktypeColor(schedule.getWorktypeColor())
                    .scheduleType(schedule.getScheduleType())
                    .build();
                scheduleDTOs.add(scheduleDTO);
            }
            UserDTO userDTO = UserDTO.builder()
                .userName(user.getUserName())
                .organizationName(user.getOrganizationName())
                .schedules(scheduleDTOs)
                .build();
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }
}
