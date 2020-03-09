package com.railroad.rest.serviceProvided;

import com.railroad.entity.User;
import com.railroad.entity.requirement.Requirement;
import com.railroad.entity.serviceProvided.ServiceProvided;
import com.railroad.rest.common.AbstractService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Optional;

@Stateless
public class ServiceProvidedService extends AbstractService {
    @Inject
    private ServiceProvidedQuery sq;


    public Collection<ServiceProvided> getSerivcesProvided(Integer maxResults, Optional<Integer> firstResults) throws IllegalArgumentException{
        Integer firstRes = parameterValidation(maxResults,firstResults);

        return sq.getSerivcesProvided(maxResults,firstRes);
    }

    public ServiceProvided saveServiceProvided(ServiceProvided serviceProvided) {
        return sq.saveServiceProvided(serviceProvided);
    }

    public Collection<Requirement> getServiceProvidedRequirements(Long id, Integer maxResults, Optional<Integer> firstResults)throws IllegalArgumentException {
        Integer firstRes = parameterValidation(maxResults,firstResults);

        return sq.getServiceProvidedRequirements(id,maxResults,firstRes);
    }

    public Collection<User> getServiceProvidedArtists(Long id, Integer maxResults, Optional<Integer> firstResults)throws IllegalArgumentException {
        Integer firstRes = parameterValidation(maxResults,firstResults);

        return sq.getServiceProvidedArtists(id, maxResults,firstRes);
    }


    public ServiceProvided getServicesProvided(Long id) {
        return sq.getServicesProvided(id);
    }

    public ServiceProvided updateServiceProvided(ServiceProvided service) {
        return sq.updateServiceProvided(service);
    }
}
