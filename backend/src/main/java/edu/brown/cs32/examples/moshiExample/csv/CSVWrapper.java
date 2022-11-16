package edu.brown.cs32.examples.moshiExample.csv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is the CSVWrapper class that helps contain the CSVParser's string data in a local variable,
 * modeling defensive coding to avoid modifications in the string data
 */
public class CSVWrapper {

  private List<List<String>> loadedCsv;
  private boolean loadSuccess;

  /**
   * This is the constructor: it instantiates the global variables in this class
   */
  public CSVWrapper() {
    this.loadedCsv = new ArrayList<>();
    this.loadSuccess = false;
  }

  /**
   * This method returns an unmodifiable List<List<String>> of loadedCsv
   * @return
   */
  public List<List<String>> getCsv() {
    return Collections.unmodifiableList(this.loadedCsv);
  }

  /**
   * This method adds the data and contents of the input data and adds it to this.loadedCsv (a
   * defensive copy of the data)
   * @param data
   */
  public void setData(List<List<String>> data){
    this.loadedCsv.clear();
    this.loadedCsv.addAll(data);
  }

  /**
   * This method sets the boolean value of this.loadSuccess
   * @param value
   */
  public void setLoadSuccess(boolean value){
    this.loadSuccess = value;
  }

  /**
   * This method returns the boolean value of this.loadSuccess
   * @return
   */
  public boolean getLoadSuccess(){
    return this.loadSuccess;
  }
}
