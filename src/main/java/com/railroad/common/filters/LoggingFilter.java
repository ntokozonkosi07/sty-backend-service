package com.railroad.common.filters;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import com.railroad.common.annotation.Log;

@Log
@Provider
public class LoggingFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("-----------------------------------------------------------------");
		System.out.println(requestContext.getRequest().getMethod() +" "+ requestContext.getUriInfo().getAbsolutePath().getPath());
		System.out.println("-----------------------------------------------------------------");
	}

}
