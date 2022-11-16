package edu.brown.cs32.examples.moshiExample.csv;

/**
 * This is the Counter class which is in charge of calculating all the counts, it contains various
 * instances of ints to represent the needed counts
 */
public class Counter {

  private int totalCharacters;
  private int totalWords;
  private int rowCount;
  private int colCount;

  /*
  This is the constructor, instantiating each count into 0 and first
   */
  public Counter() {
    this.totalCharacters = 0;
    this.totalWords = 0;
    this.rowCount = 0;
    this.colCount = 0;
  }

  /*
  This method adds all the counts by calling the helper methods, it also calculates the totalCharacter
  count (which includes numbers, letters, spaces, apostrophes, and additional symbols)
   */
  public void addCounts(String[] row) {
    this.addColCount(row);
    this.addRowCount();
    for (int i = 0; i < row.length; i++) {
      String word = row[i];
      this.addWordCount(word);
      this.totalCharacters += word.length();
    }
  }

  /*
  This method adds a word count for every non-empty string, taking in the given word
   */
  private void addWordCount(String word) {
    if (!word.equals("")) {
      String[] wordArray = word.split(" ");
      this.totalWords += wordArray.length;
    }
  }

  /*
  This method adds the row count
   */
  private void addRowCount() {
    this.rowCount++;
  }

  /*
  This method adds the col count
   */
  private void addColCount(String[] row) {
    this.colCount = row.length;
  }

  /*
  This method returns the total character count
   */
  public int getTotalCharacters() {
    return this.totalCharacters;
  }

  /*
  This method returns the total word count
   */
  public int getTotalWords() {
    return this.totalWords;
  }

  /*
  This method returns the total row count
   */
  public int getRowCount() {
    return this.rowCount;
  }

  /*
  This method returns the total column count
   */
  public int getColCount() {
    return this.colCount;
  }
}
