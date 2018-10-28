package nl.han.oose.services;

import nl.han.oose.DAO.PlaylistDAO;
import nl.han.oose.DAO.TrackDAO;
import nl.han.oose.entities.AllPlayLists;
import nl.han.oose.entities.Playlist;
import nl.han.oose.entities.Track;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class PlaylistService {

    @Inject
    private PlaylistDAO playlistObject;

    @Inject
    private TrackDAO trackObject;

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

    public AllPlayLists returnAllPlayListsWithTotalLength() {
        List<Integer> playlistIDs = playlistObject.getPlaylistIDsFromDB();
        List<Playlist> playlists = new ArrayList<>();
        AllPlayLists allplaylists;

        for (int playlistID : playlistIDs) {
            List<Integer> trackIDs = playlistObject.getAllTracksIDsOfAPlaylist(playlistID);
            playlists.add(new Playlist(playlistID, playlistObject.getName(playlistID), true, getTracks(trackIDs)));
        }
        allplaylists = new AllPlayLists(playlists, getAllPlaylistsLength(playlists));

        return allplaylists;
    }

    private List<Track> getTracks(List<Integer> trackIDs) {
        List<Track> tracks = new ArrayList<>();
        for (int trackID : trackIDs) {
            tracks.add(trackObject.getTrack(trackID));
        }
        return tracks;
    }

    public void deletePlaylist(int playlistID) {
        playlistObject.deletePlaylist(playlistID);
    }


    public void addPlaylist(Playlist playlist) {
        playlistObject.persistPlaylist(playlist);
    }

    public void changeName(Playlist playlist) {
        playlistObject.changePlaylistName(playlist.getId(), playlist.getName());
    }
}
