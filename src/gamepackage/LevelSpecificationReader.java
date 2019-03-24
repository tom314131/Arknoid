package gamepackage;

import backgroundandcolor.BackgroundImage;
import backgroundandcolor.ColorsParser;
import backgroundandcolor.SingleColorBackground;
import blockpackage.BlocksDefinitionReader;
import blockpackage.BlocksFromSymbolsFactory;
import geomitryprimitives.Velocity;
import interfaces.LevelInformation;
import interfaces.Sprite;
import sprites.Block;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.LineNumberReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Class Name: LevelSpecificationReader.
 */
public class LevelSpecificationReader implements LevelInformation {

    //members
    private String levelName;
    private List<Velocity> velocities = new ArrayList<>();
    private int paddleSpeed;
    private int paddleWidth;
    private Sprite background;
    private List<Block> blocks = new ArrayList<Block>();
    private int ballsToClear;

    /**
     * Function Name: fromFile.
     *
     * @param fileName - the file we read from
     * @return list of level information
     * @throws IOException if cannot read properly
     */
    public List<LevelInformation> fromFile(String fileName) throws IOException {
        Reader fileReader = null;
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
        fileReader = new InputStreamReader(is);
        return fromReader(fileReader);
    }

    /**
     * Function Name: fromReader.
     *
     * @param reader - the file we read with
     * @return list of level information
     * @throws IOException if cannot read properly
     */
    public List<LevelInformation> fromReader(Reader reader) throws IOException {
        List<LevelInformation> levels = new ArrayList<LevelInformation>();
        Map<String, BlocksFromSymbolsFactory> fileDefinitions = new HashMap<String, BlocksFromSymbolsFactory>();
        LevelSpecificationReader currentLevel = null;
        LineNumberReader lineReader = null;
        BlocksFromSymbolsFactory bfsf = null;
        Integer levelRowHeight = null;
        Integer levelRowIndex = null;
        Integer xStartBlock = null;
        Integer yStartBlock = null;
        boolean readingBlocks = false;
        String line;
        try {
            lineReader = new LineNumberReader(reader);
            while (true) {
                while (true) {
                    do {
                        do {
                            line = lineReader.readLine();
                                if (line == null) {
                                    return levels;
                                }
                            line = line.trim();
                        } while ("".equals(line));
                    } while (line.startsWith("#"));

                    if (!readingBlocks) {
                        if (line.equals("START_LEVEL")) {
                            currentLevel = new LevelSpecificationReader();
                        } else if ("END_LEVEL".equals(line)) {
                            levels.add(currentLevel);
                            currentLevel = null;
                            bfsf = null;
                            levelRowHeight = null;
                            levelRowIndex = null;
                            xStartBlock = null;
                            yStartBlock = null;
                        } else if (line.equals("START_BLOCKS")) {
                            levelRowIndex = 0;
                            readingBlocks = true;
                        } else {
                            String[] parts = line.split(":");
                            String key = parts[0];
                            String value = parts[1];
                            if ("block_definitions".equals(key)) {
                                if (fileDefinitions.containsKey(value)) {
                                    bfsf = fileDefinitions.get(value);
                                } else {
                                    try {
                                        bfsf = BlocksDefinitionReader.fromFile(value);
                                    } catch (Exception ex) {
                                        throw new RuntimeException("Failed loading block definitions from: " + value);
                                    }
                                    fileDefinitions.put(value, bfsf);
                                }
                            } else if (key.equals("row_height")) {
                                levelRowHeight = Integer.parseInt(value);
                            } else if (key.equals("blocks_start_x")) {
                                xStartBlock = Integer.parseInt(value);
                            } else if (key.equals("blocks_start_y")) {
                                yStartBlock = Integer.parseInt(value);
                            } else {
                                setLevelProperty(currentLevel, key, value);
                            }
                        }
                    } else if ("END_BLOCKS".equals(line)) {
                        readingBlocks = false;
                    } else {
                        int currentX = xStartBlock;
                        for (int i = 0; i < line.length(); ++i) {
                            char symbol = line.charAt(i);
                            int currentY = levelRowIndex * levelRowHeight + yStartBlock;
                            if (bfsf.isSpaceSymbol(String.valueOf(symbol))) {
                                currentX += bfsf.getSpaceWidth(String.valueOf(symbol));
                            } else {
                                Block b = bfsf.getBlock(String.valueOf(symbol), currentX, currentY);
                                if (b == null) {
                                    throw new RuntimeException("Failed creating block of type:" + symbol);
                                }
                                currentX = (int) ((double) currentX + b.getCollisionRectangle().getWidth());
                                currentLevel.addBlock(b);
                            }
                        }

                        levelRowIndex = levelRowIndex + 1;
                    }
                }

            }
        } finally {
            if (lineReader != null) {
                lineReader.close();
            }
        }
    }


    /**
     * Function Name: setLevelProperty.
     *
     * @param currentLevel - the current level
     * @param key          - the key of property
     * @param value        - the value of property
     */
    private static void setLevelProperty(LevelSpecificationReader currentLevel, String key, String value) {
        if (key.equals("level_name")) {
            currentLevel.levelName = value;
        } else {
            if (key.equals("background")) {
                if (value.startsWith("color(RGB(") && value.endsWith("))")) {
                    String param = extractParam(value, "color(RGB(", "))");
                    String[] colors = param.split(",");
                    int color1 = Integer.parseInt(colors[0].trim());
                    int color2 = Integer.parseInt(colors[1].trim());
                    int color3 = Integer.parseInt(colors[2].trim());
                    Color color = new Color(color1, color2, color3);
                    currentLevel.setBackground(new SingleColorBackground(color));

                } else if (value.startsWith("color(") && value.endsWith(")")) {
                    String prefix = "color(";
                    String col = value.substring(prefix.length(), value.length() - 1);
                    currentLevel.setBackground(new SingleColorBackground(new ColorsParser().colorFromString(col)));
                } else if (value.startsWith("image(") && value.endsWith(")")) {
                    String prefix = "image(";
                    //String imageName = value.substring(prefix.length(), value.length()-1);
                    String param = extractParam(value, "image(", ")");
                    InputStream is = null;
                    try {
                        is = new BufferedInputStream(
                                ClassLoader.getSystemClassLoader().getResourceAsStream(param));
                        BufferedImage image = ImageIO.read(is);
                        currentLevel.setBackground(new BackgroundImage(image));
                    } catch (IOException ex) {
                        throw new RuntimeException("Failed loading image: " + param);
                    } finally {
                        if (is != null) {
                            try {
                                is.close();
                            } catch (IOException ex) {
                                throw new RuntimeException("Failed loading image: " + param);
                            }
                        }

                    }
                }

            } else if (key.equals("paddle_speed")) {
                currentLevel.setPaddleSpeed(Integer.parseInt(value));
            } else if (key.equals("paddle_width")) {
                currentLevel.setPaddleWidth(Integer.parseInt(value));
            } else if (key.equals("ball_velocities")) {
                String[] ballVelocities = value.split(" ");
                for (int i = 0; i < ballVelocities.length; i++) {
                    String[] angleAndSpeed = ballVelocities[i].split(",");
                    currentLevel.addVelocity(Velocity.fromAngleAndSpeed(Integer.parseInt(angleAndSpeed[0]),
                            Integer.parseInt(angleAndSpeed[1])));
                }
            }
        }
    }

    /**
     * Function Name: extractParam.
     *
     * @param propValue - the property.
     * @param prefix    - the start of the string
     * @param postfix   - the end of string
     * @return the parameter
     */
    private static String extractParam(String propValue, String prefix, String postfix) {
        return propValue.substring(prefix.length(), propValue.length() - postfix.length());
    }

    /**
     * Function Name: setBackground.
     *
     * @param backgroundSprite - the background
     */
    public void setBackground(Sprite backgroundSprite) {
        this.background = backgroundSprite;
    }

    /**
     * Function Name: setNumberOfBlocksToRemove.
     *
     * @param numberOfBlocksToClear - blocks to clear.
     */
    public void setNumberOfBlocksToRemove(int numberOfBlocksToClear) {
        this.ballsToClear = numberOfBlocksToClear;
    }

    /**
     * Function Name: addBlock.
     *
     * @param block - block
     */
    public void addBlock(Block block) {
        this.blocks.add(block);
    }

    /**
     * Function Name: addVelocity.
     *
     * @param velocity - velocity
     */
    public void addVelocity(Velocity velocity) {
        this.velocities.add(velocity);
    }

    /**
     * Function Name: setPaddleSpeed.
     *
     * @param paddlespeed - paddle speed
     */
    public void setPaddleSpeed(int paddlespeed) {
        this.paddleSpeed = paddlespeed;
    }

    /**
     * Function Name: setPaddleWidth.
     *
     * @param paddlewidth - paddle width
     */
    public void setPaddleWidth(int paddlewidth) {
        this.paddleWidth = paddlewidth;
    }

    /**
     * Function Name: setLevelName.
     *
     * @param levelname - level name
     */
    public void setLevelName(String levelname) {
        this.levelName = levelname;
    }

    /**
     * Function Name: numberOfBalls.
     *
     * @return number of balls
     */
    public int numberOfBalls() {
        return this.velocities.size();
    }

    /**
     * Function Name: initialBallVelocities.
     *
     * @return list of velocities of the balls.
     */
    public List<Velocity> initialBallVelocities() {
        return this.velocities;
    }

    /**
     * Function Name: paddleSpeed.
     *
     * @return paddle speed.
     */
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    /**
     * Function Name: paddleWidth.
     *
     * @return paddle width.
     */
    public int paddleWidth() {
        return this.paddleWidth;
    }

    /**
     * Function Name: levelName.
     *
     * @return level name.
     */
    public String levelName() {
        return this.levelName;
    }

    /**
     * Function Name: getBackground.
     *
     * @return a sprite with the background of the level
     */
    @Override
    public Sprite getBackground() {
        return this.background;
    }

    /**
     * Function Name: blocks.
     *
     * @return list of blocks.
     */
    public List<Block> blocks() {
        return this.blocks;
    }

    /**
     * Function Name: numberOfBlocksToRemove.
     *
     * @return number of blocks to remove.
     */
    public int numberOfBlocksToRemove() {
        return this.ballsToClear;
    }
}
