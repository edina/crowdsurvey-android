package uk.ac.edina.fieldtriplite.document;

import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import uk.ac.edina.fieldtriplite.model.SurveyModel;

public class Survey {
    private static final String LOG_TAG = "Survey";
    private static final String VIEW_NAME = "surveys";
    private static final String DOC_TYPE = "survey";
    private static final String DATA_PROP = "data";

    /**
     * Put a survey in the local database
     * @param database an instance of the database
     * @param survey an instance of a survey
     * @return document created in the database
     * @throws CouchbaseLiteException
     */
    public static Document putSurvey(Database database, SurveyModel survey)  throws CouchbaseLiteException {
        Document document;
        Map<String, Object> currentProperties;

        if (survey.getId() != null) {
            document = database.getDocument(survey.getId());
        } else {
            document = database.createDocument();
        }

        Map<String, Object> properties = new HashMap<>();
        if((currentProperties = document.getProperties()) != null) {
            properties.putAll(currentProperties);
        }
        properties.put("type", DOC_TYPE);
        properties.put(DATA_PROP, survey);

        document.putProperties(properties);

        Log.d(LOG_TAG, "Survey id [" + document.getId().toString() + "] stored");

        return document;
    }

    /**
     * Retrieve a survey by id from the local database
     * @param database an instance of the database
     * @param surveyId id of the survey in the database
     * @return a survey
     * @throws CouchbaseLiteException
     */
    public static SurveyModel getSurvey(Database database, String surveyId)  throws CouchbaseLiteException {
        Document document = database.getDocument(surveyId);
        Object surveyHashMap = document.getProperty(DATA_PROP);

        ObjectMapper mapper = new ObjectMapper();
        SurveyModel survey = mapper.convertValue(surveyHashMap, SurveyModel.class);

        return survey;
    }
}
