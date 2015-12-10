package uk.ac.edina.fieldtriplite.model;

import java.util.HashMap;

public class GeoJSONModel {
    protected String type;
    protected GeoJSONGeometry geometry;
    protected HashMap<String, Object> properties = new HashMap<String, Object>();

    /**
     * Get the type of the GeoJSON
     * @return the type of the GeoJSON
     */
    public String getType() {
        return type;
    }

    /**
     * Set the type of the GeoJSON
     * @param type of the GeoJSON
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get geometry of the GeoJSON
     * @return GeoJSON geometry
     */
    public GeoJSONGeometry getGeometry() {
        return geometry;
    }

    /**
     *
     * @param geometry
     */
    public void setGeometry(GeoJSONGeometry geometry) {
        this.geometry = geometry;
    }

    /**
     *
     * @return
     */
    public HashMap<String, Object> getProperties() {
        return properties;
    }

    /**
     *
     * @param properties
     */
    public void setProperties(HashMap<String, Object> properties) {
        this.properties = properties;
    }
}
