package com.railroad.rest.serviceProvided;

import com.railroad.entity.User;
import com.railroad.entity.Requirement;
import com.railroad.entity.ServiceProvided;
import com.railroad.rest.common.Repository;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashMap;

@Transactional
class ServiceProvidedQuery extends Repository<ServiceProvided> {

    protected ServiceProvidedQuery() {
        super(ServiceProvided.class);
    }

    @Override
    public Collection<ServiceProvided> findAll(int maxResults, int firstResults) {
        return this.getCollectionResults(maxResults, firstResults, ServiceProvided.FIND_ALL_SERVICE_PROVIDED);
    }

    @Override
    public ServiceProvided findById(Long id) {
        return this.getSingleResults(new HashMap<String, Long>(){{put("id", Long.valueOf(id)); }}, ServiceProvided.FIND_SERVICE_PROVIDED_BY_ID);
    }

    public Collection<ServiceProvided> getServiceProvidedByArtistId(Long artistId, Integer maxResults, Integer firstResults) throws IllegalArgumentException{
        return this.getCollectionResults(new HashMap<String, Long>(){{put("id", Long.valueOf(artistId)); }},maxResults, firstResults, ServiceProvided.FIND_ALL_SERVICE_PROVIDED_BY_ARTIST_ID);
    }
    public Collection<ServiceProvided> findServicesProvidedByRequirementId(Long requirementId, Integer maxResults, Integer firstResults) throws IllegalArgumentException{
        return this.getCollectionResults(new HashMap<String, Long>(){{put("id", Long.valueOf(requirementId)); }},maxResults, firstResults, ServiceProvided.FIND_SERVICES_PROVIDED_BY_REQUIREMENT_ID);
    }
}
