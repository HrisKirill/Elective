package com.hristoforov.elective.dao.implementations;

import com.hristoforov.elective.DBCPDataSource;
import com.hristoforov.elective.dao.interfaces.TopicDao;
import com.hristoforov.elective.entities.Topic;

import java.util.List;

public class TopicDaoImpl extends DBCPDataSource implements TopicDao {
    @Override
    public void create(Topic entity) {

    }

    @Override
    public void update(Topic entity) {

    }

    @Override
    public void remove(Topic entity) {

    }

    @Override
    public List<Topic> findAll() {
        return null;
    }

    @Override
    public Topic findById(Long id) {
        return null;
    }
}
