package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import chess.ChessBoard;
import chess.ChessPiece;

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
        ChessBoard board = new ChessBoard();
        board.getBoard();
        out.print(board);
        String[] row1 = { WHITE_ROOK, WHITE_KNIGHT, WHITE_BISHOP, WHITE_QUEEN, WHITE_KING, WHITE_KNIGHT, WHITE_BISHOP, WHITE_KNIGHT};
        out.print(EMPTY);
        for (int boardCol = 0; boardCol < 8; ++boardCol) {
            printTopLetters(out, row1[boardCol]);
            out.print(EMPTY);

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
}
