package edu.brown.cs32.examples.moshiExample.handlers;

import edu.brown.cs32.examples.moshiExample.csv.CSVWrapper;
import edu.brown.cs32.examples.moshiExample.csv.Counter;
import edu.brown.cs32.examples.moshiExample.csv.CounterUpdate;
import edu.brown.cs32.examples.moshiExample.handlers.Response.GetResponse;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Javadoc for StatsHandler class:
 *  This class is used to calculate the stats of the stored CSV in the CSVWrapper Class
 *  which will store a statsResponse or errorResponse that is serialized to the web API localhost
 * */
public class StatsHandler implements Route {
    private CSVWrapper csv;
    private List<List<String>> csvData;

    private Map<String, Object> statResponse;
    private Map<String, Object> errorResponse;

    /** Javadoc for StatsHandler constructor
     *  This constructor takes in a csvWrapper class, allowing the CSV parsed data to be shared
     *  between loadcsv, getcsv and stats which all involve functionality from the CSVWrapper class
     *  that stores the loaded CSV string data.
     * */
    public StatsHandler(CSVWrapper csvWrapper) {
        this.csv = csvWrapper;
        this.csvData = csvWrapper.getCsv();
        this.statResponse = new HashMap<String, Object>();
        this.errorResponse = new HashMap<String, Object>();
    }

    /** Javadoc for handle:
     * @param request the request to handle (inputted URL)
     * @param response use to modify properties of the response
     * @return NA, just serializes statsResponse or errorResponse to the frontend Web API
     * @throws Exception
     */

    @Override
    public Object handle(Request request, Response response) throws Exception {
        if (this.csv.getLoadSuccess()) {
            CounterUpdate count = new CounterUpdate();
            count.counter(this.csvData);
            this.statResponse.put("stats", "Columns: " + count.getColCount() + ", rows: " + count.getRowCount() + ", characters: " + count.getCharCount() + ", words: " + count.getWordCount());
            this.statResponse.put("result", "success");
            return new GetResponse(this.statResponse).serialize();
        }
        else {
            this.errorResponse.put("result", "error_datasource");
            return new GetResponse(this.errorResponse).serialize();
        }
    }

}
