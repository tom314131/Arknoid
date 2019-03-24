package decorators;

import interfaces.BlockCreator;
import sprites.Block;

/**
 * Class Name: BlockCreatorDecorator.
 */
public abstract class BlockCreatorDecorator implements BlockCreator {

    //member
    private BlockCreator decorated;

    /**
     * Function Name: BlockCreatorDecorator.
     * Constructor
     * @param decorated - the decoration
     */
    public BlockCreatorDecorator(BlockCreator decorated) {
        this.decorated = decorated;
    }

    /**
     * Function Name: create.
     * @param x - the up left x point of block
     * @param y - the up left y point of block
     * @return - block with decoration
     */
    public Block create(int x, int y) {
        return this.decorated.create(x, y);
    }
}
