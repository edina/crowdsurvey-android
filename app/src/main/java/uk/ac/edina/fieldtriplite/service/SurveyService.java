package uk.ac.edina.fieldtriplite.service;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;

import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.ObjectCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.edina.fieldtriplite.model.SurveyModel;
import uk.ac.edina.fieldtriplite.model.SurveyModelRepository;

/**
 * Created by murrayking on 23/12/2015.
 */
public class SurveyService {

    public void addCustomSurvey(Activity context, ViewGroup layoutContainer) {

        retrieveSurveyModel(context, layoutContainer);
        /*
        SurveyParser surveyParser = new SurveyParser();

        List<SurveyField> surveyFields = surveyParser.buildFields(loopbackSurveyModel);
        SurveyModelToView surveyModelToView = new SurveyModelToView(context, layoutContainer);
        for (SurveyField field : surveyFields) {
            field.convertToView(surveyModelToView);
        }
        */


    }

    public void getCustomSurvey(Activity context, ObjectCallback<SurveyModel> callback) {

        liveCall(context, callback);
        /*
        SurveyParser surveyParser = new SurveyParser();

        List<SurveyField> surveyFields = surveyParser.buildFields(loopbackSurveyModel);
        SurveyModelToView surveyModelToView = new SurveyModelToView(context, layoutContainer);
        for (SurveyField field : surveyFields) {
            field.convertToView(surveyModelToView);
        }
        */


    }


    private void retrieveSurveyModel(Activity context, ViewGroup layoutContainer) {
        // TODO: 23/12/2015
        // make live call to server;
        //liveCall(context);
        stubCall(context, layoutContainer);
       // liveCall(context);


    }

    private void liveCall(Context context, ObjectCallback<SurveyModel> callback) {
        String emulatorUrl = "http://129.215.169.232:3001/api";
        RestAdapter adapter = new RestAdapter(
                context, emulatorUrl);
        SurveyModelRepository repository = adapter.createRepository(SurveyModelRepository.class);
        String surveyId = "566ed9b30351d817555158cd";
        repository.findById(surveyId, callback);
    }

    private void stubCall(final Activity context, final ViewGroup layoutContainer) {
        //in background thread to mimic call to server
        new Thread(new Runnable() {
            public void run() {
                SurveyModel surveyModel = new SurveyModel();
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

                properties.put("options", new ArrayList() {{
                    add("No");
                    add("Yes");
                    add("other");
                }});


                fieldOne.put("properties", properties);

                fields.add(fieldOne);
                surveyModel.setFields(fields);


            }
        }).start();

    }


}
