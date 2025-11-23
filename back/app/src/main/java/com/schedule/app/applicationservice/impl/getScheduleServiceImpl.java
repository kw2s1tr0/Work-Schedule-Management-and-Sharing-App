package com.schedule.app.applicationservice.impl;

import com.schedule.app.applicationservice.GetScheduleService;
import com.schedule.app.domainservice.ScheduleService;
import com.schedule.app.dto.ScheduleDTO;
import com.schedule.app.dto.UserDTO;
import com.schedule.app.entity.Schedule;
import com.schedule.app.entity.User;
import com.schedule.app.form.ScheduleSearchForm;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.DefaultScheduleOutputRecord;
import com.schedule.app.record.output.IrregularScheduleOutputRecord;
import com.schedule.app.record.output.RegularScheduleOutputRecord;
import com.schedule.app.record.output.UserRecord;
import com.schedule.app.repository.CommonScheduleSearchMapper;
import com.schedule.app.repository.ScheduleSearchMapper;
import com.schedule.app.repository.UserSearchMapper;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetScheduleServiceImpl implements GetScheduleService {

  private final ScheduleService scheduleService;
  private final ScheduleSearchMapper scheduleSearchMapper;
  private final CommonScheduleSearchMapper commonScheduleSearchMapper;
  private final UserSearchMapper userSearchMapper;

  /**
   * スケジュール検索サービス ユーザーごとのスケジュールリストを検索条件に基づき取得する
   *
   * @param form スケジュール検索条件
   * @return 条件に一致したユーザーDTOリスト(各ユーザーはスケジュールDTOリスト内包)
   */
  @Override
  public List<UserDTO> scheduleSearchService(ScheduleSearchForm form) {
    ScheduleSearchRecord record = toScheduleSearchRecord(form);
    List<RegularScheduleOutputRecord> regularSchedules = readRegularSchedule(record);
    List<IrregularScheduleOutputRecord> irregularSchedules = readIrregularSchedule(record);
    List<DefaultScheduleOutputRecord> defaultSchedules = readDefaultSchedule(record);
    List<DefaultScheduleOutputRecord> commonDefaultScheduleRecords =
        readCommonDefaultScheduleRecord(record);
    List<RegularScheduleOutputRecord> commonRegularScheduleRecords =
        readCommonRegularUserRecords(record);
    List<IrregularScheduleOutputRecord> commonIrregularScheduleRecords =
        readCommonIrregularUserRecords(record);
    List<UserRecord> userRecords = readUserRecords(record);
    List<User> userList =
        toUserEntityList(
            defaultSchedules,
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

  /**
   * スケジュール検索条件をScheduleSearchRecordに変換する
   *
   * @param form スケジュール検索条件
   * @return ScheduleSearchRecord
   */
  @Override
  public ScheduleSearchRecord toScheduleSearchRecord(ScheduleSearchForm form) {

    // 週の開始日と終了日を取得
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
        // その年の1/1を基準に、指定された週に移動し、週の初めの月曜日に設定し、週の終わりの日曜日も取得
        from =
            LocalDate.ofYearDay(year, 1)
                .with(weekFields.weekOfYear(), week)
                .with(weekFields.dayOfWeek(), 1);
        to = from.plusDays(6);
        break;
      case MONTH:
        year = Integer.parseInt(form.month().substring(0, 4));
        month = Integer.parseInt(form.month().substring(5));
        from = LocalDate.of(year, month, 1);
        to = from.plusMonths(1).minusDays(1);
        break;
      default:
        throw new IllegalArgumentException("Invalid view mode: " + form.viewMode());
    }

    // 名前をスペースで分割してリスト化
    List<String> names;
    if (form.name() == null || form.name().isBlank()) {
      names = null;
    } else {
      names = Arrays.asList(form.name().trim().split("\\s+|　+"));
    }

    // ユーザーIDを正規化(全角→半角)
    String userId;
    if (form.userId() == null) {
      userId = null;
    } else {
      userId = Normalizer.normalize(form.userId(), Normalizer.Form.NFKC);
    }

    ScheduleSearchRecord record =
        ScheduleSearchRecord.builder()
            .userId(userId)
            .from(from)
            .to(to)
            .names(names)
            .organizationCode(form.organizationCode())
            .build();
    return record;
  }

  /**
   * 定期スケジュールレコードを取得する
   *
   * @param record スケジュール検索条件
   * @return 定期スケジュールレコードリスト
   */
  @Override
  public List<RegularScheduleOutputRecord> readRegularSchedule(ScheduleSearchRecord record) {
    List<RegularScheduleOutputRecord> regularRecords =
        scheduleSearchMapper.readRegularScheduleRecord(record);
    return regularRecords;
  }

  /**
   * 非定期スケジュールレコードを取得する
   *
   * @param record スケジュール検索条件
   * @return 非定期スケジュールレコードリスト
   */
  @Override
  public List<IrregularScheduleOutputRecord> readIrregularSchedule(ScheduleSearchRecord record) {
    List<IrregularScheduleOutputRecord> irregularRecords =
        scheduleSearchMapper.readIrregularScheduleRecord(record);
    return irregularRecords;
  }

  /**
   * デフォルトスケジュールレコードを取得する
   *
   * @param record スケジュール検索条件
   * @return デフォルトスケジュールレコードリスト
   */
  @Override
  public List<DefaultScheduleOutputRecord> readDefaultSchedule(ScheduleSearchRecord record) {
    List<DefaultScheduleOutputRecord> defaultRecords =
        scheduleSearchMapper.readDefaultScheduleRecord(record);
    return defaultRecords;
  }

  /**
   * 共通デフォルトスケジュールレコードを取得する
   *
   * @param record スケジュール検索条件
   * @return 共通デフォルトスケジュールレコードリスト
   */
  @Override
  public List<DefaultScheduleOutputRecord> readCommonDefaultScheduleRecord(
      ScheduleSearchRecord record) {
    List<DefaultScheduleOutputRecord> defaultScheduleRecords =
        commonScheduleSearchMapper.readDefaultScheduleRecord(record);
    return defaultScheduleRecords;
  }

  /**
   * 共通定期スケジュールレコードを取得する
   *
   * @param record スケジュール検索条件
   * @return 共通定期スケジュールレコードリスト
   */
  @Override
  public List<RegularScheduleOutputRecord> readCommonRegularUserRecords(
      ScheduleSearchRecord record) {
    List<RegularScheduleOutputRecord> regularScheduleRecords =
        commonScheduleSearchMapper.readRegularScheduleRecord(record);
    return regularScheduleRecords;
  }

  /**
   * 共通非定期スケジュールレコードを取得する
   *
   * @param record スケジュール検索条件
   * @return 共通非定期スケジュールレコードリスト
   */
  @Override
  public List<IrregularScheduleOutputRecord> readCommonIrregularUserRecords(
      ScheduleSearchRecord record) {
    List<IrregularScheduleOutputRecord> irregularScheduleRecords =
        commonScheduleSearchMapper.readIrregularScheduleRecord(record);
    return irregularScheduleRecords;
  }

  /**
   * ユーザーレコードを取得する
   *
   * @param record スケジュール検索条件
   * @return ユーザーレコードリスト
   */
  @Override
  public List<UserRecord> readUserRecords(ScheduleSearchRecord record) {
    List<UserRecord> userRecords = userSearchMapper.readUserRecord(record);
    return userRecords;
  }

  /**
   * 検索結果のレコードをユーザーエンティティリストに変換する
   *
   * @param defaultRecords ユーザーレコードリスト（デフォルトスケジュールリストを内包）
   * @param regularRecords ユーザーレコードリスト（定期スケジュールリストを内包）
   * @param irregularRecords ユーザーレコードリスト（非定期スケジュールリストを内包）
   * @param commonDefaultScheduleRecords 共通デフォルトスケジュールレコード
   * @param commonRegularScheduleRecords 共通定期スケジュールレコード
   * @param commonIrregularScheduleRecords 共通非定期スケジュールレコード
   * @param from 検索開始日
   * @param to 検索終了日
   * @param userRecords ユーザーレコードリスト
   * @return ユーザーエンティティリスト
   */
  @Override
  public List<User> toUserEntityList(
      List<DefaultScheduleOutputRecord> defaultRecords,
      List<RegularScheduleOutputRecord> regularRecords,
      List<IrregularScheduleOutputRecord> irregularRecords,
      List<DefaultScheduleOutputRecord> commonDefaultScheduleRecords,
      List<RegularScheduleOutputRecord> commonRegularScheduleRecords,
      List<IrregularScheduleOutputRecord> commonIrregularScheduleRecords,
      LocalDate from,
      LocalDate to,
      List<UserRecord> userRecords) {

    // ユーザーレコードが空の場合は空のリストを返す
    if (userRecords.isEmpty()) {
      List<User> emptyList = new ArrayList<>();
      return emptyList;
    }

    List<User> userList = new ArrayList<>();

    // ユーザーレコードを元にユーザーエンティティを生成し、リストに追加していく
    for (UserRecord userRecord : userRecords) {

      List<Schedule> schedules = new ArrayList<>();

      // ユーザーのスケジュールを生成
      // 日付ごとにスケジュールをチェックし、該当するスケジュールをリストにセット
      for (LocalDate date = from; !date.isAfter(to); date = date.plusDays(1)) {

        // スケジュールエンティティを生成
        // スケジュールが適合した場合にフィールドがセットされる仕組み
        Schedule schedule = Schedule.builder().date(date).build();

        // スケジュールエンティティにマッチするスケジュール情報をセット
        scheduleService.scheduleService(
            defaultRecords,
            regularRecords,
            irregularRecords,
            commonDefaultScheduleRecords,
            commonRegularScheduleRecords,
            commonIrregularScheduleRecords,
            schedule,
            userRecord);

        schedules.add(schedule);
      }

      // ユーザーエンティティを生成し、スケジュールリストをセット
      User user =
          User.builder()
              .userName(userRecord.getName())
              .organizationName(userRecord.getOrganizationName())
              .schedules(schedules)
              .build();

      // ユーザーリストに追加
      userList.add(user);
    }
    return userList;
  }

  /**
   * ユーザーエンティティリストをユーザーDTOリストに変換する
   *
   * @param userList ユーザーエンティティリスト
   * @return ユーザーDTOリスト
   */
  @Override
  public List<UserDTO> toUserDTOList(List<User> userList) {

    // ユーザーレコードが空の場合は空のリストを返す
    if (userList.isEmpty()) {
      List<UserDTO> emptyList = new ArrayList<>();
      return emptyList;
    }

    List<UserDTO> userDTOList = new ArrayList<>();

    // ユーザーエンティティリストを元にユーザーDTOリストを生成
    for (User user : userList) {
      List<ScheduleDTO> scheduleDTOs = new ArrayList<>();

      for (Schedule schedule : user.getSchedules()) {
        ScheduleDTO scheduleDTO =
            ScheduleDTO.builder()
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
      UserDTO userDTO =
          UserDTO.builder()
              .userName(user.getUserName())
              .organizationName(user.getOrganizationName())
              .schedules(scheduleDTOs)
              .build();
      userDTOList.add(userDTO);
    }
    return userDTOList;
  }
}
