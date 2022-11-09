package com.hristoforov.elective.utils;

import com.hristoforov.elective.entities.Course;
import com.hristoforov.elective.entities.Role;
import com.hristoforov.elective.entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkWithEntityFromDb {
    public static  User createUserFromDb(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getLong("user_id"),
                resultSet.getString("last_name"),
                resultSet.getString("first_name"),
                resultSet.getString("login"),
                resultSet.getString("password"),
                resultSet.getString("email"),
                Role.valueOf(resultSet.getString("role")));
    }

    public static Course createCourseFromDb(ResultSet resultSet) throws SQLException {
        return new Course(resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getInt("duration"),
                resultSet.getDate("start_date"),
                resultSet.getDate("end_date"),
                createUserFromDb(resultSet)
        );
    }
}
