package uk.ac.edina.fieldtriplite;

import android.app.Application;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;

import java.io.IOException;

import uk.ac.edina.fieldtriplite.service.SurveyService;
import uk.ac.edina.fieldtriplite.service.SurveyServiceImpl;

/**
 * Created by murrayking on 05/01/2016.
 */
public class FieldTripApplication extends Application {

    public static final String LOG_TAG = "ApplicationContext" ;
    // Couchbase properties
    private static final String DATABASE_NAME = "crowdsurveydb";
    private Database database;
    private Manager manager;
    private SurveyService surveyService = new SurveyServiceImpl();


    @Override
    public void onCreate() {
        super.onCreate();
        initDatabase();

    }

    /**
     * Initialize the couchbase database
     */
    private void initDatabase() {
        try {

            Manager.enableLogging(LOG_TAG, com.couchbase.lite.util.Log.VERBOSE);
            Manager.enableLogging(com.couchbase.lite.util.Log.TAG, com.couchbase.lite.util.Log.VERBOSE);
            Manager.enableLogging(com.couchbase.lite.util.Log.TAG_SYNC_ASYNC_TASK, com.couchbase.lite.util.Log.VERBOSE);
            Manager.enableLogging(com.couchbase.lite.util.Log.TAG_SYNC, com.couchbase.lite.util.Log.VERBOSE);
            Manager.enableLogging(com.couchbase.lite.util.Log.TAG_QUERY, com.couchbase.lite.util.Log.VERBOSE);
            Manager.enableLogging(com.couchbase.lite.util.Log.TAG_VIEW, com.couchbase.lite.util.Log.VERBOSE);
            Manager.enableLogging(com.couchbase.lite.util.Log.TAG_DATABASE, com.couchbase.lite.util.Log.VERBOSE);

            manager = new Manager(new AndroidContext(getApplicationContext()), Manager.DEFAULT_OPTIONS);
        } catch (IOException e) {
            com.couchbase.lite.util.Log.e(LOG_TAG, "Cannot create Manager object", e);
            return;
        }

        try {
            database = manager.getDatabase(DATABASE_NAME);
        } catch (CouchbaseLiteException e) {
            com.couchbase.lite.util.Log.e(LOG_TAG, "Cannot get Database", e);
            return;
        }
    }

    /**
     * Getter for the application database
     *
     * @return current instance of the application database
     */
    public Database getDatabase() {
        return this.database;
    }

    public SurveyService getSurveyService() {
        return surveyService;
    }

    /**
     * Used for injecting mock service
     * @param surveyService
     */
    public void setSurveyService(SurveyService surveyService) {
        this.surveyService = surveyService;
    }
}
