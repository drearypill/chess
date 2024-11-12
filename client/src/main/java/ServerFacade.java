
import com.google.gson.Gson;
import exception.ResponseException;

import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.Collections;

public class ServerFacade {

    private final String serverUrl;
    String authToken;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    public boolean register(String username, String password, String email) {
        var body = Map.of("username", username, "password", password, "email", email);
        var jsonBody = new Gson().toJson(body);
        Map resp = makeRequest("POST", "/user", jsonBody);
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
        try {
            Map resp = makeRequest("POST", "/session", jsonBody);
            if (resp.containsKey("Error")) {
                return false;
            }
            authToken = (String) resp.get("authToken");
            return true;
        } catch (ResponseException ex) {
            return false;
        }
    }

    public boolean logout() {
        Map resp = makeRequest("DELETE", "/session");
        if (resp.containsKey("Error")) {
            return false;
        }
        authToken = null;
        return true;
    }

    private Map makeRequest(String method, String endpoint) {
        try {
            return makeRequest(method, endpoint, null);
        } catch (ResponseException ex) {
            return Collections.emptyMap();
        }
    }

    private <T> T makeRequest(String method, String path, Object request,) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }


    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new ResponseException(status, "failure: " + status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream erespBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }


    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}
