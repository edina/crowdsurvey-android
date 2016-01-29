package uk.ac.edina.fieldtriplite.service;

import com.strongloop.android.loopback.callbacks.ObjectCallback;

import uk.ac.edina.fieldtriplite.model.SurveyModel;

/**
 * Created by murrayking on 06/01/2016.
 */
public interface SurveyService {

    void getCustomSurvey(ObjectCallback<SurveyModel> callback);
    void getCustomSurvey(String surveyId, ObjectCallback<SurveyModel> callback);
    void downloadSurvey(String surveyId);
    void activateSurvey(String surveyId);
}
