package uk.ac.edina.fieldtriplite.survey;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import uk.ac.edina.fieldtriplite.model.SurveyRadioField;
import uk.ac.edina.fieldtriplite.model.SurveyTextField;
import uk.ac.edina.fieldtriplite.validation.FieldValidation;

/**
 * Created by murrayking on 22/12/2015.
 */
public class SurveyModelToView implements SurveyVisitor {
    ViewGroup layoutContainer;
    Activity context;
    FieldValidation fieldValidation = new FieldValidation();

    public SurveyModelToView(Activity context, ViewGroup layoutContainer){
        this.context = context;
        this.layoutContainer = layoutContainer;
    }

    @Override
    public void visit(final SurveyTextField field) {

        final TextInputLayout dynamic = new TextInputLayout(context);
        final Integer maxChars = field.getSurveyFieldProperties().getMaxChars();

        final EditText dynamicEditText = new EditText(context);
        dynamicEditText.setHint(field.getLabel());
        dynamicEditText.setId(field.getFormId());

        dynamic.addView(dynamicEditText, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layoutContainer.addView(dynamic, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        fieldValidation.maxNumberOfChars(dynamic, maxChars, dynamicEditText);

    }



    @Override
    public void visit(SurveyRadioField field) {


    }


}
