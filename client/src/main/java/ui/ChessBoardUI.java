package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class ChessBoardUI {

    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawTopLetters(out);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawTopLetters(PrintStream out) {

        String[] headers = { "a", "b", "c", "d", "e", "f", "g", "h"};
        for (int boardCol = 0; boardCol < 8; ++boardCol) {
//            drawHeader(out, headers[boardCol]);
            printTopLetters(out, headers[boardCol]);
            out.print(EMPTY.repeat(1));


//            if (boardCol < 3 - 1) {
//                out.print(EMPTY.repeat(1));
//            }
        }

        out.println();
    }

    private static void drawHeader(PrintStream out, String headerText) {
        int prefixLength = 3 / 2;
        int suffixLength = 3 - prefixLength - 1;

        out.print(EMPTY.repeat(prefixLength));
        printTopLetters(out, headerText);
        out.print(EMPTY.repeat(suffixLength));
    }

    private static void printTopLetters(PrintStream out, String player) {
        out.print(SET_BG_COLOR_LIGHT_GREEN);
        out.print(SET_TEXT_COLOR_DEEP_GREEN);

        out.print(player);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }
}
