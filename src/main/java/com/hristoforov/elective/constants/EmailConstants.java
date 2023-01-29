package com.hristoforov.elective.constants;

public final class EmailConstants {

    public static final String EMAIL_SUBJECT = "Elective App message";
    public static final String ENTER = "<br>";
    public static final String HELLO = "Dear %s,<br>";
    public static final String SIGNATURE = "Best regards,<br>Elective App team";

    public static final String MESSAGE_EDIT_COURSE = HELLO +
            "Course \"%s\" has been changed. Information about this course:<br>" +
            "Title: %s" + ENTER + "Duration: %d minutes" + ENTER + "Start date: %s" + ENTER + "End date: %s" +
            ENTER +
            SIGNATURE;
    public static final String MESSAGE_SIGN_UP_TEACHER = HELLO +
            "Thank you for being with us!<br>" +
            "We could not pull this off without you.Teach students everything you know" +
            ENTER +
            SIGNATURE;

    public static final String MESSAGE_JOIN_COURSE = HELLO +
            "You have joined \"%s\" course.We are really grateful for your activity!" +
            "<br>Keep it up!<br>" +
            ENTER +
            SIGNATURE;

    public static final String MESSAGE_CONGRATULATIONS = HELLO +
            "Thank you for being with us!<br>" +
            "We are always glad to new users, join the courses and learn new with us!" +
            ENTER +
            SIGNATURE;

    public static final String MESSAGE_ADD_COURSE = HELLO +
            "We hasten to inform you, we have a new course \"%s\", which will start on %s" +
            ",we look forward to seeing you.<br>" +
            ENTER +
            SIGNATURE;

    public static final String MESSAGE_RATE_STUDENT = HELLO +
            "You got a score of %d in the course \"%s\"." +
            "<br>Keep it up!<br>" +
            ENTER +
            SIGNATURE;
}
