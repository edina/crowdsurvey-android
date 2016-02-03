package uk.ac.edina.fieldtriplite.model;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import com.strongloop.android.loopback.Model;
import com.strongloop.android.loopback.callbacks.VoidCallback;
import com.strongloop.android.remoting.adapters.Adapter;

import java.io.IOException;
import java.util.Map;

public class AssetModel extends Model {
    private String id;
    private String mimetype;
    private int length;
    private String filename;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
