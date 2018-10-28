package nl.han.oose.DAO;

import nl.han.oose.entities.Track;
import nl.han.oose.persistence.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrackDAO {

    private ConnectionFactory connectionFactory;

    public TrackDAO() {
        connectionFactory = new ConnectionFactory();
    }

    public Track getTrack(int trackID) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM track WHERE ID = ?")
        ) {
            statement.setString(1, Integer.toString(trackID));
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            if (resultSet.getDate("Publication_Date") != null) {
                return new Track(
                        resultSet.getInt("ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Performer"),
                        resultSet.getInt("Duration"),
                        resultSet.getString("Album"),
                        resultSet.getInt("Play_Count"),
                        resultSet.getDate("Publication_Date").toString(),
                        resultSet.getString("Description"),
                        resultSet.getBoolean("Offline_Availible")
                );

            } else {
                return new Track(
                        resultSet.getInt("ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Performer"),
                        resultSet.getInt("Duration"),
                        resultSet.getString("Album"),
                        resultSet.getInt("Play_Count"),
                        null,
                        resultSet.getString("Description"),
                        resultSet.getBoolean("Offline_Availible")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Track> getTracksNotInPlaylist(int playlistID) {
        List<Track> tracks = new ArrayList<>();
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM track WHERE ID NOT IN (SELECT trackID FROM playlisttracks WHERE playlistID = ?)")
        ) {
            statement.setString(1, Integer.toString(playlistID));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getDate("Publication_Date") != null) {
                    tracks.add(new Track(
                            resultSet.getInt("ID"),
                            resultSet.getString("Title"),
                            resultSet.getString("Performer"),
                            resultSet.getInt("Duration"),
                            resultSet.getString("Album"),
                            resultSet.getInt("Play_Count"),
                            resultSet.getDate("Publication_Date").toString(),
                            resultSet.getString("Description"),
                            resultSet.getBoolean("Offline_Availible")
                    ));
                } else {
                    tracks.add(new Track(
                            resultSet.getInt("ID"),
                            resultSet.getString("Title"),
                            resultSet.getString("Performer"),
                            resultSet.getInt("Duration"),
                            resultSet.getString("Album"),
                            resultSet.getInt("Play_Count"),
                            null,
                            resultSet.getString("Description"),
                            resultSet.getBoolean("Offline_Availible")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tracks;
    }

    public List<Track> getAllTracksOfAPlaylistFromDB(int playlistID) {
        List<Track> tracks = new ArrayList<>();
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM track WHERE ID IN (SELECT trackID FROM playlisttracks WHERE playlistID = ?)")
        ) {
            statement.setString(1, Integer.toString(playlistID));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getDate("Publication_Date") != null) {
                    tracks.add(new Track(
                            resultSet.getInt("ID"),
                            resultSet.getString("Title"),
                            resultSet.getString("Performer"),
                            resultSet.getInt("Duration"),
                            resultSet.getString("Album"),
                            resultSet.getInt("Play_Count"),
                            resultSet.getDate("Publication_Date").toString(),
                            resultSet.getString("Description"),
                            resultSet.getBoolean("Offline_Availible")
                    ));
                } else {
                    tracks.add(new Track(
                            resultSet.getInt("ID"),
                            resultSet.getString("Title"),
                            resultSet.getString("Performer"),
                            resultSet.getInt("Duration"),
                            resultSet.getString("Album"),
                            resultSet.getInt("Play_Count"),
                            null,
                            resultSet.getString("Description"),
                            resultSet.getBoolean("Offline_Availible")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tracks;
    }

    public void setTrackAvailible(int id, boolean offlineAvailible) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE track SET Offline_Availible = ? WHERE ID = ?")
        ) {
            statement.setString(1, Boolean.toString(offlineAvailible));
            statement.setString(2, Integer.toString(id));
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
