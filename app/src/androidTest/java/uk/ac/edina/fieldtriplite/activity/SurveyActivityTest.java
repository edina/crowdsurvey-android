package uk.ac.edina.fieldtriplite.activity;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.support.test.rule.ActivityTestRule;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.strongloop.android.loopback.callbacks.ObjectCallback;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.edina.fieldtriplite.model.SurveyField;
import uk.ac.edina.fieldtriplite.model.SurveyModel;
import uk.ac.edina.fieldtriplite.service.SurveyService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by murray on 26/12/15.
 */
public class SurveyActivityTest {
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
    public ActivityTestRule<SurveyActivity> activityRule = new ActivityTestRule<>(
            SurveyActivity.class);


    @Before
    public void setUpActivity() {
        SurveyActivity surveyActivity = activityRule.getActivity();
        surveyActivity.setSurveyService(new SurveyServiceMock());
    }

    @Test
    public void changeTextInFirstTextField() {
        SurveyActivity surveyActivity = activityRule.getActivity();

        List<SurveyField> surveyFields = surveyActivity.getSurveyFields();
        SurveyField firstTextField = surveyFields.get(0);
        EditText materialDesignEditBox = (EditText) activityRule.getActivity().findViewById(firstTextField.getFormId());
        assertNotNull(materialDesignEditBox);
        String hint = materialDesignEditBox.getText().toString();
        TextInputLayout textInputLayout = (TextInputLayout)materialDesignEditBox.getParent();

        assertEquals("1. Date of survey", textInputLayout.getHint());
    }

    class SurveyServiceMock implements SurveyService {

        @Override
        public void getCustomSurvey(Activity context, final ObjectCallback<SurveyModel> callback) {

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
            callback.onSuccess(surveyModel);

        }


    }
}

