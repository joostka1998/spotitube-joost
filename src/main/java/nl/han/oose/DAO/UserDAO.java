package nl.han.oose.DAO;

import nl.han.oose.persistence.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private ConnectionFactory connectionFactory;

    public UserDAO() {
        connectionFactory = new ConnectionFactory();
    }

//    public List<User> getAllAccounts() {
//
//        List<User> accounts = new ArrayList<>();
//
//        try (
//                Connection connection = connectionFactory.getConnection();
//                PreparedStatement statement = connection.prepareStatement("SELECT * FROM USER ");
//        ) {
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                int id = resultSet.getInt("UserID");
//                String user = resultSet.getString("Username");
//                String password = resultSet.getString("Password");
//
//                accounts.add(new User(id, user, password));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return accounts;
//    }

//    public void persistAccount(Account account) {
//
//        try (
//                Connection connection = connectionFactory.getConnection();
//                PreparedStatement statement = connection.prepareStatement("INSERT INTO USER (Username, Password) VALUES (?, ?)");
//        ) {
//            statement.setString(1, account.getUser());
//            statement.setString(2, account.getPassword());
//            statement.execute();
//        } catch (SQLException e){
//            throw new RuntimeException(e);
//        }
//    }

    public boolean login(String username, String password) {

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM `user` WHERE Username = ? AND Password = ?");
        ) {
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getUserID(String username) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT UserID FROM `user` WHERE Username = ?");
        ) {
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return -1;
            } else {
                return resultSet.getInt("UserID");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
                PreparedStatement statement = connection.prepareStatement("DELETE FROM token WHERE UserID = (?)");
        ) {
            statement.setString(1, String.valueOf(id));
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

