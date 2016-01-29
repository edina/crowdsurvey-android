package uk.ac.edina.fieldtriplite.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.ObjectCallback;

import uk.ac.edina.fieldtriplite.FieldTripApplication;
import uk.ac.edina.fieldtriplite.document.Survey;
import uk.ac.edina.fieldtriplite.model.SurveyModel;
import uk.ac.edina.fieldtriplite.model.SurveyModelRepository;

/**
 * Created by murrayking on 23/12/2015.
 */
public class SurveyServiceImpl extends SurveyServiceBase {

    private static final String LOG_TAG = "SurveyService";
    private static String DEFAULT_SURVEY = "566ed9b30351d817555158cd";
    private static String emulatorUrl =  "http://129.215.169.232:3001/api";

    public SurveyServiceImpl(Context context) {
        super(context);
    }

    public void getCustomSurvey(ObjectCallback<SurveyModel> callback) {
        getCustomSurvey(DEFAULT_SURVEY, callback);
    }

    public void getCustomSurvey(String surveyId, ObjectCallback<SurveyModel> callback) {
        liveCall(surveyId, callback);
    }

    public void downloadSurvey(String surveyId) {
        RestAdapter adapter = new RestAdapter(context, emulatorUrl);
        SurveyModelRepository repository = adapter.createRepository(SurveyModelRepository.class);
        final Database database =  ((FieldTripApplication)context.getApplicationContext()).getDatabase();


        repository.findById(surveyId, new ObjectCallback<SurveyModel>() {
            @Override
            public void onSuccess(SurveyModel survey) {
                Log.d(LOG_TAG, "REST Result" + survey.toString());
                try {
                    Survey.putSurvey(database, survey);
                    activateSurvey(survey.getId());

                } catch (CouchbaseLiteException ex) {
                    Log.e(LOG_TAG, ex.getMessage());
                }
            }

            @Override
            public void onError(Throwable t) {
                Log.e(LOG_TAG, t.toString());
            }
        });
    }

    public void activateSurvey(String surveyId) {
        SharedPreferences settings = context.getSharedPreferences(FieldTripApplication.PREFS_NAME,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = settings.edit();
        editor.putString("activeSurvey", surveyId);
    }

    private void liveCall(String surveyId, ObjectCallback<SurveyModel> callback) {
        RestAdapter adapter = new RestAdapter(context, emulatorUrl);
        SurveyModelRepository repository = adapter.createRepository(SurveyModelRepository.class);
        repository.findById(surveyId, callback);
    }
}
