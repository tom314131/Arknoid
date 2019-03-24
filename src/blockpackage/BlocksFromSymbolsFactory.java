package blockpackage;

import interfaces.BlockCreator;
import sprites.Block;

import java.util.HashMap;
import java.util.Map;

/**
 * Class Name: BlocksFromSymbolsFactory.
 */
public class BlocksFromSymbolsFactory {

    //members
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * Function Name: BlocksFromSymbolsFactory.
     * constructor
     */
    public BlocksFromSymbolsFactory() {
        spacerWidths = new HashMap<String, Integer>();
        blockCreators = new HashMap<String, BlockCreator>();
    }

    /**
     * Function Name: isSpaceSymbol.
     * @param s - symbol
     * @return if it a space symbol or not
     */
    public boolean isSpaceSymbol(String s) {
        if (s.equals("-")) {
            return true;
        }
        return false;
    }

    /**
     * Function Name: isSpaceSymbol.
     * @param s - symbol
     * @return if it a block symbol or not
     */
    public boolean isBlockSymbol(String s) {
        if (s.equals("-")) {
            return false;
        }
        return true;
    }


    /**
     * Function Name: getBlock.
     * @param s    - the symbol of the block
     * @param xpos - up left x pos of the block
     * @param ypos - up left y pos of the block
     * @return the block
     */
    public Block getBlock(String s, int xpos, int ypos) {
        return this.blockCreators.get(s).create(xpos, ypos);
    }

    /**
     * Function Name: getSpaceWidth.
     * @param s - the symbol of the space
     * @return the block
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }

    /**
     * Function Name: addBlockCreator.
     * @param symbol  - symbol of a block.
     * @param creator - the block creator
     */
    public void addBlockCreator(char symbol, BlockCreator creator) {
        this.blockCreators.put(String.valueOf(symbol), creator);
    }

    /**
     * Function Name: addSpacer.
     * @param symbol - symbol of a spacer.
     * @param width  - the width of spacer
     */
    public void addSpacer(char symbol, int width) {
        this.spacerWidths.put(String.valueOf(symbol), width);
    }
}
