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
public class ParseSurveyTest {
    SurveyModel surveyModel;

    @Before
    public void setUp(){
        surveyModel = new SurveyModel();
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
        properties.put("other", Boolean.TRUE);
        fieldOne.put("properties", properties);

        fields.add(fieldOne);
        surveyModel.setFields(fields);
    }

    @Test
    public void testBuildFields() {
        ParseSurvey parseSurvey = new ParseSurvey();
        List<SurveyField> surveyFields =  parseSurvey.buildFields(surveyModel);
        assertNotNull(surveyFields);
        assertEquals(1, surveyFields.size());
        SurveyField surveyField = surveyFields.get(0);
        assertEquals("No id", "form-text-1", surveyField.getId());
        assertEquals("No type", "text", surveyField.getType());
        assertEquals("No label", "1. Date of survey", surveyField.getLabel());
        assertEquals("No required", Boolean.TRUE, surveyField.isRequired());
        assertEquals("No persistent", Boolean.TRUE, surveyField.isPersistent());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuildFieldWithMissingParams() {

        SurveyModel surveyModel2 = new SurveyModel();
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
        surveyModel2.setFields(fields2);
        ParseSurvey parseSurvey = new ParseSurvey();

        List<SurveyField> surveyFields =  parseSurvey.buildFields(surveyModel2);


    }

    @Test
    public void testSurveyFieldProperties(){
        ParseSurvey parseSurvey = new ParseSurvey();
        List<SurveyField> surveyFields =  parseSurvey.buildFields(surveyModel);
        assertNotNull(surveyFields);
        assertEquals(1, surveyFields.size());
        SurveyField surveyField = surveyFields.get(0);
        SurveyFieldProperties surveyFieldProperties = surveyField.getSurveyFieldProperties();
        assertNotNull(surveyFieldProperties);

        assertEquals("No other", Boolean.TRUE, surveyFieldProperties.isOther());

    }
}
