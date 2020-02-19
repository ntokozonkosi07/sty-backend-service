package com.railroad.rest.exception.mappers;

import javax.persistence.NoResultException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoResultExceptionMapper extends Throwable implements ExceptionMapper<NoResultException> {
    @Override
    public Response toResponse(NoResultException exception) {
        String msg = exception.getMessage();
        return Response.ok(msg, MediaType.TEXT_PLAIN).status(Response.Status.NOT_FOUND).build();
    }
}
