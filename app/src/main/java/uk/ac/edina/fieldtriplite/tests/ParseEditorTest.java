package uk.ac.edina.fieldtriplite.tests;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import uk.ac.edina.fieldtriplite.model.ParseEditor;
import uk.ac.edina.fieldtriplite.model.RecordModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by murray on 08/12/15.
 */
public class ParseEditorTest {
    RecordModel recordModel;

    @Before
    public void setUp(){
        recordModel = new RecordModel();
        List<Map<String,Object>> fields = new ArrayList<>();

        Map<String,Object> fieldOne = new HashMap<>() ;
        fieldOne.put("id", "form-text-1");
        fieldOne.put("type", "text");
        fieldOne.put("label", "1. Date of survey");
        fieldOne.put("required", true);
        fieldOne.put("persistent", true);
        Map<String,Object> properties = new HashMap<>();
        properties.put("prefix", "record");
        properties.put("placeholder", "Place default text here (if any)");
        properties.put("max-chars", "30");
        fieldOne.put("properties", properties);

        recordModel.setFields(fields);
    }

    @Test
    public void testBuildFields() {
        ParseEditor parseEditor = new ParseEditor();
        parseEditor.buildFields(recordModel);
        assertEquals("test", "test");

    }
}
