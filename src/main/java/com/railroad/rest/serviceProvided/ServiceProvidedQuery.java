package com.railroad.rest.serviceProvided;

import com.railroad.entity.Artist;
import com.railroad.entity.User;
import com.railroad.entity.requirement.Requirement;
import com.railroad.entity.serviceProvided.ServiceProvided;
import com.railroad.entity.serviceProvided.ServiceProvidedDto;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Collection;

@Transactional
class ServiceProvidedQuery {

    @Inject
    EntityManager entityManager;

    Collection<ServiceProvided> getUsers(Integer maxResults, Integer firstResults){
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

    Collection<Artist> getServiceProvidedArtists(Long id, Integer maxResults, Integer firstResults) throws IllegalArgumentException{
        return entityManager.createNamedQuery(ServiceProvided.FIND_ALL_SERVICE_PROVIDED_ARTISTS, Artist.class)
                .setParameter("id", id)
                .setMaxResults(maxResults)
                .setFirstResult(firstResults)
                .getResultList();
    }

}
