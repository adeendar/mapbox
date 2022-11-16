package edu.brown.cs32.examples.moshiExample.handlers;


import edu.brown.cs32.examples.moshiExample.csv.CSVParser;
import edu.brown.cs32.examples.moshiExample.csv.CSVWrapper;
import edu.brown.cs32.examples.moshiExample.handlers.Response.GetResponse;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;

import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Handler class for the loadcsv API handler.
 * This class implements Route and is called when a user inputs /loadCSV?filepath=<filepath> into the localServer
 * which will use the CSVParserClass to parse and store the CSV data if a valid file is inputted.
 */
public class LoadCSVHandler implements Route {

  private String filepath;
  private CSVWrapper csv;
  private Map<String, Object> jsonMap;

  /**
   * Constructor accepts some shared state
   * @param csv a CSVParserClass object that is used to load a CSV
   *                  and is referenced again in getcsv so the loaded
   *                  CSV can be serialized and output!
   */

  public LoadCSVHandler(CSVWrapper csv) {
    this.csv = csv;
    this.jsonMap = new HashMap<String, Object>();
  }

  /**
   * Javadoc for handle:
   * @param request the request to handle (inputted URL)
   * @param response use to modify properties of the response
   * @return response content
   */
  @Override
  public Object handle(Request request, Response response) {
    try {
      // Setting up jsonMap and getting filepath
      this.jsonMap.clear();
      this.filepath = request.queryParams("filepath");

      // Error handling for numerous queries and if no query input
      if (request.queryParams().size() > 1) {
        throw new IOException();
      } else if  (this.filepath == null) {
        throw new DataFormatException();
      } else {
        // Displaying success message to user on local server!
        CSVParser csvParser = new CSVParser(new FileReader(this.filepath));
        List<List<String>> data = csvParser.getStringData();
        this.csv.setData(data);
        this.csv.setLoadSuccess(true);

        this.jsonMap.put("result", "success");
        this.jsonMap.put("filepath", this.filepath);
      }
      return new GetResponse(this.jsonMap).serialize();
    }
    catch (FileNotFoundException e) {
      // Displaying error to user on local server!
      this.jsonMap.clear();
      this.csv.setLoadSuccess(false);
      this.jsonMap.put("result", "error_datasource");
      return new GetResponse(this.jsonMap).serialize();
    }
    catch (DataFormatException e) {
      // Displaying error to user on local server!
      this.jsonMap.clear();
      this.csv.setLoadSuccess(false);
      this.jsonMap.put("result", "error_bad_json");
      return new GetResponse(this.jsonMap).serialize();
    }
    catch(IOException e){
      // Displaying error to user on local server!
      this.jsonMap.clear();
      this.csv.setLoadSuccess(false);
      this.jsonMap.put("result", "error_bad_request");
      return new GetResponse(this.jsonMap).serialize();
    }
  }
}
