package com.railroad.rest.exception.mappers;

import com.railroad.rest.exception.UpdateException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class UpdateExceptionMapper implements ExceptionMapper<UpdateException> {
    @Override
    public Response toResponse(UpdateException exception) {
        return null;
    }
}
