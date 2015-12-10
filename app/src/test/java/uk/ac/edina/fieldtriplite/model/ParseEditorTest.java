package uk.ac.edina.fieldtriplite.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


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

        fields.add(fieldOne);
        recordModel.setFields(fields);
    }

    @Test
    public void testBuildFields() {
        ParseEditor parseEditor = new ParseEditor();
        List<RecordField> recordFields =  parseEditor.buildFields(recordModel);
        assertNotNull(recordFields);
        assertEquals(1, recordFields.size());
        RecordField recordField = recordFields.get(0);
        assertEquals("No id", "form-text-1", recordField.getId());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuildFieldWithMissingParams() {

        RecordModel recordModel2 = new RecordModel();
        List<Map<String,Object>> fields2 = new ArrayList<>();

        Map<String,Object> fieldOne = new HashMap<>() ;
        fieldOne.put("label", "1. Date of survey");
        fieldOne.put("required", true);
        fieldOne.put("persistent", true);
        Map<String,Object> properties = new HashMap<>();
        properties.put("prefix", "record");
        properties.put("placeholder", "Place default text here (if any)");
        properties.put("max-chars", "30");
        fieldOne.put("properties", properties);

        fields2.add(fieldOne);
        recordModel2.setFields(fields2);
        ParseEditor parseEditor = new ParseEditor();

        List<RecordField> recordFields =  parseEditor.buildFields(recordModel2);


    }
}
