
/**
 * The TetrisModel informs the TetrisView whenever it should display a Tetrimino
 * by inserting an instance of this class into the TetrisController's Queue.
 * 
 * @author John Cowgill, Baldwin Browne, Chunda Zeng
 * @version October 26, 2014
 */
public class DisplayTetrimino {

    public int row, column;
    public Integer[][] coords;
    public char symbol;
    
    /**
     * Constructor captures the necessary details about the Tetrimino to display.
     * @param display_me the Tetrimino to display
     * @param row the uppermost row of the Tetrimino
     * @param column the leftmost column of the Tetrimino
     */
    public DisplayTetrimino(Tetrimino display_me, int row, int column) {
        this.row = row;
        this.column = column;
        coords = display_me.getCoordinates();
        symbol = display_me.getSymbol();
    }
    
}