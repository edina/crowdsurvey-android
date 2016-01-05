package uk.ac.edina.fieldtriplite.model;

public class RecordField {
    private String id;
    private Object val;
    private String label;

    /**
     * Get the record field Id
     * @return record field Id
     */
    public String getId() {
        return id;
    }

    /**
     * Set the Id for the record field
     * @param id of the record field
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the value of the record field
     * @return
     */
    public Object getVal() {
        return val;
    }

    /**
     * Get the value of the record field
     * @param val value for the field
     */
    public void setVal(Object val) {
        this.val = val;
    }

    /**
     * Get the label for the record field
     * @return label for the field
     */
    public String getLabel() {
        return label;
    }

    /**
     * Set the label for the field
     * @param label for the field
     */
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "RecordField{" +
                "id='" + id + '\'' +
                ", val=" + val +
                ", label='" + label + '\'' +
                '}';
    }
}
