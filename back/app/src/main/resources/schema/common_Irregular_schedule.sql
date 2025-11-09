DROP TABLE IF EXISTS common_irregular_schedule;
CREATE TABLE common_irregular_schedule (
    id INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY, -- 自動採番ID
    start_time TIME,              -- 開始時刻
    end_time TIME,                -- 終了時刻
    date DATE NOT NULL,              -- 適用日
    work_type_id VARCHAR(10) NOT NULL      -- 勤怠種別ID（例: 出社、在宅、有給）
);
