package com.railroad.rest.serviceProvided;

import com.railroad.entity.ServiceProvided;
import com.railroad.rest.common.AbstractService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;

@Stateless
public class ServiceProvidedService extends AbstractService {
    @Inject
    private ServiceProvidedQuery sq;

    public ServiceProvided saveServiceProvided(ServiceProvided serviceProvided) {
        return sq.save(serviceProvided);
    }

    public ServiceProvided findById(Long id) {
        return sq.findById(id);
    }

    public ServiceProvided update(ServiceProvided service) {
        return sq.update(service);
    }

    public Collection<ServiceProvided> getServiceProvidedByArtistId(Long artistId, Integer maxResults, Integer firstResults)throws IllegalArgumentException {
        return sq.getServiceProvidedByArtistId(artistId, maxResults,firstResults);
    }

    public Collection<ServiceProvided> getSerivcesProvided(Integer maxResults, Integer firstResults) throws IllegalArgumentException{
        return sq.findAll(maxResults,firstResults);
    }

    public  Collection<ServiceProvided> findServicesProvidedByRequirementId(Long requirementId, int maxResults, int firstResults){
        return sq.findServicesProvidedByRequirementId(requirementId, maxResults, firstResults);
    }
}
