package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import chess.ChessBoard;
import chess.ChessPiece;
import chess.ChessPosition;

import static chess.ChessGame.TeamColor.WHITE;
import static ui.EscapeSequences.*;

public class ChessBoardUI {

    public static void main(String[] args) {
        drawBoard();
    }

    public static void drawBoard() {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        ChessBoard chessBoard = new ChessBoard();
        chessBoard.resetBoard();

        drawLetters(out); // should also take in which team it is??
        for (int i = 1; i < 9; i++) {
            drawRow(out, chessBoard, i);
        }

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
        drawLetters(out);

    }


    private static void drawLetters(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREEN);
        String[] letters = {"h", "g", "f", "e", "d", "c", "b", "a"};
        out.print(EMPTY);

        for (int boardCol = 0; boardCol < 8; ++boardCol) {
            out.print(" ");
            printLetters(out, 1 == 1 ? letters[boardCol] : letters[7 - boardCol]); //TODO: figure out who's perspective
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
