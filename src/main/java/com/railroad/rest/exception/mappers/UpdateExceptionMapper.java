package com.railroad.rest.exception.mappers;

import com.railroad.common.annotation.CustomExceptionMapperQualifier;
import com.railroad.rest.exception.UpdateException;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import static javax.ws.rs.core.Response.Status.CONFLICT;

public class UpdateExceptionMapper implements ExceptionMapper<UpdateException> {
    @Inject
    @CustomExceptionMapperQualifier
    ICustomExceptiontMapper em;

    @Override
    public Response toResponse(UpdateException exception) {
        return em.mapResponse(exception, CONFLICT);
    }
}
