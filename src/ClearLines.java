

/**
 * The TetrisModel informs the TetrisView whenever it should clear rows of
 * blocks by inserting an instance of this class into the TetrisController's
 * Queue.
 * 
 * @author John Cowgill, Baldwin Browne, Chunda Zeng
 * @version October 26, 2014
 */
public class ClearLines {
    
    public int first_line, line_count;
    public boolean[] clear_line = new boolean[4];
    
    /**
     * Constructor captures the necessary details about the rows to clear.
     * @param first_line the first row that may be cleared
     * @param lines_to_clear bit pattern representing which rows to clear
     */
    public ClearLines(int first_line, int lines_to_clear) {
        this.first_line = first_line;
        line_count = 0;
        for(int r = 0; r < 4; r++) {
            if((lines_to_clear & (1 << r)) != 0) {
                clear_line[r] = true;
                line_count++;
            } else {
                clear_line[r] = false;
            }
        }
    }
    
}
