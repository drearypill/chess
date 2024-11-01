package service;

import dataaccess.*;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.*;
import java.util.HashSet;

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
        authData = new AuthData("authToken", "Username");
        authDAO.addAuth(authData);
    }
    @BeforeEach
    void setup() {
        gameDAO.clear();
    }
    @Test
    @DisplayName("Create Valid Game")
    void createGameTestPositive() throws UnauthorizedException {
        int gameID1 = gameService.createGame(authData.authToken(), "name");
        Assertions.assertTrue(gameDAO.gameExists(gameID1));
        int gameID2 = gameService.createGame(authData.authToken(), "name");
        Assertions.assertNotEquals(gameID1, gameID2);
    }

    @Test
    @DisplayName("Create Invalid Game")
    void createGameTestNegative() {
        Assertions.assertThrows(UnauthorizedException.class, () -> gameService.createGame("badToken", "name"));
    }

    @Test
    @DisplayName("Proper List Games")
    void listGamesTestPositive() throws UnauthorizedException {
        int gameID1 = gameService.createGame(authData.authToken(), "name");
        int gameID2 = gameService.createGame(authData.authToken(), "name");
        int gameID3 = gameService.createGame(authData.authToken(), "name");
        HashSet<GameData> expected = HashSet.newHashSet(8);
        expected.add(new GameData(gameID1, null, null, "name", null));
        expected.add(new GameData(gameID2, null, null, "name", null));
        expected.add(new GameData(gameID3, null, null, "name", null));
        Assertions.assertEquals(expected, gameService.listGames(authData.authToken()));
    }
    @Test
    @DisplayName("Improper List Games")
    void listGamesTestNegative() {
        Assertions.assertThrows(UnauthorizedException.class, () -> gameService.listGames("badToken"));
    }
    @Test
    @DisplayName("Proper Join Game")
    void joinGameTestPositive() throws UnauthorizedException, BadRequestException, DataAccessException {
        int gameID = gameService.createGame(authData.authToken(), "name");
        gameService.joinGame(authData.authToken(), gameID, "WHITE");
        GameData expectedGameData = new GameData(gameID, authData.username(), null, "name", null);
        Assertions.assertEquals(expectedGameData, gameDAO.getGame(gameID));
    }
    @Test
    @DisplayName("Improper Join Game")
    void joinGameTestNegative() throws UnauthorizedException {
        int gameID = gameService.createGame(authData.authToken(), "name");
        Assertions.assertThrows(UnauthorizedException.class, () -> gameService.joinGame("badToken", gameID, "WHITE"));
        Assertions.assertThrows(BadRequestException.class, () -> gameService.joinGame(authData.authToken(), 11111, "WHITE"));
        Assertions.assertThrows(BadRequestException.class, () -> gameService.joinGame(authData.authToken(), gameID, "INVALID"));
    }

    @Test
    @DisplayName("Proper Clear DB")
    void clearTestPositive() throws UnauthorizedException {
        gameService.createGame(authData.authToken(), "name");
        gameService.clear();
        Assertions.assertEquals(gameDAO.listGames(), HashSet.newHashSet(16));
    }
}