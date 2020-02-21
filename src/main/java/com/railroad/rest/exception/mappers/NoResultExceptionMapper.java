package com.railroad.rest.exception.mappers;

import com.railroad.common.annotation.CustomExceptionMapperQualifier;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoResultExceptionMapper extends Throwable implements ExceptionMapper<NoResultException> {
    @Inject
    @CustomExceptionMapperQualifier
    ICustomExceptiontMapper em;

    @Override
    public Response toResponse(NoResultException exception) {
        return em.mapResponse(exception, Response.Status.NOT_FOUND);
    }
}
