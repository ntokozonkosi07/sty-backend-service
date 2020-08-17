package com.railroad.rest.colour;

import com.railroad.entity.Colour;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;

@Stateless
public class ColourService {

    @Inject
    ColourQuery query;

    public Collection<Colour> findAll(int maxResults, int firstResults){
        return query.findAll(maxResults, firstResults);
    }
}
