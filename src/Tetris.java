
/**
 * The classic Tetris game implemented in Java.
 * 
 * @author John Cowgill, Baldwin Browne, Chunda Zeng
 * @version October 26, 2014
 */
public class Tetris {
    
    /**
     * These constants set the size for the Tetris playing area.
     * A width of 10 and a height of 20 are the standard values.
     */
    public static final int TETRIS_WIDTH = 10;
    public static final int TETRIS_HEIGHT = 20;

    /**
     * Spawn our Tetris application by creating a new TetrisController.
     * @param args unused command line arguments
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                new TetrisController();
            }
            
        });
    }
    
}
