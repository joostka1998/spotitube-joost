package nl.han.oose.controllers;

import nl.han.oose.entities.LoginRequest;
import nl.han.oose.entities.LoginToken;
import nl.han.oose.services.LoginService;
import nl.han.oose.services.TokenService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginController {

    @Inject
    private LoginService loginService;

    @Inject
    private TokenService tokenService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(LoginRequest loginRequest) {
        if (loginService.validateAccount(loginRequest) == true) {
            tokenService.removeOldTokens(loginRequest);
            LoginToken loginToken = tokenService.generateLoginToken(loginRequest);
            return Response.ok().entity(loginToken).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
