package nl.han.oose.playlists;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/playlists")
public class PlaylistsController {

    List<Playlist> playlists = new ArrayList<>();
    List<Track> tracks = new ArrayList<>();
    AllPlayLists allplaylists;

    public PlaylistsController() {
        tracks.add(new Track(1, "Highway to Hell", "AC/DC", 171, 0, "01-01-1979", "Hard rock", false));
        tracks.add(new Track(2, "Let it be", "Beatles", 112, 0, "01-01-1970", "Rock", false));
        playlists.add(new Playlist(0, "song0", true, tracks));
        playlists.add(new Playlist(1, "song1", true, null));
        allplaylists = new AllPlayLists(playlists, getAllPlaylistsLength(playlists));
    }

    public int getAllPlaylistsLength(List<Playlist> playlists) {
        int totalDuration = 0;
        for (Playlist p : playlists) {
            if (p.getTracks() != null) {
                for (Track t : p.getTracks()) {
                    totalDuration = totalDuration + t.getDuration();
                }
            }

        }
        return totalDuration;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylists(@QueryParam("token") String token) {
        if (token.equals("1234-1234")) {
            return Response.ok().entity(allplaylists).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylist(@PathParam("id") final String id, @QueryParam("token") String token) {
        if (token.equals("1234-1234")) {
            for (Playlist p : playlists) {
                try {
                    if (p.getId() == Integer.parseInt(id)) {
                        return Response.ok().entity(p.getTracks()).build();
                    }
                } catch (NumberFormatException nfe) {
                    return Response.status(Response.Status.BAD_REQUEST).build();
                }
            }
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    /*
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Playlist> addPlaylist(Playlist playlist) {
        playlists.add(playlist);
        return playlists;
    }
    */
}
