package chess;
import java.util.Objects;


/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {
    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPosition chessPosition = (ChessPosition) o;
        return (row == chessPosition.row)
                && (col == chessPosition.col);
    }

    private final int row;
    private final int col;

    public ChessPosition(int row, int col) {
        this.row=row;
        this.col=col;
    }
    public boolean checkInBounds(){
        return !(row <1 || row >8 || col <1 || col >8);
    }
    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return row;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return col;
    }

    @Override
    public String toString() {
        return "[" +
                 row + "," +
                 col +
                ']';
    }
}
