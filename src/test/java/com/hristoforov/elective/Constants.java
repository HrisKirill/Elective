package com.hristoforov.elective;

import com.hristoforov.elective.entities.course.Course;
import com.hristoforov.elective.entities.enums.Role;
import com.hristoforov.elective.entities.enums.Status;
import com.hristoforov.elective.entities.topic.Topic;
import com.hristoforov.elective.entities.user.User;

import java.sql.Date;

public class Constants {
    public static final long ID_VALUE = 1L;
    public static final String LAST_NAME_VALUE = "Grab";
    public static final String FIRST_NAME_VALUE = "Max";
    public static final String LOGIN_VALUE = "maxim";
    public static final String PASSWORD_VALUE = "Maxim124";
    public static final String HASH_PASSWORD = "34cc3ee2be969c46d0dcdf57862a0c46d5b9d5a63fb57893e5bb2dd46421b2bb";
    public static final String EMAIL_VALUE = "max@gmail.com";
    public static final Role ROLE_VALUE = Role.STUDENT;
    public static final Status STATUS_VALUE = Status.UNBLOCKED;

    public static final String TITLE_COURSE_VALUE = "MyCourse";
    public static final String TITLE_TOPIC_VALUE = "MyTopic";
    public static final int DURATION_VALUE = 45;
    public static final Date START_DATE_VALUE = Date.valueOf("2015-03-31");
    public static final Date END_DATE_VALUE = Date.valueOf("2016-03-31");

    public static User getTestUser() {
        return new User.Builder()
                .id(ID_VALUE)
                .lastName(LAST_NAME_VALUE)
                .firstName(FIRST_NAME_VALUE)
                .login(LOGIN_VALUE)
                .password(PASSWORD_VALUE)
                .email(EMAIL_VALUE)
                .role(ROLE_VALUE)
                .status(STATUS_VALUE)
                .build();
    }

    public static Course getTestCourse() {
        return Course.builder()
                .id(ID_VALUE)
                .title(TITLE_COURSE_VALUE)
                .duration(DURATION_VALUE)
                .startDate(START_DATE_VALUE)
                .endDate(END_DATE_VALUE)
                .build();
    }

    public static Topic getTestTopic() {
        return Topic.builder()
                .id(ID_VALUE)
                .title(TITLE_TOPIC_VALUE)
                .build();
    }

}
