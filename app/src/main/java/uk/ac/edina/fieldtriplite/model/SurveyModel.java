package uk.ac.edina.fieldtriplite.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.strongloop.android.loopback.Model;

import java.util.List;
import java.util.Map;

/**
 * Loopback model of Record.
 */
@JsonIgnoreProperties({ "repository" })
public class SurveyModel extends Model {

    private String id;

    private List<Map<String, Object>> fields;
    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Map<String, Object>> getFields() {
        return fields;
    }

    public void setFields(List<Map<String, Object>> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "SurveyModel{" +
                "id='" + id + '\'' +
                ", fields=" + fields +
                '}';
    }
}
