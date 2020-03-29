package com.railroad.rest.requirement;

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
class RequirementQuery extends Repository<Requirement> {

    public RequirementQuery() {
        super(Requirement.class);
    }

    @Override
    public Collection<Requirement> findAll(int maxResults, int firstResults) {
        return this.getCollectionResults(maxResults, firstResults, Requirement.FIND_ALL_REQUIREMENTS);
    }

    @Override
    public Requirement findById(Long id) {
        return this.getSingleResults(new HashMap<String, Long>(){{ put("id", Long.valueOf(id)); }}, Requirement.FIND_REQUIREMENT_BY_ID);
    }

    public Collection<Requirement> findRequirementsByServiceProvidedId(Long id, int maxResults, int firstResults){
        return this.getCollectionResults(new HashMap<String, Long>(){{ put("id", Long.valueOf(id)); }}, maxResults, firstResults, Requirement.FIND_REQUIREMENTS_BY_SERVICE_PROVIDED);
    }

}
