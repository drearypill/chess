package ui;

import chess.ChessGame;
import client.ServerFacade;
import model.GameData;

import java.util.*;

import static java.lang.System.out;

public class PostLogin {

    ServerFacade server;
    Map<Integer, GameData> games;
    Map<Integer, Integer> countToGameIdMap; // listed count -> gameID

    boolean inGame;

    public PostLogin(ServerFacade server) {
        this.server = server;
        games = new HashMap<>();
        countToGameIdMap = new HashMap<>();
    }

    public void run() {
        boolean loggedIn = true;
        inGame = false;
        while (loggedIn && !inGame) {
            String[] input = getUserInput();
            refreshGames();
            switch (input[0]) {
                case "quit"-> {return;}
                case "help"-> {printHelpMenu();}
                case "logout"-> {
                    server.logout();
                    loggedIn = false;
                }
                case "list"-> {
                    refreshGames();
                    printGames();}
                case "create"-> {
                    if (input.length != 2) {
                        out.println("Please provide a name");
                        printCreate();
                        break;
                    }
                    server.createGame(input[1]);
                    out.println("Created game successfully!");
                    printGames();
                }
                case "join"-> {
                    handleJoin(input);
                }
                case "observe"-> {
                    handleObserve(input);
                }
                default-> {
                    out.println("Command not recognized, please try again");
                    printHelpMenu();
                }
            }
        }
        PreLogin prelogin = new PreLogin(server);
        prelogin.run();
    }

    private void handleObserve(String[] input) {
        if (input.length != 2) {
            out.println("Please provide a game number");
            printObserve();
            return;
        }
        int observeGameCount;
        try {
            observeGameCount = Integer.parseInt(input[1]);
        } catch (NumberFormatException e) {
            out.println("Please provide a valid game number");
            printObserve();
            return;
        }
        int observeGameID = countToGameIdMap.get(observeGameCount);
        GameData observeGame = games.get(observeGameID);
        if (observeGame == null) {
            out.println("Game does not exist or could not be found.");
            printObserve();
            return;
        }
        if (!server.joinGame(observeGameID, null)) {
            out.println("You have joined the game as an observer");
            inGame = true;
            server.connectWS();
            server.connect(observeGame.gameID(), null);
            InGame inObservedGame = new InGame(server, observeGame, null);
            inObservedGame.run();
        } else {
            out.println("Game does not exist or could not be joined.");
            printObserve();
        }
    }

    private void handleJoin(String[] input) {
        if (input.length != 3) {
            out.println("Please provide a game number and color choice");
            printJoin();
            return;
        }
        int selectedCount;
        try {
            selectedCount = Integer.parseInt(input[1]); // Get the display count
        } catch (NumberFormatException e) {
            out.println("Please provide a valid game number");
            printJoin();
            return;
        }
        Integer gameID = countToGameIdMap.get(selectedCount);  // Using the count-to-ID map
        if (gameID == null) {
            out.println("Game does not exist.");
            printJoin();
            return;
        }
        GameData joinGameData = games.get(gameID); // Get the GameData by gameID
        if (joinGameData == null) {
            out.println("Game does not exist.");
            printJoin();
            return;
        }

        ChessGame.TeamColor color = input[2].equalsIgnoreCase("WHITE")
                ? ChessGame.TeamColor.WHITE : ChessGame.TeamColor.BLACK;

        if (server.joinGame(joinGameData.gameID(), input[2].toUpperCase())) {
            out.println("You have joined the game");
            refreshGames();
            inGame = true;
            server.connectWS();
            server.connect(joinGameData.gameID(), color);
            InGame gameREPL = new InGame(server, joinGameData, color);
            gameREPL.run();
        } else {
            out.println("Color taken or another issue occurred.");
            printJoin();
        }
    }

    private String[] getUserInput() {
        out.print("\n[LOGGED IN] >>> ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().split(" ");
    }


    private void refreshGames() {
        games.clear(); // Clear the existing games
        Set<GameData> gameList = server.listGames(); // Assuming listGames returns a Set or List of GameData
        for (GameData game : gameList) {
            games.put(game.gameID(), game); // Use the game ID as the key
        }
    }

    public void printGames() {
        refreshGames();
        int count = 1;  // Initialize a counter starting at 1
        for (GameData game : games.values()) {
            countToGameIdMap.put(count, game.gameID());
            String whiteUser = game.whiteUsername() != null ? game.whiteUsername() : "open";
            String blackUser = game.blackUsername() != null ? game.blackUsername() : "open";
            out.printf("%d -- Game Name: %s  |  White User: %s  |  Black User: %s %n", count++, game.gameName(), whiteUser, blackUser);
        }
    }

    private void printHelpMenu() {
        printCreate();
        out.println("list - list all games");
        printJoin();
        printObserve();
        out.println("logout - log out of current user");
        out.println("quit - stop playing");
        out.println("help - show this menu");
    }

    private void printCreate() {
        out.println("create <NAME> - create a new game");
    }

    private void printJoin() {
        out.println("join <ID> [WHITE|BLACK] - join a game as color");
    }

    private void printObserve() {
        out.println("observe <ID> - observe a game");
    }
}
