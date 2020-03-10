package com.railroad.rest.serviceProvided;

import com.railroad.entity.User;
import com.railroad.entity.Requirement;
import com.railroad.entity.ServiceProvided;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Collection;

@Transactional
class ServiceProvidedQuery {

    @Inject
    EntityManager entityManager;

    Collection<ServiceProvided> getSerivcesProvided(Integer maxResults, Integer firstResults) throws IllegalArgumentException{
        return entityManager.createNamedQuery(ServiceProvided.FIND_ALL_SERVICE_PROVIDED, ServiceProvided.class)
                .setMaxResults(maxResults)
                .setFirstResult(firstResults)
                .getResultList();
    }

    ServiceProvided saveServiceProvided(ServiceProvided serviceProvided) throws EntityExistsException{
        entityManager.persist(serviceProvided);
        return serviceProvided;
    }

    Collection<Requirement> getServiceProvidedRequirements(Long id, Integer maxResults, Integer firstResults) throws IllegalArgumentException{
        return entityManager.createNamedQuery(ServiceProvided.FIND_ALL_SERVICE_PROVIDED_REQUIREMENTS, Requirement.class)
                .setParameter("id", id)
                .setMaxResults(maxResults)
                .setFirstResult(firstResults)
                .getResultList();
    }

    Collection<User> getServiceProvidedArtists(Long id, Integer maxResults, Integer firstResults) throws IllegalArgumentException{
        return entityManager.createNamedQuery(ServiceProvided.FIND_ALL_SERVICE_PROVIDED_ARTISTS, User.class)
                .setParameter("id", id)
                .setMaxResults(maxResults)
                .setFirstResult(firstResults)
                .getResultList();
    }

    public ServiceProvided getServicesProvided(Long id) {
        return entityManager.createNamedQuery(ServiceProvided.FIND_SERVICE_PROVIDED_BY_ID, ServiceProvided.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public ServiceProvided updateServiceProvided(ServiceProvided service) throws IllegalArgumentException{
        return entityManager.merge(service);
    }
}
