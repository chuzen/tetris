
/**
 * The TetrisModel informs the TetrisView whenever it should erase a Tetrimino
 * by inserting an instance of this class into the TetrisController's Queue.
 * 
 * @author John Cowgill, Baldwin Browne, Chunda Zeng
 * @version October 26, 2014
 */
public class EraseTetrimino {

    public int row, column;
    public Integer[][] coords;
    
    /**
     * Constructor captures the necessary details about the Tetrimino to erase.
     * @param erase_me the Tetrimino to erase
     * @param row the uppermost row of the Tetrimino
     * @param column the leftmost column of the Tetrimino
     */
    public EraseTetrimino(Tetrimino erase_me, int row, int column) {
        this.row = row;
        this.column = column;
        coords = erase_me.getCoordinates();
    }
    
}