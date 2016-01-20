package uk.ac.edina.fieldtriplite.service;

import android.app.Activity;

import com.strongloop.android.loopback.callbacks.ObjectCallback;

import uk.ac.edina.fieldtriplite.model.SurveyModel;

/**
 * Created by murrayking on 06/01/2016.
 */
public interface SurveyService {

    void getCustomSurvey(Activity context, ObjectCallback<SurveyModel> callback);
    void getCustomSurvey(Activity context, String surveyId, ObjectCallback<SurveyModel> callback);
}
