package uk.ac.edina.fieldtriplite.survey;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import uk.ac.edina.fieldtriplite.model.SurveyRadioField;
import uk.ac.edina.fieldtriplite.model.SurveyTextField;

/**
 * Created by murrayking on 22/12/2015.
 */
public class SurveyModelToView implements SurveyVisitor {
    ViewGroup layoutContainer;
    Activity context;

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

        dynamicEditText.addTextChangedListener(new TextWatcher() {
            
            public void afterTextChanged(Editable s) {
                String textToValidate = dynamicEditText.getText().toString();
                if (textToValidate.length() > maxChars) {
                    dynamic.setError("Too Long");
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}


            public void onTextChanged(CharSequence s, int start, int before, int count) {}

        });

    }

    @Override
    public void visit(SurveyRadioField field) {


    }


}
