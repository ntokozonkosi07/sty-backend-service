package com.railroad.common.producers;

import com.railroad.rest.exception.mappers.ICustomExceptiontMapper;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

@RequestScoped
public class ExceptionMapperProducer {
    @Produces
    ICustomExceptiontMapper exceptionMapper;
}
