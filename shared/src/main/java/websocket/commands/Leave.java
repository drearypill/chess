package websocket.commands;

public class Leave extends UserGameCommand {

    int gameID;

    public Leave(CommandType commandType, String authToken, int gameID) {
        super(commandType, authToken, gameID);
        this.commandType = CommandType.LEAVE;
        this.gameID = gameID;
    }

}
