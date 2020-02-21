package com.railroad.rest.exception.mappers;

import com.railroad.common.annotation.CustomExceptionMapperQualifier;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.CONFLICT;

@Provider
public class PersistenceExceptionMapper extends Exception
        implements ExceptionMapper<PersistenceException> {

    @Inject
    @CustomExceptionMapperQualifier
    ICustomExceptiontMapper em;

    @Override
    public Response toResponse(PersistenceException exception) {
        return em.mapResponse(exception, CONFLICT);
    }
}
