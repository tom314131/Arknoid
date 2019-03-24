package gamepackage;

import java.io.Serializable;

/**
 * Class Name: ScoreInfo.
 */
public class ScoreInfo implements Serializable {

    //members
    private String playerName;
    private int playerScore;

    /**
     * Constructor.
     * @param name - player name.
     * @param score - the score
     */
    public ScoreInfo(String name, int score) {
        this.playerName = name;
        this.playerScore = score;
    }

    /**
     * Function Name:getName.
     * @return the player name
     */
    public String getName() { return this.playerName; }

    /**
     * Function Name:getScore.
     * @return the score
     */
    public int getScore() { return this.playerScore; }
}
