package decorators;

import interfaces.BlockCreator;
import sprites.Block;

/**
 * Class Name: HitPointsDecorator.
 */
public class HitPointsDecorator extends BlockCreatorDecorator {
    //member
    private int hitPoints;

    /**
     * Function Name: HitPointsDecorator.
     * @param creator - block creator
     * @param propValue - the property
     */
    public HitPointsDecorator(BlockCreator creator, String propValue) {
        super(creator);
        this.hitPoints = Integer.parseInt(propValue);
    }

    /**
     * Function Name: create.
     * Function Operation: add hit points to block
     * @param xpos - x position of block
     * @param ypos - y position of block
     * @return updated block with hit points
     */
    @Override
    public Block create(int xpos, int ypos) {
        Block block = super.create(xpos, ypos);
        block.setHitNum(this.hitPoints);
        return block;
    }
}
