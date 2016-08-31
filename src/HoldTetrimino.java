
/**
 * The TetrisModel informs the TetrisView whenever it should display a new Hold
 * Piece by inserting an instance of this class into the TetrisController's
 * Queue.
 * 
 * @author John Cowgill, Baldwin Browne, Chunda Zeng
 * @version October 26, 2014
 */
public class HoldTetrimino {
    
    public Tetrimino hold;
    
    /**
     * Constructor just records the Tetrimino.
     * @param piece the hold piece to display
     */
    public HoldTetrimino(Tetrimino piece) {
        hold = piece;
    }
    
}