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
        drawLetters(out);
        for (int i = 1; i < 9; i++) {
            drawRow(out, i);
        }

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
        drawLetters(out);

    }


    private static void drawLetters(PrintStream out) {
        setBackground(out);
        String[] letters = {"h", "g", "f", "e", "d", "c", "b", "a"};
        out.print(EMPTY);


        for (int boardCol = 0; boardCol < 8; ++boardCol) {
            out.print(" ");
            printLetters(out, 1 == 1 ? letters[boardCol] : letters[7 - boardCol]); //TODO: figure out who's perspective
            out.print(" ");
        }

        out.println();
    }

    private static void drawRow(PrintStream out, int boardRow) {
        ChessPiece[][] board = new ChessPiece[8][8];
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.resetBoard();

        out.print(" " + boardRow + " ");
        for (int boardCol = 1; boardCol < 9; ++boardCol) {
            out.print(SET_TEXT_COLOR_NICE);

            if ((boardCol + boardRow) % 2 == 0) {
                setDarkSquare(out);
            }
            else {
                setLightSquare(out);
            }
            printPiece(out, chessBoard.getPiece(new ChessPosition(boardRow, boardCol)));
            if (boardCol == 8) {
                setBackground(out);
            }
        }
        out.print(SET_TEXT_COLOR_NICE);
        out.print(" " + boardRow + " ");
        out.println();
    }


    private static void printLetters(PrintStream out, String player) {
        //out.print(SET_BG_COLOR_DARK_GREEN);
        out.print(SET_TEXT_COLOR_NICE);

        out.print(player);
    }

    private static void setBackground(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREEN);
    }
    private static void setLightSquare(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT);
    }
    private static void setDarkSquare(PrintStream out) {
        out.print(SET_BG_COLOR_DARK);
    }

    private static void printPiece(PrintStream out, ChessPiece piece) {
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

    private static void printPiece(PrintStream out, ChessPiece piece, String Rook, String Knight, String Bishop,
                                   String Queen, String King, String Pawn) {
            switch (piece.getPieceType()) {
                case ROOK -> out.print(Rook);
                case KNIGHT -> out.print(Knight);
                case BISHOP -> out.print(Bishop);
                case QUEEN -> out.print(Queen);
                case KING -> out.print(King);
                case PAWN -> out.print(Pawn);
            }

        }


}
