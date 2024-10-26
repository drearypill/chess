package chess;

import java.util.Collection;

public class PieceMoves {
    public static void getDiagonalMoves(ChessBoard board, ChessPosition myPosition,
                                        Collection<ChessMove> places) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPiece movingPiece = board.getPiece(myPosition);

        // Define the four directions for straight moves
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        for (int[] dir : directions) {
            addMovesInDirection(board, myPosition, places, movingPiece, row, col, dir[0], dir[1]);
        }
    }

    public static void getStraightMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> places) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPiece movingPiece = board.getPiece(myPosition);

        // Define the four directions for straight moves
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int[] dir : directions) {
            addMovesInDirection(board, myPosition, places, movingPiece, row, col, dir[0], dir[1]);
        }
    }

    // Helper method to add moves in a given direction
    private static void addMovesInDirection(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> places,
                                            ChessPiece movingPiece, int row, int col, int rowInc, int colInc) {
        for (int i = 1; i < 8; i++) {  // Move up to 7 squares in the direction
            ChessPosition spot = new ChessPosition(row + i * rowInc, col + i * colInc);
            if (!spot.checkInBounds()) {break;}

            ChessPiece collisionPiece = board.getPiece(spot);
            if (collisionPiece == null) {
                places.add(new ChessMove(myPosition, spot, null));
            } else {
                if (collisionPiece.getTeamColor() != movingPiece.getTeamColor()) {
                    places.add(new ChessMove(myPosition, spot, null));
                }
                break;  // Stop moving in this direction after encountering any piece
            }
        }
    }


    public static void getPawnMoves(ChessBoard board, ChessPosition myPosition,
                                    Collection<ChessMove> places, ChessGame.TeamColor color) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        int direction = (color == ChessGame.TeamColor.WHITE) ? 1 : -1;
        int initialRow = (color == ChessGame.TeamColor.WHITE) ? 2 : 7;

        addForwardMoves(board, myPosition, places, row, col, direction, initialRow);
        addCaptureMoves(board, myPosition, places, row, col, direction);

    }

    private static void addForwardMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> places,
                                        int row, int col, int direction, int initialRow) {
        ChessPosition forwardOne = new ChessPosition(row + direction, col);
        if (forwardOne.checkInBounds() && board.getPiece(forwardOne) == null) {
            addPromotionOrStandardMove(places, myPosition, forwardOne, row, direction);
            if (row == initialRow) { // Initial double step
                ChessPosition forwardTwo = new ChessPosition(row + 2 * direction, col);
                if (forwardTwo.checkInBounds() && board.getPiece(forwardTwo) == null) {
                    places.add(new ChessMove(myPosition, forwardTwo, null));
                }
            }
        }
    }

    private static void addCaptureMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> places,
                                        int row, int col, int direction) {
        for (int side = -1; side <= 1; side += 2) { // Check both diagonals
            ChessPosition captureSpot = new ChessPosition(row + direction, col + side);
            if (captureSpot.checkInBounds()) {
                ChessPiece collisionPiece = board.getPiece(captureSpot);
                if (collisionPiece != null && collisionPiece.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    addPromotionOrStandardMove(places, myPosition, captureSpot, row, direction);
                }
            }
        }
    }

    private static void addPromotionOrStandardMove(Collection<ChessMove> places, ChessPosition from, ChessPosition to, int row, int direction) {
        int promotionRow = (direction == 1) ? 8 : 1;
        if (row + direction == promotionRow) {
            places.add(new ChessMove(from, to, ChessPiece.PieceType.ROOK));
            places.add(new ChessMove(from, to, ChessPiece.PieceType.KNIGHT));
            places.add(new ChessMove(from, to, ChessPiece.PieceType.BISHOP));
            places.add(new ChessMove(from, to, ChessPiece.PieceType.QUEEN));
        } else {
            places.add(new ChessMove(from, to, null));
        }
    }

    public static void getKnightMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> places) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPiece movingPiece = board.getPiece(myPosition);

        // Define all possible knight moves as row, col offsets
        int[][] knightMoves = {
                {1, -2}, {1, 2}, {2, -1}, {2, 1},
                {-1, -2}, {-1, 2}, {-2, -1}, {-2, 1}
        };

        // Check each knight move position
        for (int[] move : knightMoves) {
            ChessPosition spot = new ChessPosition(row + move[0], col + move[1]);
            if (spot.checkInBounds()) {
                ChessPiece collisionPiece = board.getPiece(spot);
                // Add the move if the spot is empty or has an opponent piece
                if (collisionPiece == null || collisionPiece.getTeamColor() != movingPiece.getTeamColor()) {
                    places.add(new ChessMove(myPosition, spot, null));
                }
            }
        }
    }

    public static Collection<ChessMove> getKingMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> places) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessPiece movingPiece = board.getPiece(myPosition);

        // Define all possible king moves as row, col offsets
        int[][] kingMoves = {
                {1, 0}, {1, 1}, {1, -1},
                {-1, 0}, {-1, 1}, {-1, -1},
                {0, 1}, {0, -1}
        };

        // Check each adjacent position
        for (int[] move : kingMoves) {
            ChessPosition spot = new ChessPosition(row + move[0], col + move[1]);
            if (spot.checkInBounds()) {
                ChessPiece collisionPiece = board.getPiece(spot);
                // Add the move if the spot is empty or has an opponent piece
                if (collisionPiece == null || collisionPiece.getPieceType() != movingPiece.getPieceType()) {
                    places.add(new ChessMove(myPosition, spot, null));
                }
            }
        }
        return places;
    }





}
