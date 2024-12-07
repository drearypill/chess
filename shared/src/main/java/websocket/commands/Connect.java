package websocket.commands;

import chess.ChessGame;

public class Connect extends UserGameCommand {

    ChessGame.TeamColor playerColor;

    public Connect(String authToken, int gameID, ChessGame.TeamColor playerColor, CommandType command) {
        super(command, authToken, gameID);
        this.commandType = UserGameCommand.CommandType.CONNECT;
        this.playerColor = playerColor;
    }

    public ChessGame.TeamColor getColor() {
        return playerColor;
    }
}
