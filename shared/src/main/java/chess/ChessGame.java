package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

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
        board.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {return this.TeamTurn;}

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {TeamTurn = team;}

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
        ChessPiece checkPiece = board.getPiece(startPosition);
        ArrayList<ChessMove> validMoves = new ArrayList<>();

        // Skip empty pieces
        if (checkPiece != null) {
            Collection<ChessMove> movesToCheck = checkPiece.pieceMoves(board, startPosition);

            // Remove any that endanger the king
            for (ChessMove move : movesToCheck) {
                if (moveWillEndangerKing(move)){
                    validMoves.add(move);
                }
            }
        }

        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */



    public boolean  moveWillEndangerKing(ChessMove move) {

        ChessPosition start = move.getStartPosition();
        ChessPosition end = move.getEndPosition();
        ChessPiece startPiece = board.getPiece(start);
        ChessBoard originalBoard = new ChessBoard(board);
        board.movePiece(move);
        boolean endangersKing = isInCheck(startPiece.getTeamColor());
        board = originalBoard;

        return endangersKing;
    }

    public void makeMove(ChessMove move) throws InvalidMoveException {

        // Check if starting piece exists
        if (board.getPiece(move.getStartPosition()) == null) {
            throw new InvalidMoveException("Invalid move, no valid piece found");
        }

        // Check if it's the current teams turn
        if (board.getPiece(move.getStartPosition()).getTeamColor() != TeamTurn) {
            throw new InvalidMoveException("Invalid move, wait your turn!");
        }

        // Before any move can be made, we have to make sure the king is not in check
        if (isInCheck(board.getPiece(move.getStartPosition()).getTeamColor())) {
            // Check if the new move is going to remove check
            if (!moveWillEndangerKing(move)) {
                throw new InvalidMoveException("Your king is in check, you must resolve that!");
            }
        }

        ArrayList<ChessMove> possibleMoves = new ArrayList<>(validMoves(move.getStartPosition()));

        // Check if the desired move is valid
        if (!possibleMoves.contains(move)) {
            throw new InvalidMoveException("Invalid move: " + move);
        }

        // Check if moving will endanger the king
        if (moveWillEndangerKing(move)) {
            throw new InvalidMoveException("That move puts your king in check!");
        }

        // Do move here
        board.movePiece(move);

        // Change turn
        toggleTeamTurn();
    }


    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */


    public boolean isInCheck(TeamColor teamColor) {
        if (teamColor == TeamColor.BLACK) {
            TeamColor opposingTeam = TeamColor.WHITE;
        }
        if (teamColor == TeamColor.WHITE) {
            TeamColor opposingTeam = TeamColor.BLACK;
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
        Collection<ChessPiece> attackingPieces = new ArrayList<>();
        if (board.getPiece(target) != null) {
            TeamColor targetTeam = board.getPiece(target).getTeamColor();


            for (int row = 0; row <= 7; row++) {
                for (int col = 0; col <= 7; col++) {
                    ChessPosition currentPosition = new ChessPosition(row, col);
                    ChessPiece piece = board.getPiece(currentPosition);
                    if (piece != null && !piece.getTeamColor().equals(targetTeam)) {
                        if (canAttackPosition(piece, currentPosition, target)) {
                            attackingPieces.add(piece);
                        }
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


    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {

        // If king isn't in check, no checkmate
        if (!isInCheck(teamColor)) {
            return false;
        }

        // Get a list of all our teams moves
        Collection<ChessMove> validTeamMoves = getValidTeamMoves(teamColor);

        // Check if we have any moves that can save us
        for (ChessMove move : validTeamMoves) {
            if (!moveWillEndangerKing(move)){
                return false;
            }
        }

        // If no moves are found, checkmate
        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (isInCheck(teamColor)) {
            return false;
        }
        Collection<ChessMove> validTeamMoves = getValidTeamMoves(teamColor);
        return validTeamMoves.isEmpty();
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
        return board;
    }

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
    private Collection<ChessMove> getValidTeamMoves(TeamColor teamColor) {
        ArrayList<ChessMove> validMoves = new ArrayList<>();

        Collection<ChessPiece> pieces = getTeamPieces(teamColor);

        for (ChessPiece piece : pieces) {
            validMoves.addAll(validMoves(board.getPiecePosition(piece)));
        }

        return validMoves;
    }

    private void toggleTeamTurn() {
        if (TeamTurn == TeamColor.BLACK) {
            TeamTurn = TeamColor.WHITE;
        }
        else {
            TeamTurn = TeamColor.BLACK;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessGame chessGame)) return false;
        return getTeamTurn() == chessGame.getTeamTurn() && Objects.equals(getBoard(), chessGame.getBoard());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTeamTurn(), getBoard());
    }
}
