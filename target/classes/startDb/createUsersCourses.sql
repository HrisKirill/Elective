
CREATE TABLE users_courses (
    course_id INT NOT NULL,
    user_id INT NOT NULL,
    mark INT UNSIGNED NULL DEFAULT 0,
    INDEX fk_users_courses_user1_idx (user_id ASC) VISIBLE,
    INDEX fk_users_courses_course1_idx (course_id ASC) VISIBLE,
    CONSTRAINT fk_users_courses_user1
        FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_users_courses_course1
        FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE CASCADE ON UPDATE CASCADE
);