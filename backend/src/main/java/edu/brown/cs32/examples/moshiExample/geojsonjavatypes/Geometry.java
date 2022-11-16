package edu.brown.cs32.examples.moshiExample.geojsonjavatypes;

import java.util.List;

/** Javadoc for Geometry:
 * This class is used to allow the GeoHandler to utilize the coordinates
 * within the Features in the geoJson, seeing if the Feature is within
 * the bounding range of the max lat and long and min lat and long.
 * */
public class Geometry {
    String type;
    public List<List<List<List<Float>>>> coordinates;
}
