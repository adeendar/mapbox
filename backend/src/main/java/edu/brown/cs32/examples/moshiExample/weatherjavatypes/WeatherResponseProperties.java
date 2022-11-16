package edu.brown.cs32.examples.moshiExample.weatherjavatypes;

/** Javadoc for WeatherResponseProperties:
 *  This class is used to help parse through the Moshi Json. This class is provided
 *  to the adapter, so we can access the forecast URL in the json after accessing the
 *  properties field in WeatherResponse.
 * */
public class WeatherResponseProperties extends WeatherResponse {
    public String forecast;
}