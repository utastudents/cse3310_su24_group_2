//Manages word puzzles, randomness etc
package uta.cse3310;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class WordPuzzle {
 private List<String> words;
private String displayedPuzzle;
 private String currentPuzzle;

 public WordPuzzle() {
 words = new ArrayList<>();
displayedPuzzle = "";
 currentPuzzle = "";
loadWords(getWordSource());
}

 private String getWordSource() {
String testGrid = System.getenv("TEST_GRID");
 if (testGrid != null) {
 return "src/main/resources/test_words_" + testGrid + ".txt";
 }
 return "src/main/resources/words.txt";
 }

 public void loadWords(String filePath) {
try {
words = Files.lines(Paths.get(filePath))
.filter(word -> word.length() >= 3 && word.matches("^[a-z]+$"))
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
 currentPuzzle = String.join(" ", selectedWords);
 displayedPuzzle = currentPuzzle.replaceAll("[a-z]", "_");
 }

 public boolean revealLetter(char letter) {
 boolean revealed = false;
StringBuilder newDisplayed = new StringBuilder(displayedPuzzle);
for (int i = 0; i < currentPuzzle.length(); i++) {
if (Character.toLowerCase(currentPuzzle.charAt(i)) == Character.toLowerCase(letter) && displayedPuzzle.charAt(i) == '_') {
newDisplayed.setCharAt(i, currentPuzzle.charAt(i));
revealed = true;
 }
 }
displayedPuzzle = newDisplayed.toString();
 return revealed;
}

 public boolean checkSolved(String guess) {
return guess.equalsIgnoreCase(currentPuzzle);
}

public String getDisplayedPuzzle() {
 return displayedPuzzle;
}

 public String getCurrentPuzzle() {
 return currentPuzzle;
}
}
