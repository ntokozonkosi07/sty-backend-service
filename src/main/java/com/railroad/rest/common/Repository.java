package com.railroad.rest.common;

import com.railroad.entity.Role;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collection;

public abstract class Repository<T> {

    @Inject protected EntityManager em;

    public abstract Collection<T> findAll(int maxResults, int firstResults);
    public abstract T findById(Long id);

    public T save(T obj){
        em.persist(obj);
        return obj;
    }

    public T update(T obj){
        em.merge(obj);
        return obj;
    }

    public void delete(Long id) {
        T obj = this.findById(id);
        if (em.contains(obj)) {
            em.remove(obj);
        } else {
            em.merge(obj);
        }
    }
}
