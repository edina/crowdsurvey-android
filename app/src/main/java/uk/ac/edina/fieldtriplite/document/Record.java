package uk.ac.edina.fieldtriplite.document;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.util.Log;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import uk.ac.edina.fieldtriplite.FieldTripMap;
import uk.ac.edina.fieldtriplite.model.SurveyModel;

public class Record {
    private static final String VIEW_NAME = "records";
    private static final String DOC_TYPE = "record";
    private static final String RECORD_PROP = "record";

    /**
     * Put a record in the database
     * @param database an instance of the database
     * @param record an instance of a record
     * @return document created in the database
     * @throws CouchbaseLiteException
     */
    public static Document putRecord(Database database, SurveyModel record)  throws CouchbaseLiteException {
        Document document = database.createDocument();

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("type", DOC_TYPE);
        properties.put(RECORD_PROP, record);

        document.putProperties(properties);

        Log.d(FieldTripMap.LOG_TAG, "Saved record id: %s", document.getId());

        return document;
    }

    /**
     * Retrieve a record by id from the database
     * @param database an instance of the database
     * @param recordId id of the record in the database
     * @return a record
     * @throws CouchbaseLiteException
     */
    public static SurveyModel getRecord(Database database, String recordId)  throws CouchbaseLiteException {
        Document document = database.getDocument(recordId);
        Object recordHashMap = document.getProperty(RECORD_PROP);

        ObjectMapper mapper = new ObjectMapper();
        SurveyModel record = mapper.convertValue(recordHashMap, SurveyModel.class);

        return record;
    }

}
