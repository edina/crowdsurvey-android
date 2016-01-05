package uk.ac.edina.fieldtriplite.activity;

import android.support.design.widget.TextInputLayout;
import android.support.test.rule.ActivityTestRule;
import android.test.ActivityInstrumentationTestCase2;
import static android.support.test.espresso.Espresso.onView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import uk.ac.edina.fieldtriplite.R;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by murray on 26/12/15.
 */
public class SurveyActivityTest {
    /**
     * A JUnit {@link Rule @Rule} to launch your activity under test. This is a replacement
     * for {@link ActivityInstrumentationTestCase2}.
     * <p>
     * Rules are interceptors which are executed for each test method and will run before
     * any of your setup code in the {@link Before @Before} method.
     * <p>
     * {@link ActivityTestRule} will create and launch of the activity for you and also expose
     * the activity under test. To get a reference to the activity you can use
     * the {@link ActivityTestRule#getActivity()} method.
     */
    @Rule
    public ActivityTestRule<SurveyActivity> activityRule = new ActivityTestRule<>(
            SurveyActivity.class);

    @Test
    public void changeText_sameActivity() {

        TextInputLayout materialDesignEditBox = (TextInputLayout)activityRule.getActivity().findViewById(666);
        String hint = materialDesignEditBox.getHint().toString();

        assertEquals("1. Date of survey", hint);
    }

}