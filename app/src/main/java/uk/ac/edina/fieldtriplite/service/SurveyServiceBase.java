package uk.ac.edina.fieldtriplite.service;

import android.content.Context;

/**
 * Created by rgamez on 28/01/2016.
 */
public abstract class SurveyServiceBase implements SurveyService{
    protected final Context context;

    public SurveyServiceBase(Context context) {
        this.context = context;
    }
}
