package websocket.commands;

import chess.ChessGame;

public class Connect extends UserGameCommand {
    int gameID;
    ChessGame.TeamColor playerColor;


    public Connect(String authToken, int gameID, ChessGame.TeamColor playerColor, CommandType command) {
        super(command, authToken, gameID);
        this.commandType = UserGameCommand.CommandType.CONNECT;
        this.gameID = gameID;
        this.playerColor = playerColor;
    }

    public int getGameID() {
        return gameID;
    }

    public ChessGame.TeamColor getColor() {
        return playerColor;
    }
}
