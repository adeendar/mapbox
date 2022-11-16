package edu.brown.cs32.examples.moshiExample.handlers;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.Moshi;
import edu.brown.cs32.examples.moshiExample.geojsonjavatypes.Features;
import edu.brown.cs32.examples.moshiExample.geojsonjavatypes.GeoJsonResponse;
import edu.brown.cs32.examples.moshiExample.geojsonjavatypes.Geometry;
import edu.brown.cs32.examples.moshiExample.handlers.Response.GetResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;

public class GeoHandler implements Route {

    public GeoHandler() {}

    /**
     * Javadoc for handle
     * @param request the request to handle (inputted URL)
     * @param response use to modify properties of the response
     * @return response content which includes the geodata within the
     * latitude and longitude range
     * @throws Exception
     */

    public Object handle(Request request, Response response) throws Exception {

        Map<String, Object> geoMap = new HashMap<>();

        try {

            if (request.queryParams().size() != 4) {
                geoMap.clear();
                System.out.print(request.queryParams().toString());
                geoMap.put("result", "error_bad_request");
                return new GetResponse(geoMap).serialize();
            }
            else {
                Float latmin = Float.parseFloat(request.queryParams("latmin"));
                Float lonmin = Float.parseFloat(request.queryParams("lonmin"));
                Float latmax = Float.parseFloat(request.queryParams("latmax"));
                Float lonmax = Float.parseFloat(request.queryParams("lonmax"));
                if (latmin == null || lonmin == null || latmax == null || lonmax == null) {
                    throw new DataFormatException();
                }
                String filepath = "data/geojson/fullDownload.geojson";
                String geoJsonList = new String(Files.readAllBytes(Paths.get(filepath)));

                GeoJsonResponse geoJson = this.deserialize(geoJsonList);

                List<Features> featuresList = geoJson.features;

                Map<String, Object> output = new HashMap<>();
                output.put("type", "FeatureCollection");
                output.put("features", this.filter(featuresList, latmin, latmax, lonmin, lonmax));

                geoMap.put("result", "success");
                geoMap.put("data",output);
                return new GetResponse(geoMap).serialize();
            }

        } catch (Exception e) {
            geoMap.clear();
            e.printStackTrace();
            geoMap.put("result", "error_bad_request");
            return new GetResponse(geoMap).serialize();
        }

    }

    /**
     * Javadoc for filter
     * @param oldFeats a List<Features> object that is a list of feature in the geoJson
     * @return formattedFeaturesList, the features that are within the latitude and longitude
     * inputted into the API.
     */
    public List<Map<String, Object>> filter(List<Features> oldFeats, Float latmin, Float latmax, Float lonmin, Float lonmax) {

        List<Features> filteredFeatures = new ArrayList<>();

        for (Features feature : oldFeats) {

            Geometry geometry = feature.geometry;
            boolean withinBounds = false;
            if (geometry != null) {


                List<List<List<List<Float>>>> coordsList = feature.geometry.coordinates;
                List<List<Float>> coordinate = coordsList.get(0).get(0);

                for (List<Float> coord : coordinate) {
                    Float lat = coord.get(0);
                    Float lon = coord.get(1);

                    if ((lat < latmax) && (lat > latmin) && (lon < lonmax) && (lon
                        > lonmin)) {
                        withinBounds = true;
                    }
                }

                if (withinBounds) {
                    filteredFeatures.add(feature);
                }
            }
        }

        List<Map<String, Object>> formattedFeaturesList = new ArrayList<>();


        for (Features ff : filteredFeatures) {
            Map<String, Object> internalFeatMap = new HashMap<>();
            internalFeatMap.put("type", "Feature");

            Geometry geometry = ff.geometry;
            Map<String, Object> geometryMap = new HashMap<>();
            geometryMap.put("type", "MultiPolygon");
            geometryMap.put("coordinates", geometry.coordinates);

            internalFeatMap.put("geometry", geometryMap);
            internalFeatMap.put("properties", ff.properties);

            formattedFeaturesList.add(internalFeatMap);
        }

        return formattedFeaturesList;
    }

    /**
     * Javadoc for deserialize:
     *    * @throws JsonDataException if the json is not able to be properly adapted
     *    * @throws IOException for any input/output errors
     * @param jsonList an inputted jsonList (the geodata) as a string which is being deserialized from the web API
     * @return deserializedJson as GeoJsonResponse class object. This allows for the interior geoJson data to be
     * accessed so the List of features can be filtered!
     * @throws JsonDataException
     * @throws IOException
     */

    public GeoJsonResponse deserialize (String jsonList) throws JsonDataException, IOException {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<GeoJsonResponse> geoJsonAdapter = moshi.adapter(GeoJsonResponse.class);
        return geoJsonAdapter.fromJson(jsonList);
    }

}
