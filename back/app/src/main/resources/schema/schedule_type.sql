DROP TABLE IF EXISTS schedule_type;
CREATE TABLE schedule_type (
    id VARCHAR(10) PRIMARY KEY, -- 勤怠種別ID
    work_type_name VARCHAR(10) NOT NULL,    -- 勤怠種別名（例: 出社、在宅、有給）
    work_type_color VARCHAR(9) NOT NULL     -- 表示色、カラーコード
);