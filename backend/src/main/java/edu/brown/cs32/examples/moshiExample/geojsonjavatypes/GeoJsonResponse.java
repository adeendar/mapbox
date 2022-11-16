package edu.brown.cs32.examples.moshiExample.geojsonjavatypes;

import java.util.List;
import java.util.Map;

/** Javadoc for GeoJsonResponse:
 *  This class is used to help parse through the Moshi Json. This class is provided
 *  to the adapter so we can access properties in the json.
 * */

public class GeoJsonResponse {
  String type;
  public List<Features> features;

  public GeoJsonResponse(List<Features> newFeats) {
    this.features = newFeats;
  }
}


