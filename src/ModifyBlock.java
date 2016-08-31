

import java.awt.*;

/**
 * Each time that the TetrisView performs an update(), it will create a list of
 * instances of this class to send to the BlockGridPanel representing the Tetris
 * well to update its display. Each instance represents a single block that
 * needs to be modified.
 * 
 * @author John Cowgill, Baldwin Browne, Chunda Zeng
 * @version October 26, 2014
 */
public class ModifyBlock {

    public int row, column;
    public Color fill_color, draw_color;
    
    /**
     * Constructor captures the necessary details about the block to modify.
     * @param row the row where the block is located on the grid
     * @param column the column where the block is located on the grid
     * @param fill_color the Color used to fill the block
     * @param draw_color the Color used for the outline of the block
     */
    public ModifyBlock(int row, int column, Color fill_color, Color draw_color) {
        this.row = row;
        this.column = column;
        this.fill_color = fill_color;
        this.draw_color = draw_color;
    }

}
