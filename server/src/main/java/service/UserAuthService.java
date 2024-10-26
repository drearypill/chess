package service;

import dataaccess.*;
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

    public AuthData createUser(UserData userData) throws BadRequestException {
        try {
            userDAO.createUser(userData);
        } catch (DataAccessException e) {
            throw new BadRequestException(e.getMessage());
        }
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, userData.username());
        authDAO.addAuth(authData);

        return authData;
    }

    public AuthData loginUser(UserData userData) throws UnauthorizedException {
        boolean userAuthenticated = false;
        try {
            userAuthenticated = userDAO.authenticateUser(userData.username(), userData.password());
        } catch (DataAccessException e) {
            throw new UnauthorizedException();
        }

        if (userAuthenticated) {
            String authToken = UUID.randomUUID().toString();
            AuthData authData = new AuthData(authToken, userData.username());
            authDAO.addAuth(authData);
            return authData;
        }
        else {
            throw new UnauthorizedException();
        }
    }

    public void logoutUser(String authToken) throws UnauthorizedException {
        try {
            authDAO.getAuth(authToken);
        } catch (DataAccessException e) {
            throw new UnauthorizedException();
        }
        authDAO.deleteAuth(authToken);
    }

    public void clear() {
        userDAO.clear();
        authDAO.clear();
    }
}

