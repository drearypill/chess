package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;

import java.util.UUID;

public class UserAuthService {
    UserDAO userDAO;
    AuthDAO authDAO;
    public UserAuthService(UserDAO userDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }

    public AuthData createUser(UserData userData) throws DataAccessException {
        userDAO.createUser(userData);
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(userData.username(), authToken);
        authDAO.addAuth(authData);
        return authData;
    }

    // if the username DNE or the password is incorrect throws DataAccessException
    public AuthData loginUser(UserData userData) throws DataAccessException {
        boolean userAuthenticated = userDAO.authenticateUser(userData.username(), userData.password());
        if (userAuthenticated) {
            String authToken = UUID.randomUUID().toString();
            return new AuthData(userData.username(), authToken);
        }
        else {
            throw new DataAccessException("Password is incorrect");
        }
    }
    public void logoutUser(String authToken) throws DataAccessException {
        authDAO.getAuth(authToken); // If the auth is not valid ,exception
        authDAO.deleteAuth(authToken);
    }

    public void clear() {
        userDAO.clear();
        authDAO.clear();
    }
}

