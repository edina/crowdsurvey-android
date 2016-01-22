package uk.ac.edina.fieldtriplite.survey;

import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.Iterator;

import uk.ac.edina.fieldtriplite.activity.SurveyActivity;
import uk.ac.edina.fieldtriplite.model.SurveyField;
import uk.ac.edina.fieldtriplite.model.SurveyImageField;
import uk.ac.edina.fieldtriplite.model.SurveyRadioField;
import uk.ac.edina.fieldtriplite.model.SurveyTextField;
import uk.ac.edina.fieldtriplite.validation.FieldValidation;

/**
 * Created by murrayking on 22/12/2015.
 */
public class SurveyModelToViewVisitor implements SurveyVisitor {
    private final VisitAll visitAll = new VisitAll(this);
    ViewGroup layoutContainer;
    SurveyActivity activity;
    FieldValidation fieldValidation = new FieldValidation();

    public SurveyModelToViewVisitor(SurveyActivity activity, ViewGroup layoutContainer){
        this.activity = activity;
        this.layoutContainer = layoutContainer;
    }

    public void visitAll(Iterator<SurveyField> fieldIterator){
        visitAll.visit(fieldIterator);
    }

    @Override
    public void visit(final SurveyTextField field) {

        final TextInputLayout dynamic = new TextInputLayout(activity);
        final Integer maxChars = field.getSurveyFieldProperties().getMaxChars();

        final EditText dynamicEditText = new EditText(activity);
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
        LinearLayout linearLayout = new LinearLayout(activity);
        Button button = new Button(activity);
        button.setText("Take Photo");
        linearLayout.addView(button, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layoutContainer.addView(linearLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CameraFieldHelper cameraFieldHelper = new CameraFieldHelper(activity);
                cameraFieldHelper.takePhoto();

            }
        });

    }


}
