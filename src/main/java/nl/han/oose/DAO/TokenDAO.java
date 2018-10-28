package nl.han.oose.DAO;

import nl.han.oose.persistence.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TokenDAO {

    private ConnectionFactory connectionFactory;

    public TokenDAO() {
        connectionFactory = new ConnectionFactory();
    }

    public void insertTokenInDB(int id, String token, String expirationDate) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO token (UserID, Token, Expiration_Date) VALUES (?, ?, ?)");
        ) {
            statement.setString(1, String.valueOf(id));
            statement.setString(2, token);
            statement.setString(3, expirationDate);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeOldTokenFromDB(int id) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement("DELETE FROM token WHERE UserID = (?)")
        ) {
            statement.setString(1, String.valueOf(id));
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isTokenInDB(String token) {

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT Expiration_Date FROM token WHERE Token = (?)")
        ) {
            statement.setString(1, token);

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return false;
            } else {
                //String datetime = resultSet.getString("Expiration_Date");
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
