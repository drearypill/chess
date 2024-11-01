package dataaccess;
import model.AuthData;
import java.sql.Connection;
import java.sql.SQLException;

import static dataaccess.DatabaseManager.DATABASE_NAME;


public class SQLAuthDAO implements AuthDAO {
    public SQLAuthDAO() {
        try (var conn = DatabaseManager.getConnection()) {
            conn.setCatalog(DATABASE_NAME);
            var createTestTable = """            
                    CREATE TABLE if NOT EXISTS auth (
                                    username VARCHAR(255) NOT NULL,
                                    authToken VARCHAR(255) NOT NULL,
                                    PRIMARY KEY (authToken)
                                    )""";
            try (var createTableStatement = conn.prepareStatement(createTestTable)) {
                createTableStatement.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void addAuth(AuthData authData) {
        try (var conn = DatabaseManager.getConnection()) {
            try (var statement = conn.prepareStatement("INSERT INTO auth (username, authToken) VALUES(?, ?)")) {
                statement.setString(1, authData.username());
                statement.setString(2, authData.authToken());
                statement.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            return;
        }
    }
    @Override
    public void deleteAuth(String authToken) {
        try (Connection conn = DatabaseManager.getConnection() ) {
            try (var statement = conn.prepareStatement("DELETE FROM auth WHERE authToken=?")) {
                statement.setString(1, authToken);
                statement.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            return;
        }
    }
    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var statement = conn.prepareStatement("SELECT username, authToken FROM auth WHERE authToken=?")) {
                statement.setString(1, authToken);
                try (var results = statement.executeQuery()) {
                    results.next();
                    var username = results.getString("username");
                    return new AuthData(username, authToken);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Auth Token does not exist: " + authToken);
        }
    }
    @Override
    public void clear() {
        try (var conn = DatabaseManager.getConnection()) {
            try (var statement = conn.prepareStatement("TRUNCATE auth")) {
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException | DataAccessException e) {}
    }
}