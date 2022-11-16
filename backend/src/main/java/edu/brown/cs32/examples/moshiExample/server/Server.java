package edu.brown.cs32.examples.moshiExample.server;

import static spark.Spark.after;
import edu.brown.cs32.examples.moshiExample.csv.CSVWrapper;
import edu.brown.cs32.examples.moshiExample.handlers.*;
import spark.Spark;

/**
 * Top-level class. Contains the main() method which starts Spark and runs the various handlers,
 * which a user could add to by adding a new datasource with their path for the url and handler.
 * Datasource lets all handlers share the same state, allowing for very integratable functionality.
 */

public class Server {

    /**
     * Javadoc for main method
     * This method runs the server port and sets up the handlers!
     *
     * @param args a list of strings which are passed into the command line. This
     *             is not used in our program because queries are submitted via API url
     * @returns void just instantiates the handlers to properly display jsonData to the API
     * based on the users request into the URL query
     */
    public static void main(String[] args) {
        CSVWrapper csvWrapper = new CSVWrapper();

        // Spark is a class that lets you set the port for a local server
        Spark.port(3232);
        /*
            Setting CORS headers to allow cross-origin requests from the client; this is necessary for the client to
            be able to make requests to the server.

            By setting the Access-Control-Allow-Origin header to "*", we allow requests from any origin.
            This is not a good idea in real-world applications, since it opens up your server to cross-origin requests
            from any website. Instead, you should set this header to the origin of your client, or a list of origins
            that you trust.

            By setting the Access-Control-Allow-Methods header to "*", we allow requests with any HTTP method.
            Again, it's generally better to be more specific here and only allow the methods you need, but for
            this demo we'll allow all methods.

            We recommend you learn more about CORS with these resources:
                - https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS
                - A-
         */
        after(
            (request, response) -> {
                response.header("Access-Control-Allow-Origin", "*");
                response.header("Access-Control-Allow-Methods", "*");
            });

        /**
         * Information for adding additonal handlers (extensibility)!:
         * Create a Spark.get(<handlerpathname>, new HandlerClass())
         * and have the handler class implement Route and the handle
         * method. This allows for users to create their own web API paths
         * and handlers for those paths!
         */
        // Setting up the all handlers!
        Spark.get("loadcsv", new LoadCSVHandler(csvWrapper));
        Spark.get("getcsv", new GetCSVHandler(csvWrapper));
        Spark.get("weather", new WeatherHandler());
        Spark.get("stats", new StatsHandler(csvWrapper));
        Spark.get("geodata", new GeoHandler());
        Spark.init();
        Spark.awaitInitialization();
        System.out.println("Server started.");
    }
}
