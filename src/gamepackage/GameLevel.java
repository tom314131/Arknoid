package gamepackage;


import biuoop.DialogManager;
import indicators.LivesIndicator;
import indicators.ScoreIndicator;
import interfaces.Animation;
import interfaces.LevelInformation;
import interfaces.Sprite;
import menupackage.HighScoresTable;
import menupackage.EndGame;
import menupackage.HighScoresAnimation;
import menupackage.KeyPressStoppableAnimation;
import menupackage.PauseScreen;
import sprites.Ball;
import sprites.Block;
import sprites.Paddle;
import sprites.SpriteCollection;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import interfaces.Collidable;
import collisiondetection.GameEnvironment;
import geomitryprimitives.Point;
import geomitryprimitives.Rectangle;
import geomitryprimitives.Velocity;
import hitlisteners.BallRemover;
import hitlisteners.BlockRemover;
import hitlisteners.ScoreTrackingListener;

/**
 * Class Name: GameLevel.
 */
public class GameLevel implements Animation {
    private LevelInformation levelInformation;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private AnimationRunner runner;
    private boolean running;
    private boolean firstTime;


    //magic numbers
    private int widthGui = 800;
    private int heightGui = 600;
    private int frameblockHeight = 25;
    private int frameblockWidth = 40;
    private int blockHeight = 20;
    private int blockWidth = 50;
    private int padHeight = 10;

    //start position to create blocks
    private int startX = 800;
    private int startY = 150;
    private int blockPerRow = 12;

    private int ballNumber = 3;
    private boolean keepRun = true;
    private List<Ball> balls = new ArrayList<Ball>();
    private Paddle pad;
    private double dt;


    //GUI gui = new GUI("Game_Initialize", widthGui, heightGui);
    private GUI gui;
    //add keyboard sensor to the game
    private biuoop.KeyboardSensor keyboard;
    private CountdownAnimation cda;


    private BlockRemover br = new BlockRemover(this, null);
    private BallRemover deathRegion;
    private ScoreTrackingListener stl;
    private LivesIndicator li;
    private Counter score;
    private Counter lives;
    private boolean last;
    private HighScoresTable scores;


    /**
     * Function Name: GameLevel.
     * Function Operation: create game environment and sprite collection.
     * @param le             - level type
     * @param ks             - keyboard sensor
     * @param runner         - animation runner
     * @param gui            - the gui
     * @param stl            - score tracking listener
     * @param livesIndicator - live indicator
     * @param last           - if last level or not
     */
    public GameLevel(LevelInformation le, KeyboardSensor ks, AnimationRunner runner,
                     GUI gui, ScoreTrackingListener stl, LivesIndicator livesIndicator, boolean last) {
        this.runner = runner;
        this.environment = new GameEnvironment();
        this.sprites = new SpriteCollection();
        this.sprites.setTime(runner.getFramesPerSecond());
        this.levelInformation = le;
        this.keyboard = ks;
        this.gui = gui;
        this.stl = stl;
        this.li = livesIndicator;
        this.score = this.stl.getCurrentScore();
        this.lives = this.li.getLives();
        this.last = last;
    }

    /**
     * Function Name: returnEnviroment.
     *
     * @return the game environment
     */
    public GameEnvironment returnEnviroment() {
        return this.environment;
    }

    /**
     * Function Name: addCollidable.
     * Function Operation: add collidable to the game environment.
     *
     * @param c - the collidable
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Function Name: removeCollidable.
     * Function Operation: remove collidable to the game environment.
     *
     * @param c - the collidable
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Function Name: addSprite.
     * Function Operation: add sprite to the game environment.
     *
     * @param s - the sprite
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Function Name: removeSprite.
     * Function Operation: remove sprite to the game environment.
     *
     * @param s - the sprite
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Function Name: setGui.
     * Function Operation: set the gui of the game.
     *
     * @param guiGame - the gui of the game.
     */
    public void setGui(GUI guiGame) {
        this.gui = guiGame;
    }

    /**
     * Function Name: getGui.
     *
     * @return the gui of the game
     */
    public GUI getGui() {
        return this.gui;
    }

    /**
     * Function Name: initialize.
     * Function Operation: Initialize a new game: create the Blocks and Ball (and Paddle)
     * and add them to the game.
     */
    public void initialize() {
        deathRegion = new BallRemover(this, new Counter(this.levelInformation.numberOfBalls()));
        //create the gui
        //add frame blocks to the game
        this.sprites.addSprite(this.levelInformation.getBackground());
        creatingBlocksFrame();
        createBlocksToGame();
        this.firstTime = false;

        //this.runner = new AnimationRunner(this.gui,60);

    }

    /**
     * Function name: run.
     * Function Operation: Run the game -- start the animation loop.
     */
    public void playOneTurn() {
        this.creatingIndicators();
        createPaddle();
        this.createBallsOnTopOfPaddle();
        this.pad.setBalls(balls);
        this.running = true;
        cda = new CountdownAnimation(2000, 3, sprites);
        this.runner.run(cda);
        this.runner.run(this);
    }

    /**
     * Function Name: creatingBlocksFrame.
     * Function Operation: create the frame blocks of the game.
     */
    public void creatingBlocksFrame() {
        Block deathRegionBlock = new Block(new Rectangle(new Point(0, heightGui + frameblockHeight),
                widthGui, frameblockHeight));
        Block up = new Block(new Rectangle(new Point(0, 15),
                widthGui, frameblockHeight));
        Block left = new Block(new Rectangle(new Point(0, 0),
                frameblockHeight, widthGui));
        Block right = new Block(new Rectangle(new Point(widthGui - frameblockHeight, 0),
                frameblockHeight, widthGui));
        //down.setHitNum(1);
        up.setHitNum(-1);
        right.setHitNum(-1);
        left.setHitNum(-1);
        deathRegionBlock.addHitListener(deathRegion);
        deathRegionBlock.setHitNum(-1);
        //down.addToGame(this);
        up.addToGame(this);
        left.addToGame(this);
        right.addToGame(this);
        deathRegionBlock.addToGame(this);
    }

    /**
     * Function Name: createBallsOnTopOfPaddle.
     * Function Operation: create the balls of the game.
     */
    public void createBallsOnTopOfPaddle() {
        Random rand = new Random();
        balls = new ArrayList<Ball>();
        deathRegion.setRemainingBalls(new Counter(this.levelInformation.numberOfBalls()));
        for (int i = 0; i < this.levelInformation.numberOfBalls(); i++) {
            Ball ball = new Ball(widthGui / 2, this.pad.leftUpPoint().getY() - 15, 5, Color.WHITE.darker());
            Velocity v = null;
            if (this.levelInformation.levelName().equals("Direct Hit")) {
                v = this.levelInformation.initialBallVelocities().get(0);
            } else {
                v = this.levelInformation.initialBallVelocities().get(i);
            }
            if (!firstTime) {
                v.setDX(v.getDX() * this.dt);
                v.setDY(v.getDY() * this.dt);
                v.setSpeed(v.getSpeed() * dt);

            }
            if (i == this.levelInformation.numberOfBalls() - 1) {
                this.firstTime = true;
            }
            ball.setVelocity(v);
            ball.setGameEnvironment(this.returnEnviroment());
            ball.addHitListener(deathRegion);
            ball.addToGame(this);
            balls.add(ball);
        }
    }

    /**
     * Function Name: createPaddle.
     * Function Operation: create the paddle of the game.
     */
    public void createPaddle() {
        pad = null;
        pad = new Paddle(new Rectangle(new Point(widthGui / 2 - this.levelInformation.paddleWidth() / 2,
                heightGui - 2 * frameblockHeight),
                this.levelInformation.paddleWidth(), padHeight), keyboard);
        pad.getGuiWidth(widthGui);
        //pad.setBalls(ball1, ball2);
        pad.setSideFrameBlockWidth(frameblockHeight);
        pad.setSpeed(this.levelInformation.paddleSpeed() * this.dt);
        pad.addToGame(this);
    }

    /**
     * Function Name: createBlocksToGame.
     * Function Operation: create the blocks of the game.
     */
    public void createBlocksToGame() {
        int countblocks = 0;
        for (int i = 0; i < this.levelInformation.blocks().size(); i++) {
            Block b = this.levelInformation.blocks().get(i);
            b.addHitListener(br);
            b.addHitListener(stl);
            b.addToGame(this);
            countblocks++;
        }
        br.setRemainingBlocks(new Counter(countblocks));
    }

    /**
     * Function Name: creatingIndicators.
     * Function Operation: create the live and score indicators of the game.
     */
    public void creatingIndicators() {
        ScoreIndicator sco = new ScoreIndicator(this.score);
        sco.addToGame(this);
        LivesIndicator live = new LivesIndicator(this.lives);
        live.addToGame(this);
    }

    /**
     * Function  Name: shouldStop.
     *
     * @return true if the game should stop and false otherwise.
     */
    public boolean shouldStop() {
        if (deathRegion.getRemainingBalls().getValue() == 0) {
            this.lives.decrease(1);
        }
        if (lives.getValue() == 0 || (last && br.getRemainingBlocks().getValue() == 0)) {
            Animation end = new EndGame(this.keyboard, this.lives.getValue(), this.score.getValue());
            Animation high = new HighScoresAnimation(this.scores, "space", keyboard);
            Animation aEnd = new KeyPressStoppableAnimation(keyboard, "space", end);
            Animation aHigh = new KeyPressStoppableAnimation(keyboard, "space", high);
            runner.run(aEnd);
            if (scores.getRank(this.score.getValue()) < 10) {
                DialogManager dialog = gui.getDialogManager();
                String name = dialog.showQuestionDialog("Name", "What is your name?", "");
                scores.add(new ScoreInfo(name, this.score.getValue()));
            }
            runner.run(aHigh);
            try {
                scores.save(new File("highscores.txt"));
            } catch (IOException e) {
                e.getMessage();
            }
            //gui.close();
        }
        if (br.getRemainingBlocks().getValue() == 0
                || deathRegion.getRemainingBalls().getValue() == 0 || this.lives.getValue() == 0) {
            for (int i = 0; i < balls.size(); i++) {
                balls.get(i).removeFromGame(this);
            }
            pad.removeFromGame(this);
            return this.running;
        }
        return !this.running;
    }

    /**
     * Function Name: doOneFrame.
     * Function Operation: draw the game with all the sprites and indicators
     * @param d - the surface of the game
     * @param dts - 1/fps
     */
    public void doOneFrame(DrawSurface d, double dts) {
        this.levelInformation.getBackground().drawOn(d);
        stl.setRemainingBlocks(br.getRemainingBlocks());
        this.score = stl.getCurrentScore();
        this.lives = li.getLives();
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();
        d.setColor(Color.BLACK);
        d.drawText(widthGui - 300, 13, "Level Name: " + this.levelInformation.levelName(), 15);
        if (this.keyboard.isPressed("p")) {
            Animation pause = new PauseScreen(this.keyboard);
            Animation aPause = new KeyPressStoppableAnimation(this.keyboard, "space", pause);
            runner.run(aPause);
        }
    }

    /**
     * Function Name: returnLives.
     * @return the remaining life of the player
     */
    public int returnLives() {
        return this.lives.getValue();
    }

    /**
     * Function Name: returnBlocks.
     * @return the remaining blocks in the game.
     */
    public int returnBlocks() {
        return br.getRemainingBlocks().getValue();
    }

    /**
     * Function Name: returnScore.
     * @return the score in the game.
     */
    public int returnScore() {
        return this.score.getValue();
    }

    /**
     * Function Name:setDt.
     * Function Operation: set the dt
     * @param dts - 1/fps
     */
    public void setDt(double dts) {
        this.dt = dts;
    }

    /**
     * Function Name: SetScore.
     * @param scor - high score table
     */
    public void setScore(HighScoresTable scor) {
        this.scores = scor;
    }
}
