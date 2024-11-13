package ui;

import client.ServerFacade;
import model.GameData;

import java.util.*;

import static java.lang.System.out;

public class PostLogin {

    ServerFacade server;
    Map<Integer, GameData> games;
    Map<Integer, Integer> countToGameIdMap; // listed count -> gameID


    public PostLogin(ServerFacade server) {
        this.server = server;
        games = new HashMap<>();
        countToGameIdMap = new HashMap<>();
    }

    public void run() {
        boolean loggedIn = true;
        while (loggedIn) {
            String[] input = getUserInput();
            switch (input[0]) {
                case "quit":
                    return;
                case "help":
                    printHelpMenu();
                    break;
                case "logout":
                    server.logout();
                    loggedIn = false;
                    break;
                case "list":
                    refreshGames();
                    printGames();
                    break;
                case "create":
                    if (input.length != 2) {
                        out.println("Please provide a name");
                        printCreate();
                        break;
                    }
                    server.createGame(input[1]);
                    out.println("Created game successfully!");
                    refreshGames();
                    printGames();
                    break;
                case "join":
                    refreshGames();
                    if (input.length != 3) {
                        out.println("Please provide a game number and color choice");
                        printJoin();
                        break;
                    }

                    int selectedCount;
                    try {
                        selectedCount = Integer.parseInt(input[1]); // Get the display count
                    } catch (NumberFormatException e) {
                        out.println("Please provide a valid game number");
                        printJoin();
                        break;
                    }

                    // Map the selected count to the corresponding gameID
                    Integer gameID = countToGameIdMap.get(selectedCount);  // Using the count-to-ID map

                    if (gameID == null) {
                        out.println("Game does not exist.");
                        printJoin();
                        break;
                    }

                    GameData joinGame = games.get(gameID); // Get the GameData by gameID

                    if (joinGame == null) {
                        out.println("Game does not exist.");
                        printJoin();
                        break;
                    }

                    if (server.joinGame(joinGame.gameID(), input[2].toUpperCase())) {
                        out.println("You have joined the game");
                        refreshGames();
                        ChessBoardUI.drawBoard(input[2].toUpperCase());
                    } else {
                        out.println("Color taken or another issue occurred.");
                        printJoin();
                    }
                    break;

//                case "observe":
//                    refreshGames();
//                    System.out.println(games);
//                    if (input.length != 2) {
//                        out.println("Please provide a game ID");
//                        printObserve();
//                        break;
//                    }
//                    int observeGameId;
//                    try {
//                        observeGameId = Integer.parseInt(input[1]);
//                    } catch (NumberFormatException e) {
//                        out.println("Please provide a valid game ID");
//                        printObserve();
//                        break;
//                    }
//                    GameData observeGame = games.get(observeGameId);
//
//                    if (observeGame == null) {
//                        out.println("Game does not exist or could not be found.");
//                        printObserve();
//                        break;
//                    }
//
//                    out.println(observeGame);
//                    out.println("Game ID: " + observeGame.gameID());
//                    if (!server.joinGame(observeGame.gameID(), null)) {
//                        out.println("You have joined the game as an observer");
//                        ChessBoardUI.drawBoard("WHITE");
//                        ChessBoardUI.drawBoard("BLACK");
//                    } else {
//                        out.println("Game does not exist or could not be joined.");
//                        printObserve();
//                    }
//                    break;
                case "observe":
                    refreshGames();
                    System.out.println(games);
                    if (input.length != 2) {
                        out.println("Please provide a game number");
                        printObserve();
                        break;
                    }
                    int observeGameCount;
                    try {
                        observeGameCount = Integer.parseInt(input[1]);
                    } catch (NumberFormatException e) {
                        out.println("Please provide a valid game number");
                        printObserve();
                        break;
                    }

                    int observeGameID = countToGameIdMap.get(observeGameCount);
                    GameData observeGame = games.get(observeGameID);


                    if (observeGame == null) {
                        out.println("Game does not exist or could not be found.");
                        printObserve();
                        break;
                    }

                    out.println(observeGameID);
                    out.println("Game ID: " + observeGame.gameID());
                    if (!server.joinGame(observeGameID, null)) {
                            out.println("You have joined the game as an observer");
                            ChessBoardUI.drawBoard("WHITE");
                            ChessBoardUI.drawBoard("BLACK");
                        } else {
                            out.println("Game does not exist or could not be joined.");
                            printObserve();
                        }
                    break;

                default:
                    out.println("Command not recognized, please try again");
                    printHelpMenu();
                    break;
            }
        }

        PreLogin prelogin = new PreLogin(server);
        prelogin.run();
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
