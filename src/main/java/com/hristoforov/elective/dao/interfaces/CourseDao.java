package com.hristoforov.elective.dao.interfaces;

import com.hristoforov.elective.entities.Course;

import java.util.List;

public interface CourseDao extends Dao<Course> {
    @Override
    void create(Course entity);

    @Override
    void update(Course entity);

    @Override
    void remove(Course entity);

    @Override
    List<Course> findAll();

    @Override
    Course findById(Long id);

    Course findByTitle(String title);
}
