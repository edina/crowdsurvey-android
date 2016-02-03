package uk.ac.edina.fieldtriplite.model;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.ObjectCallback;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import uk.ac.edina.fieldtriplite.BuildConfig;
import uk.ac.edina.fieldtriplite.FieldTripMap;

/**
 * Created by murrayking on 11/12/2015.
 */

public class ParseSurveyIntegrationTest extends ActivityInstrumentationTestCase2<FieldTripMap> {


    RestAdapter adapter;

    private static final String url = BuildConfig.API_URL;
    SurveyModel surveyModel;


    Context mockContext;
    private FieldTripMap fieldTripMapActivity;

    public ParseSurveyIntegrationTest() {
        super(FieldTripMap.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();


        // Starts the activity under test using the default Intent with:
        // action = {@link Intent#ACTION_MAIN}
        // flags = {@link Intent#FLAG_ACTIVITY_NEW_TASK}
        // All other fields are null or empty.
        fieldTripMapActivity = getActivity();


        assertNotNull("mFieldTripMapActivity is not null", fieldTripMapActivity);

        mockContext = fieldTripMapActivity.getApplicationContext();
        assertNotNull(mockContext);
        adapter = new RestAdapter(mockContext, url);

    }

    @Test
    public void testLiveLoopBackCall() throws Throwable {
        final CountDownLatch signal = new CountDownLatch(1);


        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                SurveyModelRepository repository = adapter.createRepository(SurveyModelRepository.class);

                repository.findById("1", new ObjectCallback<SurveyModel>() {
                    @Override
                    public void onSuccess(SurveyModel object) {
                        Log.d("SurveyModel", object.toString());
                        surveyModel = object;
                        signal.countDown();
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d("SurveyModel", t.toString());
                        signal.countDown();
                    }
                });
            }
        });

        try {
            signal.await(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertNotNull("NO Survey Model returned", surveyModel);

        SurveyParser parseSurvey = new SurveyParser();

        List<SurveyField> surveyFields = parseSurvey.buildFields(surveyModel);

        SurveyField surveyField1 = surveyFields.get(0);

        assertEquals("1. Date of survey", surveyField1.getLabel());
        assertEquals(SurveyField.Type.TEXT, surveyField1.getType());
        assertEquals(Boolean.TRUE, surveyField1.isRequired());
        assertEquals(Boolean.TRUE, surveyField1.isPersistent());
        assertEquals("form-text-1", surveyField1.getId());

        SurveyFieldProperties surveyFieldProperties = surveyField1.getSurveyFieldProperties();
        assertEquals("30", surveyFieldProperties.getMaxChars());
        assertEquals("Place default text here (if any)", surveyFieldProperties.getPlaceholder());
        assertEquals("record", surveyFieldProperties.getPrefix());
        assertEquals(0, signal.getCount());
    }




}
