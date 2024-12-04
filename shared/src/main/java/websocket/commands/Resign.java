package websocket.commands;

public class Resign extends UserGameCommand {

    int gameID;

    public Resign(String authToken, int gameID, CommandType commandType) {
        super(commandType, authToken, gameID);
        this.commandType = CommandType.RESIGN;
        this.gameID = gameID;
    }

}