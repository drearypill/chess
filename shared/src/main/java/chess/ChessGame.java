package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor teamTurn;
    private ChessBoard board;

    private boolean gameOver;

    private static ChessGame staticInstance;

    public static ChessGame getStaticInstance() {
        if (staticInstance == null) {
            staticInstance = new ChessGame(); // Create a default instance if none exists
        }
        return staticInstance;
    }

    public static void updateStaticInstance(ChessGame game) {
        staticInstance = game;
    }

    public ChessGame() {
        this.teamTurn = TeamColor.WHITE;
        this.board = new ChessBoard();

        board.resetBoard();
    }

    public ChessBoard updateBoard(ChessBoard board) {
        this.board = board;
        return board;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {return this.teamTurn;}

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;}

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
            Collection<ChessMove> checkingMoves = checkPiece.pieceMoves(board, startPosition);
            for (ChessMove move : checkingMoves) {
                if (!moveWillEndangerKing(move)){
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
     * 
     */



    public boolean  moveWillEndangerKing(ChessMove move) {

        ChessPosition start = move.startPosition();
        ChessPiece startPiece = board.getPiece(start);
        ChessBoard originalBoard = new ChessBoard(board);
        board.movePiece(move);
        boolean endangersKing = isInCheck(startPiece.getTeamColor());
        board = originalBoard;
        //System.out.println(move);
        //System.out.println(endangersKing);
        return endangersKing;
    }

    public void makeMove(ChessMove move) throws InvalidMoveException {
        boolean isTeamsTurn = getTeamTurn() == board.getTeamOfSquare(move.getStartPosition());
        Collection<ChessMove> goodMoves = validMoves(move.getStartPosition());
        if (goodMoves == null) {
            throw new InvalidMoveException("No valid moves available");
        }
        boolean isValidMove = goodMoves.contains(move);

        if (isValidMove && isTeamsTurn) {
            ChessPiece pieceToMove = board.getPiece(move.getStartPosition());
            if (move.getPromotionPiece() != null) { //Change piece type if promotion is applied
                pieceToMove = new ChessPiece(pieceToMove.getTeamColor(), move.getPromotionPiece());
            }

            board.addPiece(move.getStartPosition(), null);
            board.addPiece(move.getEndPosition(), pieceToMove);
            setTeamTurn(getTeamTurn() == TeamColor.WHITE ? TeamColor.BLACK : TeamColor.WHITE);
        }
        else {
            throw new InvalidMoveException(String.format("Valid move: %b  Your Turn: %b", isValidMove, isTeamsTurn));
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */


    public boolean isInCheck(TeamColor teamColor) {
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
        ChessPiece targetPiece = board.getPiece(target);

        // Check if there is a piece at the target position
        if (targetPiece != null) {
            TeamColor targetTeam = targetPiece.getTeamColor();
            // Iterate through the board
            for (int row = 1; row <= 8; row++) {
                for (int col = 1; col <= 8; col++) {
                    ChessPosition currentPosition = new ChessPosition(row, col);
                    ChessPiece piece = board.getPiece(currentPosition);
                    if (isOpponentPiece(piece, targetTeam) && (canAttackPosition(piece, currentPosition, target))) {
                        attackingPieces.add(piece);
                    }
                }
            }
        }

        return attackingPieces;
    }

    // Helper method to check if the piece is an opponent's piece
    private boolean isOpponentPiece(ChessPiece piece, TeamColor targetTeam) {
        return piece != null && !piece.getTeamColor().equals(targetTeam);
    }

    private boolean canAttackPosition(ChessPiece piece, ChessPosition currentPosition, ChessPosition targetPosition) {
        Collection<ChessMove> validMoves = piece.pieceMoves(board, currentPosition);
        for (ChessMove move : validMoves) {
            if (move.endPosition().equals(targetPosition)) {
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

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean getGameOver() {
        return gameOver;
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

    public void toggleTeamTurn() {
        if (teamTurn == TeamColor.BLACK) {
            teamTurn = TeamColor.WHITE;
        }
        else {
            teamTurn = TeamColor.BLACK;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (!(o instanceof ChessGame chessGame)) {return false;}
        return getTeamTurn() == chessGame.getTeamTurn() && Objects.equals(getBoard(), chessGame.getBoard());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTeamTurn(), getBoard());
    }

    @Override
    public String toString() {
        return "ChessGame{" +
               "teamTurn=" + teamTurn +
               ", board=" + board +
               '}';
    }
}
