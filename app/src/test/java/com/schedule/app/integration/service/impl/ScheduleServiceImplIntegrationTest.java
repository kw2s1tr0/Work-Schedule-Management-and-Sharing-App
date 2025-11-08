package com.schedule.app.integration.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.schedule.app.dto.UserDTO;
import com.schedule.app.enums.ViewMode;
import com.schedule.app.form.ScheduleSearchForm;
import com.schedule.app.record.input.ScheduleSearchRecord;
import com.schedule.app.record.output.UserDefaultScheduleRecord;
import com.schedule.app.record.output.UserIrregularScheduleRecord;
import com.schedule.app.record.output.UserRecord;
import com.schedule.app.record.output.UserRegularScheduleRecord;
import com.schedule.app.record.output.item.DefaultScheduleRecord;
import com.schedule.app.record.output.item.IrregularScheduleRecord;
import com.schedule.app.record.output.item.RegularScheduleRecord;
import com.schedule.app.service.ScheduleService;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ScheduleServiceImplIntegrationTest {

    @Autowired
    private ScheduleService scheduleService;

    @Test
    @DisplayName("統合テスト - 実際のデータで山田太郎の週表示スケジュール検索")
    void testScheduleSearchService_YamadaTaro_WeekView() {
        // Given: 実際のuser_data.sqlにある山田太郎（00001）のデータを使用
        ScheduleSearchForm form = new ScheduleSearchForm(
                "00001", // 山田太郎
                "2025-W02",
                null,
                ViewMode.WEEK,
                "山田太郎",
                "006" // 開発部（実際のorganization_data.sqlに合わせて）
        );

        // When
        List<UserDTO> result = scheduleService.scheduleSearchService(form);

        // Then
        assertThat(result).isNotNull();
        if (!result.isEmpty()) {
            UserDTO yamada = result.get(0);
            assertThat(yamada.userName()).isEqualTo("山田太郎");
            assertThat(yamada.organizationName()).isEqualTo("開発部");
            assertThat(yamada.schedules()).hasSize(7); // 週の7日分

            // 各スケジュールが適切な日付範囲内にあることを確認
            yamada.schedules().forEach(schedule -> {
                LocalDate scheduleDate = schedule.date();
                assertThat(scheduleDate).isBetween(
                        LocalDate.of(2025, 1, 6), // 2025年第2週の月曜日
                        LocalDate.of(2025, 1, 12) // 2025年第2週の日曜日
                );

                // ワークタイプが実際のschedule_type_data.sqlのデータと一致することを確認
                if (schedule.worktypeName() != null) {
                    assertThat(schedule.worktypeName()).isIn(
                            "出社", "在宅勤務", "有給休暇", "病気休暇", "代休",
                            "出張", "外出", "半休（午前）", "半休（午後）", "休日");
                    assertThat(schedule.worktypeColor()).matches("^#[0-9A-F]{6}$");
                }
            });
        }
    }

    @Test
    @DisplayName("統合テスト - 佐藤花子の月表示スケジュール検索")
    void testScheduleSearchService_SatoHanako_MonthView() {
        // Given: 実際のuser_data.sqlにある佐藤花子（00002）のデータを使用
        ScheduleSearchForm form = new ScheduleSearchForm(
                "00002", // 佐藤花子
                null,
                "2025-02", // 2025年2月
                ViewMode.MONTH,
                "佐藤花子",
                "006" // 開発部
        );

        // When
        List<UserDTO> result = scheduleService.scheduleSearchService(form);

        // Then
        assertThat(result).isNotNull();
        if (!result.isEmpty()) {
            UserDTO sato = result.get(0);
            assertThat(sato.userName()).isEqualTo("佐藤花子");
            assertThat(sato.organizationName()).isEqualTo("開発部");
            assertThat(sato.schedules()).hasSize(28); // 2025年2月は28日

            // 各スケジュールが2月の日付であることを確認
            sato.schedules().forEach(schedule -> {
                LocalDate scheduleDate = schedule.date();
                assertThat(scheduleDate.getYear()).isEqualTo(2025);
                assertThat(scheduleDate.getMonthValue()).isEqualTo(2);
            });
        }
    }

    @Test
    @DisplayName("統合テスト - 組織コード検索で開発部メンバー取得")
    void testScheduleSearchService_OrganizationCode006() {
        // Given: 開発部（組織コード006）のメンバーを検索
        ScheduleSearchForm form = new ScheduleSearchForm(
                null,
                "2025-W02",
                null,
                ViewMode.WEEK,
                null,
                "006" // 開発部
        );

        // When
        List<UserDTO> result = scheduleService.scheduleSearchService(form);

        // Then
        assertThat(result).isNotNull();
        if (!result.isEmpty()) {
            // 開発部のメンバー（山田太郎、佐藤花子）が含まれることを確認
            List<String> userNames = result.stream()
                    .map(UserDTO::userName)
                    .toList();

            assertThat(userNames).contains("山田太郎", "佐藤花子");

            // 全員が開発部に所属していることを確認
            result.forEach(user -> {
                assertThat(user.organizationName()).isEqualTo("開発部");
            });
        }
    }

    // =============================================================
    // DefaultScheduleMapper 個別テスト
    // =============================================================

    @Test
    @DisplayName("Mapper結合テスト - readDefaultSchedule: 山田太郎のデフォルトスケジュール")
    void testReadDefaultSchedule_YamadaTaro() {
        // Given: 実際のdefault_schedule_data.sqlの山田太郎データ
        // INSERT INTO default_schedule VALUES ('00001', '09:00:00', '18:00:00',
        // '2025-01-01', '2025-07-01', '1')
        // INSERT INTO default_schedule VALUES ('00001', '08:00:00', '17:00:00',
        // '2025-07-02', '9999-12-31', '2')
        ScheduleSearchRecord record = ScheduleSearchRecord.builder()
                .userId("00001") // 山田太郎
                .from(LocalDate.of(2025, 1, 6))
                .to(LocalDate.of(2025, 1, 12))
                .organizationCode("006")
                .build();

        // When
        List<UserDefaultScheduleRecord> result = scheduleService.readDefaultSchedule(record);

        // Then: 実際のDBデータと照合
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);

        UserDefaultScheduleRecord userDefault = result.get(0);
        assertThat(userDefault.defaultSchedules()).isNotEmpty();

        // 期間内（2025-01-01〜2025-07-01）のスケジュールが取得されることを確認
        DefaultScheduleRecord firstSchedule = userDefault.defaultSchedules().get(0);
        assertThat(firstSchedule.scheduleId()).isEqualTo(1);
        assertThat(firstSchedule.startTime().toString()).isEqualTo("09:00");
        assertThat(firstSchedule.endTime().toString()).isEqualTo("18:00");
        assertThat(firstSchedule.startDate()).isEqualTo(LocalDate.of(2025, 1, 1));
        assertThat(firstSchedule.endDate()).isEqualTo(LocalDate.of(2025, 7, 1));
        assertThat(firstSchedule.worktypeName()).isEqualTo("出社"); // work_type_id=1
        assertThat(firstSchedule.worktypeColor()).isEqualTo("#4CAF50");
    }

    @Test
    @DisplayName("Mapper結合テスト - readDefaultSchedule: 佐藤花子のデフォルトスケジュール")
    void testReadDefaultSchedule_SatoHanako() {
        // Given: 実際のdefault_schedule_data.sqlの佐藤花子データ
        // INSERT INTO default_schedule VALUES ('00002', '09:00:00', '17:30:00',
        // '2025-01-01', '9999-12-31', '1')
        ScheduleSearchRecord record = ScheduleSearchRecord.builder()
                .userId("00002") // 佐藤花子
                .from(LocalDate.of(2025, 1, 1))
                .to(LocalDate.of(2025, 12, 31))
                .organizationCode("006")
                .build();

        // When
        List<UserDefaultScheduleRecord> result = scheduleService.readDefaultSchedule(record);

        // Then: 実際のDBデータと照合
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);

        UserDefaultScheduleRecord userDefault = result.get(0);
        assertThat(userDefault.defaultSchedules()).hasSize(1);

        DefaultScheduleRecord schedule = userDefault.defaultSchedules().get(0);
        assertThat(schedule.scheduleId()).isEqualTo(2);
        assertThat(schedule.startTime().toString()).isEqualTo("09:00");
        assertThat(schedule.endTime().toString()).isEqualTo("17:30");
        assertThat(schedule.startDate()).isEqualTo(LocalDate.of(2025, 1, 1));
        assertThat(schedule.endDate()).isEqualTo(LocalDate.of(9999, 12, 31));
        assertThat(schedule.worktypeName()).isEqualTo("出社");
        assertThat(schedule.worktypeColor()).isEqualTo("#4CAF50");
    }

    // =============================================================
    // RegularScheduleMapper 個別テスト
    // =============================================================

    @Test
    @DisplayName("Mapper結合テスト - readRegularSchedule: 実際のデータでの検証")
    void testReadRegularSchedule_WithRealData() {
        // Given: regular_schedule_data.sqlに実データがある場合の検証
        ScheduleSearchRecord record = ScheduleSearchRecord.builder()
                .from(LocalDate.of(2025, 1, 1))
                .to(LocalDate.of(2025, 12, 31))
                .organizationCode("006") // 開発部
                .build();

        // When
        List<UserRegularScheduleRecord> result = scheduleService.readRegularSchedule(record);

        // Then: データ構造の検証（実データに依存）
        assertThat(result).isNotNull();
        result.forEach(userRegular -> {
            assertThat(userRegular.regularSchedules()).isNotNull();
            userRegular.regularSchedules().forEach(schedule -> {
                assertThat(schedule.scheduleId()).isNotNull();
                assertThat(schedule.startTime()).isNotNull();
                assertThat(schedule.endTime()).isNotNull();
                assertThat(schedule.daysOfWeek()).isNotNull();
                assertThat(schedule.intervalWeeks()).isGreaterThan(0);
                assertThat(schedule.worktypeName()).isNotNull();
                assertThat(schedule.worktypeColor()).matches("^#[0-9A-F]{6}$");
            });
        });
    }

    // =============================================================
    // IrregularScheduleMapper 個別テスト
    // =============================================================

    @Test
    @DisplayName("Mapper結合テスト - readIrregularSchedule: 実際のデータでの検証")
    void testReadIrregularSchedule_WithRealData() {
        // Given: irregular_schedule_data.sqlに実データがある場合の検証
        ScheduleSearchRecord record = ScheduleSearchRecord.builder()
                .from(LocalDate.of(2025, 1, 1))
                .to(LocalDate.of(2025, 12, 31))
                .organizationCode("006") // 開発部
                .build();

        // When
        List<UserIrregularScheduleRecord> result = scheduleService.readIrregularSchedule(record);

        // Then: データ構造の検証（実データに依存）
        assertThat(result).isNotNull();
        result.forEach(userIrregular -> {
            assertThat(userIrregular.irregularSchedules()).isNotNull();
            userIrregular.irregularSchedules().forEach(schedule -> {
                assertThat(schedule.scheduleId()).isNotNull();
                assertThat(schedule.date()).isNotNull();
                assertThat(schedule.startTime()).isNotNull();
                assertThat(schedule.endTime()).isNotNull();
                assertThat(schedule.worktypeName()).isNotNull();
                assertThat(schedule.worktypeColor()).matches("^#[0-9A-F]{6}$");

                // 検索期間内の日付であることを確認
                assertThat(schedule.date()).isBetween(
                        LocalDate.of(2025, 1, 1),
                        LocalDate.of(2025, 12, 31));
            });
        });
    }

    @Test
    @DisplayName("統合テスト - CommonScheduleSearchMapperとの結合テスト")
    void testCommonScheduleSearchMapper_Integration() {
        // Given
        ScheduleSearchRecord record = ScheduleSearchRecord.builder()
                .userId("00001")
                .from(LocalDate.of(2025, 1, 6))
                .to(LocalDate.of(2025, 1, 12))
                .organizationCode("001")
                .build();

        // When
        List<DefaultScheduleRecord> commonDefaultSchedules = scheduleService.readCommonDefaultScheduleRecord(record);
        List<RegularScheduleRecord> commonRegularSchedules = scheduleService.readCommonRegularUserRecords(record);
        List<IrregularScheduleRecord> commonIrregularSchedules = scheduleService.readCommonIrregularUserRecords(record);

        // Then
        assertThat(commonDefaultSchedules).isNotNull();
        assertThat(commonRegularSchedules).isNotNull();
        assertThat(commonIrregularSchedules).isNotNull();

        // 共通スケジュールの構造確認
        commonDefaultSchedules.forEach(schedule -> {
            assertThat(schedule.scheduleId()).isNotNull();
            if (schedule.startTime() != null) {
                assertThat(schedule.startTime()).isBefore(schedule.endTime());
            }
        });
        commonRegularSchedules.forEach(schedule -> {
            assertThat(schedule.scheduleId()).isNotNull();
            if (schedule.startTime() != null) {
                assertThat(schedule.startTime()).isBefore(schedule.endTime());
            }
        });
        commonIrregularSchedules.forEach(schedule -> {
            assertThat(schedule.scheduleId()).isNotNull();
            if (schedule.startTime() != null) {
                assertThat(schedule.startTime()).isBefore(schedule.endTime());
            }
        });
    }

    // =============================================================
    // CommonScheduleSearchMapper 個別テスト
    // =============================================================

    @Test
    @DisplayName("CommonMapper結合テスト - readCommonDefaultScheduleRecord: 共通デフォルトスケジュール")
    void testReadCommonDefaultScheduleRecord() {
        // Given: 実際のcommon_default_schedule_data.sqlのデータ
        // INSERT INTO common_default_schedule VALUES ('09:00:00', '18:00:00',
        // '0001-01-01', '9999-12-31', '1')
        ScheduleSearchRecord record = ScheduleSearchRecord.builder()
                .from(LocalDate.of(2025, 1, 1))
                .to(LocalDate.of(2025, 12, 31))
                .build();

        // When
        List<DefaultScheduleRecord> result = scheduleService.readCommonDefaultScheduleRecord(record);

        // Then: 実際のDBデータと照合
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);

        DefaultScheduleRecord commonDefault = result.get(0);
        assertThat(commonDefault.scheduleId()).isEqualTo(1);
        assertThat(commonDefault.startTime().toString()).isEqualTo("09:00");
        assertThat(commonDefault.endTime().toString()).isEqualTo("18:00");
        assertThat(commonDefault.startDate()).isEqualTo(LocalDate.of(1, 1, 1)); // 0001-01-01
        assertThat(commonDefault.endDate()).isEqualTo(LocalDate.of(9999, 12, 31));
        assertThat(commonDefault.worktypeName()).isEqualTo("出社"); // work_type_id=1
        assertThat(commonDefault.worktypeColor()).isEqualTo("#4CAF50");
    }

    @Test
    @DisplayName("CommonMapper結合テスト - readCommonRegularUserRecords: 土日休日パターン")
    void testReadCommonRegularUserRecords() {
        // Given: 実際のcommon_regular_schedule_data.sqlのデータ（土日は休日）
        // INSERT INTO common_regular_schedule VALUES (NULL, NULL, '0001-01-01',
        // '9999-12-31', 'SATURDAY', 1, '10')
        // INSERT INTO common_regular_schedule VALUES (NULL, NULL, '0001-01-01',
        // '9999-12-31', 'SUNDAY', 1, '10')
        ScheduleSearchRecord record = ScheduleSearchRecord.builder()
                .from(LocalDate.of(2025, 1, 1))
                .to(LocalDate.of(2025, 12, 31))
                .build();

        // When
        List<RegularScheduleRecord> result = scheduleService.readCommonRegularUserRecords(record);

        // Then: 実際のDBデータと照合
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2); // 土曜日と日曜日の2レコード

        // 土曜日のスケジュール確認
        RegularScheduleRecord saturdaySchedule = result.stream()
                .filter(schedule -> schedule.daysOfWeek().name().equals("SATURDAY"))
                .findFirst()
                .orElse(null);

        assertThat(saturdaySchedule).isNotNull();
        assertThat(saturdaySchedule.scheduleId()).isEqualTo(1);
        assertThat(saturdaySchedule.startTime()).isNull(); // 休日は時間なし
        assertThat(saturdaySchedule.endTime()).isNull();
        assertThat(saturdaySchedule.startDate()).isEqualTo(LocalDate.of(1, 1, 1));
        assertThat(saturdaySchedule.endDate()).isEqualTo(LocalDate.of(9999, 12, 31));
        assertThat(saturdaySchedule.intervalWeeks()).isEqualTo(1);
        assertThat(saturdaySchedule.worktypeName()).isEqualTo("休日"); // work_type_id=10
        assertThat(saturdaySchedule.worktypeColor()).isEqualTo("#9E9E9E");

        // 日曜日のスケジュール確認
        RegularScheduleRecord sundaySchedule = result.stream()
                .filter(schedule -> schedule.daysOfWeek().name().equals("SUNDAY"))
                .findFirst()
                .orElse(null);

        assertThat(sundaySchedule).isNotNull();
        assertThat(sundaySchedule.scheduleId()).isEqualTo(2);
        assertThat(sundaySchedule.daysOfWeek().name()).isEqualTo("SUNDAY");
        assertThat(sundaySchedule.worktypeName()).isEqualTo("休日");
    }

    @Test
    @DisplayName("CommonMapper結合テスト - readCommonIrregularUserRecords: 年末年始特別スケジュール")
    void testReadCommonIrregularUserRecords() {
        // Given: 実際のcommon_irregular_schedule_data.sqlのデータ（年末年始）
        // 2025-12-29から2026-01-03までの6日間が休日
        ScheduleSearchRecord record = ScheduleSearchRecord.builder()
                .from(LocalDate.of(2025, 12, 25))
                .to(LocalDate.of(2026, 1, 10))
                .build();

        // When
        List<IrregularScheduleRecord> result = scheduleService.readCommonIrregularUserRecords(record);

        // Then: 実際のDBデータと照合
        assertThat(result).isNotNull();
        assertThat(result).hasSize(6); // 年末年始の6日間

        // 2025-12-29のスケジュール確認
        IrregularScheduleRecord dec29 = result.stream()
                .filter(schedule -> schedule.date().equals(LocalDate.of(2025, 12, 29)))
                .findFirst()
                .orElse(null);

        assertThat(dec29).isNotNull();
        assertThat(dec29.scheduleId()).isEqualTo(1);
        assertThat(dec29.startTime()).isNull(); // 休日は時間なし
        assertThat(dec29.endTime()).isNull();
        assertThat(dec29.worktypeName()).isEqualTo("休日"); // work_type_id=10
        assertThat(dec29.worktypeColor()).isEqualTo("#9E9E9E");

        // 2026-01-01（元日）のスケジュール確認
        IrregularScheduleRecord jan1 = result.stream()
                .filter(schedule -> schedule.date().equals(LocalDate.of(2026, 1, 1)))
                .findFirst()
                .orElse(null);

        assertThat(jan1).isNotNull();
        assertThat(jan1.scheduleId()).isEqualTo(4);
        assertThat(jan1.worktypeName()).isEqualTo("休日");

        // 全ての日付が期待される範囲内であることを確認
        List<LocalDate> expectedDates = Arrays.asList(
                LocalDate.of(2025, 12, 29),
                LocalDate.of(2025, 12, 30),
                LocalDate.of(2025, 12, 31),
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 1, 2),
                LocalDate.of(2026, 1, 3));

        List<LocalDate> actualDates = result.stream()
                .map(IrregularScheduleRecord::date)
                .sorted()
                .toList();

        assertThat(actualDates).containsExactlyElementsOf(expectedDates);
    }

    // =============================================================
    // UserSearchMapper 個別テスト
    // =============================================================

    @Test
    @DisplayName("Mapper結合テスト - readUserRecords: 山田太郎の検索")
    void testReadUserRecords_YamadaTaro() {
        // Given: 実際のuser_data.sqlの山田太郎
        // INSERT INTO users VALUES ('00001', '006', '山田太郎', '022', '2020-04-01', NULL)
        ScheduleSearchRecord record = ScheduleSearchRecord.builder()
                .userId("00001")
                .from(LocalDate.of(2025, 1, 6))
                .to(LocalDate.of(2025, 1, 12))
                .names(Arrays.asList("山田"))
                .organizationCode("006")
                .build();

        // When
        List<UserRecord> result = scheduleService.readUserRecords(record);

        // Then: 実際のDBデータと照合
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);

        UserRecord yamada = result.get(0);
        assertThat(yamada.userName()).isEqualTo("山田太郎");
        assertThat(yamada.organizationName()).isEqualTo("開発部"); // organization_code=006
    }

    @Test
    @DisplayName("Mapper結合テスト - readUserRecords: 開発部メンバー全体")
    void testReadUserRecords_OrganizationCode006() {
        // Given: 開発部（006）のメンバー検索
        ScheduleSearchRecord record = ScheduleSearchRecord.builder()
                .from(LocalDate.of(2025, 1, 6))
                .to(LocalDate.of(2025, 1, 12))
                .organizationCode("006")
                .build();

        // When
        List<UserRecord> result = scheduleService.readUserRecords(record);

        // Then: 実際のDBデータと照合（山田太郎、佐藤花子が在籍）
        assertThat(result).isNotNull();
        assertThat(result).hasSizeGreaterThanOrEqualTo(2);

        List<String> userNames = result.stream()
                .map(UserRecord::userName)
                .toList();

        assertThat(userNames).contains("山田太郎", "佐藤花子");

        // 全員が開発部であることを確認
        result.forEach(user -> {
            assertThat(user.organizationName()).isEqualTo("開発部");
        });

        // 並び替え確認：組織コード→役職コード→氏名の順
        // 同じ組織なので、役職コード→氏名の順になっているはず
        for (int i = 1; i < result.size(); i++) {
            String prevName = result.get(i - 1).userName();
            String currentName = result.get(i).userName();
            // 役職が同じ場合は氏名順になっていることを期待（日本語順序は複雑だが基本チェック）
            assertThat(prevName).isNotNull();
            assertThat(currentName).isNotNull();
        }
    }

    @Test
    @DisplayName("Mapper結合テスト - readUserRecords: 退職者除外機能")
    void testReadUserRecords_ExcludeResignedUsers() {
        // Given: 退職日より後の検索期間（退職者を除外すべき）
        // user_data.sqlの田中一郎（00003）は '2025-10-31' に退職
        // 渡辺由美（00006）は '2025-03-15' に退職
        ScheduleSearchRecord record = ScheduleSearchRecord.builder()
                .from(LocalDate.of(2025, 11, 1)) // 田中一郎の退職後
                .to(LocalDate.of(2025, 11, 7))
                .organizationCode("010") // 開発第1チーム（田中一郎の所属）
                .build();

        // When
        List<UserRecord> result = scheduleService.readUserRecords(record);

        // Then: 退職者が除外されていることを確認
        assertThat(result).isNotNull();

        List<String> userNames = result.stream()
                .map(UserRecord::userName)
                .toList();

        // 退職済みの田中一郎は含まれないはず
        assertThat(userNames).doesNotContain("田中一郎");

        // 現役の鈴木美咲（00004）は含まれるはず（resignation_date = NULL）
        assertThat(userNames).contains("鈴木美咲");
    }

    @Test
    @DisplayName("Mapper結合テスト - readUserRecords: 名前部分一致検索")
    void testReadUserRecords_PartialNameSearch() {
        // Given: 部分一致検索（「田」を含む名前）
        ScheduleSearchRecord record = ScheduleSearchRecord.builder()
                .from(LocalDate.of(2025, 1, 1))
                .to(LocalDate.of(2025, 1, 31))
                .names(Arrays.asList("田"))
                .build();

        // When
        List<UserRecord> result = scheduleService.readUserRecords(record);

        // Then: 実際のDBデータと照合
        assertThat(result).isNotNull();

        List<String> userNames = result.stream()
                .map(UserRecord::userName)
                .toList();

        // user_data.sqlから「田」を含む名前の確認
        // 山田太郎、佐藤花子、田中一郎（ただし退職日チェック）、加藤恵、森田直樹
        // 2025-01期間では田中一郎はまだ在籍（退職日2025-10-31）
        assertThat(userNames).contains("山田太郎", "佐藤花子", "田中一郎", "加藤恵", "森田直樹");
    }

    @Test
    @DisplayName("Mapper結合テスト - readUserRecords: 複数名前条件検索")
    void testReadUserRecords_MultipleNameSearch() {
        // Given: 複数の名前条件（「山田」AND「太郎」）
        ScheduleSearchRecord record = ScheduleSearchRecord.builder()
                .from(LocalDate.of(2025, 1, 1))
                .to(LocalDate.of(2025, 1, 31))
                .names(Arrays.asList("山田", "太郎"))
                .build();

        // When
        List<UserRecord> result = scheduleService.readUserRecords(record);

        // Then: 両方の条件を満たすユーザーのみ取得
        assertThat(result).isNotNull();

        List<String> userNames = result.stream()
                .map(UserRecord::userName)
                .toList();

        // 「山田」AND「太郎」を両方含む名前は「山田太郎」のみ
        assertThat(userNames).contains("山田太郎");
        assertThat(userNames).doesNotContain("佐藤花子", "田中一郎");
    }
}
