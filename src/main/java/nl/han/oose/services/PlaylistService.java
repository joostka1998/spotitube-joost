package nl.han.oose.services;

import nl.han.oose.entities.AllPlayLists;
import nl.han.oose.entities.Playlist;
import nl.han.oose.entities.Track;
import nl.han.oose.entities.Tracks;

import java.util.ArrayList;
import java.util.List;

public class PlaylistService {

    List<Playlist> playlists = new ArrayList<>();
    List<Track> tracks = new ArrayList<>();
    List<Track> tracks2 = new ArrayList<>();
    AllPlayLists allplaylists;


    public PlaylistService() {
        tracks.add(new Track(1, "Highway to Hell", "AC/DC", 171, 0, "01-01-1979", "Hard rock", false));
        tracks.add(new Track(2, "Let it be", "Beatles", 112, 0, "01-01-1970", "Rock", false));
        tracks2.add(new Track(1, "1", "1", 171, 0, "01-01-1979", "Hard rock", false));
        tracks2.add(new Track(2, "2", "1", 112, 0, "01-01-1970", "Rock", false));
        playlists.add(new Playlist(0, "playlist0", true, tracks));
        playlists.add(new Playlist(1, "playlist1", true, tracks2));
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

    public AllPlayLists returnAllPlayLists() {
        return allplaylists;
    }

    public List<Playlist> returnPlaylists() {
        return playlists;
    }

    public Tracks getAllTracksOfAPlaylist(int id) {
        for (Playlist playlist : playlists) {
            if (playlist.getId() == id) {
                return new Tracks(playlist.getTracks());
            }
        }
        return null;
    }
}
