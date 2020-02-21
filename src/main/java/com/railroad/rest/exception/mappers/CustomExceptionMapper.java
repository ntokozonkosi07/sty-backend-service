package com.railroad.rest.exception.mappers;

import com.railroad.common.annotation.CustomExceptionMapperQualifier;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@CustomExceptionMapperQualifier
public class CustomExceptionMapper implements ICustomExceptiontMapper {
    @Override
    public Response mapResponse(Exception exception, Response.Status status) {
        String msg = exception.getMessage();
        return Response.ok(msg, MediaType.TEXT_PLAIN).status(status).build();
    }
}
