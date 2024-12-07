package server;

import com.google.gson.Gson;
import dataaccess.*;
import model.GameData;
import service.GameService;
import spark.Request;
import spark.Response;
import model.GamesList;

import java.util.HashSet;

public class GameHandler {

    GameService gameService;
    public GameHandler(GameService gameService) {
        this.gameService = gameService;
    }

    public Object listGames(Request req, Response resp) throws UnauthorizedException {
        String authToken = req.headers("authorization");
        GamesList games = new GamesList(gameService.listGames(authToken));
        resp.status(200);
        return new Gson().toJson(games);
    }

    public Object createGame(Request req, Response resp) throws BadRequestException, UnauthorizedException {

        if (!req.body().contains("\"gameName\":")) {
            throw new BadRequestException("No gameName provided");
        }

        GameData gameData = new Gson().fromJson(req.body(), GameData.class);

        String authToken = req.headers("authorization");
        int gameID =  gameService.createGame(authToken, gameData.gameName());

        resp.status(200);
        return "{ \"gameID\": %d }".formatted(gameID);
    }

    public Object joinGame(Request req, Response resp) throws BadRequestException, UnauthorizedException {

        if (!req.body().contains("\"gameID\":")) {
            throw new BadRequestException("No gameID provided");
        }

        String authToken = req.headers("authorization");
        record JoinGameData(String playerColor, int gameID) {}
        JoinGameData joinData = new Gson().fromJson(req.body(), JoinGameData.class);
        boolean joinSuccess =  gameService.joinGame(authToken, joinData.gameID(), joinData.playerColor());

        if (!joinSuccess) {
            resp.status(403);
            return "{ \"message\": \"Error: already taken\" }";
        }
        resp.status(200);
        return "{}";
    }

    public Object leaveGame(Request req, Response resp) throws BadRequestException, UnauthorizedException {

        if (!req.body().contains("\"gameID\":")) {
            throw new BadRequestException("No gameID provided");
        }

        String authToken = req.headers("authorization");
        record JoinGameData(String playerColor, int gameID) {}
        JoinGameData joinData = new Gson().fromJson(req.body(), JoinGameData.class);
        boolean leaveSuccess =  gameService.leaveGame(authToken, joinData.gameID(), joinData.playerColor());

        if (!leaveSuccess) {
            resp.status(403);
            return "{ \"message\": \"Error: you can't leave bro\" }";
        }
        resp.status(200);
        return "{}";
    }


}
