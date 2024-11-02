import server.Server;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;


public class Main {

    public static void main(String[] args) throws DataAccessException {
        try { DatabaseManager.createDatabase(); } catch (DataAccessException ex) {
            throw new RuntimeException(ex);
        }

        System.out.println("♕ 240 Chess Server: ");
        Server server = new Server();
        server.run(8080);
    }
}
