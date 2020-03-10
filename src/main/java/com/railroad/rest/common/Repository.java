package com.railroad.rest.common;

import java.util.Collection;

public interface Repository<T> {
    Collection<T> findAll(int maxResults, int firstResults);
    T findById(Long id);
    T save(T obj);
    T update(T obj);
    void delete(Long id);
}
