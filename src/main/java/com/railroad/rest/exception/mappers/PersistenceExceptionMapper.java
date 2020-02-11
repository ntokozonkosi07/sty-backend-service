package com.railroad.rest.exception.mappers;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.CONFLICT;

@Provider
public class PersistenceExceptionMapper extends Exception
        implements ExceptionMapper<PersistenceException> {

    @Override
    public Response toResponse(PersistenceException exception) {
        final Response build = Response.status(CONFLICT).build();
        return build;
    }
}
