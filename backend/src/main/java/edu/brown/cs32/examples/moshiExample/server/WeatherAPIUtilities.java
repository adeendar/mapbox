package edu.brown.cs32.examples.moshiExample.server;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.Moshi;
import edu.brown.cs32.examples.moshiExample.weatherjavatypes.ForecastResponse;
import edu.brown.cs32.examples.moshiExample.weatherjavatypes.WeatherResponse;
import java.io.IOException;

  /** Javadocs for WeatherAPIUtilities Class:
   * This class is used by the WeatherHandler to process the jsonData
   * from the get requests. The deserialize and deserializeInt methods
   * parse through the weather json to access the necessary data to
   * access the temperature.
   * */

public class WeatherAPIUtilities {

  /** Constructor */
  public WeatherAPIUtilities() {}

  /**
   * deserialize Javadoc:
   * @param jsonList a string that represents the first json the weatherhandler accesses
   *                 from the get request that contains data on the specific latitude and
   *                 longitiude which has another link to be accesssed for getting temperature.
   * @return deserializedJson as WeatherResponse class. This allows for the interior url to be
   *          accessed for the second get request for getting temperature.
   * @throws JsonDataException if the json is not able to be properly adapted
   * @throws IOException for any input/output errors
   */
  public WeatherResponse deserialize(String jsonList) throws JsonDataException, IOException {
      Moshi moshi = new Moshi.Builder().build();
      JsonAdapter<WeatherResponse> weatherAdapter = moshi.adapter(WeatherResponse.class);
      return weatherAdapter.fromJson(jsonList);
  }

    /**
     * deserializeInt Javadoc:
     * @param interiorJson a string that represents the interior json the weatherhandler accesses
     *                 from the second get request that contains data on the specific latitude and
     *                 longitiude to get temperature.
     * @returns deserializedJson as ForecastResponse class. This allows for the int temperature to be
     *          accessed using the weatherjavatypes package.
     * @throws JsonDataException if the jsoonData is not a valid Json
     * @throws IOException for any input/output errors
     */

  public ForecastResponse deserializeInt(String interiorJson) throws JsonDataException, IOException {
      Moshi moshi = new Moshi.Builder().build();
      JsonAdapter<ForecastResponse> weatherAdapter = moshi.adapter(ForecastResponse.class);
      return weatherAdapter.fromJson(interiorJson);
  }

}
