package websocket.commands;

public class Resign extends UserGameCommand {

    int gameID;

    public Resign(CommandType commandType, String authToken, int gameID) {
        super(commandType, authToken, gameID);
        this.commandType = CommandType.RESIGN;
        this.gameID = gameID;
    }

}