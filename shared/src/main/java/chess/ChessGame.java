package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor TeamTurn;
    private ChessBoard board;
    public ChessGame() {
        this.board = new ChessBoard();
        this.TeamTurn = TeamColor.WHITE;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return this.TeamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.TeamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece umpiece = board.getPiece(startPosition);
        ArrayList<ChessMove> validMoves = new ArrayList<>();

        if (umpiece == null) {
            return null;
        }
        else if (umpiece != null) {
            Collection<ChessMove> movesToCheck = umpiece.pieceMoves(board, startPosition);
        }


        throw new RuntimeException("Not implemented");
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {

        throw new InvalidMoveException("Invalid move");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    private Collection<ChessPiece> getTeamPieces(TeamColor teamColor) {
        ArrayList<ChessPiece> pieces = new ArrayList<>();

        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                ChessPiece piece = board.getPiece(new ChessPosition(row, col));
                if (piece != null && piece.getTeamColor() == teamColor) {
                    pieces.add(piece);
                }
            }
        }

        return pieces;
    }
    public boolean isInCheck(TeamColor teamColor) {
        TeamColor opposingTeam = TeamColor.WHITE;
        if (teamColor == TeamColor.WHITE) {
            opposingTeam = TeamColor.BLACK;
        }

        // get all our remaining pieces
        Collection<ChessPiece> ourPieces = getTeamPieces(teamColor);

        // search pieces for king
        ChessPiece ourKing = null;
        for (ChessPiece piece : ourPieces) {
            if (piece.getPieceType() == ChessPiece.PieceType.KING) {
                ourKing = piece;
                break;
            }
        }

        if (ourKing == null) {
            return false;
        }

        ChessPosition ourKingPosition = board.getPiecePosition(ourKing);
        // check if the king is under attack

        return !attackersAtSpace(ourKingPosition).isEmpty();
    }
    private Collection<ChessPiece> attackersAtSpace(ChessPosition target) {
        TeamColor targetTeam = board.getPiece(target).getTeamColor();
        Collection<ChessPiece> attackingPieces = new ArrayList<>();

        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition currentPosition = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(currentPosition);
                if (piece != null && !piece.getTeamColor().equals(targetTeam)) {
                    if (canAttackPosition(piece, currentPosition, target)) {
                        attackingPieces.add(piece);
                    }
                }
            }
        }

        return attackingPieces;
    }

    private boolean canAttackPosition(ChessPiece piece, ChessPosition currentPosition, ChessPosition targetPosition) {
        Collection<ChessMove> validMoves = piece.pieceMoves(board, currentPosition);
        for (ChessMove move : validMoves) {
            if (move.getEndPosition().equals(targetPosition)) {
                return true;
            }
        }
        return false;
    }
    private void toggleTeamTurn() {
        if (TeamTurn == TeamColor.BLACK) {
            TeamTurn = TeamColor.WHITE;
        }
        else {
            TeamTurn = TeamColor.BLACK;
        }
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;

    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.board;
    }
}
