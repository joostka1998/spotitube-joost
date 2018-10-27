package nl.han.oose.services;

import nl.han.oose.DAO.UserDAO;
import nl.han.oose.entities.LoginRequest;

import javax.inject.Inject;

public class LoginService {

    @Inject
    private UserDAO userObject;

    public boolean validateAccount(LoginRequest login) {
        userObject.login(login.getUser(), login.getPassword());
        if (userObject.login(login.getUser(), login.getPassword())) {
            return true;
        } else {
            return false;
        }
    }


}
