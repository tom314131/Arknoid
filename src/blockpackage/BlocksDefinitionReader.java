package blockpackage;

import decorators.BlockDecorator;
import interfaces.BlockCreator;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

/**
 * Class Name: BlocksDefinitionReader.
 */
public class BlocksDefinitionReader {

    /**
     * Function Name: fromFile.
     * @param fileName - the file we read
     * @return all different types of block that exist in the level
     * @throws IOException - if cannot read the file
     */
    public static BlocksFromSymbolsFactory fromFile(String fileName) throws IOException {
        Reader fileReader = null;
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
        fileReader = new InputStreamReader(is);
        return fromReader(fileReader);
    }

    /**
     * Function Name: fromReader.
     * @param reader - the reader we use to read the file.
     * @return all different types of block that exist in the level
     */
    public static BlocksFromSymbolsFactory fromReader(Reader reader) {
        BlocksFromSymbolsFactory result = new BlocksFromSymbolsFactory();
        Map<String, String> defaultMap = new HashMap<String, String>();
        Map<String, String> bdefMap = new HashMap<String, String>();
        Map<String, String> sdefMap = new HashMap<String, String>();

        BlockDecorator blockDecorator = new BlockDecorator();

        LineNumberReader lineReader = null;
        try {
            lineReader = new LineNumberReader(reader);
            String line = null;

            while ((line = lineReader.readLine()) != null) {
                line = line.trim();
                if (!line.startsWith("#") && !line.equals("")) {
                    if (line.startsWith("sdef")) {
                        String prop = line.substring("sdef".length()).trim();
                        sdefMap = getProperties(prop);
                        char symbol = getSymbol(sdefMap);
                        int i = Integer.parseInt(sdefMap.get("width"));
                        result.addSpacer(symbol, Integer.parseInt(sdefMap.get("width")));
                    } else if (line.startsWith("bdef")) {
                        String prop = line.substring("bdef".length()).trim();
                        bdefMap = getProperties(prop);
                        char symbol = getSymbol(bdefMap);
                        Map<String, String> finalPropertiesMap = new HashMap<String, String>(defaultMap);
                        //combine default properties and bdef properties
                        finalPropertiesMap.putAll(defaultMap);
                        finalPropertiesMap.putAll(bdefMap);
                        BlockCreator blockCreator = new BlockTemplate();
                        Iterator iterator = finalPropertiesMap.keySet().iterator();
                        String key = (String) iterator.next();

                        //decorate the block with his properties
                        for ( ;; key = (String) iterator.next()) {
                            if (!key.startsWith("symbol")) {
                                blockCreator = blockDecorator.decorate(blockCreator, key, finalPropertiesMap.get(key));
                            }
                            if (!iterator.hasNext()) {
                                break;
                            }
                        }
                        result.addBlockCreator(symbol, blockCreator);
                    } else if (line.startsWith("default")) {
                        String defaultProperties = line.substring("default".length()).trim();
                        defaultMap = getProperties(defaultProperties);
                    } else {
                        throw new RuntimeException("Failing in loading blocks");
                    }
                }
            }
        } catch (IOException e) {
            e.getMessage();
        }
        return result;
    }
    /**
     * Function Name: getProperties.
     * Function Operation: get Properties of the block.
     * @param st - the string that include the key's properties and value's properties
     * @return Map of properties
     * */
    public static Map<String, String> getProperties(String st) {
        Map<String, String> map = new HashMap<String, String>();
        String[] pairs = st.split(" ");
        String[] arr = pairs;
        int len = pairs.length;

        for (int i = 0; i < len; i++) {
            String pair = arr[i];
            String[] keyValue = pair.split(":");
            if (keyValue.length != 2) {
                throw new RuntimeException("Incorrect Properties format : " + st);
            }
            map.put(keyValue[0], keyValue[1]);
        }
        return map;
    }

    /**
     * Function Name: getSymbol.
     * @param properties - the property of symbol
     * @return the symbol sign
     */
    private static char getSymbol(Map<String, String> properties) {
        String symbol = properties.get("symbol");
        if (symbol.length() != 1) {
            throw new RuntimeException("Symbol must be a single character: " + symbol);
        } else {
            return symbol.charAt(0);
        }
    }
}

