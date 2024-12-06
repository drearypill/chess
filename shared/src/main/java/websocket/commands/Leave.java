package websocket.commands;

public class Leave extends UserGameCommand {

    //int gameID;

    public Leave(String authToken, int gameID, CommandType commandType) {
        super(commandType, authToken, gameID);
        this.commandType = CommandType.LEAVE;
        // this.gameID = gameID;
    }

}
