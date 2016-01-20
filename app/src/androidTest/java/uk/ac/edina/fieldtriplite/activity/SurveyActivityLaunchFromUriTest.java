package uk.ac.edina.fieldtriplite.activity;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.net.Uri;
import android.util.Log;

import static java.lang.Thread.*;


public class SurveyActivityLaunchFromUriTest extends ActivityInstrumentationTestCase2<SurveyActivity> {

    public static final String LOG_TAG = "LaunchFromUriTest";
    private Instrumentation instrumentation;

    public SurveyActivityLaunchFromUriTest() {
        super(SurveyActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(false);
        instrumentation = getInstrumentation();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testLaunchFromSurvey() {
        ActivityMonitor monitor =
                instrumentation.addMonitor(SurveyActivity.class.getName(), null, false);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("crowdsurvey://uk.ac.edina.crowdsurvey?surveyId=566ed9b30351d817555158cd");
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Log.d(LOG_TAG, "Starting: " + intent.toString());
        instrumentation.startActivitySync(intent);

        Activity currentActivity = getInstrumentation().waitForMonitor(monitor);
        assertNotNull(currentActivity);
        instrumentation.removeMonitor(monitor);
    }
}