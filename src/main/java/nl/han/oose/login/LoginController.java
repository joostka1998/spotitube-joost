package nl.han.oose.login;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginController {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(LoginRequest loginRequest) {

        if (loginRequest.getUser().equals("joost") && loginRequest.getPassword().equals("pass123")) {
            LoginToken loginToken = new LoginToken("1234-1234-1234", "joostkaal");
            return Response.ok().entity(loginToken).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
