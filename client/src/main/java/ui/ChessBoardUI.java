package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import chess.ChessBoard;
import chess.ChessPiece;
import chess.ChessPosition;

import static chess.ChessGame.TeamColor.WHITE;
import static java.lang.System.out;
import static ui.EscapeSequences.*;

public class ChessBoardUI {

    public static void main(String[] args, String team) {
        drawBoard(team);
    }

    public static void drawBoard(String team) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        ChessBoard chessBoard = new ChessBoard();
        chessBoard.resetBoard();

        drawLetters(out, team); // should also take in which team it is??
        int start = team.equals("WHITE") ? 8 : 1;
        int end = team.equals("WHITE") ? 0 : 9;
        int step = team.equals("WHITE") ? -1 : 1;

        for (int i = start; i != end; i += step) {
            drawRow(out, chessBoard, i);
        }

        drawLetters(out, team);
        out.print(RESET_TEXT_COLOR + RESET_BG_COLOR);

    }


    private static void drawLetters(PrintStream out, String team) {
        out.print(SET_BG_COLOR_DARK_GREEN);
        String[] letters = {"h", "g", "f", "e", "d", "c", "b", "a"};
        out.print(EMPTY);

        for (int boardCol = 0; boardCol < 8; ++boardCol) {
            out.print(" ");
            printLetters(out, Objects.equals(team, "BLACK") ? letters[boardCol] : letters[7 - boardCol]); //need to: figure out whose perspective
            out.print(" ");
        }

        out.println();
    }

    private static void drawRow(PrintStream out, ChessBoard chessBoard, int boardRow) {

        out.print(" " + boardRow + " "); // number the row
        for (int boardCol = 1; boardCol < 9; ++boardCol) {
            out.print(SET_TEXT_COLOR_NICE);

            if ((boardCol + boardRow) % 2 == 0) {
                out.print(SET_BG_COLOR_DARK);
            }
            else {
                out.print(SET_BG_COLOR_LIGHT);
            }
            drawSquare(out, chessBoard.getPiece(new ChessPosition(boardRow, boardCol)));
            if (boardCol == 8) {
                out.print(SET_BG_COLOR_DARK_GREEN);
            }
        }
        out.print(SET_TEXT_COLOR_NICE);
        out.print(" " + boardRow + " ");
        out.println();
    }


    private static void printLetters(PrintStream out, String player) {
        out.print(SET_TEXT_COLOR_NICE);

        out.print(player);
    }


    private static void drawSquare(PrintStream out, ChessPiece piece) {
        out.print(SET_TEXT_COLOR_BLACK);

        if (piece != null) {
            out.print(" ");
            if (piece.getTeamColor() == WHITE) {
                printPiece(out, piece, WHITE_ROOK, WHITE_KNIGHT, WHITE_BISHOP, WHITE_QUEEN, WHITE_KING, WHITE_PAWN);
            }
            else {
                printPiece(out, piece, BLACK_ROOK, BLACK_KNIGHT, BLACK_BISHOP, BLACK_QUEEN, BLACK_KING, BLACK_PAWN);
            }
            out.print(" ");
        }
        else {out.print(EMPTY);}
    }

    private static void printPiece(PrintStream out, ChessPiece piece, String rook, String knight, String bishop,
                                   String queen, String king, String pawn) {
            switch (piece.getPieceType()) {
                case ROOK -> out.print(rook);
                case KNIGHT -> out.print(knight);
                case BISHOP -> out.print(bishop);
                case QUEEN -> out.print(queen);
                case KING -> out.print(king);
                case PAWN -> out.print(pawn);
            }

        }


}
