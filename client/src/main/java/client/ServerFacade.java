package client;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import model.GameData;
import websocket.commands.*;

import java.util.*;


public class ServerFacade {

    HttpCommunicator http;
    WebsocketCommunicator ws;
    String serverDomain;
    String authToken;

    public ServerFacade() throws Exception {
        this("localhost:8080");
    }

    public ServerFacade(String serverDomain) throws Exception {
        this.serverDomain = serverDomain;
        http = new HttpCommunicator(this, serverDomain);
    }

    protected String getAuthToken() {
        return authToken;
    }

    protected void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public boolean register(String username, String password, String email) {
        return http.register(username, password, email);
    }

    public boolean login(String username, String password) {
        return http.login(username, password);
    }

    public boolean logout() {
        return http.logout();
    }

    public int createGame(String gameName) {
        return http.createGame(gameName);
    }

    public HashSet<GameData> listGames() {
        return http.listGames();
    }

    public boolean joinGame(int gameId, String playerColor) {
        return http.joinGame(gameId, playerColor);
    }

//    public ChessGame getBoardForCurrentGame(int gameId) {
//        return http.getBoard(gameId);
//    }


    public void connectWS() {
        try {
            ws = new WebsocketCommunicator(serverDomain);
        }
        catch (Exception e) {
            System.out.println("Failed to make connection with server");
        }
    }

    public void sendCommand(UserGameCommand command) {
        String message = new Gson().toJson(command);
        ws.sendMessage(message);
    }

    public void connect(int gameID, ChessGame.TeamColor color) {
        //System.out.println("auth is " + authToken);
        sendCommand(new Connect(authToken, gameID, color, UserGameCommand.CommandType.CONNECT));
    }

    public void makeMove(int gameID, ChessMove move) {
        sendCommand(new MakeMove(authToken, gameID, move, UserGameCommand.CommandType.MAKE_MOVE));
    }

    public void leave(int gameID) {
        sendCommand(new Leave(authToken, gameID, UserGameCommand.CommandType.LEAVE));
    }

    public void resign(int gameID) {
        sendCommand(new Resign(authToken, gameID, UserGameCommand.CommandType.RESIGN));
    }
}
