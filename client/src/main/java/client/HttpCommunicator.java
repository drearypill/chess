package client;

import com.google.gson.Gson;
import model.GameData;
import model.GamesList;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Map;

public class HttpCommunicator {

    String baseURL;
    String authToken;

    public HttpCommunicator(String url) {
        baseURL = url;
    }

    public boolean register(String username, String password, String email) {
        var body = Map.of("username", username, "password", password, "email", email);
        var jsonBody = new Gson().toJson(body);
        Map resp = request("POST", "/user", jsonBody);
        if (resp.containsKey("Error")) {
            return false;
        }
        authToken = (String) resp.get("authToken");
        return true;
    }

    /**
     *
     * @param username
     * @param password
     * @return success
     */
    public boolean login(String username, String password) {
        var body = Map.of("username", username, "password", password);
        var jsonBody = new Gson().toJson(body);
        Map resp = request("POST", "/session", jsonBody);
        if (resp.containsKey("Error")) {
            return false;
        }
        authToken = (String) resp.get("authToken");
        return true;
    }

    public boolean logout() {
        Map resp = request("DELETE", "/session");
        if (resp.containsKey("Error")) {
            return false;
        }
        authToken = null;
        return true;
    }


    private Map request(String method, String endpoint) {
        return request(method, endpoint, null);
    }

    private Map request(String method, String endpoint, String body) {
        try {
            HttpURLConnection http = requestHelper(method, endpoint, body);

            int responseCode = http.getResponseCode();

            if (responseCode == 401) {
                return Map.of("Error", "Unauthorized request");
            }

            try (InputStream respBody = http.getInputStream();
                 InputStreamReader reader = new InputStreamReader(respBody)) {
                return new Gson().fromJson(reader, Map.class);
            }
        } catch (URISyntaxException | IOException e) {
            return Map.of("Error", e.getMessage());
        }
    }

    private String requestString(String method, String endpoint) {
        return requestString(method, endpoint, null);
    }

    private String requestString(String method, String endpoint, String body) {
        try {
            HttpURLConnection http = requestHelper(method, endpoint, body);

            if (http.getResponseCode() == 401) {
                return "Error: 401";
            }

            try (InputStream respBody = http.getInputStream();
                 InputStreamReader reader = new InputStreamReader(respBody)) {
                return readerToString(reader);
            }
        } catch (URISyntaxException | IOException e) {
            return String.format("Error: %s", e.getMessage());
        }
    }

    private HttpURLConnection requestHelper(String method, String endpoint, String body)
            throws URISyntaxException, IOException {
        URI uri = new URI(baseURL + endpoint);
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod(method);

        if (authToken != null) {
            http.addRequestProperty("authorization", authToken);
        }

        if (body != null) {
            http.setDoOutput(true);
            http.addRequestProperty("Content-Type", "application/json");
            try (OutputStream outputStream = http.getOutputStream()) {
                outputStream.write(body.getBytes());
            }
        }

        http.connect();
        return http;
    }

    public int createGame(String gameName) {
        var body = Map.of("gameName", gameName);
        var jsonBody = new Gson().toJson(body);
        Map resp = request("POST", "/game", jsonBody);
        if (resp.containsKey("Error")) {
            return -1;
        }
        double gameID = (double) resp.get("gameID");
        return (int) gameID;
    }

    public HashSet<GameData> listGames() {
        String resp = requestString("GET", "/game");
        if (resp.contains("Error")) {
            return HashSet.newHashSet(8);
        }
        GamesList games = new Gson().fromJson(resp, GamesList.class);

        return games.games();
    }


    public boolean joinGame(int gameId, String playerColor) {
        Map body;
        if (playerColor != null) {
            body = Map.of("gameID", gameId, "playerColor", playerColor);
        } else {
            body = Map.of("gameID", gameId);
        }
        var jsonBody = new Gson().toJson(body);
        Map resp = request("PUT", "/game", jsonBody);
        return !resp.containsKey("Error");
    }

    private String readerToString(InputStreamReader reader) {
        StringBuilder sb = new StringBuilder();
        try {
            for (int ch; (ch = reader.read()) != -1; ) {
                sb.append((char) ch);
            }
            return sb.toString();
        } catch (IOException e) {
            return "";
        }

    }
}
