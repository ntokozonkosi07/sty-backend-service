package com.railroad.rest.common;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

public abstract class Repository<T> {
    private final Class<T> type;

    @Inject protected EntityManager em;

    protected Repository(Class<T> type) { this.type = type; }

    public abstract T findById(Long id);

    public abstract Collection<T> findAll(int maxResults, int firstResults);

    public T save(T obj){
        em.persist(obj);
        return obj;
    }

    public T update(T obj){
        T _obj = em.merge(obj);
        return _obj;
    }

    public void delete(Long id) {
        T obj = this.findById(id);
        if (em.contains(obj)) {
            em.remove(obj);
        } else {
            update(obj);
        }
    }

    /**
     * Gets single result of an type
     * @param params sql parameters key value pair of Map<key<String>, Value<T>>
     * @Param query JPQL query to exceute
     * @return single instance type of T
     * */
    protected T getSingleResultByQuery(Map<String, ?> params, String query){
        TypedQuery<T> tq = em.createQuery(query, type);
        params.keySet().forEach(k -> tq.setParameter(k, params.get(k)));
        return (T) tq.getSingleResult();
    }

    /**
     * Gets single result of an type
     * @param params sql parameters key value pair of type Mpassword cannot be nullap<key<String>, Value<T>>
     * @Param query NamedQuery to exceute, this has to be configured in teh @NamedQuery annotation on an Entity Type T
     * @return single instance type of T
     * */
    protected T getSingleResultByNamedQuery(Map<String, ?> params, String query){
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

    public T findEntityByTypeAttribute(Field field, String id){
        String queryString = String.format("SELECT x FROM %S x WHERE x.%s = :%s", field.getClass().getTypeName(), id, id);
        TypedQuery<T> query = (TypedQuery<T>) em.createQuery(queryString).getSingleResult();
        return query.getSingleResult();
    };
}
