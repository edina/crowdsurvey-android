package uk.ac.edina.fieldtriplite.matchers;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;
import android.widget.EditText;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Created by murrayking on 08/01/2016.
 */
public class ErrorMessageMatcher {


    public static Matcher<View> hasErrorText(final String expectedError) {
        return new BoundedMatcher<View, View>(View.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("with error: " + expectedError);
            }

            @Override
            protected boolean matchesSafely(View view) {

                if (!(view instanceof EditText)) {
                    return false;
                }

                EditText editText = (EditText) view;

                return expectedError.equals(editText.getError());
            }
        };
    }
}
