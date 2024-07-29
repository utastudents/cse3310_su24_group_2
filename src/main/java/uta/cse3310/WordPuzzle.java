//Manages word puzzles, randomness etc
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
    private int maxAttempts;
    private int attemptsLeft;

    public WordPuzzle(String wordFilePath, int maxAttempts) {
        this.words = new ArrayList<>();
        this.revealedLetters = new ArrayList<>();
        this.displayedPuzzle = "";
        this.maxAttempts = maxAttempts;
        this.attemptsLeft = maxAttempts;
        loadWords(wordFilePath); // Load words from file
    }
    //To load words from the file
    public void loadWords(String filePath) {
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            this.words = stream
                    .filter(word -> word.length() >= 3 && word.matches("[a-zA-Z]+"))
                    .map(String::toLowerCase) 
                    .collect(Collectors.toList() );
                    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //To generate a puzzle with a given number of words 
    public void generatePuzzle(int numberOfWords) {
        Random random = new Random();
        List<String> selectedWords = new ArrayList<>();
        for (int i = 0; i < numberOfWords; i++) {
            selectedWords.add(words.get(random.nextInt(words.size())));
        }
        this.displayedPuzzle = String.join(" ", selectedWords);
    }

    //Reveal a letter in the puzzle
    public boolean revealLetter(char letter) {
        boolean revealed = false;
        for (int i = 0; i < displayedPuzzle.length(); i++) {
            if (displayedPuzzle.charAt(i) == letter) {
                revealedLetters.add(letter);
                revealed = true;
            }
        }
        if (!revealed) {
            attemptsLeft--;
        } 
        return revealed;
    }

    //Check if the puzzle has been solved with the given words 
    public boolean checkSolved(List<String> guessedWords) {
        return guessedWords.stream().allMatch(words::contains);
    }

    public String getDisplayedPuzzle() {
        return displayedPuzzle;
    }

    public List<Character> getRevealedLetters() {
        return revealedLetters;
    }


    public int getMaxAttempts() {
        return maxAttempts;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }
//To check if the attempt is correct 
    public boolean isCorrect(String attempt) {
        return displayedPuzzle.equalsIgnoreCase(attempt);
    }

    public void selectWords(int numberOfWords) {
        generatePuzzle(numberOfWords);
    }
}

