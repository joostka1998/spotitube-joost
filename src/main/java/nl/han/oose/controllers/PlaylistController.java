package nl.han.oose.controllers;

import nl.han.oose.entities.Playlist;
import nl.han.oose.services.PlaylistService;
import nl.han.oose.services.TokenService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/playlists")
public class PlaylistController {

    @Inject
    PlaylistService playlistService;

    @Inject
    TokenService tokenService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylists(@QueryParam("token") String token) {
        if (tokenService.isValidToken(token)) {
            return Response.ok().entity(playlistService.returnAllPlayListsWithTotalLength()).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@PathParam("id") final String id, @QueryParam("token") String token) {
        if (tokenService.isValidToken(token)) {
            try {
                playlistService.deletePlaylist(Integer.parseInt(id));
                return Response.ok().entity(playlistService.returnAllPlayListsWithTotalLength()).build();
            } catch (NumberFormatException nfe) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Path("")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlaylist(Playlist playlist, @QueryParam("token") String token) {
        if (tokenService.isValidToken(token)) {
            playlistService.addPlaylist(playlist);
            return Response.ok().entity(playlistService.returnAllPlayListsWithTotalLength()).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response setPlaylistName(Playlist playlist, @QueryParam("token") String token) {
        if (tokenService.isValidToken(token)) {
            playlistService.changeName(playlist);
            return Response.ok().entity(playlistService.returnAllPlayListsWithTotalLength()).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


//    @Path("/{id}/tracks")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getPlaylist(@PathParam("id") final String id, @QueryParam("token") String token) {
//        if (tokenService.isValidToken(token)) {
//            try {
//                return Response.ok().entity(playlistService.getAllTracksOfAPlaylist(Integer.parseInt(id))).build();
//            } catch (NumberFormatException nfe) {
//                return Response.status(Response.Status.BAD_REQUEST).build();
//            }
//        } else {
//            return Response.status(Response.Status.BAD_REQUEST).build();
//        }
//    }

}
