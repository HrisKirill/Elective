package com.hristoforov.elective.dao.interfaces;

import com.hristoforov.elective.entities.User;

import java.util.List;

public interface UserDao extends Dao<User> {
    @Override
    void create(User entity);

    @Override
    void update(User entity);

    @Override
    void remove(User entity);

    @Override
    List<User> findAll();

    @Override
    User findById(Long id);


    User findByLogin(String login);


    User findByEmail(String email);
}
