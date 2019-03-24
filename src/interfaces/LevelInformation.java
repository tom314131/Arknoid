package interfaces;

import sprites.Block;
import geomitryprimitives.Velocity;

import java.util.List;

/**
 * Interface Name: LevelInformation.
 */
public interface LevelInformation {
    /**
     * Function Name: numberOfBalls.
     * @return the number of balls in the level.
     */
    int numberOfBalls();

    /**
     * Function Name: initialBallVelocities.
     * @return list of the initial velocity of the balls
     */
    List<Velocity> initialBallVelocities();

    /**
     * Function Name: paddleSpeed.
     * @return the speed of the paddle.
     */
    int paddleSpeed();

    /**
     * Function Name: paddleWidth.
     * @return the width of the paddle.
     */
    int paddleWidth();

    /**
     * Function Name: levelName.
     * @return the level name will be displayed at the top of the screen.
     */
    String levelName();

    /**
     * Function Name: getBackground.
     * @return a sprite with the background of the level
     */
    Sprite getBackground();

    /**
     * Function Name: blocks.
     * @return the list of blocks that make up this level, each block contains
     *          its size, color and location.
     */
    List<Block> blocks();

    /**
     * Function Name: numberOfBlocksToRemove.
     * @return Number of blocks that should be removed
     *      before the level is considered to be "cleared".
     */
    int numberOfBlocksToRemove();
}
