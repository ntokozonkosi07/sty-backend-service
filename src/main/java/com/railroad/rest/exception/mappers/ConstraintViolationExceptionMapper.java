package com.railroad.rest.exception.mappers;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper extends Exception
        implements ExceptionMapper<ConstraintViolationException> {

    public ConstraintViolationExceptionMapper(){
        super("Email already exists");
    }

    public ConstraintViolationExceptionMapper(String string){
        super(string);
    }

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        final Response build = Response.status(Response.Status.NOT_FOUND).build();
        return build;
    }
}
