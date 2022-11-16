package edu.brown.cs32.examples.moshiExample.weatherjavatypes;

/** Javadoc for ForecastResponse:
 *  This class is used to help parse through the Moshi Json. This class is provided
 *  to the adapter for accessing temperature from the second Json, so we can access
 *  properties in the json to reach the interior temperature.
 * */

public class ForecastResponse {

    public ForecastProperties properties;

}
