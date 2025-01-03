package websocket.commands;

import chess.ChessGame;
import chess.ChessMove;

public class MakeMove extends UserGameCommand {

    //int gameID;
    ChessMove move;

    public MakeMove(String authToken, int gameID, ChessMove move, CommandType command) {
        super(command, authToken, gameID);
        this.commandType = CommandType.MAKE_MOVE;
//        this.gameID = gameID;
        this.move = move;
    }

    public ChessMove getMove() {
        return move;
    }
}
