import chess.*;
import ui.ClientUI;
import client.ServerFacade;


public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        ServerFacade server = new ServerFacade();

        ClientUI prelogin = new ClientUI(server);
        prelogin.run();
    }
}