package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import chess.ChessBoard;
import chess.ChessPiece;
import chess.ChessPosition;

import static ui.EscapeSequences.*;

public class ChessBoardUI {

    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);
        setBackground(out);

        drawTopLetters(out);
        drawRow(out);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawTopLetters(PrintStream out) {

        String[] letters = { "a", "b", "c", "d", "e", "f", "g", "h"};
        out.print(EMPTY);

        for (int boardCol = 0; boardCol < 8; ++boardCol) {
            printTopLetters(out, letters[boardCol]);
            out.print(EMPTY);

        }

        out.println();
    }

    private static void drawRow(PrintStream out) {
        ChessPiece[][] board = new ChessPiece[8][8];
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.resetBoard();
        chessBoard.getBoard();
//        out.print(chessBoard);
        String[] row1 = { WHITE_ROOK, WHITE_KNIGHT, WHITE_BISHOP, WHITE_QUEEN, WHITE_KING, WHITE_KNIGHT, WHITE_BISHOP, WHITE_KNIGHT};
        out.print(EMPTY);
        setDarkSquare(out);
        for (int boardCol = 1; boardCol < 9; ++boardCol) {
            if (chessBoard.getPiece(new ChessPosition(1, boardCol)) != null) {
//                printTopLetters(out, board[1][boardCol].toString());
                printTopLetters(out, chessBoard.getPiece(new ChessPosition(1, boardCol)).toString());
//                out.println();
//                printTopLetters(out, chessBoard.getPiece(new ChessPosition(2, boardCol)).toString());

                out.print(EMPTY);
            }
            else {out.print(EMPTY);}

        }
        out.println();


        for (int boardCol = 1; boardCol < 9; ++boardCol) {
            if (chessBoard.getPiece(new ChessPosition(1, boardCol)) != null) {
//                printTopLetters(out, board[1][boardCol].toString());
                printTopLetters(out, chessBoard.getPiece(new ChessPosition(2, boardCol)).toString());

                out.print(EMPTY);
            }
            else {out.print(EMPTY);}

        }
    }


    private static void printTopLetters(PrintStream out, String player) {
        out.print(SET_BG_COLOR_LIGHT_GREEN);
        out.print(SET_TEXT_COLOR_DEEP_GREEN);

        out.print(player);
    }

    private static void setBackground(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREEN);
    }
    private static void setLightSquare(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
    }
    private static void setDarkSquare(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREY);
    }

    private String[][] makeStartBoard() {
        String[][] startBoard = new String[8][8];
        return startBoard;
    }
}
