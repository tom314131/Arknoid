
import menupackage.MenuAnimation;
import gamepackage.AnimationRunner;
import gamepackage.GameFlow;
import menupackage.HighScoresTable;
import gamepackage.LevelSets;
import gamepackage.LevelSpecificationReader;
import menupackage.KeyPressStoppableAnimation;
import menupackage.HighScoresAnimation;


import interfaces.LevelInformation;
import interfaces.Menu;
import interfaces.Task;

import biuoop.GUI;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class Name: Ass6Game.
 */
public class Ass6Game {
    /**
     * Function Name: main.
     * Function Operation: run the game with the levels in the
     * the args array, if args is null the level games
     * are 1, 2, 3 and 4.
     *
     * @param args - the levels of the game.
     */
    public static void main(String[] args) {
        boolean bool = true;
        GUI gui = new GUI("Game_Initialize", 800, 600);
        int fps = 60;
        double dt = 1 / (double) (fps);
        AnimationRunner ar = new AnimationRunner(gui, fps);
        //add keyboard sensor to the game
        biuoop.KeyboardSensor keyboard = gui.getKeyboardSensor();

        GameFlow gameFlow = new GameFlow(ar, keyboard, gui);
        gameFlow.setDt(dt);
        File file = new File("highscores.txt");
        HighScoresTable highScoresTable = HighScoresTable.loadFromFile(file);

        List<LevelSets.LevelSet> levelSetsList = new ArrayList<LevelSets.LevelSet>();
        if (args.length != 0) {
            try {
                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(args[0]);
                if (is != null) {
                    levelSetsList = new LevelSets().fromFile(args[0]);
                    bool = false;
                }
            } catch (IOException ex) {
                System.out.println("Somthing wrong during reading.");
            }
        }
        if (bool) {
            try {
                levelSetsList = new LevelSets().fromFile("level_sets.txt");
            } catch (IOException ex) {
                throw new RuntimeException("Cannot load level sets");
            }
        }
        Menu<Task<Void>> levelSetsMenu = new MenuAnimation<Task<Void>>("Level Sets", ar, keyboard);
        Iterator iterator = levelSetsList.iterator();
        while (iterator.hasNext()) {
            final LevelSets.LevelSet levelSet = (LevelSets.LevelSet) iterator.next();
            levelSetsMenu.addSelection(levelSet.getKey(), levelSet.getLevelName(), new Task<Void>() {
                @Override
                public Void run() {
                    try {
                        List<LevelInformation> levelInformations =
                                new LevelSpecificationReader().fromFile(levelSet.getLevelFilePath());
                        gameFlow.runLevels(levelInformations);
                    } catch (IOException ex) {
                        throw new RuntimeException("Failed to load levels");
                    }
                    return null;
                }
            });

        }
        Menu<Task<Void>> menu = new MenuAnimation<Task<Void>>("Arkanoid", ar, keyboard);

        menu.addSubMenu("s", "Start Game", levelSetsMenu);
        menu.addSelection("h", "High Scores Table", new Task<Void>() {
            @Override
            public Void run() {
                try {
                    highScoresTable.load(file);
                } catch (IOException e) {
                    e.getMessage();
                }
                ar.run(new KeyPressStoppableAnimation(keyboard, "space",
                        new HighScoresAnimation(highScoresTable, "space", keyboard)));
                return null;
            }
        });
        menu.addSelection("e", "Exit", new Task<Void>() {
            public Void run() {
                System.exit(0);
                return null;
            }
        });

        while (true) {
            ar.run(menu);
            Task<Void> task = menu.getStatus();
            task.run();
            menu.resetGame();
        }

    }
}
