package uta.cse3310;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordPuzzle {
    private List<String> words;
    private List<Character> revealedLetters;
    private String displayedPuzzle;

    public WordPuzzle() {
        words = new ArrayList<>();
        revealedLetters = new ArrayList<>();
        displayedPuzzle = "";
        loadWords("src/main/resources/words.txt"); // Load words from file
    }

    public void loadWords(String filePath) {
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            words = stream
                    .filter(word -> word.length() >= 3 && word.matches("[a-z]+"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generatePuzzle(int numberOfWords) {
        Random random = new Random();
        List<String> selectedWords = new ArrayList<>();
        for (int i = 0; i < numberOfWords; i++) {
            selectedWords.add(words.get(random.nextInt(words.size())));
        }
        displayedPuzzle = String.join(" ", selectedWords);
    }

    public boolean revealLetter(char letter) {
        boolean revealed = false;
        for (int i = 0; i < displayedPuzzle.length(); i++) {
            if (displayedPuzzle.charAt(i) == letter) {
                revealedLetters.add(letter);
                revealed = true;
            }
        }
        return revealed;
    }

    public boolean checkSolved(List<String> guessedWords) {
        return guessedWords.stream().allMatch(words::contains);
    }

    public String getDisplayedPuzzle() {
        return displayedPuzzle;
    }

    public List<Character> getRevealedLetters() {
        return revealedLetters;
    }
}

