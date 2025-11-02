-- 共通定期スケジュールサンプルデータ（土日は休日パターン）
INSERT INTO common_regular_schedule (user_id, start_time, end_time, start_date, end_date, days_of_week, interval_weeks, work_type_id) VALUES
    (1, NULL, NULL, '2025-01-01', '2025-12-31', 'SATURDAY', 1, '10'),
    (1, NULL, NULL, '2025-01-01', '2025-12-31', 'SUNDAY', 1, '10');