package interfaces;

/**
 * Interface Name: Menu.
 * @param <T> - general parameter
 */
public interface Menu<T> extends Animation {
    /**
     * Function Name: addSelection.
     * Function Operation: add a selection to the menu
     * @param key - the key we press to chose the selection
     * @param message - the name of the selection
     * @param returnVal - what we get if we chose the selection
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * Function Name: getStatus.
     * @return what is our status ( rue or false)
     */
    T getStatus();

    /**Function Name:resetGame.
     * Function Operation: reset the game
     */
    void resetGame();
    /**
     * Function Name: addSubMenu.
     * Function Operation: add a sub menu to the menu
     * @param key - the key we press to chose the selection
     * @param message - the name of the selection
     * @param subMenu - the sub menu we go to
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);
}
