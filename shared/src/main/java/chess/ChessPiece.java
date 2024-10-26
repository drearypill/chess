package chess;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public record ChessPiece(ChessGame.TeamColor pieceColor, chess.ChessPiece.PieceType type) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPiece that)) return false;
        return type == that.type && pieceColor == that.pieceColor;
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
        return pieceColor;
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
     */

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> places = new ArrayList<>();
        switch (board.getPiece(myPosition).getPieceType()) {
            case KING -> {
                int row = myPosition.getRow();
                int col = myPosition.getColumn();
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        ChessPosition spot = new ChessPosition(row + i, col + j);
                        if (spot.checkInBounds()) {
                            ChessPiece collisionPiece = board.getPiece(spot);
                            if (collisionPiece == null || collisionPiece.pieceColor != this.pieceColor) {
                                places.add(new ChessMove(myPosition, spot, null));
                            }
                        }
                    }
                }
                return places;
            }
            case BISHOP -> PieceMoves.getDiagonalMoves(board, myPosition, places);
            case ROOK -> PieceMoves.getStraightMoves(board, myPosition, places);
            case QUEEN -> {
                PieceMoves.getDiagonalMoves(board, myPosition, places);
                PieceMoves.getStraightMoves(board, myPosition, places);
            }
            case KNIGHT -> PieceMoves.getKnightMoves(board, myPosition, places);
            case PAWN -> PieceMoves.getPawnMoves(board, myPosition, places, pieceColor);

        }


        return places;
    }
}
