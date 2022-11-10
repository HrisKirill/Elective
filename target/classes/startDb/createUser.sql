CREATE TABLE user (
    user_id INT NOT NULL AUTO_INCREMENT,
    last_name VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    login VARCHAR(45) NOT NULL,
    password VARCHAR(45) NOT NULL,
    email VARCHAR(45) NOT NULL,
    role VARCHAR(45) NOT NULL,
    PRIMARY KEY (user_id),
    UNIQUE INDEX login_UNIQUE (login ASC) VISIBLE,
    UNIQUE INDEX email_UNIQUE (email ASC) VISIBLE
);