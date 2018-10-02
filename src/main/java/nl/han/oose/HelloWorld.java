package nl.han.oose;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class HelloWorld {

    @GET
    public String helloWorld() {
        return "Hello World";
    }
}
