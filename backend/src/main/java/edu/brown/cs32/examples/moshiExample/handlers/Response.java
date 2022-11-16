package edu.brown.cs32.examples.moshiExample.handlers;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * This class wraps the response method to make the serialization within each handler more extensible
 */
public class Response {

  /**
   * This record has a method which serializes a Map<String,Object> through Moshi and JsonAdapter and
   * returns the response from the JsonAdapter
   * @param data
   */
  public record GetResponse(Map<String, Object> data) {

    /** Javadoc for serialize:
     * This method takes the inputted data map and serializes it as a json to display
     * to localserver
     * @return jsonData the outputted json form the data
     */
    String serialize() {
      Moshi moshi = new Moshi.Builder().build();
      Type map = Types.newParameterizedType(Map.class, String.class, Object.class);
      JsonAdapter<Map<String, Object>> jsonAdapter = moshi.adapter(map);
      return jsonAdapter.toJson(data);
    }
  }
}
