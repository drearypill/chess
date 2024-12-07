package ui;

import chess.*;
import client.ServerFacade;
import model.GameData;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import static java.lang.System.out;
import static ui.EscapeSequences.RESET_BG_COLOR;
import static ui.EscapeSequences.RESET_TEXT_COLOR;

public class InGame {

    ServerFacade server;
    ChessGame game;
    public static ChessGame.TeamColor color;
    int gameID;
    public static ChessBoardUI boardPrinter;


    public InGame(ServerFacade server, GameData gameData, ChessGame.TeamColor color) {
        this.server = server;
        this.game = gameData.game();
        InGame.color = color;
        this.gameID = gameData.gameID();
        boardPrinter = new ChessBoardUI(game);
    }

    public void run() {
        boolean inGame = true;

        if (color == null) {
            observeLoop(inGame);
            return;
        }
        String teamColor = color.name();
        out.print(RESET_TEXT_COLOR + RESET_BG_COLOR);
        //ChessBoardUI.drawBoard(teamColor, null);
        while (inGame) {
            String[] input = getUserInput();
            switch (input[0]) {
                case "help":
                    printHelpMenu();
                    break;
                case "redraw":
                    boardPrinter.drawBoard(teamColor, null);
//                  System.out.println(server.getBoardForCurrentGame(gameID).toString());

                    break;
                case "leave":
                    inGame = false;
                    server.leave(gameID);
                    break;
                case "move":
                    if (input.length == 3 && input[1].matches("[a-h][1-8]") && input[2].matches("[a-h][1-8]")) {
                        ChessPosition from = new ChessPosition(input[1].charAt(1) - '0', input[1].charAt(0) - ('a'-1));
                        ChessPosition to = new ChessPosition(input[2].charAt(1) - '0',input[2].charAt(0) - ('a'-1));
                        makeMove(from, to, input);

                        boardPrinter.drawBoard(teamColor, null);
                    }
                    else {
                        out.println("Please provide a to and from coordinate (ex: 'c3 d5')");
                        printMakeMove();
                    }
                    break;
                case "resign":
                    out.println("Are you sure you want to forfeit? (yes/no)");
                    String[] confirmation = getUserInput();
                    if (confirmation.length == 1 && confirmation[0].equalsIgnoreCase("yes")) {
                        server.resign(gameID);
                    }
                    else {
                        out.println("Resignation cancelled");
                    }
                    break;
                case "highlight":
                    if (input.length == 2 && input[1].matches("[a-h][1-8]")) {
                        ChessPosition position = new ChessPosition(input[1].charAt(1) - '0', input[1].charAt(0) - ('a'-1));
                        boardPrinter.drawBoard(teamColor, position);
                    }
                    else {
                        out.println("Please provide a coordinate (ex: 'c3')");
                        printHighlight();
                    }
                    break;
                default:
                    out.println("Command not recognized, please try again");
                    printHelpMenu();
                    break;
            }
        }
        PostLogin postlogin = new PostLogin(server);
        postlogin.run();
    }

    private void observeLoop(Boolean inGame) {
        boardPrinter.drawBoard("WHITE", null);
        while (inGame) {
            String[] input = getUserInput();
            switch (input[0]) {
                case "help":
                    printObserveHelpMenu();
                    break;
                case "redraw":
                    boardPrinter.drawBoard("WHITE", null);
                    break;
                case "leave":
                    inGame = false;
                    server.leave(gameID);
                    break;
                case "highlight":
                    if (input.length == 2 && input[1].matches("[a-h][1-8]")) {
                        ChessPosition position = new ChessPosition(input[1].charAt(1) - '0', input[1].charAt(0) - ('a'-1));
                        boardPrinter.drawBoard("WHITE", position);
                    }
                    else {
                        out.println("Please provide a coordinate (ex: 'c3')");
                        printHighlight();
                    }
                    break;
                default:
                    out.println("Command not recognized, please try again");
                    printObserveHelpMenu();
                    break;
            }
        }
        PostLogin postlogin = new PostLogin(server);
        postlogin.run();
    }

    private String[] getUserInput() {
        String prompt = color == null ? "OBSERVING" : color == ChessGame.TeamColor.WHITE ? "WHITE" : "BLACK";
        out.printf("\n[%s] >>> ", prompt);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().split(" ");
    }
    private void printHelpMenu() {
        out.println("redraw - redraw the game board");
        out.println("leave - leave the current game");
        printMakeMove();
        out.println("resign - forfeit this game");
        printHighlight();
        out.println("help - show this menu");
    }

    private void printObserveHelpMenu() {
        out.println("redraw - redraw the game board");
        out.println("leave - leave the current game");
        printHighlight();
        out.println("help - show this menu");
    }

    private void printMakeMove() {
        out.println("move <from> <to> - make a move");
    }
    private void printHighlight() {
        out.println("highlight <coordinate> - highlight all legal moves for the given piece");
    }
    private void makeMove(ChessPosition from, ChessPosition to, String[] input) {
        ChessPiece.PieceType promotion = null;
        if (input.length == 4) {
            promotion = getPieceType(input[3]);
            if (promotion == null) { // If it was improperly typed by the user
                out.println("Please provide proper promotion piece name (ex: 'knight')");
                printMakeMove();
            }
        }

        try {
            game.makeMove(new ChessMove(from, to, promotion));
        } catch (InvalidMoveException e) {}
        server.makeMove(gameID, new ChessMove(from, to, promotion));

    }

    public ChessPiece.PieceType getPieceType(String name) {
        return switch (name.toUpperCase()) {
            case "QUEEN" -> ChessPiece.PieceType.QUEEN;
            case "BISHOP" -> ChessPiece.PieceType.BISHOP;
            case "KNIGHT" -> ChessPiece.PieceType.KNIGHT;
            case "ROOK" -> ChessPiece.PieceType.ROOK;
            case "PAWN" -> ChessPiece.PieceType.PAWN;
            default -> null;
        };
    }

    public ChessGame getGame() {
        return game;
    }
}