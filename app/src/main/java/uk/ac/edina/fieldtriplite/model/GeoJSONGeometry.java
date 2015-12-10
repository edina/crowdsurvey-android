package uk.ac.edina.fieldtriplite.model;

public class GeoJSONGeometry {
    private String type;
    private Object coordinates;

    /**
     * Get the GeoJSON geometry type
     * @return GeoJSON geometry type
     */
    public String getType() {
        return type;
    }

    /**
     * Set GeoJSON geometry type
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the coordinates for the geometry
     * @return the coordinates for the geometry
     */
    public Object getCoordinates() {
        return coordinates;
    }

    /**
     * Set the coordinates for the geometry
     * @param coordinates of the geometry
     */
    public void setCoordinates(Object coordinates) {
        this.coordinates = coordinates;
    }
}
