package com.railroad.rest.exception.mappers;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class EntityExistsExceptionMapper extends Throwable implements ExceptionMapper<EntityExistsException> {
    @Override
    public Response toResponse(EntityExistsException exception) {
        String msg = exception.getMessage();
        return Response.ok(msg, MediaType.TEXT_PLAIN).status(Response.Status.NOT_FOUND).build();
    }
}
