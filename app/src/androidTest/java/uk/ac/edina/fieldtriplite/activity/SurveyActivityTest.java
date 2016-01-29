package uk.ac.edina.fieldtriplite.activity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.test.ActivityInstrumentationTestCase2;

import com.strongloop.android.loopback.callbacks.ObjectCallback;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.edina.fieldtriplite.FieldTripApplication;
import uk.ac.edina.fieldtriplite.R;
import uk.ac.edina.fieldtriplite.matchers.TextInputLayoutHintMatcher;
import uk.ac.edina.fieldtriplite.model.SurveyField;
import uk.ac.edina.fieldtriplite.model.SurveyModel;
import uk.ac.edina.fieldtriplite.service.SurveyService;
import uk.ac.edina.fieldtriplite.service.SurveyServiceBase;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by murray on 26/12/15.
 */
public class SurveyActivityTest {

    private static final String STRING_TO_BE_TYPED = "test";

    private static final String MAX_CHARS_TEST = "1234567";
    /**
     * A JUnit {@link Rule @Rule} to launch your activity under test. This is a replacement
     * for {@link ActivityInstrumentationTestCase2}.
     * <p/>
     * Rules are interceptors which are executed for each test method and will run before
     * any of your setup code in the {@link Before @Before} method.
     * <p/>
     * {@link ActivityTestRule} will create and launch of the activity for you and also expose
     * the activity under test. To get a reference to the activity you can use
     * the {@link ActivityTestRule#getActivity()} method.
     */


    @Rule
    public IntentsTestRule<SurveyActivity> activityRule = new IntentsTestRule<SurveyActivity>(
            SurveyActivity.class, true, false
    );

    @Before
    public void setUpActivity() {

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        FieldTripApplication app = (FieldTripApplication) instrumentation.getTargetContext()
                .getApplicationContext();

        app.setSurveyService(new SurveyServiceMock(app.getApplicationContext()));

    }

    @Test
    public void changeTextInFirstTextField() {
        activityRule.launchActivity(new Intent());
        List<SurveyField> surveyFields = getSurveyFields();
        SurveyField firstTextField = surveyFields.get(0);

        onView(withId(firstTextField.getFormId())).check(matches(isDisplayed()));


        onView(withId(firstTextField.getFormId()))
                .perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard());


        onView(withId(firstTextField.getFormId())).check(matches(withText(STRING_TO_BE_TYPED)));


    }





    @Test
    public void testHintTextOnFirstField() {
        activityRule.launchActivity(new Intent());
        List<SurveyField> surveyFields = getSurveyFields();
        SurveyField firstTextField = surveyFields.get(0);

        onView(withChild(withId(firstTextField.getFormId())))
                .check(matches(TextInputLayoutHintMatcher.withHint("1. Date of survey")));


    }

    @Test
    public void testMaxCharsValidationError() {
        activityRule.launchActivity(new Intent());
        List<SurveyField> surveyFields = getSurveyFields();
        SurveyField firstTextField = surveyFields.get(0);

        onView(withId(firstTextField.getFormId()))
                .perform(typeText(MAX_CHARS_TEST));

        onView(withChild(withId(firstTextField.getFormId())))
                .check(matches(TextInputLayoutHintMatcher.withError("Too Long only 5 allowed")));


    }

    @Test
    public void testCameraFieldDisplayed() {
        activityRule.launchActivity(new Intent());
        List<SurveyField> surveyFields = getSurveyFields();
        SurveyField cameraField = surveyFields.get(1);

        onView(withId(cameraField.getFormId())).check(matches(isDisplayed()));
        onView(withText(R.string.take_photo_button)).check(matches(isDisplayed()));
        onView(withText(R.string.gallery_photo_button)).check(matches(isDisplayed()));

    }

    @Test
    public void testTakePhotoLaunchingIntent() {
        activityRule.launchActivity(new Intent());

        Intent resultData = new Intent();
        Bitmap data = null;
        resultData.putExtra("data", data);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(result);
        onView(withText(R.string.take_photo_button)).perform(click());

    }

    @Test
    public void testChooseFromGalleryLaunchingIntent() {
        activityRule.launchActivity(new Intent());

        Intent resultData = new Intent();
        Bitmap data = null;
        resultData.putExtra("data", data);
        resultData.setData(Uri.parse( "http://www.test.com"));
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        intending(hasData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)).respondWith(result);
        onView(withText(R.string.gallery_photo_button)).perform(click());

    }


    /**
     * Thread issue on rooted device add a short sleep if required
     *
     * @return List<SurveyField>
     * @throws InterruptedException
     */

    private List<SurveyField> getSurveyFields() {
        SurveyActivity surveyActivity = activityRule.getActivity();

        List<SurveyField> surveyFields = surveyActivity.getSurveyFields();

        if (surveyFields == null) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ignored) {

            }
            surveyFields = surveyActivity.getSurveyFields();
        }
        return surveyFields;
    }

    class SurveyServiceMock extends SurveyServiceBase {

        public SurveyServiceMock(Context context) {
            super(context);
        }

        @Override
        public void getCustomSurvey(ObjectCallback<SurveyModel> callback) {
            getCustomSurvey("", callback);
        }

        @Override
        public void getCustomSurvey(String surveyId, ObjectCallback<SurveyModel> callback) {

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
            properties.put("max-chars", "5");
            properties.put("other", Boolean.TRUE);

            properties.put("options", new ArrayList() {{
                add("No");
                add("Yes");
                add("other");
            }});

            fields.add(fieldOne);

            Map<String, Object> fieldTwo = new HashMap<>();
            fieldTwo.put("id", "form-image-1");
            fieldTwo.put("type", "image");
            fieldTwo.put("label", "Take a photo");
            fieldTwo.put("required", true);
            fieldTwo.put("persistent", false);
            Map<String, Object> propertiesTwo = new HashMap<>();
            propertiesTwo.put("multi-image", Boolean.FALSE);
            fieldTwo.put("properties", propertiesTwo);

            fieldOne.put("properties", properties);

            fields.add(fieldTwo);

            surveyModel.setFields(fields);
            callback.onSuccess(surveyModel);

        }

        @Override
        public void downloadSurvey(String surveyId) {
            return;
        }

        @Override
        public void activateSurvey(String surveyId) {
            return;
        }
    }
}

