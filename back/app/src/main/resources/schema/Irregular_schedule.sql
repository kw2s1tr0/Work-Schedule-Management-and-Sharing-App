CREATE TABLE irregular_schedule (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, -- 自動採番ID
    user_id VARCHAR(10) NOT NULL,              -- ユーザID（外部キー）
    start_time TIME NOT NULL,              -- 開始時刻
    end_time TIME NOT NULL,                -- 終了時刻
    date DATE NOT NULL,              -- 適用日
    work_type_id VARCHAR(10) NOT NULL,      -- 勤怠種別ID（例: 出社、在宅、有給）
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (work_type_id) REFERENCES schedule_type(id)
);

