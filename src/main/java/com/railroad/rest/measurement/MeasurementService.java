package com.railroad.rest.measurement;

import com.railroad.entity.Measurement;
import com.railroad.rest.common.Repository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;

@Stateless
public class MeasurementService {
    @Inject
    Repository<Measurement> query;

    public Collection<Measurement> findAll(Integer maxResults, Integer firstResults){
        return query.findAll(maxResults,firstResults);
    }
}
