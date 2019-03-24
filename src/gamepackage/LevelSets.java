package gamepackage;

import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Name: LevelSets.
 */
public class LevelSets {

    /**
     * Function Name: fromFile.
     *
     * @param fileName - the file
     * @return level list from the file.
     * @throws IOException if cannot read file properly
     */
    public List<LevelSets.LevelSet> fromFile(String fileName) throws IOException {
        Reader fileReader = null;
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
        fileReader = new InputStreamReader(is);
        return fromReader(fileReader);
    }

    /**
     * Function Name: fromReader.
     *
     * @param reader - the reader
     * @return level list from the file.
     * @throws IOException if cannot read file properly
     */
    public List<LevelSets.LevelSet> fromReader(Reader reader) throws IOException {
        List<LevelSets.LevelSet> levelSetsList = new ArrayList<LevelSets.LevelSet>();
        String line;
        LineNumberReader lineReader = null;
        try {
            lineReader = new LineNumberReader(reader);
            while (true) {
                while (true) {
                    do {
                        do {
                            line = lineReader.readLine();
                                if (line == null) {
                                    return levelSetsList;
                                }
                            line = line.trim();
                        } while ("".equals(line));
                    } while (line.startsWith("#"));
                    LevelSets.LevelSet levelSet = new LevelSet();
                    line = line.trim();
                    String[] keyAndName = line.split(":");
                    levelSet.setKey(keyAndName[0]);
                    levelSet.setLevelName(keyAndName[1]);
                    line = lineReader.readLine().trim();
                    levelSet.setLevelFilePath(line);
                    levelSetsList.add(levelSet);
                }
            }
        } finally {
            if (lineReader != null) {
                lineReader.close();
            }
        }
    }

    /**
     * Class Name: LevelSet.
     */
    public class LevelSet {

        //members
        private String key;
        private String levelName;
        private String levelFilePath;

        /**
         * Constructor.
         */
        public LevelSet() {

        }

        /**
         * Function Name: setKey.
         * Function Operation: set the key
         *
         * @param keys - key level.
         */
        public void setKey(String keys) {
            this.key = keys;
        }

        /**
         * Function Name: setLevelFilePath.
         * Function Operation: set the path to level
         *
         * @param levelFilePaths - path to level.
         */
        public void setLevelFilePath(String levelFilePaths) {
            this.levelFilePath = levelFilePaths;
        }

        /**
         * Function Name: setLevelName.
         * Function Operation: set the level name
         *
         * @param levelNames - level name
         */
        public void setLevelName(String levelNames) {
            this.levelName = levelNames;
        }

        /**
         * Function Name: getKey.
         *
         * @return the key
         */
        public String getKey() {
            return this.key;
        }

        /**
         * Function Name: getLevelFilePath.
         *
         * @return the file to path.
         */
        public String getLevelFilePath() {
            return this.levelFilePath;
        }

        /**
         * Function Name: getLevelName.
         *
         * @return level name
         */
        public String getLevelName() {
            return this.levelName;
        }
    }
}
