package adventofcode.elfs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class FileElf {
    private FileElf() {
    }

    private static final String DEFAULT_FILE_PATH_PREFIX = "./resources/inputs/";

    public static ArrayList<String> getLinesFromFile(String filename) {
        File file = new File(DEFAULT_FILE_PATH_PREFIX + filename);

        ArrayList<String> lines = new ArrayList<>();
        try (FileReader fileReader = new FileReader(file)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    public static ArrayList<Long> getLongsFromFile(String filename) {
        return getLinesFromFile(filename).stream().map(Long::valueOf).collect(Collectors.toCollection(ArrayList::new));
    }

    public static char[][] getCharArraysFromFile(String filename) {
        ArrayList<String> lines = getLinesFromFile(filename);
        return lines.stream().map(String::toCharArray).collect(Collectors.toList()).toArray(new char[1][1]);
    }
}