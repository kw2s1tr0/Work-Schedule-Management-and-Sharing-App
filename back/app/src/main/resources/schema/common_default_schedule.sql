CREATE TABLE common_default_schedule (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, -- 自動採番ID
    start_time TIME NOT NULL,              -- 開始時刻
    end_time TIME NOT NULL,                -- 終了時刻
    start_date DATE NOT NULL,              -- 適用開始日
    end_date DATE NOT NULL,                -- 適用終了日
    work_type_id VARCHAR(10) NOT NULL,       -- 勤怠種別ID（例: 出社、在宅、有給）

    FOREIGN KEY (work_type_id) REFERENCES schedule_type(id)
);

