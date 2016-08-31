

import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.util.LinkedList;

/**
 * This class creates a customized JPanel designed to display a grid of blocks.
 * 
 * @author John Cowgill, Baldwin Browne, Chunda Zeng
 * @version October 26, 2014
 */
public class BlockGridPanel extends JPanel {
    
    /**
     * This private class is used for keeping track of the blocks on the grid.
     */
    private class Block {

        public Color fill_color, draw_color;
        
        /**
         * Constructor captures the Color details for this particular block.
         * @param fill_color the Color used to fill the block
         * @param draw_color the Color used to draw the outline of the block
         */
        public Block(Color fill_color, Color draw_color) {
            this.fill_color = fill_color;
            this.draw_color = draw_color;
        }

    }
    
    /**
     * Private variables for the size of the grid and the size of each block.
     */
    private int rows, columns, block_size;
    
    /**
     * This list holds the rows of blocks on the grid, and each row is an array
     * of type Block representing the columns within that row.
     */
    private List<Block[]> blocks;
    
    /**
     * Constructor for a new BlockGridPanel.
     * @param rows the number of rows on the grid
     * @param columns the number of columns on the grid
     * @param block_size the size (width and height) of each block on the grid
     */
    public BlockGridPanel(int rows, int columns, int block_size) {
        super();
        // Check for valid parameters
        if(rows < 1 || columns < 1 || block_size < 1) {
            throw new IllegalArgumentException("Invalid argument!");
        }
        this.rows = 0; // This value will be set by addNewRow below
        this.columns = columns;
        this.block_size = block_size;
        blocks = new LinkedList<Block[]>(); // Create the list of rows
        for(int r = 0; r < rows; r++) {
            addNewRow(r, null, null); // Add all of the rows to the list
        }
    }
    
    /**
     * Used to add a new row to the list of blocks on the grid.
     * @param r the index of the new row to add
     * @param fill_color the Color to use to fill the blocks in the new row
     * @param draw_color the Color to use to draw the outline for each block
     */
    public void addNewRow(int r, Color fill_color, Color draw_color) {
        // Check for valid parameter
        if(r < 0 || r > rows) {
            throw new IllegalArgumentException("Invalid argument!");
        }
        Block[] row = new Block[columns];
        for(int c = 0; c < columns; c++) {
            row[c] = new Block(fill_color, draw_color);
        }
        blocks.add(r, row);
        rows++;
    }
    
    /**
     * Used to remove an existing row from the list of blocks on the grid.
     * @param r the index of the row to remove
     */
    public void removeRow(int r) {
        // Check for valid parameter
        if(r < 0 || r >= rows) {
            throw new IllegalArgumentException("Invalid argument!");
        }
        blocks.remove(r);
        rows--;
    }
    
    /**
     * Get the number of rows on the grid.
     * @return the number of rows
     */
    public int getRows() {
        return rows;
    }
    
    /**
     * Get the number of columns on the grid.
     * @return the number of columns
     */
    public int getColumns() {
        return columns;
    }
    
    /**
     * Get the size of the blocks on the grid.
     * @return the block size
     */
    public int getBlockSize() {
        return block_size;
    }
    
    /**
     * Get the fill color for a particular block on the grid.
     * @param r the row of the block to check
     * @param c the column of the block to check
     * @return the fill color for the block
     */
    public Color getBlockFillColor(int r, int c) {
        // Check for valid parameters
        if(r < 0 || r >= rows || c < 0 || c >= columns) {
            throw new IllegalArgumentException("Invalid argument!");
        }
        return blocks.get(r)[c].fill_color;
    }
    
    /**
     * Get the outline color for a particular block on the grid.
     * @param r the row of the block to check
     * @param c the column of the block to check
     * @return the outline color for the block
     */
    public Color getBlockDrawColor(int r, int c) {
        // Check for valid parameters
        if(r < 0 || r >= rows || c < 0 || c >= columns) {
            throw new IllegalArgumentException("Invalid argument!");
        }
        return blocks.get(r)[c].draw_color;
    }
    
    /**
     * Change the colors for a particular block on the grid.
     * @param r the row of the block to change
     * @param c the column of the block to change
     * @param fill_color the new fill Color for the block
     * @param draw_color the new outline Color for the block
     */
    public void paintBlock(int r, int c, Color fill_color, Color draw_color) {
        // Check for valid parameters
        if(r < 0 || r >= rows || c < 0 || c >= columns) {
            throw new IllegalArgumentException("Invalid argument!");
        }
        blocks.get(r)[c].fill_color = fill_color;
        blocks.get(r)[c].draw_color = draw_color;
        repaint(); // Redraw the entire block grid to reflect the changes
    }
    
    /**
     * Change the colors of multiple blocks on the grid using a list of
     * ModifyBlock objects. This method is more efficient than calling the
     * previous paintBlock() method repeatedly, because it will change all of
     * the desired blocks and then call repaint() only once instead of once
     * for each block that needs to be changed.
     * @param list the list of blocks that need to be changed
     */
    public void paintBlockList(List<ModifyBlock> list) {
        for(ModifyBlock b : list) {
            blocks.get(b.row)[b.column].fill_color = b.fill_color;
            blocks.get(b.row)[b.column].draw_color = b.draw_color;
        }
        repaint(); // Redraw the entire block grid to reflect the changes
    }
    
    /**
     * Sets all of the blocks on the grid to the specified colors.
     * @param fill_color the Color to fill all of the blocks with
     * @param draw_color the Color to draw the outline of all the blocks with
     */
    public void clearBlockGrid(Color fill_color, Color draw_color) {
        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < columns; c++) {
                blocks.get(r)[c].fill_color = fill_color;
                blocks.get(r)[c].draw_color = draw_color;
            }
        }
        repaint(); // Redraw the entire block grid to reflect the changes
    }
    
    /**
     * This overrides the paintComponent method in JPanel so that the blocks on
     * the grid will be displayed each time repaint() is called.
     * @param g the Graphics component to use
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < columns; c++) {
                Block b = blocks.get(r)[c];
                if(b.fill_color != null) { // Do not fill a block if the Color is null
                    g.setColor(b.fill_color);
                    g.fillRect(c * block_size, r * block_size, block_size, block_size);
                }
                if(b.draw_color != null) { // Do not draw a block if the Color is null
                    g.setColor(b.draw_color);
                    g.drawRect(c * block_size, r * block_size, block_size, block_size);
                }
            }
        }
    }
    
    /**
     * This overrides the getPreferredSize() method in JPanel so that the size
     * of the panel created will be large enough to display the entire grid of
     * blocks.
     * @return the Dimension necessary to display the block grid properly
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(columns * block_size, rows * block_size);
    }

}
