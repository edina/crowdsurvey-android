package uk.ac.edina.fieldtriplite.model;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Created by murray on 08/12/15.
 */
public class SurveyParserTest {
    SurveyModel surveyModel;

    @Before
    public void setUp() {
        surveyModel = new SurveyModel();
        List<Map<String, Object>> fields = new ArrayList<>();

        Map<String, Object> fieldOne = new HashMap<>();
        fieldOne.put("id", "form-text-1");
        fieldOne.put("type", "text");
        fieldOne.put("label", "1. Date of survey");
        fieldOne.put("required", true);
        fieldOne.put("persistent", true);
        Map<String, Object> properties = new HashMap<>();
        properties.put("prefix", "record");
        properties.put("placeholder", "Place default text here (if any)");
        properties.put("max-chars", "30");
        properties.put("other", Boolean.TRUE);

        properties.put("options", new ArrayList(){{
            add("No");
            add("Yes");
            add("other");
        }});


        fieldOne.put("properties", properties);

        fields.add(fieldOne);
        surveyModel.setFields(fields);
    }

    SurveyModel createSurveyModelWithOptions(){

        SurveyModel s = new SurveyModel();
        List<Map<String, Object>> fields = new ArrayList<>();

        Map<String, Object> fieldOne = new HashMap<>();
        fieldOne.put("id", "form-text-1");
        fieldOne.put("type", "text");
        fieldOne.put("label", "1. Date of survey");
        fieldOne.put("required", true);
        fieldOne.put("persistent", true);
        Map<String, Object> properties = new HashMap<>();
        properties.put("prefix", "record");
        properties.put("placeholder", "Place default text here (if any)");
        properties.put("max-chars", "30");
        properties.put("other", Boolean.TRUE);

        properties.put("options", new ArrayList(){{
            add( new ArrayList() {{ add("Label"); add("ImageLocation");}});
            add( new ArrayList() {{ add("Label2"); add("ImageLocation2");}});
        }});


        fieldOne.put("properties", properties);

        fields.add(fieldOne);
        s.setFields(fields);
        return s;

    }

    SurveyModel createSurveyModelWithIllegalType(){

        SurveyModel s = new SurveyModel();
        List<Map<String, Object>> fields = new ArrayList<>();

        Map<String, Object> fieldOne = new HashMap<>();
        fieldOne.put("id", "form-text-1");
        fieldOne.put("type", "no there");
        fieldOne.put("label", "1. Date of survey");
        fieldOne.put("required", true);
        fieldOne.put("persistent", true);
        Map<String, Object> properties = new HashMap<>();
        properties.put("prefix", "record");
        properties.put("placeholder", "Place default text here (if any)");
        properties.put("max-chars", "30");
        properties.put("other", Boolean.TRUE);

        properties.put("options", new ArrayList(){{
            add( new ArrayList() {{ add("Label"); add("ImageLocation");}});
            add( new ArrayList() {{ add("Label2"); add("ImageLocation2");}});
        }});


        fieldOne.put("properties", properties);

        fields.add(fieldOne);
        s.setFields(fields);
        return s;

    }


    SurveyModel createSurveyModelWithImageField(){

        SurveyModel s = new SurveyModel();
        List<Map<String, Object>> fields = new ArrayList<>();

        Map<String, Object> fieldOne = new HashMap<>();
        fieldOne.put("id", "form-text-1");
        fieldOne.put("type", "text");
        fieldOne.put("label", "1. Date of survey");
        fieldOne.put("required", true);
        fieldOne.put("persistent", true);
        Map<String, Object> properties = new HashMap<>();
        properties.put("prefix", "record");
        properties.put("placeholder", "Place default text here (if any)");
        properties.put("max-chars", "30");
        properties.put("other", Boolean.TRUE);

        properties.put("options", new ArrayList(){{
            add( new ArrayList() {{ add("Label"); add("ImageLocation");}});
            add( new ArrayList() {{ add("Label2"); add("ImageLocation2");}});
        }});


        fieldOne.put("properties", properties);
        /*
        "id": "form-image-1",
                "type": "image",
                "label": "Take a photo",
                "required": true,
                "persistent": false,
                "properties": {
            "los": false,
                    "multi-image": false
        }*/
        Map<String, Object> fieldTwo = new HashMap<>();
        fieldOne.put("id", "form-image-1");
        fieldOne.put("type", "image");
        fieldOne.put("label", "Take a photo");
        fieldOne.put("required", true);
        fieldOne.put("persistent", false);


        Map<String, Object> fieldTwoProperties = new HashMap<>();
        fieldTwoProperties.put("los", false);
        fieldTwoProperties.put("multi-image", false);
        fields.add(fieldOne);
        fields.add(fieldTwo);
        s.setFields(fields);
        return s;

    }

    @Test
    public void testBuildFields() {
        SurveyParser surveyParser = new SurveyParser();
        List<SurveyField> surveyFields = surveyParser.buildFields(surveyModel);
        assertNotNull(surveyFields);
        assertEquals(1, surveyFields.size());
        SurveyField surveyField = surveyFields.get(0);
        assertEquals("No id", "form-text-1", surveyField.getId());
        assertEquals("No type", SurveyField.Type.TEXT, surveyField.getType());
        assertEquals("No label", "1. Date of survey", surveyField.getLabel());
        assertEquals("No required", Boolean.TRUE, surveyField.isRequired());
        assertEquals("No persistent", Boolean.TRUE, surveyField.isPersistent());
    }
    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Test
    public void testtIllegalTypeOfField() {

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Illegal Survey Field Type");

        SurveyParser surveyParser = new SurveyParser();
        SurveyModel modelWithIllegalType = createSurveyModelWithIllegalType();
        List<SurveyField> surveyFields = surveyParser.buildFields(modelWithIllegalType);


    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuildFieldWithMissingParams() {

        SurveyModel surveyModel2 = new SurveyModel();
        List<Map<String, Object>> fields2 = new ArrayList<>();

        Map<String, Object> fieldOne = new HashMap<>();
        fieldOne.put("label", "1. Date of survey");
        fieldOne.put("required", true);
        fieldOne.put("persistent", true);
        Map<String, Object> properties = new HashMap<>();
        properties.put("prefix", "record");
        properties.put("placeholder", "Place default text here (if any)");
        properties.put("max-chars", "30");
        fieldOne.put("properties", properties);

        fields2.add(fieldOne);
        surveyModel2.setFields(fields2);
        SurveyParser surveyParser = new SurveyParser();

        List<SurveyField> surveyFields = surveyParser.buildFields(surveyModel2);


    }

    @Test
    public void testSurveyFieldProperties() {

        SurveyParser surveyParser = new SurveyParser();
        List<SurveyField> surveyFields = surveyParser.buildFields(surveyModel);
        assertNotNull(surveyFields);
        assertEquals(1, surveyFields.size());
        SurveyField surveyField = surveyFields.get(0);
        SurveyFieldProperties surveyFieldProperties = surveyField.getSurveyFieldProperties();
        assertNotNull(surveyFieldProperties);

        assertEquals("No other", Boolean.TRUE, surveyFieldProperties.isOther());
        assertNotNull(surveyFieldProperties.getOptions());
        List<Option> options = surveyFieldProperties.getOptions();

        Option option1 = options.get(0);
        assertEquals("No option 1", "No", option1.getLabel());
        Option option2 = options.get(1);
        assertEquals("No option 2", "Yes", option2.getLabel());
        Option option3 = options.get(2);
        assertEquals("No option 3", "other", option3.getLabel());
    }

    @Test
    public void testImageFieldProperties() {

        SurveyParser surveyParser = new SurveyParser();
        List<SurveyField> surveyFields = surveyParser.buildFields(createSurveyModelWithImageField());
        assertNotNull(surveyFields);
        assertEquals(2, surveyFields.size());
        SurveyField surveyField = surveyFields.get(1);
        SurveyFieldProperties surveyFieldProperties = surveyField.getSurveyFieldProperties();
        assertNotNull(surveyFieldProperties);

        assertEquals("No other", Boolean.TRUE, surveyFieldProperties.isOther());
        assertNotNull(surveyFieldProperties.getOptions());
        List<Option> options = surveyFieldProperties.getOptions();


    }

    @Test
    public void testSurveyFieldPropertiesOptions() {

        SurveyModel surveyModel = createSurveyModelWithOptions();

        SurveyParser surveyParser = new SurveyParser();
        List<SurveyField> surveyFields = surveyParser.buildFields(surveyModel);
        assertNotNull(surveyFields);
        assertEquals(1, surveyFields.size());
        SurveyField surveyField = surveyFields.get(0);
        SurveyFieldProperties surveyFieldProperties = surveyField.getSurveyFieldProperties();
        assertNotNull(surveyFieldProperties);

        assertEquals("No other", Boolean.TRUE, surveyFieldProperties.isOther());
        assertNotNull(surveyFieldProperties.getOptions());
        List<Option> options = surveyFieldProperties.getOptions();

        Option option1 = options.get(0);
        assertEquals("No option Label", "Label", option1.getLabel());
        assertEquals("No option Image Location", "ImageLocation", option1.getImageLocation().get());

        Option option2 = options.get(1);
        assertEquals("No option Label", "Label2", option2.getLabel());
        assertEquals("No option Image Location", "ImageLocation2", option2.getImageLocation().get());
    }

    @Test
    public void testVisitSurveyModels(){

        SurveyField surveyField = new SurveyFieldBase.SurveyFieldBuilder().id("testid").label("label").type("text").build();

    }

}
