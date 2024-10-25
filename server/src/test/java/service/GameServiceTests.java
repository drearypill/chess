package service;

import dataaccess.*;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.*;

public class GameServiceTests {
    static GameService gameService;
    static GameDAO gameDAO;
    static AuthDAO authDAO;
    static AuthData authData;
    @BeforeAll
    static void init() {
        gameDAO = new MemoryGameDAO();
        authDAO = new MemoryAuthDAO();
        gameService = new GameService(gameDAO, authDAO);
        authData = new AuthData("Username", "authToken");
        authDAO.addAuth(authData);
    }

    @BeforeEach
    void setup() {
        gameDAO.clear();
    }

    @Test
    @DisplayName("Create Invalid Game")
    void createGameTestNegative() throws UnauthorizedException {
        Assertions.assertThrows(UnauthorizedException.class, () -> gameService.createGame("badToken"));
    }

    @Test
    @DisplayName("Improper List Games")
    void listGamesTestNegative() {
        Assertions.assertThrows(UnauthorizedException.class, () -> gameService.listGames("badToken"));
    }

    @Test
    @DisplayName("Proper Join Game")
    void joinGameTestPositive() throws UnauthorizedException, DataAccessException { //
        int gameID = gameService.createGame(authData.authToken());
        gameService.joinGame(authData.authToken(), gameID, "WHITE");
        GameData expectedGameData = new GameData(gameID, authData.username(), null, null, null);
        Assertions.assertEquals(expectedGameData, gameDAO.getGame(gameID));
    }

}