-- 共通定期スケジュールサンプルデータ（土日は休日パターン）
INSERT INTO common_regular_schedule (start_time, end_time, start_date, end_date, days_of_week, interval_weeks, work_type_id) VALUES
    (NULL, NULL, '0001-01-01', '9999-12-31', 'SATURDAY', 1, '12'),
    (NULL, NULL, '0001-01-01', '9999-12-31', 'SUNDAY', 1, '12');