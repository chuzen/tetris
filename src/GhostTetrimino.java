

/**
 * The TetrisModel informs the TetrisView whenever it should display a ghost
 * Tetrimino by inserting an instance of this class into the TetrisController's
 * Queue.
 * 
 * @author John Cowgill, Baldwin Browne, Chunda Zeng
 * @version October 26, 2014
 */
public class GhostTetrimino {

    public int row, column;
    public Integer[][] coords;
    public char symbol;
    
    /**
     * Constructor captures the necessary details about the ghost Tetrimino.
     * @param ghost_me the ghost Tetrimino to display
     * @param row the uppermost row of the Tetrimino
     * @param column the leftmost column of the Tetrimino
     */
    public GhostTetrimino(Tetrimino ghost_me, int row, int column) {
        this.row = row;
        this.column = column;
        coords = ghost_me.getCoordinates();
        symbol = Character.toLowerCase(ghost_me.getSymbol());
    }
    
}