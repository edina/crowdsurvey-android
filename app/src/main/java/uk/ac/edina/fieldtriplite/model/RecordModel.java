package uk.ac.edina.fieldtriplite.model;

import java.util.ArrayList;
import java.util.List;

public class RecordModel extends GeoJSONModel {
    private String id;
    private String name;
    private List<RecordField> fields = new ArrayList<RecordField>();

    public RecordModel() {
        super();
        properties.put("fields", fields);
    }

    /**
     * Get the record Id
     * @return record Id
     */
    public String getId() {
        return id;
    }

    /**
     * Set the recordId
     * @param recordId Record Id
     */
    public void setId(String recordId) {
        id = recordId;
    }

    /**
     * Add a field to the record
     * @param field a record field
     */
    public void addRecordField(RecordField field) {
        fields.add(field);
    }

    /**
     * Get the list of field in the record
     * @return list of fields in the record
     */
    public List<RecordField> getRecordFields() {
        return fields;
    }

    @Override
    public String toString() {
        return "RecordModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", fields=" + fields +
                '}';
    }
}
