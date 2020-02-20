package com.railroad.rest.serviceProvided;

import com.railroad.entity.serviceProvided.ServiceProvided;
import com.railroad.entity.serviceProvided.ServiceProvidedDto;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Transactional
public class ServiceProvidedQuery {

    @Inject
    EntityManager entityManager;

    public ServiceProvided createService(ServiceProvidedDto serviceDto) throws EntityExistsException {
        ServiceProvided service = new ServiceProvided();
        service.setName(serviceDto.getName());

        // check if requirements exists in the database,
        // if it does return it and assign it to the service object
        // or create a requirement and return it back and also assign it to service object
        // then persist the service object

        if(service.getId() != null){
            throw new EntityExistsException();
        }

        entityManager.persist(service);

        return null;
    }
}
