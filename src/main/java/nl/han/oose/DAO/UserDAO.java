package nl.han.oose.DAO;

import nl.han.oose.entities.User;
import nl.han.oose.persistence.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private ConnectionFactory connectionFactory;

    public UserDAO() {
        connectionFactory = new ConnectionFactory();
    }

    public List<User> getAllAccounts() {

        List<User> accounts = new ArrayList<>();

        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM USER ");
        ) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("UserID");
                String user = resultSet.getString("Username");
                String password = resultSet.getString("Password");

                accounts.add(new User(id, user, password));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return accounts;
    }

//    public void persistAccount(Account account) {
//
//        try (
//                Connection connection = connectionFactory.getConnection();
//                PreparedStatement statement = connection.prepareStatement("INSERT INTO ACCOUNT (user, password) VALUES (?, ?)");
//        ) {
//            statement.setString(1, account.getUser());
//            statement.setString(2, account.getPassword());
//            statement.execute();
//        } catch (SQLException e){
//            throw new RuntimeException(e);
//        }
//    }
}

