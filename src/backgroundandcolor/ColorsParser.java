package backgroundandcolor;

import java.awt.Color;
import java.lang.reflect.Field;

/**
 * Class Name: ColorsParser.
 */
public class ColorsParser {

    /**
     * Constructor.
     */
    public ColorsParser() {
    }

    /**
     * Function Name: colorFromString.
     * Function Operation: parse color definition and return the specified color.
     * @param s - the name of color
     * @return the final color
     */
    public java.awt.Color colorFromString(String s) {
        Color color;
        try {
            Field field = Color.class.getField(s);
            color = (Color) field.get(null);
        } catch (NoSuchFieldException ex) {
            throw new RuntimeException("Illegal color name: " + s);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException("Illegal color name: " + s);
        }
        return color;
    }
}
