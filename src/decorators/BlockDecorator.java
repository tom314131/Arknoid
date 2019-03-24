package decorators;

import interfaces.BlockCreator;

/**
 * Class Name: BlockDecorator.
 */
public class BlockDecorator  {

    /**
     * Constructor.
     */
    public BlockDecorator() {

    }

    /**
     * Function Name: decorate.
     * @param creator - block creator
     * @param propKey - the property that we want to decorate
     * @param propValue - the value of the property that we decorate
     * @return decorated block creator
     */
    public BlockCreator decorate(BlockCreator creator, String propKey, String propValue) {
        if ("height".equals(propKey)) {
            return new HeightDecorator(creator, propValue);
        } else if ("width".equals(propKey)) {
            return new WidthDecorator(creator, propValue);
        } else if ("hit_points".equals(propKey)) {
            return new HitPointsDecorator(creator, propValue);
        } else if (!propKey.startsWith("fill") && !propKey.startsWith("stroke")) {
            throw new RuntimeException("Unsupported property: " + propKey + " with value:" + propValue);
        } else {
            Integer hitPointsValue = null;
            boolean isFill = propKey.startsWith("fill");
            int dividerIndex = propKey.indexOf("-");
            if (dividerIndex != -1) {
                hitPointsValue = Integer.parseInt(propKey.substring(dividerIndex + 1));
            }
            return new DrawingBackgroundDecorator(creator, propValue, isFill, hitPointsValue);
        }
    }
}
