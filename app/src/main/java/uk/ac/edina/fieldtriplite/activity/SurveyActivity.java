package uk.ac.edina.fieldtriplite.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.strongloop.android.loopback.callbacks.ObjectCallback;

import java.util.List;

import uk.ac.edina.fieldtriplite.R;
import uk.ac.edina.fieldtriplite.model.SurveyField;
import uk.ac.edina.fieldtriplite.model.SurveyModel;
import uk.ac.edina.fieldtriplite.model.SurveyParser;
import uk.ac.edina.fieldtriplite.service.SurveyService;
import uk.ac.edina.fieldtriplite.survey.SurveyModelToView;

public class SurveyActivity extends AppCompatActivity {

    private List<SurveyField> surveyFields;
    private LinearLayout container;
    class SurveyModelCallBack implements ObjectCallback<SurveyModel>{

        @Override
            public void onSuccess(SurveyModel surveyModel) {
                Log.d("SurveyModel", surveyModel.toString());
                SurveyParser surveyParser = new SurveyParser();
                surveyFields = surveyParser.buildFields(surveyModel);
                SurveyActivity.this.runOnUiThread(
                        new SurveyModelToViewRunner()
                );

            }

            @Override
            public void onError(Throwable t) {
                Log.d("SurveyModel", t.toString());

            }
        };


    class SurveyModelToViewRunner implements Runnable {


        @Override
        public void run() {

            SurveyModelToView surveyModelToView = new SurveyModelToView(SurveyActivity.this, container);
            for (SurveyField field : surveyFields) {
                field.convertToView(surveyModelToView);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        container = (LinearLayout)findViewById(R.id.viewToAddTo);
        SurveyService surveyService = new SurveyService();

        surveyService.getCustomSurvey(this, new SurveyModelCallBack());

    }


}
