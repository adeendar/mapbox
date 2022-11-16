package edu.brown.cs32.examples.moshiExample.handlers;

import edu.brown.cs32.examples.moshiExample.handlers.Response.GetResponse;
import edu.brown.cs32.examples.moshiExample.server.HttpUtilities;
import edu.brown.cs32.examples.moshiExample.server.WeatherAPIUtilities;
import edu.brown.cs32.examples.moshiExample.weatherjavatypes.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.zip.DataFormatException;
import spark.Request;
import spark.Response;
import spark.Route;
import java.util.Map;

/**
 * This is the WeatherHandler class and it implements the Route interface, it contains the methods
 * and datastructures used to handle a weather request through the API -- retrieving information
 * from the U.S. National Weather Service
 */
public class WeatherHandler implements Route {
    private String lat;
    private String lon;
    private Map<String, Object> weatherMap;

    /**
     * This is the constructor, it instantiates the Map that will contain the final JSON
     */
    public WeatherHandler() {
        this.weatherMap = new HashMap<String,Object>();
    }

    /**
     * This method is the handle method that overrides that of the Route interface,
     * it takes in a request and a response and does all the higher level logic work for
     * retrieving the JSON from the U.S. National Weather Service API and gets the temperature
     * of the location corresponding to the requested latitude and longitude coordinates. It
     * also catches any Exceptions the program may throw
     * @param request
     * @param response
     * @return responsecontent
     */
    @Override
    public Object handle(Request request, Response response) {
        try {
            // Setting up data for handler
            this.weatherMap.clear();
            this.lat = request.queryParams("lat");
            this.lon = request.queryParams("lon");
            // Error catching for no queries and too many inputs!
            if (request.queryParams().size() > 2 || this.lat == null || this.lon == null) {
                throw new DataFormatException();
            }
            // Deserializing json to get temperature!
            WeatherAPIUtilities weatherApi = new WeatherAPIUtilities();
            HttpUtilities webSearcher = new HttpUtilities();
            String jsonList = webSearcher.getRequest("https://api.weather.gov/points/" + this.lat + "," + this.lon);
            WeatherResponse jsonMap = weatherApi.deserialize(jsonList);
            String forecastUrl = jsonMap.properties.forecast;
            String interiorJson = webSearcher.getRequest(forecastUrl);
            ForecastResponse forecast = weatherApi.deserializeInt(interiorJson);
            int temperature = forecast.properties.periods.get(0).temperature;
            // Displaying outputted temperature to the user!
            this.weatherMap.clear();
            this.weatherMap.put("result", "success");
            this.weatherMap.put("lat", this.lat);
            this.weatherMap.put("lon", this.lon);
            this.weatherMap.put("temperature", temperature);
            return new GetResponse(this.weatherMap).serialize();
        } catch(NullPointerException e) {
            // Displaying an error to user on local server!
            this.weatherMap.clear();
            this.weatherMap.put("result", "error_datasource");
            return new GetResponse(this.weatherMap).serialize();
        } catch(DataFormatException e) {
            // Displaying an error to user on local server!
            this.weatherMap.clear();
            this.weatherMap.put("result", "error_bad_request");
            return new GetResponse(this.weatherMap).serialize();
        } catch (IOException | InterruptedException e) {
            // Displaying an error to user on local server!
            this.weatherMap.clear();
            this.weatherMap.put("result", "error_bad_json");
            return new GetResponse(this.weatherMap).serialize();
        }
    }
}