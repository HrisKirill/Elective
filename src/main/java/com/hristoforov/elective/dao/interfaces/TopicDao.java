package com.hristoforov.elective.dao.interfaces;

import com.hristoforov.elective.entities.Topic;

import java.util.List;

public interface TopicDao extends Dao<Topic> {
    @Override
    void create(Topic entity);

    @Override
    void update(Topic entity);

    @Override
    void remove(Topic entity);

    @Override
    List<Topic> findAll();

    @Override
    Topic findById(Long id);

    Topic findByTitle(String title);
}
