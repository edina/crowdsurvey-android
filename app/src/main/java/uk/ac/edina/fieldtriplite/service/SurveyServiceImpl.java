package uk.ac.edina.fieldtriplite.service;

import android.app.Activity;
import android.content.Context;

import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.ObjectCallback;

import uk.ac.edina.fieldtriplite.model.SurveyModel;
import uk.ac.edina.fieldtriplite.model.SurveyModelRepository;

/**
 * Created by murrayking on 23/12/2015.
 */
public class SurveyServiceImpl implements SurveyService {

    private static String DEFAULT_SURVEY = "566ed9b30351d817555158cd";

    public void getCustomSurvey(Activity context, ObjectCallback<SurveyModel> callback) {
        getCustomSurvey(context, DEFAULT_SURVEY, callback);
    }

    public void getCustomSurvey(Activity context, String surveyId, ObjectCallback<SurveyModel> callback) {
        liveCall(context, surveyId, callback);
    }


    private void liveCall(Context context, String surveyId, ObjectCallback<SurveyModel> callback) {
        String emulatorUrl = "http://129.215.169.232:3001/api";
        RestAdapter adapter = new RestAdapter(
                context, emulatorUrl);
        SurveyModelRepository repository = adapter.createRepository(SurveyModelRepository.class);
        repository.findById(surveyId, callback);
    }
}
