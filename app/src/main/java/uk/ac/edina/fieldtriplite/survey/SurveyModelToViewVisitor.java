package uk.ac.edina.fieldtriplite.survey;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.Iterator;

import uk.ac.edina.fieldtriplite.model.SurveyField;
import uk.ac.edina.fieldtriplite.model.SurveyImageField;
import uk.ac.edina.fieldtriplite.model.SurveyRadioField;
import uk.ac.edina.fieldtriplite.model.SurveyTextField;
import uk.ac.edina.fieldtriplite.validation.FieldValidation;

/**
 * Created by murrayking on 22/12/2015.
 */
public class SurveyModelToViewVisitor implements SurveyVisitor {
    private final VisitAll visitAll = new VisitAll();
    ViewGroup layoutContainer;
    Activity context;
    FieldValidation fieldValidation = new FieldValidation();

    public SurveyModelToViewVisitor(Activity context, ViewGroup layoutContainer){
        this.context = context;
        this.layoutContainer = layoutContainer;
    }

    public void visitAll(Iterator<SurveyField> fieldIterator){
        visitAll.visit(fieldIterator);
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

    @Override
    public void visit(SurveyImageField field) {
        LinearLayout linearLayout = new LinearLayout(context);
        Button button = new Button(context);
        button.setText("Take Photo");
        linearLayout.addView(button, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layoutContainer.addView(linearLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }


}
