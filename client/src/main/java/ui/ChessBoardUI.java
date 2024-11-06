package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import static ui.EscapeSequences.*;

public class ChessBoardUI {

    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);
        setBackground(out);

        drawTopLetters(out);
        drawRow(out, 1);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawTopLetters(PrintStream out) {

        String[] letters = { "a", "b", "c", "d", "e", "f", "g", "h"};
        out.print(EMPTY);

        for (int boardCol = 0; boardCol < 8; ++boardCol) {
            out.print(" ");
            printTopLetters(out, letters[boardCol]);
            out.print(" ");

        }

        out.println();
    }

    private static void drawRow(PrintStream out, int boardRow) {
        ChessPiece[][] board = new ChessPiece[8][8];
        ChessBoard chessBoard = new ChessBoard();
        chessBoard.resetBoard();
        chessBoard.getBoard();
//        out.print(chessBoard);
        out.print(EMPTY);
        setDarkSquare(out);
        for (int boardCol = 1; boardCol < 9; ++boardCol) {
            if ((boardCol + boardRow) % 2 == 0) {
                setDarkSquare(out);
            }
            else {
                setLightSquare(out);
            }
            printPiece(out, chessBoard.getPiece(new ChessPosition(boardRow, boardCol)));
        }
        out.println();
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

    private static void printPiece(PrintStream out, ChessPiece piece) {
        if (piece != null) {
            if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                out.print(" ");

                if (piece.getPieceType() == ChessPiece.PieceType.ROOK) {
                    out.print(WHITE_ROOK);
                }
                else if (piece.getPieceType() == ChessPiece.PieceType.KNIGHT) {
                    out.print(WHITE_KNIGHT);
                }
                else if (piece.getPieceType() == ChessPiece.PieceType.BISHOP) {
                    out.print(WHITE_BISHOP);
                }
                else if (piece.getPieceType() == ChessPiece.PieceType.QUEEN) {
                    out.print(WHITE_QUEEN);
                }
                else if (piece.getPieceType() == ChessPiece.PieceType.KING) {
                    out.print(WHITE_KING);
                }

                out.print(" ");

            }

        }
        else {out.print(EMPTY);}
    }

    private String[][] makeStartBoard() {
        String[][] startBoard = new String[8][8];
        return startBoard;
    }
}
