package chess;


/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public record ChessMove(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType promotionPiece) {

    /**
     * @return ChessPosition of starting location
     */
    @Override
    public ChessPosition startPosition() {
        return startPosition;
    }

    /**
     * @return ChessPosition of ending location
     */
    @Override
    public ChessPosition endPosition() {
        return endPosition;
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        return startPosition;
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        return endPosition;
    }

    public ChessPiece.PieceType getPromotionPiece() {
        return promotionPiece;
    }


    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    @Override
    public ChessPiece.PieceType promotionPiece() {
        return promotionPiece;
    }

    @Override
    public String toString() {
        return "" + startPosition +
                endPosition
                + promotionPiece;
    }

    @Override
    public int hashCode() {
        var promotionCode = (promotionPiece == null ?
                9 : promotionPiece.ordinal());
        return (71 * startPosition.hashCode()) + endPosition.hashCode() + promotionCode;
    }

}
