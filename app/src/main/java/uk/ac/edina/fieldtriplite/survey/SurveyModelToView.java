package uk.ac.edina.fieldtriplite.survey;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
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
    Context context;

    public SurveyModelToView(Context context, ViewGroup layoutContainer){
        this.context = context;
        this.layoutContainer = layoutContainer;
    }

    @Override
    public void visit(SurveyTextField field) {
        TextInputLayout dynamic = new TextInputLayout(context);

        dynamic.setHint(field.getLabel());
        EditText dynamicEditText = new EditText(context);

        dynamic.addView(dynamicEditText, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layoutContainer.addView(dynamic, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void visit(SurveyRadioField field) {

    }

    public ViewGroup getSurveyView(){
        return layoutContainer;
    }
}
