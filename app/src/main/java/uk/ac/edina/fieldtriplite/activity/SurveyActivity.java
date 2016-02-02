package uk.ac.edina.fieldtriplite.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;
import com.strongloop.android.loopback.callbacks.ObjectCallback;

import java.util.List;
import java.util.Observable;

import uk.ac.edina.fieldtriplite.FieldTripApplication;
import uk.ac.edina.fieldtriplite.R;
import uk.ac.edina.fieldtriplite.document.Record;
import uk.ac.edina.fieldtriplite.model.RecordModel;
import uk.ac.edina.fieldtriplite.model.SurveyField;
import uk.ac.edina.fieldtriplite.model.SurveyModel;
import uk.ac.edina.fieldtriplite.model.SurveyParser;
import uk.ac.edina.fieldtriplite.survey.SurveyModelToViewVisitor;
import uk.ac.edina.fieldtriplite.survey.SurveyViewToRecordVisitor;

public class SurveyActivity extends AppCompatActivity {

    public static final String LOG_TAG = "SurveyModel";


    private List<SurveyField> surveyFields;
    private LinearLayout container;

    class ObservableCameraChange extends Observable {
        @Override
        public void setChanged(){
            super.setChanged();
        }

    }

    private ObservableCameraChange observableCameraChange = new ObservableCameraChange();



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
        }


    class SurveyModelToViewRunner implements Runnable {


        @Override
        public void run() {

            SurveyModelToViewVisitor surveyModelToViewVisitor = new SurveyModelToViewVisitor(SurveyActivity.this, container);
            surveyModelToViewVisitor.visitAll(surveyFields.iterator());

            addSaveRecordButton();
        }
    }

    private void addSaveRecordButton() {
        Button submitButton = new Button(SurveyActivity.this);
        submitButton.setText("Submit");
        container.addView(submitButton, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveRecord();
            }
        });

    }

    private void saveRecord() {


        SurveyViewToRecordVisitor surveyViewToRecordVisitor = new SurveyViewToRecordVisitor(container);

        surveyViewToRecordVisitor.visitAll(surveyFields.iterator());


        RecordModel record = surveyViewToRecordVisitor.getRecordModel();
        Log.d(LOG_TAG, record.toString());
        FieldTripApplication application = (FieldTripApplication) this.getApplicationContext();
        try {
            Document document = Record.putRecord(application.getDatabase(), record);
        } catch (CouchbaseLiteException e) {
            Log.e(LOG_TAG, "Unable to save record" + e.toString());
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        container = (LinearLayout)findViewById(R.id.viewToAddTo);
        FieldTripApplication application = (FieldTripApplication)getApplicationContext();
        application.getSurveyService().getCustomSurvey(new SurveyModelCallBack());
    }

    public List<SurveyField> getSurveyFields() {
        return surveyFields;
    }



    public static class ActivityResult{
        private int requestCode;
        private int resultCode;
        private Intent data;

        public ActivityResult(int requestCode, int resultCode, Intent data) {
            this.requestCode = requestCode;
            this.resultCode = resultCode;
            this.data = data;
        }

        public int getResultCode() {
            return resultCode;
        }

        public int getRequestCode() {
            return requestCode;
        }

        public Intent getData() {
            return data;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        observableCameraChange.setChanged();
        observableCameraChange.notifyObservers(new ActivityResult(requestCode,resultCode, data));

    }

    public Observable getObservableCameraChange() {
        return observableCameraChange;
    }
}
