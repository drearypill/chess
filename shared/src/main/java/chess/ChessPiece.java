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
    @Override
    public int hashCode() {
        int result=type != null ? type.hashCode() : 0;
        result=31 * result + pieceColor.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    private ChessPiece.PieceType type;
    private ChessGame.TeamColor pieceColor;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.type=type;
        this.pieceColor=pieceColor;
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
     * @return
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> places=new ArrayList<>();
        switch (board.getPiece(myPosition).getPieceType()) {
            case KING -> {
                int row=myPosition.getRow();
                int col=myPosition.getColumn();
                for (int i=-1; i < 2; i++) {
                    for (int j=-1; j < 2; j++) {
                        ChessPosition spot=new ChessPosition(row + i, col + j);
                        if (spot.checkInBounds()) {
                            ChessPiece collisionPiece=board.getPiece(spot);
                            if (collisionPiece == null || collisionPiece.pieceColor != this.pieceColor) {
                                places.add(new ChessMove(myPosition, spot, null));
                            }
                        }
                    }
                }
                return places;
            }
            case BISHOP -> {
                int row=myPosition.getRow();
                int col=myPosition.getColumn();
                ChessPiece movingPiece=board.getPiece(myPosition);
                for (int i=0; i < 8; i++) {
                    ChessPosition spot=new ChessPosition(row + i, col + i);
                    if (spot.checkInBounds()) {
                        ChessPiece collisionPiece=board.getPiece(spot);
                        if (collisionPiece == null) {
                            places.add(new ChessMove(myPosition, spot, null));
                            continue;
                            }
                        if (collisionPiece.pieceColor != movingPiece.pieceColor) {
                            places.add(new ChessMove(myPosition, spot, null));
                            break;
                            }
                        if (collisionPiece.pieceColor == movingPiece.pieceColor && i != 0) {
                            break;
                            }
                        }
                    }

                for (int i=0; i < 8; i++) {
                    ChessPosition spot=new ChessPosition(row - i, col + i);
                    if (spot.checkInBounds()) {
                        ChessPiece collisionPiece=board.getPiece(spot);
                        if (collisionPiece == null) {
                            places.add(new ChessMove(myPosition, spot, null));
                            continue;}
                        if (collisionPiece.pieceColor != movingPiece.pieceColor) {
                            places.add(new ChessMove(myPosition, spot, null));
                            break;
                        }
                        if (collisionPiece.pieceColor == movingPiece.pieceColor && i != 0) {
                            break;
                        }

                    }
                }
                for (int i=0; i < 8; i++) {
                    ChessPosition spot=new ChessPosition(row + i, col - i);
                    if (spot.checkInBounds()) {
                        ChessPiece collisionPiece=board.getPiece(spot);
                        if (collisionPiece == null) {
                            places.add(new ChessMove(myPosition, spot, null));
                            continue;}
                        if (collisionPiece.pieceColor != movingPiece.pieceColor) {
                            places.add(new ChessMove(myPosition, spot, null));
                            break;
                        }
                        if (collisionPiece.pieceColor == movingPiece.pieceColor && i != 0) {
                            break;
                        }

                    }
                }
                for (int i=0; i < 8; i++) {
                    ChessPosition spot=new ChessPosition(row - i, col - i);
                    if (spot.checkInBounds()) {
                        ChessPiece collisionPiece=board.getPiece(spot);
                        if (collisionPiece == null) {
                            places.add(new ChessMove(myPosition, spot, null));
                            continue;}
                        if (collisionPiece.pieceColor != movingPiece.pieceColor) {
                            places.add(new ChessMove(myPosition, spot, null));
                            break;
                        }
                        if (collisionPiece.pieceColor == movingPiece.pieceColor && i != 0) {
                            break;
                        }

                    }
                }

                return places;
            }
            case ROOK -> {
                int row=myPosition.getRow();
                int col=myPosition.getColumn();
                ChessPiece movingPiece=board.getPiece(myPosition);
                for (int i=0; i < 8; i++) {
                    ChessPosition spot=new ChessPosition(row + i, col);
                    if (spot.checkInBounds()) {
                        ChessPiece collisionPiece=board.getPiece(spot);
                        if (collisionPiece == null) {
                            places.add(new ChessMove(myPosition, spot, null));
                            continue;
                        }
                        if (collisionPiece.pieceColor != movingPiece.pieceColor) {
                            places.add(new ChessMove(myPosition, spot, null));
                            break;
                        }
                        if (collisionPiece.pieceColor == movingPiece.pieceColor && i != 0) {
                            break;
                        }
                    }
                }
                for (int i=0; i < 8; i++) {
                    ChessPosition spot=new ChessPosition(row - i, col);
                    if (spot.checkInBounds()) {
                        ChessPiece collisionPiece=board.getPiece(spot);
                        if (collisionPiece == null) {
                            places.add(new ChessMove(myPosition, spot, null));
                            continue;
                        }
                        if (collisionPiece.pieceColor != movingPiece.pieceColor) {
                            places.add(new ChessMove(myPosition, spot, null));
                            break;
                        }
                        if (collisionPiece.pieceColor == movingPiece.pieceColor && i != 0) {
                            break;
                        }
                    }
                }
                for (int i=0; i < 8; i++) {
                    ChessPosition spot=new ChessPosition(row, col + i);
                    if (spot.checkInBounds()) {
                        ChessPiece collisionPiece=board.getPiece(spot);
                        if (collisionPiece == null) {
                            places.add(new ChessMove(myPosition, spot, null));
                            continue;
                        }
                        if (collisionPiece.pieceColor != movingPiece.pieceColor) {
                            places.add(new ChessMove(myPosition, spot, null));
                            break;
                        }
                        if (collisionPiece.pieceColor == movingPiece.pieceColor && i != 0) {
                            break;
                        }
                    }
                }
                for (int i=0; i < 8; i++) {
                    ChessPosition spot=new ChessPosition(row, col - i);
                    if (spot.checkInBounds()) {
                        ChessPiece collisionPiece=board.getPiece(spot);
                        if (collisionPiece == null) {
                            places.add(new ChessMove(myPosition, spot, null));
                            continue;
                        }
                        if (collisionPiece.pieceColor != movingPiece.pieceColor) {
                            places.add(new ChessMove(myPosition, spot, null));
                            break;
                        }
                        if (collisionPiece.pieceColor == movingPiece.pieceColor && i != 0) {
                            break;
                        }
                    }
                }


            }
            case QUEEN -> {
                int row=myPosition.getRow();
                int col=myPosition.getColumn();
                ChessPiece movingPiece=board.getPiece(myPosition);
                for (int i=0; i < 8; i++) {
                    ChessPosition spot=new ChessPosition(row + i, col + i);
                    if (spot.checkInBounds()) {
                        ChessPiece collisionPiece=board.getPiece(spot);
                        if (collisionPiece == null) {
                            places.add(new ChessMove(myPosition, spot, null));
                            continue;
                        }
                        if (collisionPiece.pieceColor != movingPiece.pieceColor) {
                            places.add(new ChessMove(myPosition, spot, null));
                            break;
                        }
                        if (collisionPiece.pieceColor == movingPiece.pieceColor && i != 0) {
                            break;
                        }
                    }
                }

                for (int i=0; i < 8; i++) {
                    ChessPosition spot=new ChessPosition(row - i, col + i);
                    if (spot.checkInBounds()) {
                        ChessPiece collisionPiece=board.getPiece(spot);
                        if (collisionPiece == null) {
                            places.add(new ChessMove(myPosition, spot, null));
                            continue;}
                        if (collisionPiece.pieceColor != movingPiece.pieceColor) {
                            places.add(new ChessMove(myPosition, spot, null));
                            break;
                        }
                        if (collisionPiece.pieceColor == movingPiece.pieceColor && i != 0) {
                            break;
                        }

                    }
                }
                for (int i=0; i < 8; i++) {
                    ChessPosition spot=new ChessPosition(row + i, col - i);
                    if (spot.checkInBounds()) {
                        ChessPiece collisionPiece=board.getPiece(spot);
                        if (collisionPiece == null) {
                            places.add(new ChessMove(myPosition, spot, null));
                            continue;}
                        if (collisionPiece.pieceColor != movingPiece.pieceColor) {
                            places.add(new ChessMove(myPosition, spot, null));
                            break;
                        }
                        if (collisionPiece.pieceColor == movingPiece.pieceColor && i != 0) {
                            break;
                        }

                    }
                }
                for (int i=0; i < 8; i++) {
                    ChessPosition spot=new ChessPosition(row - i, col - i);
                    if (spot.checkInBounds()) {
                        ChessPiece collisionPiece=board.getPiece(spot);
                        if (collisionPiece == null) {
                            places.add(new ChessMove(myPosition, spot, null));
                            continue;}
                        if (collisionPiece.pieceColor != movingPiece.pieceColor) {
                            places.add(new ChessMove(myPosition, spot, null));
                            break;
                        }
                        if (collisionPiece.pieceColor == movingPiece.pieceColor && i != 0) {
                            break;
                        }

                    }
                }

                for (int i=0; i < 8; i++) {
                    ChessPosition spot=new ChessPosition(row + i, col);
                    if (spot.checkInBounds()) {
                        ChessPiece collisionPiece=board.getPiece(spot);
                        if (collisionPiece == null) {
                            places.add(new ChessMove(myPosition, spot, null));
                            continue;
                        }
                        if (collisionPiece.pieceColor != movingPiece.pieceColor) {
                            places.add(new ChessMove(myPosition, spot, null));
                            break;
                        }
                        if (collisionPiece.pieceColor == movingPiece.pieceColor && i != 0) {
                            break;
                        }
                    }
                }
                for (int i=0; i < 8; i++) {
                    ChessPosition spot=new ChessPosition(row - i, col);
                    if (spot.checkInBounds()) {
                        ChessPiece collisionPiece=board.getPiece(spot);
                        if (collisionPiece == null) {
                            places.add(new ChessMove(myPosition, spot, null));
                            continue;
                        }
                        if (collisionPiece.pieceColor != movingPiece.pieceColor) {
                            places.add(new ChessMove(myPosition, spot, null));
                            break;
                        }
                        if (collisionPiece.pieceColor == movingPiece.pieceColor && i != 0) {
                            break;
                        }
                    }
                }
                for (int i=0; i < 8; i++) {
                    ChessPosition spot=new ChessPosition(row, col + i);
                    if (spot.checkInBounds()) {
                        ChessPiece collisionPiece=board.getPiece(spot);
                        if (collisionPiece == null) {
                            places.add(new ChessMove(myPosition, spot, null));
                            continue;
                        }
                        if (collisionPiece.pieceColor != movingPiece.pieceColor) {
                            places.add(new ChessMove(myPosition, spot, null));
                            break;
                        }
                        if (collisionPiece.pieceColor == movingPiece.pieceColor && i != 0) {
                            break;
                        }
                    }
                }
                for (int i=0; i < 8; i++) {
                    ChessPosition spot=new ChessPosition(row, col - i);
                    if (spot.checkInBounds()) {
                        ChessPiece collisionPiece=board.getPiece(spot);
                        if (collisionPiece == null) {
                            places.add(new ChessMove(myPosition, spot, null));
                            continue;
                        }
                        if (collisionPiece.pieceColor != movingPiece.pieceColor) {
                            places.add(new ChessMove(myPosition, spot, null));
                            break;
                        }
                        if (collisionPiece.pieceColor == movingPiece.pieceColor && i != 0) {
                            break;
                        }
                    }
                }


            }
            case KNIGHT -> {
                int row=myPosition.getRow();
                int col=myPosition.getColumn();
                ChessPosition spot=new ChessPosition(row + 1, col -2);
                if (spot.checkInBounds()) {
                    ChessPiece collisionPiece=board.getPiece(spot);
                    if (collisionPiece == null || collisionPiece.pieceColor != this.pieceColor) {
                        places.add(new ChessMove(myPosition, spot, null));
                    }
                }
                spot = new ChessPosition(row + 1, col + 2);
                if (spot.checkInBounds()) {
                    ChessPiece collisionPiece=board.getPiece(spot);
                    if (collisionPiece == null || collisionPiece.pieceColor != this.pieceColor) {
                        places.add(new ChessMove(myPosition, spot, null));
                    }
                }
                spot = new ChessPosition(row + 2, col - 1);
                if (spot.checkInBounds()) {
                    ChessPiece collisionPiece=board.getPiece(spot);
                    if (collisionPiece == null || collisionPiece.pieceColor != this.pieceColor) {
                        places.add(new ChessMove(myPosition, spot, null));
                    }
                }
                spot = new ChessPosition(row + 2, col + 1);
                if (spot.checkInBounds()) {
                    ChessPiece collisionPiece=board.getPiece(spot);
                    if (collisionPiece == null || collisionPiece.pieceColor != this.pieceColor) {
                        places.add(new ChessMove(myPosition, spot, null));
                    }
                }
                spot = new ChessPosition(row - 1, col - 2);
                if (spot.checkInBounds()) {
                    ChessPiece collisionPiece=board.getPiece(spot);
                    if (collisionPiece == null || collisionPiece.pieceColor != this.pieceColor) {
                        places.add(new ChessMove(myPosition, spot, null));
                    }
                }
                spot = new ChessPosition(row - 1, col + 2);
                if (spot.checkInBounds()) {
                    ChessPiece collisionPiece=board.getPiece(spot);
                    if (collisionPiece == null || collisionPiece.pieceColor != this.pieceColor) {
                        places.add(new ChessMove(myPosition, spot, null));
                    }
                }
                spot = new ChessPosition(row - 2, col - 1);
                if (spot.checkInBounds()) {
                    ChessPiece collisionPiece=board.getPiece(spot);
                    if (collisionPiece == null || collisionPiece.pieceColor != this.pieceColor) {
                        places.add(new ChessMove(myPosition, spot, null));
                    }
                }
                spot = new ChessPosition(row - 2, col + 1);
                if (spot.checkInBounds()) {
                    ChessPiece collisionPiece=board.getPiece(spot);
                    if (collisionPiece == null || collisionPiece.pieceColor != this.pieceColor) {
                        places.add(new ChessMove(myPosition, spot, null));
                    }
                }

                return places;
            }
        }

      return places;
    }}
