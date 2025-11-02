-- 定期スケジュールサンプルデータ
INSERT INTO regular_schedule (user_id, start_time, end_time, start_date, end_date, days_of_week, interval_weeks, work_type_id) VALUES
    (1, '09:00:00', '18:00:00', '2025-01-01', NULL, 'MONDAY', 1, '2'),
    (1, '09:00:00', '18:00:00', '2025-01-01', NULL, 'TUESDAY', 1, '2'),
    (1, '09:00:00', '18:00:00', '2025-01-01', NULL, 'WEDNESDAY', 1, '2'),
    (1, '09:00:00', '18:00:00', '2025-01-01', NULL, 'THURSDAY', 1, '2'),
    (1, '09:00:00', '18:00:00', '2025-01-01', NULL, 'FRIDAY', 1, '2'),
    (2, '10:00:00', '19:00:00', '2025-01-01', NULL, 'MONDAY', 2, '2'),
    (2, '10:00:00', '19:00:00', '2025-01-01', NULL, 'WEDNESDAY', 2, '2'),
    (2, '10:00:00', '19:00:00', '2025-01-01', NULL, 'FRIDAY', 2, '2');