package edu.brown.cs32.examples.moshiExample.geojsonjavatypes;

import java.util.Map;

public class Features {

    /** Javadoc for features
     *  This class allows us to access specific properties of the geoJSON to check
     *  the coordinates of specific features.
     * */

    String type;
    public Geometry geometry;
    public Map<String, Object> properties;
}
