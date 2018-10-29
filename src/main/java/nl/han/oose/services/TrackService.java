package nl.han.oose.services;

import nl.han.oose.DAO.TrackDAO;
import nl.han.oose.entities.Track;

import javax.inject.Inject;
import java.util.List;

public class TrackService {

    @Inject
    private TrackDAO trackObject;


    public List<Track> getAllTracksNotInPlaylist(int playlistID) {
        return trackObject.getTracksNotInPlaylist(playlistID);
    }

    public void setOfflineAvailible(int id, boolean offlineAvailible) {
        System.out.println(offlineAvailible);
        if (trackObject.getTrack(id).isOfflineAvailible() != offlineAvailible) {
            trackObject.setTrackAvailible(id, offlineAvailible);
        }
    }
}
