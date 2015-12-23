package uk.ac.edina.fieldtriplite.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import uk.ac.edina.fieldtriplite.R;
import uk.ac.edina.fieldtriplite.service.SurveyService;

public class SurveyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        LinearLayout container = (LinearLayout)findViewById(R.id.viewToAddTo);
        SurveyService surveyService = new SurveyService();

        surveyService.addCustomSurvey(this, container);

    }


}
