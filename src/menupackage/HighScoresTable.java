package menupackage;

import gamepackage.ScoreInfo;

import java.io.ObjectOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Name: HighScoresTable.
 */
public class HighScoresTable implements Serializable {

    //members
    private List<ScoreInfo> scores;
    private int tableSize;

    /**
     * Constructor:
     * Operation: Create an empty high-scores table with the specified size.
     *
     * @param size - The size means that the table holds up to size top scores.
     */
    public HighScoresTable(int size) {
        this.scores = new ArrayList<ScoreInfo>();
        this.tableSize = size;
    }


    /**
     * Function Name: add.
     * Function Operation: Add a high-score.
     *
     * @param score - the score we add to the table
     */
    public void add(ScoreInfo score) {
        if (this.scores.size() == 0) {
            this.scores.add(score);
        } else {
            int insertedPlace;
            boolean shouldinsert = false;
            if (this.tableSize > this.scores.size()) {
                shouldinsert = true;
            }
            for (insertedPlace = 0; insertedPlace < this.scores.size(); insertedPlace++) {
                if ((score.getScore() >= this.scores.get(insertedPlace).getScore())) {
                    shouldinsert = true;
                    break;
                }
            }
            List<ScoreInfo> newScores = new ArrayList<ScoreInfo>();

            for (int i = 0; i < insertedPlace; i++) {
                if (i < tableSize) {
                    newScores.add(this.scores.get(i));
                }
            }
            if (insertedPlace <= tableSize - 1 && shouldinsert) {
                newScores.add(score);
            }
            for (int i = insertedPlace; i < this.scores.size(); i++) {
                if (newScores.size() < this.tableSize) {
                    newScores.add(this.scores.get(i));
                }
            }
            if (insertedPlace == this.scores.size() && insertedPlace < this.tableSize && !shouldinsert) {
                newScores.add(score);
            }
            this.scores = newScores;
        }
    }

    // Return table size.

    /**
     * Function Name: size.
     *
     * @return the table's size.
     */
    public int size() {
        return this.tableSize;
    }

    // Return the current high scores.
    // The list is sorted such that the highest
    // scores come first.

    /**
     * Function Name: getHighScores.
     *
     * @return Return the current high scores.
     * The list is sorted such that the highest scores come first.
     */
    public List<ScoreInfo> getHighScores() {
        List<ScoreInfo> lsi = new ArrayList<ScoreInfo>();
        for (int i = 0; i < this.scores.size(); i++) {
            lsi.add(this.scores.get(i));
        }
        return lsi;
    }

    /**
     * Function Name: getRank.
     *
     * @param score - the score
     * @return the rank between the high score table
     */
    public int getRank(int score) {
        if (this.scores.size() == 0) {
            return 0;
        }
        for (int i = 0; i < this.scores.size(); i++) {
            if (score >= this.scores.get(i).getScore() || this.scores.get(i) == null) {
                return i + 1;
            }
        }

        //not in high score table.
        return this.scores.size() + 1;
    }

    /**
     * Function Name: clear.
     * Function Operation: Clear the table.
     */
    public void clear() {
        this.scores = null;
    }

    /**
     * Function Name: load.
     *
     * @param filename - the file we load from
     * @throws IOException if cannot read the file.
     */
    public void load(File filename) throws IOException {
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(
                    new FileInputStream(filename));
            HighScoresTable highScoresTable = (HighScoresTable) objectInputStream.readObject();
            this.scores = highScoresTable.getHighScores();

        } catch (FileNotFoundException e) {
            System.out.println(" Something went wrong while reading !");
        } catch (ClassNotFoundException e) {
            System.out.println(" Something went wrong while reading !");
        } finally {
            if (objectInputStream != null) { // Exception might have happened at constructor
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    System.out.println(" Failed closing the file !");
                }
            }

        }
    }

    /**
     * Function Name: save.
     *
     * @param filename - the file we save to
     * @throws IOException if cannot save to the file.
     */
    public void save(File filename) throws IOException {
        //PrintWriter os = null;
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(this);
            /**os = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filename)));
             for (int i = 0; i < this.scores.size(); i++) {
             os.println(this.names.get(i));
             os.println(this.scores.get(i));
             }*/
        } catch (IOException e) {
            System.out.println(" Something went wrong while writing !");
        } finally {
            if (oos != null) {
                oos.close();
            }
        }
    }

    // Read a table from file and return it.
    // If the file does not exist, or there is a problem with
    // reading it, an empty table is returned.

    /**
     * Function Name: loadFromFile.
     * Function Operation: load from file.
     * @param filename - the file we load from
     * @return return a high score table from file
     */
    public static HighScoresTable loadFromFile(File filename) {

        HighScoresTable hst = new HighScoresTable(10);
        int count = 0;
        BufferedReader is = null;
        try {
            is = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            String line;
            while ((line = is.readLine()) != null) {
                count++;
            }
            hst = new HighScoresTable(count);
            hst.load(filename);
        } catch (IOException e) {
            System.out.println("highscores.txt doesn't exist, now creating!");
        }
        return hst;
    }
}
