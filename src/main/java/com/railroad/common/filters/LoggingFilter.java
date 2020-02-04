package com.railroad.common.filters;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import com.railroad.common.annotation.Log;

@Log
@Provider
public class LoggingFilter implements ContainerRequestFilter {

	@Inject
	private Logger logger;
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		logger.log(Level.INFO, requestContext.getRequest().getMethod() +" "+ requestContext.getUriInfo().getAbsolutePath().getPath());
	}

}
