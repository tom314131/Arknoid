package decorators;

import backgroundandcolor.FillDrawer;
import backgroundandcolor.ImageDrawer;
import backgroundandcolor.StrokeDrawer;
import interfaces.BlockCreator;
import interfaces.Drawer;
import sprites.Block;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

/**
 * Class Name: DrawingBackgroundDecorator.
 */
public class DrawingBackgroundDecorator extends BlockCreatorDecorator {
    private boolean isFill;
    private Integer hitPoints;
    private Drawer drawer;

    /**
     * Constructor.
     * @param decorated - the block creator
     * @param propertyValue - the value of property
     * @param fill - if is already fill
     * @param hitPoints - hit points of the block
     */
    public DrawingBackgroundDecorator(BlockCreator decorated, String propertyValue, boolean fill, Integer hitPoints) {
        super(decorated);
        this.hitPoints = hitPoints;
        this.isFill = fill;
        this.drawer = this.parseDrawer(propertyValue, isFill);
    }

    /**
     * Function Name: parseDrawer.
     * @param propValue the property value
     * @param fill - if it already fill
     * @return drawer
     */
    private Drawer parseDrawer(String propValue, boolean fill) {
        Drawer result = null;
        String param;
        if (propValue.startsWith("color(RGB(") && propValue.endsWith("))")) {
            param = this.extractParam(propValue, "color(RGB(", "))");
            String[] parts = param.split(",");
            int r = Integer.parseInt(parts[0].trim());
            int g = Integer.parseInt(parts[1].trim());
            int b = Integer.parseInt(parts[2].trim());
            Color color = new Color(r, g, b);
            if (fill) {
                result = new FillDrawer(color);
            } else {
                result = new StrokeDrawer(color);
            }
        } else {
            InputStream is;
            if (propValue.startsWith("color(") && propValue.endsWith(")")) {
                param = this.extractParam(propValue, "color(", ")");
                is = null;

                Color color;
                try {
                    Field field = Color.class.getField(param);
                    color = (Color) field.get((Object) null);
                } catch (NoSuchFieldException var20) {
                    throw new RuntimeException("Unsupported color name: " + param);
                } catch (IllegalAccessException var21) {
                    throw new RuntimeException("Unsupported color name: " + param);
                }

                if (fill) {
                    result = new FillDrawer(color);
                } else {
                    result = new StrokeDrawer(color);
                }
            } else {
                if (!propValue.startsWith("image(") || !propValue.endsWith(")")) {
                    throw new RuntimeException("Unsupported definition: " + propValue);
                }

                param = this.extractParam(propValue, "image(", ")");
                if (!fill) {
                    throw new RuntimeException("Image type not supported for stroke");
                }

                is = null;

                try {
                    is = new BufferedInputStream(ClassLoader.getSystemClassLoader().getResourceAsStream(param));
                    Image image = ImageIO.read(is);
                    result = new ImageDrawer(image);
                } catch (IOException var19) {
                    throw new RuntimeException("Failed loading image: " + param, var19);
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException var18) {
                            throw new RuntimeException("Failed loading image: " + param, var18);
                        }
                    }

                }
            }
        }

        return result;
    }

    /**
     * Function Name: extractParam.
     * @param propValue - the property
     * @param prefix - the start of the string
     * @param postfix - the end of the string
     * @return the property
     */
    private String extractParam(String propValue, String prefix, String postfix) {
        return propValue.substring(prefix.length(), propValue.length() - postfix.length());
    }

    /**
     * Function Name: create.
     * @param x - the up left x point of block
     * @param y - the up left y point of block
     * @return block with his fill color if it has
     */
    public Block create(int x, int y) {
        Block b = super.create(x, y);
        if (this.isFill) {
            if (this.hitPoints == null) {
                b.setDefaultFillDrawer(this.drawer);
            } else {
                b.addFillDrawer(this.hitPoints, this.drawer);
            }
        } else if (this.hitPoints == null) {
            b.setDefaultStrokeDrawer(this.drawer);
        } else {
            b.addStrokeDrawer(this.hitPoints, this.drawer);
        }

        return b;
    }
}
