CREATE TABLE courses_topics
(
    topic_id  INT NOT NULL,
    course_id INT NOT NULL,
    INDEX     fk_courses_topics_topic1_idx (topic_id ASC) VISIBLE,
    INDEX     fk_courses_topics_course1_idx (course_id ASC) VISIBLE,
    PRIMARY KEY (topic_id,course_id),
    CONSTRAINT fk_courses_topics_topic1
        FOREIGN KEY (topic_id)
            REFERENCES topic (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_courses_topics_course1
        FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE CASCADE ON UPDATE CASCADE
);
