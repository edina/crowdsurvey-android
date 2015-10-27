package uk.ac.edina.fieldtriplite.utils;

import android.location.Location;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by benbutchart on 25/10/15.
 */
public class LocationUtils {

    private static String LOG_TAG = "LocationUtils" ;

    public static String createEmptyGeoJSONLineString(String id) {

        JSONObject geoJSON = new JSONObject();

        try {
            JSONArray coordinatesArray = new JSONArray();
            JSONObject geometry = new JSONObject();
            geometry.put("type", "LineString");
            geometry.put("coordinates", coordinatesArray);

            JSONObject properties = new JSONObject() ;
            properties.put("id", id) ;


            geoJSON.put("type", "Feature");
            geoJSON.put("geometry", geometry);
            geoJSON.put("properties", properties) ;

        } catch (JSONException e) {
            Log.e(LOG_TAG, "createEmptyJSONString failed: " + e);
            throw new IllegalStateException("createEmptyJSONString failed ", e);
        }

        return geoJSON.toString();

    }

    public static String getLocationAsGeoJSONPoint(Location location) {
        JSONObject geoJSON = new JSONObject();

        try {
            JSONArray coordinatesArray = new JSONArray();
            coordinatesArray.put(location.getLongitude());
            coordinatesArray.put(location.getLatitude());
            JSONObject geometry = new JSONObject();
            geometry.put("type", "Point");
            geometry.put("coordinates", coordinatesArray);
            geoJSON.put("type", "Feature");
            geoJSON.put("geometry", geometry);

        } catch (JSONException e) {
            Log.e(LOG_TAG, "createEmptyJSONString failed: " + e);
            throw new IllegalStateException("createEmptyJSONString failed ", e);
        }

        return geoJSON.toString();
    }

    public static String getMockGeoJSONPoint(Location location) {
        JSONObject geoJSON = new JSONObject();

        try {
            JSONArray coordinatesArray = new JSONArray();
            coordinatesArray.put(location.getLongitude());
            coordinatesArray.put(location.getLatitude());
            JSONObject geometry = new JSONObject();
            geometry.put("type", "Point");
            geometry.put("coordinates", coordinatesArray);
            geoJSON.put("type", "Feature");
            geoJSON.put("geometry", geometry);

        } catch (JSONException e) {
            Log.e(LOG_TAG, "createEmptyJSONString failed: " + e);
            throw new IllegalStateException("createEmptyJSONString failed ", e);
        }

        return geoJSON.toString();
    }


    public static Location createLocation(double lat, double lng, float accuracy) {
        // Create a new Location
        Location newLocation = new Location("MOCK_LOCATION_PROVIDER");
        newLocation.setLatitude(lat);
        newLocation.setLongitude(lng);
        newLocation.setAccuracy(accuracy);
        return newLocation;
    }

}
