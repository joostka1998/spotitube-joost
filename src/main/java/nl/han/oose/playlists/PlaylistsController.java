package nl.han.oose.playlists;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/playlists")
public class PlaylistsController {

    @Inject
    PlaylistService playlistService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylists(@QueryParam("token") String token) {
        if (token.equals("1234-1234-1234")) {
            return Response.ok().entity(playlistService.returnAllPlayLists()).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @Path("/{id}/tracks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylist(@PathParam("id") final String id, @QueryParam("token") String token) {
        if (token.equals("1234-1234-1234")) {
            try {
                return Response.ok().entity(playlistService.getAllTracksOfAPlaylist(Integer.parseInt(id))).build();
            } catch (NumberFormatException nfe) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

}
