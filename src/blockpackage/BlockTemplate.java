package blockpackage;

import interfaces.BlockCreator;
import sprites.Block;

/**
 * Class Name: BlockTemplate.
 */
public class BlockTemplate implements BlockCreator {

    /**
     * Function Name: BlockTemplate.
     * Constructor.
     */
    public BlockTemplate() {

    }

    /**
     * Function Name: create.
     * @param xpos - x position of up left point of block
     * @param ypos - y position of up left point of block
     * @return block
     */
    @Override
    public Block create(int xpos, int ypos) {
        return new Block(xpos, ypos);
    }
}
