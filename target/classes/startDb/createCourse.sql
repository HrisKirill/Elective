CREATE TABLE course (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    duration INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (id),
    INDEX fk_course_user1_idx (user_id ASC) VISIBLE,
    UNIQUE INDEX title_UNIQUE (title ASC) VISIBLE,
    CONSTRAINT fk_course_user
    FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE ON UPDATE CASCADE
);