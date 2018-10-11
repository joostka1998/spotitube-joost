package nl.han.oose.playlists;

import java.util.ArrayList;
import java.util.List;

public class PlaylistService {

    List<Playlist> playlists = new ArrayList<>();
    List<Track> tracks = new ArrayList<>();
    AllPlayLists allplaylists;
    Tracks tracklist;


    public PlaylistService() {
        tracks.add(new Track(1, "Highway to Hell", "AC/DC", 171, 0, "01-01-1979", "Hard rock", false));
        tracks.add(new Track(2, "Let it be", "Beatles", 112, 0, "01-01-1970", "Rock", false));
        playlists.add(new Playlist(0, "song0", true, tracks));
        playlists.add(new Playlist(1, "song1", true, null));
        allplaylists = new AllPlayLists(playlists, getAllPlaylistsLength(playlists));
        tracklist = new Tracks(tracks);
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

    public AllPlayLists returnAllPlayLists() {
        return allplaylists;
    }

    public List<Playlist> returnPlaylists() {
        return playlists;
    }

    public Tracks getTracklist() {
        return tracklist;
    }
}
