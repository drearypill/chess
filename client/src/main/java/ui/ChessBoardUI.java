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
        drawTopLetters(out);
        for (int i = 1; i < 9; i++) {
            drawRow(out, i);
        }

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
        drawTopLetters(out);

    }


    private static void drawTopLetters(PrintStream out) {
        setBackground(out);
        String[] letters = {"h", "g", "f", "e", "d", "c", "b", "a"};
        out.print(EMPTY);

        for (int boardCol = 0; boardCol < 8; ++boardCol) {
            out.print(" ");
            printTopLetters(out, 1 == 1 ? letters[boardCol] : letters[8 - boardCol]); //TODO: figure out who's perspective
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
        out.print(" " + boardRow + " ");
        for (int boardCol = 1; boardCol < 9; ++boardCol) {
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
        out.print(" " + boardRow + " ");
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
                else if (piece.getPieceType() == ChessPiece.PieceType.PAWN) {
                    out.print(WHITE_PAWN);
                }

                out.print(" ");

            }
            if (piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                out.print(" ");

                if (piece.getPieceType() == ChessPiece.PieceType.ROOK) {
                    out.print(BLACK_ROOK);
                }
                else if (piece.getPieceType() == ChessPiece.PieceType.KNIGHT) {
                    out.print(BLACK_KNIGHT);
                }
                else if (piece.getPieceType() == ChessPiece.PieceType.BISHOP) {
                    out.print(BLACK_BISHOP);
                }
                else if (piece.getPieceType() == ChessPiece.PieceType.QUEEN) {
                    out.print(BLACK_QUEEN);
                }
                else if (piece.getPieceType() == ChessPiece.PieceType.KING) {
                    out.print(BLACK_KING);
                }
                else if (piece.getPieceType() == ChessPiece.PieceType.PAWN) {
                    out.print(BLACK_PAWN);
                }

                out.print(" ");

            }

        }
        else {out.print(EMPTY);}
    }

}
