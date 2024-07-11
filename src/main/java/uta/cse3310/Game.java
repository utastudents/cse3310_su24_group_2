package uta.cse3310;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<String> words;

    public Game() {
        try {
            this.words = getFilteredWords();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> getFilteredWords() throws IOException {
        List<String> words = new ArrayList<>();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("words.txt");
        if (inputStream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() >= 3 && line.matches("[a-z]+")) {
                    words.add(line);
                }
            }
            reader.close();
        }
        return words;
    }


}


