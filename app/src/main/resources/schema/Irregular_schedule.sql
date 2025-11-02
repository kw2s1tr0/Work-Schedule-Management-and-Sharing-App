DROP TABLE IF EXISTS irregular_schedule;
CREATE TABLE irregular_schedule (
    id INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY, -- 自動採番ID
    user_id INTEGER NOT NULL,              -- ユーザID（外部キー）
    start_time TIME,              -- 開始時刻
    end_time TIME,                -- 終了時刻
    start_date DATE NOT NULL,              -- 適用開始日
    end_date DATE,                -- 適用終了日
    work_type_id VARCHAR(10) NOT NULL      -- 勤怠種別ID（例: 出社、在宅、有給）
);