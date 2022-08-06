package com.app.serviceDAO.DAO;

import java.util.List;

public interface DAO<V> {
    void insert(V v);

    List<V> getList();

    V getById(long id);

    void update(long id, V v);

    void delete(long id);
}
