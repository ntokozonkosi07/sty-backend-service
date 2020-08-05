package com.railroad.rest.common;

import javax.json.bind.Jsonb;
import java.util.Optional;

public abstract class AbstractService {
    private Jsonb jsonb;


    protected Integer parameterValidation(Integer maxResults, Optional<Integer> firstResults) throws IllegalArgumentException{
        Integer firstRes = firstResults.isPresent() ? firstResults.get() : 0;

        if(firstRes > maxResults)
            throw new IllegalArgumentException("First results cannot be greater to max results");

        return firstRes;
    }
}
