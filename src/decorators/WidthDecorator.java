package decorators;

import interfaces.BlockCreator;
import sprites.Block;

/**
 * Class Name: WidthDecorator.
 */
public class WidthDecorator extends BlockCreatorDecorator {

    //member
    private int width;
    /**
     * Constructor.
     * @param creator  - block creator
     * @param propValue - the property
     */
    public WidthDecorator(BlockCreator creator, String propValue) {
        super(creator);
        this.width = Integer.parseInt(propValue);
    }

    /**
     * Function Name: create.
     * @param xpos - up left x point of block
     * @param ypos - up left y point of block
     * @return block with width
     */
    @Override
    public Block create(int xpos, int ypos) {
        Block block = super.create(xpos, ypos);
        block.setWidth(this.width);
        return block;
    }
}
