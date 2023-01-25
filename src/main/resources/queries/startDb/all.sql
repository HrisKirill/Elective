DROP DATABASE IF EXISTS elective;
CREATE DATABASE elective CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;

CREATE TABLE elective.user
(
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
last_name VARCHAR(100) NOT NULL,
first_name VARCHAR(100) NOT NULL,
login VARCHAR(45) NOT NULL,
password VARCHAR(45) NOT NULL,
email VARCHAR(45) NOT NULL,
role VARCHAR(45) NOT NULL,
status VARCHAR(45) NOT NULL,
UNIQUE INDEX login_UNIQUE (login ASC) VISIBLE,
UNIQUE INDEX email_UNIQUE (email ASC) VISIBLE
);

CREATE TABLE elective.course
(
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
title VARCHAR(100) NOT NULL,
duration INT NOT NULL,
start_date DATE NOT NULL,
end_date DATE NOT NULL,
UNIQUE INDEX title_UNIQUE (title ASC) VISIBLE
);


CREATE TABLE elective.users_courses
(
course_id INT NOT NULL,
user_id INT NOT NULL,
mark INT UNSIGNED NULL DEFAULT 0,
INDEX fk_users_courses_user1_idx (user_id ASC) VISIBLE,
INDEX fk_users_courses_course1_idx (course_id ASC) VISIBLE,
PRIMARY KEY (course_id,user_id),
CONSTRAINT fk_users_courses_user1
FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT fk_users_courses_course1
FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE elective.topic
(
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
topic_title VARCHAR(45) NOT NULL,
UNIQUE INDEX title_UNIQUE (topic_title) VISIBLE
);

CREATE TABLE elective.courses_topics
(
topic_id  INT NOT NULL,
course_id INT NOT NULL,
INDEX fk_courses_topics_topic1_idx (topic_id ASC) VISIBLE,
INDEX fk_courses_topics_course1_idx (course_id ASC) VISIBLE,
PRIMARY KEY (topic_id,course_id),
CONSTRAINT fk_courses_topics_topic1
FOREIGN KEY (topic_id) REFERENCES topic (id) ON DELETE CASCADE ON UPDATE CASCADE,
CONSTRAINT fk_courses_topics_course1
FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE CASCADE ON UPDATE CASCADE
);
