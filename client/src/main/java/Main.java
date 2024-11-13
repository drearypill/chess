
import ui.PreLogin;
import ui.PostLogin;
import client.ServerFacade;
import ui.ChessBoardUI;


public class Main {
    public static void main(String[] args) {
        System.out.println("♕ 240 Chess Client: " );
        ServerFacade server = new ServerFacade();
//        ChessBoardUI.drawBoard("WHITE");
        server.joinGame(256, null);

        PreLogin prelogin = new PreLogin(server);
        prelogin.run();
    }
}