package com.railroad.security;

import io.jsonwebtoken.*;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Key;
import java.security.Principal;
import java.util.logging.Logger;

@Auth
@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter {

    @Inject
    private SecurityUtil securityUtil;

    @Inject
    private Logger logger;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authString = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authString == null || authString.isEmpty() || !authString.startsWith("Bearer")) {
            //Beautify this exception
            throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build());
        }

        String token = authString.substring(SecurityUtil.BEARER.length()).trim();

        try{
            Key key = securityUtil.getSecurityKey();
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);

            SecurityContext originalContext = requestContext.getSecurityContext();

            requestContext.setSecurityContext(new SecurityContext() {
                @Override
                public Principal getUserPrincipal() {
                    return new Principal() {
                        @Override
                        public String getName() {
                            return claimsJws.getBody().getSubject();
                        }
                    };
                }

                @Override
                public boolean isUserInRole(String s) {
                    return originalContext.isUserInRole(s);
                }

                @Override
                public boolean isSecure() {
                    return originalContext.isSecure();
                }

                @Override
                public String getAuthenticationScheme() {
                    return originalContext.getAuthenticationScheme();
                }
            });
        }catch (ExpiredJwtException ex){
            logger.warning("JWT has expired!");
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }catch (SignatureException ex){
            logger.warning("Failed to verify token.");
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }catch (MalformedJwtException ex){
            logger.warning("Provided token is not a valid JWS.");
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }


    }
}
