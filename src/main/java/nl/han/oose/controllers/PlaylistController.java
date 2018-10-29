package nl.han.oose.controllers;

import nl.han.oose.entities.Playlist;
import nl.han.oose.entities.Track;
import nl.han.oose.entities.Tracks;
import nl.han.oose.services.PlaylistService;
import nl.han.oose.services.TokenService;
import nl.han.oose.services.TrackService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/playlists")
public class PlaylistController {

    @Inject
    private PlaylistService playlistService;

    @Inject
    private TrackService trackService;

    @Inject
    private TokenService tokenService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylists(@QueryParam("token") String token) {
        if (tokenService.isValidToken(token)) {
            return Response.ok().entity(playlistService.returnAllPlayListsWithTotalLength()).build();
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
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
            return Response.status(Response.Status.FORBIDDEN).build();
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
            return Response.status(Response.Status.FORBIDDEN).build();
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
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }


    @Path("/{id}/tracks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylist(@PathParam("id") final String playlistId, @QueryParam("token") String token) {
        if (tokenService.isValidToken(token)) {
            try {
                Tracks tracks = new Tracks(playlistService.returnTracksOfPlaylist(Integer.parseInt(playlistId)));
                return Response.ok().entity(tracks).build();
            } catch (NumberFormatException nfe) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    @Path("/{pid}/tracks/{tid}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTrackFromPlaylist(@PathParam("pid") final String playlistId, @PathParam("tid") final String trackId, @QueryParam("token") String token) {
        if (tokenService.isValidToken(token)) {
            try {
                playlistService.removeTrackFromPlaylist(Integer.parseInt(playlistId), Integer.parseInt(trackId));
                Tracks tracks = new Tracks(playlistService.returnTracksOfPlaylist(Integer.parseInt(playlistId)));
                return Response.ok().entity(tracks).build();
            } catch (NumberFormatException nfe) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    @Path("/{id}/tracks")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(Track track, @QueryParam("token") String token, @PathParam("id") final String playlistId) {
        if (tokenService.isValidToken(token)) {
            try {
                trackService.setOfflineAvailible(track.getId(), Boolean.valueOf(track.isOfflineAvailible()));
                playlistService.addTracktoPlaylist(Integer.parseInt(playlistId), track);
                Tracks tracks = new Tracks(playlistService.returnTracksOfPlaylist(Integer.parseInt(playlistId)));
                return Response.ok().entity(tracks).build();
            } catch (NumberFormatException nfe) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

}
