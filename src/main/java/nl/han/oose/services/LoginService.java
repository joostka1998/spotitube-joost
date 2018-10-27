package nl.han.oose.services;

import nl.han.oose.DAO.UserDAO;
import nl.han.oose.entities.LoginRequest;
import nl.han.oose.entities.LoginToken;
import nl.han.oose.entities.User;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class LoginService {

    @Inject
    private UserDAO userObject;

    public boolean validateAccount(LoginRequest login) {
        User user = userObject.returnUserIfUserExistsInDB(login.getUser(), login.getPassword());
        if (login.getUser() == user.getPassword() && login.getPassword() == user.getPassword()) {
            return true;
        } else {
            return false;
        }
    }

    public LoginToken generateLoginToken(LoginRequest login) {
        int tokenlength = 14;
        String token = "";

        for (int i = 0; i < tokenlength; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, 9 + 1);
            token = token + String.valueOf(randomNum);
        }

        int id = userObject.getUserID(login.getUser());

        // these 3 lines of code make a string with the date of tommorrow
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now().plusDays(1);
        Date databaseDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

        userObject.insertTokenInDB(id, token, dateFormat.format(databaseDate));

        return new LoginToken(token, login.getUser());
    }
}
