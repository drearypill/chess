import chess.*;
import server.Server;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) throws DataAccessException {
        try { DatabaseManager.createDatabase(); } catch (DataAccessException ex) {
            throw new RuntimeException(ex);
        }

//    public static void main(String[] args) {
//        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
//        System.out.println("♕ 240 Chess Server: " + piece);
        Server server = new Server();
        server.run(8080);
    }
}
