DROP TABLE IF EXISTS users;
CREATE TABLE users (
    member_id VARCHAR(10) PRIMARY KEY, -- 組織構成員ID
    organization_code VARCHAR(10) NOT NULL, -- 組織コード
    name VARCHAR(50) NOT NULL, -- 姓名
    position_code INTEGER, -- 役職コード
    resignation_date DATE, -- 退社日
    hire_date DATE NOT NULL -- 入社日
);
