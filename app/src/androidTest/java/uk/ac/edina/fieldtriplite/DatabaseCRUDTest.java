package uk.ac.edina.fieldtriplite;

import android.support.test.rule.ActivityTestRule;
import android.test.ActivityInstrumentationTestCase2;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import uk.ac.edina.fieldtriplite.activity.SurveyActivity;
import uk.ac.edina.fieldtriplite.document.Record;
import uk.ac.edina.fieldtriplite.model.RecordModel;
import static org.junit.Assert.*;

/**
 * Created by murrayking on 05/01/2016.
 */
public class DatabaseCRUDTest {

    /**
     * A JUnit {@link Rule @Rule} to launch your activity under test. This is a replacement
     * for {@link ActivityInstrumentationTestCase2}.
     * <p>
     * Rules are interceptors which are executed for each test method and will run before
     * any of your setup code in the {@link Before @Before} method.
     * <p>
     * {@link ActivityTestRule} will create and launch of the activity for you and also expose
     * the activity under test. To get a reference to the activity you can use
     * the {@link ActivityTestRule#getActivity()} method.
     */
    @Rule
    public ActivityTestRule<SurveyActivity> activityRule = new ActivityTestRule<>(
            SurveyActivity.class);

    /**
     * Test the initialization of the database
     */
    @Test
    public void testDatabaseInitialization() {
        FieldTripApplication application = (FieldTripApplication)activityRule.getActivity().getApplicationContext();

        assertNotNull(application.getDatabase());
    }

    /**
     * Test storing a record in the database
     */
    @Test
    public void testDatabasePutRecord() {

        FieldTripApplication application = (FieldTripApplication)activityRule.getActivity().getApplicationContext();
        Database database = application.getDatabase();
        RecordModel record = new RecordModel();

        try {
            Document document = Record.putRecord(database, new RecordModel());
            assertNotNull(document);
        } catch (CouchbaseLiteException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test store and retrieve a record in the database
     */
    @Test
    public void testDatabaseGetRecord() {

        FieldTripApplication application = (FieldTripApplication)activityRule.getActivity().getApplicationContext();
        Database database = application.getDatabase();
        try {
            RecordModel record = new RecordModel();
            record.setId("89be54fd-f559-4c36-80d0-721035dd17c8");
            Document document = Record.putRecord(database, record);
            assertNotNull(document);

            RecordModel retrievedRecord = Record.getRecord(database, document.getId());
            assertNotNull(retrievedRecord);

            assertEquals(record.getId(), retrievedRecord.getId());
        } catch (CouchbaseLiteException e) {
            fail(e.getMessage());
        }
    }

}
