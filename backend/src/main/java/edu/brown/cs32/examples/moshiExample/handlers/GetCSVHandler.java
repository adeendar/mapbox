package edu.brown.cs32.examples.moshiExample.handlers;

import edu.brown.cs32.examples.moshiExample.csv.CSVWrapper;
import edu.brown.cs32.examples.moshiExample.handlers.Response.GetResponse;
import java.io.FileNotFoundException;
import spark.Request;
import spark.Response;
import spark.Route;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Handler class for the getcsv api handler.
 * This class implements Route and is called when a user inputs /getcsv into the localServer
 * which will serialize the CSV passed into loadcsv if a valid csv was loaded, or serialize
 * an error message.
 */
public class GetCSVHandler implements Route {

  private CSVWrapper csv;
  private Map<String, Object> jsonMap;

  /**
   * Constructor accepts some shared state
   *
   * @param csv a CSVParserClass object that was used to load a CSV
   *                  and is referenced so we can access the parsed CSV
   *                  to serialize!
   */
  public GetCSVHandler(CSVWrapper csv) {
    this.csv = csv;
    this.jsonMap = new HashMap<String, Object>();
  }

  /**
   * Javadoc for handle:
   *
   * @param request  the request to handle (inputted into URL)
   * @param response use to modify properties of the response
   * @return response content
   */
  @Override
  public Object handle(Request request, Response response) {
    try {
      this.jsonMap.clear();
      if (request.queryParams().size() > 0) {
        throw new IOException();
      } else if (!this.csv.getLoadSuccess()) {
        throw new FileNotFoundException();
      } else {
        // Getting a defensive copy of string Data!
        this.jsonMap.put("result", "success");
        this.jsonMap.put("data", this.csv.getCsv());
        return new GetResponse(this.jsonMap).serialize();
      }
    } catch (FileNotFoundException e) {
      this.jsonMap.clear();
      this.jsonMap.put("result", "error_datasource");
      return new GetResponse(this.jsonMap).serialize();
    } catch (IOException e) {
      this.jsonMap.clear();
      this.jsonMap.put("result", "error_bad_request");
      return new GetResponse(this.jsonMap).serialize();
    }
  }
}


