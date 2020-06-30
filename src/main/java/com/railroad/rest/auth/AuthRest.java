package com.railroad.rest.auth;

import com.railroad.common.annotation.Log;
import com.railroad.security.SecurityUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;

@Log
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthRest {

    @Inject
    private SecurityUtil securityUtil;

    @Context
    private UriInfo uriInfo;

    @POST
    @Path("/login")
    public Response login(
            @NotNull @FormParam("email") String email,
            @NotNull @FormParam("password") String password,
            @NotNull @FormParam("rememberMe") String rememberMe,
            @Context HttpServletRequest request
    ) throws AuthenticationException {
        boolean justLogged = false;
        if(!SecurityUtils.getSubject().isAuthenticated()){
            justLogged = true;
        }

        SecurityUtils.getSubject().login(new UsernamePasswordToken(email, password, rememberMe));

        String token = generateToken(email);

        return Response.ok().header(HttpHeaders.AUTHORIZATION, String.format("%s", token)).build();
    }

    @POST
    @Path("/refresh")
    public Response refreshToken(){
        String token = generateToken(SecurityUtils.getSubject().toString());
        return Response.ok().header(HttpHeaders.AUTHORIZATION, String.format("%s", token)).build();
    }

    @GET
    @Path("logout")
    public Response logout(@Context HttpServletRequest request){
        SecurityUtils.getSubject().logout();
        return Response.ok().build();
    }

    private String generateToken(String email){

        Key securityKey = securityUtil.getSecurityKey();
        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setIssuer(uriInfo.getBaseUri().toString())
                .setExpiration(securityUtil.toDate(LocalDateTime.now().plusMinutes(15)))
                .signWith(SignatureAlgorithm.HS512, securityKey)
                .compact();
        return token;
    }
}
