package interfaces;

import sprites.Block;

/**
 * Interface Name:BlockCreator.
 */
public interface BlockCreator {
    /**
     * Function Name: create.
     * Function Operation: Create a block at the specified location.
     * @param xpos - x position of up left point of block
     * @param ypos - y position of up left point of block
     * @return block
     */
    Block create(int xpos, int ypos);
}
