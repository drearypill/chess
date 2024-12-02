package client;

import com.google.gson.Gson;
import model.GameData;
import model.GamesList;

import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.*;


public class ServerFacade {

    HttpCommunicator http;
    String baseURL;

    public ServerFacade() {
        this("http://localhost:8080");
    }

    public ServerFacade(String url) {
        baseURL = url;
        http = new HttpCommunicator(baseURL);
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
}
