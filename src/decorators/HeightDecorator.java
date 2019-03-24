package decorators;

import interfaces.BlockCreator;
import sprites.Block;

/**
 * Class Name: HeightDecorator.
 */
public class HeightDecorator extends BlockCreatorDecorator {

    //member
    private int height;

    /**
     * Constructor.
     * @param creator  - block creator
     * @param propValue - the property
     */
    public HeightDecorator(BlockCreator creator, String propValue) {
        super(creator);
        this.height = Integer.parseInt(propValue);
    }

    /**
     * Function Name: create.
     * @param xpos - up left x point of block
     * @param ypos - up left y point of block
     * @return block with height
     */
    @Override
    public Block create(int xpos, int ypos) {
        Block block = super.create(xpos, ypos);
        block.setHeight(this.height);
        return block;
    }
}
