package edu.brown.cs32.examples.moshiExample.server;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/** Javadoc for HTTPUtilities class:
 * This class is used for processing any get requests that are needed
 * by a user to extract data from an API. This class allows the user
 * to input a url corresponding to the API they want to use, and returns
 * a string corresponding to the jsonData the API references, or an
 * error if an IOException or InterruptedException occurs.
 * */

public class HttpUtilities {

    /**
     * Constructor
     */
    public HttpUtilities() {
    }

    /**
     * Javadoc for getRequest:
     *
     * @param url a string that represents the URL which is being inputted into the get request
     * @return stringJson a string that is the httpResponse body, a string version of the json
     * the get request is referencing
     * @throws IOException
     * @throws InterruptedException
     */
    public String getRequest(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
