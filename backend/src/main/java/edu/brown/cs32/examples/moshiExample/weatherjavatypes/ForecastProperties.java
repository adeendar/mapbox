package edu.brown.cs32.examples.moshiExample.weatherjavatypes;

import java.util.List;

/** Javadoc for ForecastProperties:
 *  This class is used to help parse through the Moshi Json. This class is provided
 *  to the adapter for accessing the list of periods which contains
 *  temperature from the second Json, so we can access temperature in the json.
 * */

public class ForecastProperties extends ForecastResponse{
    public List<Period> periods;

}
