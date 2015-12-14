package uk.ac.edina.fieldtriplite.model;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.ObjectCallback;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import uk.ac.edina.fieldtriplite.FieldTripMap;

/**
 * Created by murrayking on 11/12/2015.
 */

public class ParseSurveyIntegrationTest extends ActivityInstrumentationTestCase2<FieldTripMap> {


    RestAdapter adapter;

    //String nativeUrl = "http://dlib-rainbow.edina.ac.uk:3000/api";
    String emulatorUrl = "http://10.0.2.2:3001/api";

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
        adapter = new RestAdapter(
                mockContext, emulatorUrl);

    }

    @Test
    public void testAsyncHttpClient() throws Throwable {
        final CountDownLatch signal = new CountDownLatch(1);


        runTestOnUiThread(new Runnable() { // THIS IS THE KEY TO SUCCESS
            @Override
            public void run() {
                SurveyModelRepository repository = adapter.createRepository(SurveyModelRepository.class);

                repository.findById("5669a80fdf817f5420780c9c", new ObjectCallback<SurveyModel>() {
                    @Override
                    public void onSuccess(SurveyModel object) {
                        Log.d("RecordModel", object.toString());
                        surveyModel = object;
                        signal.countDown();
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d("RecordModel", t.toString());
                        signal.countDown();
                    }
                });
            }
        });

        try {
            signal.await(30, TimeUnit.SECONDS); // wait for callback
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        assertEquals(0, signal.getCount());
    }


    /*
    @Test
    public void testMakeLoopbackRequest(){
        SurveyModelRepository repository = adapter.createRepository(SurveyModelRepository.class);

        repository.findById(1, new ObjectCallback<SurveyModel>() {
            @Override
            public void onSuccess(SurveyModel object) {
                Log.d("RecordModel", object.toString());
            }

            @Override
            public void onError(Throwable t) {
                Log.d("RecordModel", t.toString());
            }
        });
        assertNotNull(adapter);

    }*/

}
