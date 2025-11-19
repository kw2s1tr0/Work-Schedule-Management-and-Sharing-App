-- 共通定期スケジュールサンプルデータ（土日は休日パターン）
INSERT INTO common_regular_schedule (start_time, end_time, start_date, end_date, days_of_week, work_type_id) VALUES
    ('09:00:00', '18:00:00', '0001-01-01', '9999-12-31', 'SATURDAY', '12'),
    ('09:00:00', '18:00:00', '0001-01-01', '9999-12-31', 'SUNDAY', '12');