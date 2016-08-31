

/**
 * The TetrisModel informs the TetrisView whenever it should highlight rows of
 * blocks by inserting an instance of this class into the TetrisController's
 * Queue.
 * 
 * @author John Cowgill, Baldwin Browne, Chunda Zeng
 * @version October 26, 2014
 */
public class HighlightLines {
    
    public int first_line;
    public boolean[] highlight_line = new boolean[4];
    
    /**
     * Constructor captures the necessary details about the rows to highlight.
     * @param first_line the first row that may be highlighted
     * @param lines_to_highlight bit pattern representing which rows to highlight
     */
    public HighlightLines(int first_line, int lines_to_highlight) {
        this.first_line = first_line;
        for(int r = 0; r < 4; r++) {
            if((lines_to_highlight & (1 << r)) != 0) {
                highlight_line[r] = true;
            } else {
                highlight_line[r] = false;
            }
        }
    }
    
}
