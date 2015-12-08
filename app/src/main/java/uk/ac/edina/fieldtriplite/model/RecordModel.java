package uk.ac.edina.fieldtriplite.model;
import com.strongloop.android.loopback.Model;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.VoidCallback;
import com.strongloop.android.remoting.BeanUtil;
import com.strongloop.android.remoting.BeanUtil;

import java.util.List;
import java.util.Map;

/**
 * Loopback model of Record.
 */
public class RecordModel extends Model {

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
}
