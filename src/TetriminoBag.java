

import java.util.*;

/**
 * The TetriminoBag holds one of each different Tetrimino shape, and it
 * allows these shapes to be picked out of the bag in a random order,
 * automatically refilling the bag once the last Tetrimino has been removed.
 * This ensures that the different Tetrimino shapes will be dropped into the
 * well at a consistent rate rather than totally at random.
 * 
 * @author  John Cowgill, Baldwin Browne, Chunda Zeng
 * @version September 29, 2014
 */
public class TetriminoBag {
    
    /**
     * The ArrayList for storing the different Tetriminos in the bag.
     */
    private ArrayList<Tetrimino> bag;
    
    /**
     * Constructor for a new TetriminoBag.
     */
    public TetriminoBag() {
        bag = new ArrayList();
        fillBag();
    }
    
    /**
     * Fills the bag with one of each Tetrimino shape, then shuffles the bag.
     */
    private void fillBag() {
        for(Tetrimino.TetriminoShape s : Tetrimino.TetriminoShape.values()) {
            bag.add(new Tetrimino(s));
        }
        Collections.shuffle(bag);
    }
    
    /**
     * Shows the next Tetrimino in the bag, but does not remove it.
     * @return the next Tetrimino
     */
    public Tetrimino nextTetrimino() {
        return bag.get(0);
    }
    
    /**
     * Picks the next Tetrimino out of the bag, thus removing it.
     * If this was the last Tetrimino in the bag, the bag is refilled.
     * @return the next Tetrimino
     */
    public Tetrimino pickTetrimino() {
        Tetrimino t = bag.remove(0);
        if(bag.isEmpty()) fillBag();
        return t;
    }
    
    /**
     * Prints the contents of this TetriminoBag to the console.
     */
    public void displayTetriminoBag() {
        for(Tetrimino t : bag) {
            System.out.println(t.getShape());
        }
    }
    
    /**
     * Test routine.
     */
    public static void testTetriminoBag() {
        TetriminoBag testBag = new TetriminoBag();
        System.out.println("Initial TetriminoBag contents:");
        testBag.displayTetriminoBag();
        System.out.println("Next Tetrimino in the bag is: " + testBag.nextTetrimino().getShape());
        for(int i = 1; i <= Tetrimino.TetriminoShape.values().length; i++) {
            System.out.println("Pick Tetrimino out of the bag: " + testBag.pickTetrimino().getShape());
            System.out.println("TetriminoBag contents after removing " + i + " shape(s):");
            testBag.displayTetriminoBag();
        }
    }
    
}
