package edu.brown.cs32.examples.moshiExample.csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This is the CSVParser class that has 3 different constructors, each associated with a User Story
 * where the first one takes in a String, the second one takes in a Reader, the third one takes in a
 * Reader and a CreatorFromRow. This class is in charge of parsing through the CSV file, calculating
 * the needed counts, can print the counts, and can also convert the CSV file into the necessary
 * object of type T
 */
public class CSVParser<T> {
  private Reader reader;
  private List<List<String>> csv;

  private List<T> csv2;
  private Counter counter;
  private CreatorFromRow creator;

  public CSVParser() {
    this.counter = new Counter();
  }
  /*
  This is the constructor which takes in a parameter of type String, initializing the needed
  instances and calls the parseFile method (User Story 1)
   */
  public CSVParser(String filePath) {
    try {
      this.reader = new FileReader(filePath);
    } catch (FileNotFoundException e) {
      System.err.println("File was not found");
    }
    this.csv = new ArrayList<List<String>>();
    this.counter = new Counter();

    this.parse();
  }

  /*
  This is the constructor which takes in a parameter of type Reader, initializing the needed
  instances and calls the parseFile method (User Story 2)
   */
  public CSVParser(Reader reader) {
    this.reader = reader;
    this.csv = new ArrayList<List<String>>();
    this.counter = new Counter();

    this.parse();
  }

  /*
  This is the constructor which takes in a parameter of type Reader and type CreatorFromRow,
  initializing the needed instances and calls the convertFile method
   */
  public CSVParser(Reader reader, CreatorFromRow creator) {
    this.reader = reader;
    this.creator = creator;
    this.csv2 = new ArrayList<T>();
    this.counter = new Counter();

    this.convertFile();
  }

  /*
  This method prints the word, character, row, and column counts
   */
  public void printUtilityProgram() {
    System.out.println("Words: " + this.counter.getTotalWords());
    System.out.println("Characters: " + this.counter.getTotalCharacters());
    System.out.println("Rows: " + this.counter.getRowCount());
    System.out.println("Columns: " + this.counter.getColCount());
  }

  /*
  This method returns the total word count from an instance of Counter
   */
  public int getWordCount() {
    return this.counter.getTotalWords();
  }

  /*
  This method returns the total character count from an instance of Counter
   */
  public int getCharacterCount() {
    return this.counter.getTotalCharacters();
  }

  /*
  This method returns the row count from an instance of Counter
   */
  public int getRowCount() {
    return this.counter.getRowCount();
  }

  /*
  This method returns the col count from an instance of Counter
   */
  public int getColCount() {
    return this.counter.getColCount();
  }


  public void setFileReader(FileReader fr){
      this.reader = fr;
  }
  /*
  This method parses the CSV file and for each line it adds it to the list of rows
   */
  public void parse() {
    try {
      if(this.reader != null) {
        BufferedReader br = new BufferedReader(this.reader);
        String line;
        int lineCounter = 0;
        while ((line = br.readLine()) != null) {
          if (lineCounter != 0) {
            this.addRow(line);
          }
          lineCounter++;
        }
      }
      else {
        this.csv = null;
      }
    } catch (FileNotFoundException e) {
      this.csv = null;
      System.err.println("File not found");
    } catch (IOException e) {
      this.csv = null;
      System.err.println("IOException not found");
    }
  }

  public List<List<String>> getStringData(){
    return this.csv;
  }
  /*
  This creates a List of Strings representing a row by splitting each line by commas and adds it
  to the List of rows
   */
  private void addRow(String line) {
    String[] rowArray = line.split(",");
    this.counter.addCounts(rowArray);
    List<String> row = new ArrayList<String>(Arrays.asList(line.split(",")));
    this.csv.add(row);
  }

  /*
  This parses through the CSV file and converts each line into the given CreatorFromRow
   */
  public void convertFile() {
    try {
      BufferedReader br = new BufferedReader(this.reader);
      String line;
      int lineCounter = 0;
      while ((line = br.readLine()) != null) {
        if (lineCounter != 0) {
          this.convertRow(line);
        }
        lineCounter++;
      }
    } catch (Exception e) {
      System.err.println("Something went wrong.");
    }
  }

  /*
  This method converts the given line from the csv file and converts it into the CreatorFromRow
  object, adding it to the larger list of objects
   */
  private void convertRow(String line) {
    try {
      List<String> row = new ArrayList<String>(Arrays.asList(line.split(",")));
      T rowConverted = (T) this.creator.create(row);
      this.csv2.add(rowConverted);
    } catch (Exception e) {
      System.err.println(e);
    }
  }

  public List<T> getConverted() {
    return this.csv2;
  }
}
