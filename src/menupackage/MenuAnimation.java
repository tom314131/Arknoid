package menupackage;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import gamepackage.AnimationRunner;
import interfaces.Menu;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Name: MenuAnimation.
 * @param <T> - general param
 */
public class MenuAnimation<T> implements Menu<T> {

    //members
    private String gameName;
    private AnimationRunner animationRunner;
    private KeyboardSensor keyboard;
    private List<T> menuReturnValues;
    private List<String> menuItemNames;
    private List<String> menuItemKeys;
    private List<Menu<T>> subMenus;
    private T status;
    private boolean stop;
    private List<Boolean> isSubMenu;

    /**
     * Constructor.
     *
     * @param name           - menu name
     * @param runner         - animation runner
     * @param keyboardSensor - keyboard
     */
    public MenuAnimation(String name, AnimationRunner runner, KeyboardSensor keyboardSensor) {
        this.resetGame();
        this.gameName = name;
        this.animationRunner = runner;
        this.keyboard = keyboardSensor;
        this.menuReturnValues = new ArrayList<T>();
        this.menuItemNames = new ArrayList<String>();
        this.menuItemKeys = new ArrayList<String>();
        this.subMenus = new ArrayList<Menu<T>>();
        this.isSubMenu = new ArrayList<Boolean>();

    }

    /**
     * Function Name: addSelection.
     *
     * @param key       - the key we press to chose the selection
     * @param name      - selection name.
     * @param returnVal - what we get if we chose the selection
     */
    @Override
    public void addSelection(String key, String name, T returnVal) {
        this.menuItemKeys.add(key);
        this.menuItemNames.add(name);
        this.menuReturnValues.add(returnVal);
        this.isSubMenu.add(false);
        this.subMenus.add(null);
    }

    /**
     * Function Name: getStatus.
     *
     * @return status.
     */
    @Override
    public T getStatus() {
        return this.status;
    }

    /**
     * Function Name:resetGame.
     * Function Operation: reset game.
     */
    public void resetGame() {
        this.status = null;
        this.stop = false;
    }

    /**
     * Function name: addSubMenu.
     *
     * @param key     - the key we press to chose the selection
     * @param name    - sub menu name
     * @param subMenu - the sub menu we go to
     */
    @Override
    public void addSubMenu(String key, String name, Menu<T> subMenu) {
        this.menuItemKeys.add(key);
        this.menuItemNames.add(name);
        this.menuReturnValues.add(null);
        this.isSubMenu.add(true);
        this.subMenus.add(subMenu);

    }

    /**
     * Function Name: doOneFrame.
     * Function Operation: draw one frame of the game
     *
     * @param d  - the surface of the game.
     * @param dt - 1/frame per second
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.decode("#80CBC4"));
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

        d.setColor(Color.BLACK);
        d.drawText(d.getWidth() / 2 - 101, d.getHeight() / 2 - 101, this.gameName, 45);
        d.setColor(Color.decode("#03A9F4"));
        d.drawText(d.getWidth() / 2 + 1 - 100, d.getHeight() / 2 - 100, this.gameName, 45);
        d.setColor(Color.BLACK);
        d.drawText(d.getWidth() / 2 - 99, d.getHeight() / 2 - 99, this.gameName, 45);

        for (int i = 0; i < this.menuItemNames.size(); i++) {
            d.setColor(Color.BLACK);
            d.drawText(d.getWidth() / 2 - 101, d.getHeight() / 2 - 101 + (i + 1) * 35,
                    "(" + this.menuItemKeys.get(i) + ") " + this.menuItemNames.get(i), 30);
            d.setColor(Color.decode("#3D5AFE"));
            d.drawText(d.getWidth() / 2 + 1 - 100, d.getHeight() / 2 - 100 + (i + 1) * 35,
                    "(" + this.menuItemKeys.get(i) + ") " + this.menuItemNames.get(i), 30);
            d.setColor(Color.BLACK);
            d.drawText(d.getWidth() / 2 - 99, d.getHeight() / 2 - 99 + (i + 1) * 35,
                    "(" + this.menuItemKeys.get(i) + ") " + this.menuItemNames.get(i), 30);
        }

        for (int i = 0; i < this.menuReturnValues.size(); ++i) {
            if (this.keyboard.isPressed(this.menuItemKeys.get(i))) {
                if (!this.isSubMenu.get(i)) {
                    this.status = this.menuReturnValues.get(i);
                    this.stop = true;
                } else {
                    Menu<T> subMenu = this.subMenus.get(i);
                    this.animationRunner.run(subMenu);
                    this.status = subMenu.getStatus();
                    this.stop = true;
                    subMenu.resetGame();
                }
                break;
            }
        }
    }

    /**
     * Function Name: shouldStop.
     *
     * @return wheather the game should stop or not (true or false)
     */
    @Override
    public boolean shouldStop() {
        return this.status != null;
    }
}
