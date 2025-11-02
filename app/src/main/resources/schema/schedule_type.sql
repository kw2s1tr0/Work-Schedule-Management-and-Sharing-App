DROP TABLE IF EXISTS schedule_type;
CREATE TABLE schedule_type (
    id INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY, -- 自動採番ID
    worktype_name VARCHAR(10) NOT NULL,    -- 勤怠種別名（例: 出社、在宅、有給）
    worktype_color VARCHAR(9) NOT NULL     -- 表示色、カラーコード
);