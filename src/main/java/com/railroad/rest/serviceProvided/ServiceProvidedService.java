package com.railroad.rest.serviceProvided;

import com.railroad.entity.Artist;
import com.railroad.entity.User;
import com.railroad.entity.requirement.Requirement;
import com.railroad.entity.serviceProvided.ServiceProvided;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Stateless
public class ServiceProvidedService {
    @Inject
    private ServiceProvidedQuery sq;

    private Integer parameterValidation(Integer maxResults, Optional<Integer> firstResults) throws IllegalArgumentException{
        Integer firstRes = firstResults.isPresent() ? firstResults.get() : 0;

        if(firstRes > maxResults)
            throw new IllegalArgumentException("First results cannot be greater to max results");

        return firstRes;
    }


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

    public Collection<Artist> getServiceProvidedArtists(Long id, Integer maxResults, Optional<Integer> firstResults)throws IllegalArgumentException {
        Integer firstRes = parameterValidation(maxResults,firstResults);

        return sq.getServiceProvidedArtists(id, maxResults,firstRes);
    }


}
