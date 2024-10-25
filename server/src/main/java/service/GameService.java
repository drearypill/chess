package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UnauthorizedException;
import model.AuthData;
import model.GameData;
import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class GameService {
    GameDAO gameDAO;
    AuthDAO authDAO;

    public GameService(GameDAO gameDAO, AuthDAO authDAO) {
        this.gameDAO = gameDAO;
        this.authDAO = authDAO;
    }
    public HashSet<GameData> listGames(String authToken) throws DataAccessException {
        authDAO.getAuth(authToken); // throws if not authorized
        return gameDAO.listGames();
    }
    public int createGame(String authToken) throws DataAccessException {
        authDAO.getAuth(authToken); // throws if not authorized
        int gameID;
        do { // Get random gameIDs until the gameID is not already in use
            gameID = ThreadLocalRandom.current().nextInt(1, 10000);
        } while (gameDAO.gameExists(gameID));
        gameDAO.createGame(new GameData(gameID, null, null, null, null));
        return gameID;
    }
    /***
     * Returns an int based on successfulness of joining the game.
     *     0 = Success
     *     1 = Game does not exist or other bad request
     *     2 = Player color already taken
     *     Throws UnauthorizedException if invalid authToken
     *     Throws DataAccessException if the request is bad
     */

    public int joinGame(String authToken, int gameID, String color) throws UnauthorizedException, DataAccessException {
        AuthData authData;
        GameData gameData;
        try {
            authData = authDAO.getAuth(authToken);
        } catch (DataAccessException e) {
            throw new UnauthorizedException("Invalid authToken");
        }
        if (gameDAO.gameExists(gameID)) {
            gameData = gameDAO.getGame(gameID);
        } else return 1;
        String whiteUser = gameData.whiteUsername();
        String blackUser = gameData.blackUsername();
        if (Objects.equals(color, "WHITE")) {
            if (whiteUser != null) return 2; // Spot taken
            else whiteUser = authData.username();
        } else if (Objects.equals(color, "BLACK")) {
            if (blackUser != null) return 2; // Spot taken
            else blackUser = authData.username();
        } else if (color != null) return 1; // Bad request
        gameDAO.updateGame(new GameData(gameID, whiteUser, blackUser, gameData.gameName(), gameData.game()));
        return 0;
    }

    public void clear() {
        gameDAO.clear();
    }
}
