package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
private ChessPiece.PieceType type;
private ChessGame.TeamColor pieceColor;
    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.type = type;
        this.pieceColor = pieceColor;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        throw new RuntimeException("Not implemented");
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> places = new ArrayList<>();
        switch (board.getPiece(myPosition).getPieceType()) {
            case KING -> {
                int row = myPosition.getRow();
                int col = myPosition.getColumn();

                places.add(new ChessMove(myPosition, new ChessPosition(row+1,col+1), PieceType.KING));
                places.add(new ChessMove(myPosition, new ChessPosition(row,col+1), PieceType.KING));
                places.add(new ChessMove(myPosition, new ChessPosition(row-1,col+1), PieceType.KING));
                places.add(new ChessMove(myPosition, new ChessPosition(row-1,col), PieceType.KING));
                places.add(new ChessMove(myPosition, new ChessPosition(row-1,col-1), PieceType.KING));
                places.add(new ChessMove(myPosition, new ChessPosition(row,col-1), PieceType.KING));
                places.add(new ChessMove(myPosition, new ChessPosition(row+1,col-1), PieceType.KING));
                places.add(new ChessMove(myPosition, new ChessPosition(row+1,col), PieceType.KING));
            }

        }
        return places;
    }
}
