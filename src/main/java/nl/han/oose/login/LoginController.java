package nl.han.oose.login;

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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(LoginRequest loginRequest) {
        if (loginRequest.getUser().equals(loginService.returnUserName()) && loginRequest.getPassword().equals(loginService.returnPassword())) {
            LoginToken loginToken = new LoginToken(loginService.returnUserToken(), loginService.returnFullName());
            return Response.ok().entity(loginToken).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
