package uk.ac.edina.fieldtriplite.matchers;

/**
 * Created by murrayking on 07/01/2016.
 */

import android.support.design.widget.TextInputLayout;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.hamcrest.Matchers.is;

/**
 * A custom matcher that checks the hint property of an {@link TextInputLayout}. It
 * accepts either a {@link String} or a {@link org.hamcrest.Matcher}.
 */
public class TextInputLayoutHintMatcher {

    public static Matcher<View> withHint(final String substring) {
        return withHint(is(substring));
    }

    public static Matcher<View> withHint(final Matcher<String> stringMatcher) {
        checkNotNull(stringMatcher);
        return new BoundedMatcher<View, TextInputLayout>(TextInputLayout.class) {

            @Override
            public boolean matchesSafely(TextInputLayout view) {
                final CharSequence hint = view.getHint();
                return hint != null && stringMatcher.matches(hint.toString());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with hint: ");
                stringMatcher.describeTo(description);
            }
        };
    }


    public static Matcher<View> withError(final String substring) {
        return withError(is(substring));
    }

    public static Matcher<View> withError(final Matcher<String> stringMatcher) {
        checkNotNull(stringMatcher);
        return new BoundedMatcher<View, TextInputLayout>(TextInputLayout.class) {

            @Override
            public boolean matchesSafely(TextInputLayout view) {
                final CharSequence error = view.getError();
                return error != null && stringMatcher.matches(error.toString());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with error: ");
                stringMatcher.describeTo(description);
            }
        };
    }

}
