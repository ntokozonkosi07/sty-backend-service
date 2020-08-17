package com.railroad.rest.colour;

import com.railroad.entity.Colour;
import com.railroad.rest.common.Repository;

import javax.transaction.Transactional;
import java.util.Collection;

@Transactional
public class ColourQuery extends Repository<Colour> {
    public ColourQuery() {
        super(Colour.class);
    }

    @Override
    public Colour findById(Long id) {
        return null;
    }

    @Override
    public Collection<Colour> findAll(int maxResults, int firstResults) {
        return this.getCollectionResults(maxResults,firstResults, Colour.FIND_ALL_COLOURS);
    }
}
