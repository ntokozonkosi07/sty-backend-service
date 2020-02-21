package com.railroad.rest.exception.mappers;

import javax.persistence.PersistenceException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface ICustomExceptiontMapper {
    Response mapResponse(Exception exception, Response.Status status);
}
