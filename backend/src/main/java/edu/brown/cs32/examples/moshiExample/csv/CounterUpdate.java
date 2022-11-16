package edu.brown.cs32.examples.moshiExample.csv;

import java.io.IOException;
import java.util.List;

/**
 * Javadoc for Counter class The Counter class is used to count a parsed CSV from the main method
 * for the end-user to output word, column, rows, and character counts.
 */
public class CounterUpdate {

  // Here, the global variables we will use to count (columns, words, characters, rows)
  // are instantiated and set to 0.
  private int colCount = 0;
  private int wordCount = 0;
  private int charCount = 0;
  private int rowCount = 0;

  /**
   * Javadoc for counter method The counter is called in the main method and returns in the terminal
   * for the user the count of words, characters, rows, and columns within the user's inputted CSV
   * file.
   *
   * @param data, a ArrayList<String[]> which is the output of the CSVParserClass stringData
   * @return
   * @throws IOException
   */
  public void counter(List<List<String>> data) throws IOException {
    if (!data.isEmpty()) {
      // Assuming each CSV row has the same amount of data
      this.colCount = data.get(0).size();
      this.wordCount = 0;
      this.charCount = 0;
      // A row exists for every row of the CSV
      this.rowCount = data.size();

      // This iterates through the CSV to get word
      // and character counts!
      for (int i = 0; i < this.rowCount; i++) {

        // The added word count is added to the global variable
        wordCount = wordCount + data.get(i).size();
        for (int z = 0; z < this.colCount; z++) {
          // The characters of every word are added to the
          // character count
          charCount = charCount + data.get(i).get(z).length();
        }
      }
    }
    // Here is the output!
    System.out.println(
        "Inputted CSV Counts: \n"
            + "Words: "
            + this.wordCount
            + "\n"
            + "Characters: "
            + this.charCount
            + "\n"
            + "Rows: "
            + this.rowCount
            + "\n"
            + "Columns: "
            + this.colCount
            + "\n"
            + "-------------------------------");
  }

  public int getColCount() {
    return this.colCount;
  }

  public int getRowCount() {
    return this.rowCount;
  }

  public int getWordCount() {
    return this.wordCount;
  }

  public int getCharCount() {
    return this.charCount;
  }

}

