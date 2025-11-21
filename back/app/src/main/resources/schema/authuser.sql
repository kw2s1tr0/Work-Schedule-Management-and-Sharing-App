DROP TABLE IF EXISTS authuser;

CREATE TABLE authuser (
    user_id VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL
);