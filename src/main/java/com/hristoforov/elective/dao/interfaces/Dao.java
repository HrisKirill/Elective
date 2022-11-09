package com.hristoforov.elective.dao.interfaces;

import java.util.List;

public interface Dao<E> {
    void create (E entity);

    void update (E entity);


    void remove(E entity);

    List<E> findAll();

    E findById(Long id);
}
