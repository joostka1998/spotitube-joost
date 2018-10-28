package nl.han.oose.services;

import nl.han.oose.DAO.TokenDAO;
import nl.han.oose.DAO.UserDAO;
import nl.han.oose.entities.LoginRequest;
import nl.han.oose.entities.LoginToken;

import javax.inject.Inject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class TokenService {

    @Inject
    private TokenDAO tokenObject;

    @Inject
    private UserDAO userObject;

    public LoginToken generateLoginToken(LoginRequest login) {
        int tokenlength = 14;
        String token = "";

        for (int i = 0; i < tokenlength; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, 9 + 1);
            token = token + String.valueOf(randomNum);
        }

        int id = userObject.getUserID(login.getUser());

        if (id == -1) {                          // id is only -1 when there is no id in the database
            throw new RuntimeException();       // should not be possible to find no id, but just in case
        }

        // these lines of code make a string with the date of tommorrow
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, 1);
        Date currentDatePlusOne = c.getTime();

        tokenObject.insertTokenInDB(id, token, dateFormat.format(currentDatePlusOne));

        return new LoginToken(token, login.getUser());
    }

    public void removeOldTokens(LoginRequest login) {
        int id = userObject.getUserID(login.getUser());

        tokenObject.removeOldTokenFromDB(id);
    }

    public boolean isValidToken(String token) {
        boolean doesTokenExist = tokenObject.isTokenInDB(token);

        if (doesTokenExist) {
            return true;
        } else {
            return false;
        }
    }
}
