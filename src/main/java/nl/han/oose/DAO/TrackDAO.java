package nl.han.oose.DAO;

import nl.han.oose.entities.Track;
import nl.han.oose.persistence.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
