package com.railroad.rest.common;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Map;

public abstract class Repository<T> {
    private final Class<T> type;

    protected Repository(Class<T> type) {
        this.type = type;
    }

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

    protected T getSingleResults(Map<String, ?> params, String query){
        TypedQuery tq = em.createNamedQuery(query, type);
        params.keySet().forEach(k -> tq.setParameter(k, params.get(k)));
        return (T) tq.getSingleResult();
    }

    protected Collection<T> getCollectionResults(int maxResults, int firstResults, String query){
        return em.createNamedQuery(query, type).setMaxResults(maxResults).setFirstResult(firstResults).getResultList();
    }

    protected Collection<T> getCollectionResults(Map<String, ?> params, int maxResults, int firstResults, String query){
        TypedQuery tq = em.createNamedQuery(query, type);
        params.keySet().forEach(k -> tq.setParameter(k, params.get(k)));
        return tq.setMaxResults(maxResults).setFirstResult(firstResults).getResultList();
    }
}
