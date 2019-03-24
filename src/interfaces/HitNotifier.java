package interfaces;

/**
 * Interface Name: HitNotifier.
 */
public interface HitNotifier {
    /**
     * Function Name: addHitListener.
     * Function Operation: Add hl as a listener to hit events.
     * @param hl - the listener.
     */
    void addHitListener(HitListener hl);

    /**
     * Function Name: removeHitListener.
     * Function Operation: Remove hl from the list of listeners to hit events.
     * @param hl - the listener.
     */
    void removeHitListener(HitListener hl);
}
