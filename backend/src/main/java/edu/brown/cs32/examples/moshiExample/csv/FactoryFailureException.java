package edu.brown.cs32.examples.moshiExample.csv;

import java.util.List;

/** Exception thrown when a Factory class fails. */
public class FactoryFailureException extends Exception {

  // This array of Strings is a row of CSV data meant to be turned into
  // object T
  final List<String> row;

  /**
   * Javadoc for FactoryFailureException This informative exception is for users to implement when
   * the class they have designed to use the CreatorFromRow interface, does not properly turn the
   * row into the desired object.
   */
  public FactoryFailureException(List<String> row) {
    this.row = row;
  }
}
