CREATE TABLE authuser (
    user_id VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

