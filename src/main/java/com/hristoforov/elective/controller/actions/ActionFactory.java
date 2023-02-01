package com.hristoforov.elective.controller.actions;

import com.hristoforov.elective.controller.actions.implementations.admin.*;
import com.hristoforov.elective.controller.actions.implementations.general.*;
import com.hristoforov.elective.controller.actions.implementations.student.CertificateToPdfAction;
import com.hristoforov.elective.controller.actions.implementations.student.CoursesTableForStudentAction;
import com.hristoforov.elective.controller.actions.implementations.teacher.CoursesTableForTeacherAction;
import com.hristoforov.elective.controller.actions.implementations.teacher.EditMarkAction;
import com.hristoforov.elective.controller.actions.implementations.teacher.JournalToPdfAction;
import com.hristoforov.elective.controller.actions.implementations.teacher.StudentsTableForTeacherAction;
import com.hristoforov.elective.controller.context.AppContext;

import java.util.HashMap;
import java.util.Map;

import static com.hristoforov.elective.constants.CRAPaths.*;


public final class ActionFactory {
    private static final AppContext CONTEXT = AppContext.getAppContext();
    private static final ActionFactory ACTION_FACTORY = new ActionFactory();
    private static final Map<String, Action> ACTION_MAP = new HashMap<>();

    private ActionFactory() {
    }

    static {
        ACTION_MAP.put(LOGIN_PAGE_SERVLET, new SignInAction(CONTEXT));
        ACTION_MAP.put(USER_INFO_PAGE_SERVLET, new UserInfoAction(CONTEXT));
        ACTION_MAP.put(TABLE_FOR_STUDENT_SERVLET, new CoursesTableForStudentAction(CONTEXT));
        ACTION_MAP.put(TABLE_OF_COURSES_FOR_TEACHER_SERVLET, new CoursesTableForTeacherAction(CONTEXT));
        ACTION_MAP.put(USERS_TABLES_FOR_TEACHER_SERVLET, new StudentsTableForTeacherAction(CONTEXT));
        ACTION_MAP.put(EDIT_MARK_SERVLET, new EditMarkAction(CONTEXT));
        ACTION_MAP.put(LOGOUT_SERVLET, new LogoutAction());
        ACTION_MAP.put(REGISTRATION_SERVLET, new RegistrationAction(CONTEXT));
        ACTION_MAP.put(ADD_COURSE_SERVLET, new AddCourseAction(CONTEXT));
        ACTION_MAP.put(ADD_TOPIC_SERVLET, new AddTopicAction(CONTEXT));
        ACTION_MAP.put(ADMIN_SERVLET, new AdminFeaturesAction(CONTEXT));
        ACTION_MAP.put(TEACHER_REGISTRATION_SERVLET, new AdminTeacherSignUpAction(CONTEXT));
        ACTION_MAP.put(ASSIGNMENT_OF_THE_COURSE_SERVLET, new AssignmentOfTheCourseToTeacherAction(CONTEXT));
        ACTION_MAP.put(EDIT_COURSE_SERVLET, new EditCourseAction(CONTEXT));
        ACTION_MAP.put(TABLE_OF_COURSES_SERVLET, new TableOfCoursesForAdminAction(CONTEXT));
        ACTION_MAP.put(CHANGE_STATUS_SERVLET, new TableOfStudentsToChangeStatusAction(CONTEXT));
        ACTION_MAP.put(TABLE_OF_TEACHERS_SERVLET, new TableOfTeachersForAdminAction(CONTEXT));
        ACTION_MAP.put(ERROR, new ErrorAction());
        ACTION_MAP.put(TEACHER_JOURNAL_PDF, new JournalToPdfAction(CONTEXT));
        ACTION_MAP.put(STUDENT_CERTIFICATE_PDF, new CertificateToPdfAction(CONTEXT));
        ACTION_MAP.put(COURSES_PDF, new CoursesToPdfAction(CONTEXT));
    }


    public static ActionFactory getActionFactory() {
        return ACTION_FACTORY;
    }

    public Action createAction(String actionName) {
        return ACTION_MAP.getOrDefault(actionName, new SignInAction(CONTEXT));
    }
}
