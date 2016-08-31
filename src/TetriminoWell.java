
import java.util.*;

/**
 * The TetriminoWell class is used by the TetrisModel to keep track of all the
 * blocks in the Tetris game. It will check for collisions when attempting to
 * move or rotate a Tetrimino within the well, check to see if a row of blocks
 * has been filled, and also indicate if the well has overflowed, meaning that
 * the game is over.
 * 
 * @author  John Cowgill, Baldwin Browne, Chunda Zeng
 * @version September 29, 2014
 */
public class TetriminoWell {
    
    /**
     * Constants for determining the width and height of the well.
     * There are two extra columns added to each side. There are four extra rows
     * added at the top, and two extra rows added at the bottom. These extra
     * rows and columns are used for checking when a Tetrimino is rotated or 
     * moved near the sides or bottom of the well, and when a new Tetrimino is
     * introduced at the top of the well.
     */
    public static final int WELL_WIDTH = Tetris.TETRIS_WIDTH + 2 + 2;
    public static final int WELL_HEIGHT = Tetris.TETRIS_HEIGHT + 4 + 2;
    
    /**
     * This list keeps track of all the rows and columns within the well.
     * The character stored at each row/column is a symbol representing what
     * occupies that position within the well.
     * A ' ' character indicates the position is empty.
     * A 'X' character indicates the position is outside of the well.
     * Any other character means the position is occupied by a block.
     */
    List<Character[]> well;
    
    /**
     * Constructor for a new TetriminoWell.
     */
    public TetriminoWell() {
        well = new LinkedList<Character[]>();
        for(int r = 0; r < WELL_HEIGHT; r++) {
            addNewRow(r);
        }
    }
    
    /**
     * Adds a new row to the well.
     * @param r the index of the new row to add
     */
    private void addNewRow(int r) {
        Character[] row = new Character[WELL_WIDTH];
        for(int c = 0; c < WELL_WIDTH; c++) {
            char symbol = ' ';
            if(r >= WELL_HEIGHT - 2 || c < 2 || c >= WELL_WIDTH - 2) symbol = 'X';
            row[c] = symbol;
        }
        well.add(r, row);
    }
    
    /**
     * Check to see if the given Tetrimino can occupy the desired location in
     * the well, or if it will overlap with an existing block inside the well.
     * @param t the Tetrimino to check
     * @param row the uppermost row of the desired location
     * @param column the leftmost column of the desired location
     * @return true if a collision with another block occurs, false otherwise
     */
    public boolean checkCollision(Tetrimino t, int row, int column) {
        // The TetrisModel doesn't know about the two extra columns added,
        // so a small adjustment is needed here to accomodate for this.
        column += 2;
        // Check to see if the location is outside of the well
        if(row < 0 || column < 0 || row + 4 > WELL_HEIGHT || column + 4 > WELL_WIDTH) {
            return true;
        }
        Integer[][] coords = t.getCoordinates();
        // Check the four blocks of the Tetrimino to see if there is a collision
        for(int i = 0; i < 4; i++) {
            if(well.get(row + coords[i][0])[column + coords[i][1]] != ' ') {
                return true;
            }
        }
        return false; // No collision detected
    }
    
    /**
     * Check to see if the indicated row is filled with blocks.
     * @param row the row to check
     * @return true if the row is filled with blocks, false otherwise
     */
    private boolean rowFilled(int row) {
        if(row >= WELL_HEIGHT - 2) return false;
        for(int c = 2; c < WELL_WIDTH - 2; c++) {
            if(well.get(row)[c] == ' ') return false;
        }
        return true;
    }
    
    /**
     * Check to see if the well has overflowed.
     * The top four rows are not really part of the well. They are the region
     * where new Tetriminos are introduced. If a block has settled in any of the
     * top four rows then this means the well has overflowed. Since the well is
     * filled from the bottom upwards, only the row directly above the top of
     * the well needs to be checked. This is row 3. This method basically checks
     * to see if there are any blocks in row 3. If so, the well has overflowed.
     * @return true if the well has overflowed, false otherwise
     */
    public boolean overflow() {
        for(int c = 2; c < WELL_WIDTH - 2; c++) {
            if(well.get(3)[c] != ' ') return true;
        }
        return false;
    }
    
    /**
     * This method takes the given Tetrimino and settles its blocks into the
     * well at the indicated row and column, and returns a value indicating if
     * any rows were cleared as a result. The precondition is that a prior call
     * to checkCollision() has returned false for this Tetrimino at the same
     * row and column, and that the Tetrimino cannot move any further down in
     * the well. These requirements should be checked by the TetrisModel.
     * @param t the Tetrimino to be settled into the well
     * @param row the uppermost row of the desired location
     * @param column the leftmost column of the desired location
     * @return the bit pattern of rows cleared
     */
    public int settleTetrimino(Tetrimino t, int row, int column) {
        // The TetrisModel doesn't know about the two extra columns added,
        // so a small adjustment is needed here to accomodate for this.
        column += 2;
        int rows_cleared = 0;
        int new_rows_to_add = 0;
        Integer[][] coords = t.getCoordinates();
        // Copy the symbol for this Tetrimino into the well
        // where the four blocks of the Tetrimino are located
        for(int i = 0; i < 4; i++) {
            well.get(row + coords[i][0])[column + coords[i][1]] = t.getSymbol();
        }
        // Check the four rows that were affected and see if any were filled
        for(int r = 3; r >= 0; r--) {
            if(rowFilled(row + r)) {
                rows_cleared |= 1 << r; // Record a bit for this row
                new_rows_to_add++;
                well.remove(row + r); // Remove the filled row
            }
        }
        // Up at the top of the well, add back in any rows that were cleared
        for(int i = 0; i < new_rows_to_add; i++) {
            addNewRow(0);
        }
        return rows_cleared;
    }
  
}
