package gamepackage;

/**
 * Class Name: Counter.
 */
public class Counter {

    //members
    private int counter;

    /**
     * Function Name: Counter.
     * Function Operation: Constructor.
     * @param num - the counter
     */
    public Counter(int num) {
        this.counter = num;
    }

    /**
            * Function Name: increase.
     * Function Operation:add number to current count.
     * @param number - the number we add to count
     */
    public void increase(int number) {
        this.counter += number;
    }

    /**
     * Function Name: decrease.
     * Function Operation:subtract number from current count.
     * @param number - the number we subtract from count
     */
    public void decrease(int number) {
        this.counter -= number;
    }

    // get current count.

    /**
     * Function Name: getValue.
     * @return the counter value/
     */
    public int getValue() {
        return this.counter;
    }

}
