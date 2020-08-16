package com.railroad.rest.measurement;

import com.railroad.entity.Measurement;
import com.railroad.rest.common.Repository;

import javax.transaction.Transactional;
import java.util.Collection;

@Transactional
public class MeasurementQuery extends Repository<Measurement> {
    public MeasurementQuery() {
        super(Measurement.class);
    }

    @Override
    public Measurement findById(Long id) {
        return null;
    }

    @Override
    public Collection<Measurement> findAll(int maxResults, int firstResults) {
        return this.getCollectionResults(maxResults,firstResults, Measurement.FIND_ALL_MEASUREMENTS);
    }
}
