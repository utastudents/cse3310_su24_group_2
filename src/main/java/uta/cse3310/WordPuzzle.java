package uta.cse3310;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordPuzzle {
    private List<String> words;
    private List<Character> revealedLetters;
    private String displayedPuzzle;

    public WordPuzzle() {
        words = new ArrayList<>();
        revealedLetters = new ArrayList<>();
        displayedPuzzle = "";
    }

    public void generatePuzzle(List<String> wordList, int numberOfWords) {
        Random random = new Random();
        for (int i = 0; i < numberOfWords; i++) {
            words.add(wordList.get(random.nextInt(wordList.size())));
        }

    }

    public boolean revealLetter(char letter) {

        return false;
    }

    public boolean checkSolved(List<String> guessedWords) {

        return false;
    }
}
//This should handle game play rules
