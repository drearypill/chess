package chess;

import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override

    public boolean equals(Object obj) {
        ChessBoard chessBoard = (ChessBoard) obj;
        return Arrays.deepEquals(squares, chessBoard.squares);
    }

    private ChessPiece[][] squares = new ChessPiece[9][9];
    public ChessBoard() {
        
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        if (position.checkInBounds()) {
            squares[position.getRow()][position.getColumn()]=piece;
        }
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        if (position.checkInBounds()){
            return squares[position.getRow()][position.getColumn()];
        }
        else { return null;}
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        throw new RuntimeException("Not implemented");
    }
}