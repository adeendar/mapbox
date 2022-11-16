package edu.brown.cs32.examples.moshiExample.csv;

import java.util.List;

/**
 * Creates an object of type T from a List of Strings.
 *
 * @param <T> object to be created
 */
public interface CreatorFromRow<T> {

  /**
   * Javadoc for create
   *
   * @param row, a CSV row to be turned into object t
   * @return T, the obejct the CSV row is turned into based on the class the user has designed
   * @throws FactoryFailureException, a error message when create is unable to turn the row into the
   *     T object, telling the user the object was unable to be created.
   */
  T create(List<String> row) throws FactoryFailureException;
}
