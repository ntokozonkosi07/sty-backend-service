package com.railroad.rest.serviceProvided;

import com.railroad.entity.User;
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

}
