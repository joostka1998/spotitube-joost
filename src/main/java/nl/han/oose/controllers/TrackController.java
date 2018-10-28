package nl.han.oose.controllers;


import nl.han.oose.services.TokenService;
import nl.han.oose.services.TrackService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tracks")
public class TrackController {

    @Inject
    private TrackService trackService;

    @Inject
    private TokenService tokenService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracks(@QueryParam("token") String token, @QueryParam("forPlaylist") String playlistID) {
        if (tokenService.isValidToken(token)) {
            try {
                return Response.ok().entity(trackService.getAllTracksNotInPlaylist(Integer.parseInt(playlistID))).build();
            } catch (NumberFormatException nfe) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
