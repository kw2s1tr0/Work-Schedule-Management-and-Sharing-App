-- 子テーブル（共通スケジュール）
DROP TABLE IF EXISTS common_default_schedule;
DROP TABLE IF EXISTS common_irregular_schedule;
DROP TABLE IF EXISTS common_regular_schedule;

-- 個別スケジュール
DROP TABLE IF EXISTS default_schedule;
DROP TABLE IF EXISTS irregular_schedule;
DROP TABLE IF EXISTS regular_schedule;

-- マスタ類
DROP TABLE IF EXISTS schedule_type;
DROP TABLE IF EXISTS authuser;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS position;
DROP TABLE IF EXISTS organization;

-- Enum type
DROP TYPE IF EXISTS day_of_week;