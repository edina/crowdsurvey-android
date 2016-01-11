package uk.ac.edina.fieldtriplite;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.NonNull;
import android.support.test.rule.ActivityTestRule;

/**
 * Created by murrayking on 08/01/2016.
 */
public class ActivityRuleOnCreate<T extends Activity> extends ActivityTestRule<T> {


    public ActivityRuleOnCreate(Class<T> activityClass) {
        super(activityClass);
    }

    @Override
    protected void beforeActivityLaunched() {

    }

    public interface OnBeforeActivityLaunchedListener<T> {

        void beforeActivityLaunched(@NonNull Application application, @NonNull T activity);
    }
}