package nl.han.oose.DAO;

import nl.han.oose.entities.Playlist;
import nl.han.oose.persistence.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {

    private ConnectionFactory connectionFactory;

    public PlaylistDAO() {
        connectionFactory = new ConnectionFactory();
    }

    public List<Integer> getPlaylistIDsFromDB() {
        List<Integer> playlistIDs = new ArrayList<>();

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT playlistID FROM playlist")
        ) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int playlistID = resultSet.getInt("playlistID");

                playlistIDs.add(playlistID);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return playlistIDs;
    }

    public List<Integer> getAllTracksIDsOfAPlaylist(int playlistID) {
        List<Integer> trackIDs = new ArrayList<>();

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT trackID FROM playlisttracks WHERE playlistID = ?")
        ) {
            statement.setString(1, String.valueOf(playlistID));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                trackIDs.add(resultSet.getInt("trackID"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return trackIDs;
    }

    public String getName(int playlistID) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT `Name` FROM playlist WHERE playlistID = ?")
        ) {
            statement.setString(1, String.valueOf(playlistID));
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return (resultSet.getString("Name"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletePlaylist(int playlistID) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statementplaylisttracks = connection.prepareStatement("DELETE FROM playlisttracks WHERE playlistID = ?");
                PreparedStatement statementplaylist = connection.prepareStatement("DELETE FROM playlist WHERE playlistID = ?")
        ) {
            statementplaylisttracks.setString(1, String.valueOf(playlistID));
            statementplaylisttracks.execute();
            statementplaylist.setString(1, String.valueOf(playlistID));
            statementplaylist.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void persistPlaylist(Playlist playlist) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO playlist (`Name`, Owner) VALUES (?, ?)")
        ) {
            statement.setString(1, playlist.getName());
            statement.setBoolean(2, Boolean.valueOf(playlist.isOwner()));
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void changePlaylistName(int id, String name) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE playlist SET `Name` = ? WHERE playlistID = ?")
        ) {
            statement.setString(1, name);
            statement.setString(2, String.valueOf(id));
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeTrackFromPlaylistInDB(int playlistID, int trackID) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement("DELETE FROM playlisttracks WHERE playlistID = ? AND trackID = ?")
        ) {
            statement.setString(1, Integer.toString(playlistID));
            statement.setString(2, Integer.toString(trackID));
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void persistTrackinPlaylisttracks(int playlistId, int trackId) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO playlisttracks (playlistID, trackID) VALUES (?, ?)")
        ) {
            statement.setString(1, Integer.toString(playlistId));
            statement.setString(2, Integer.toString(trackId));
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
