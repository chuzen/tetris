

/**
 * The TetrisModel informs the TetrisView whenever it should display a new Next
 * Piece by inserting an instance of this class into the TetrisController's
 * Queue.
 * 
 * @author John Cowgill, Baldwin Browne, Chunda Zeng
 * @version October 26, 2014
 */
public class NextTetrimino {
    
    public Tetrimino next;
    
    /**
     * Constructor just records the Tetrimino.
     * @param piece the next piece to display
     */
    public NextTetrimino(Tetrimino piece) {
        next = piece;
    }
    
}
