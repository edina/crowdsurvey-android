package uk.ac.edina.fieldtriplite.survey;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.common.base.Strings;

import java.util.Iterator;

import uk.ac.edina.fieldtriplite.R;
import uk.ac.edina.fieldtriplite.model.SurveyField;
import uk.ac.edina.fieldtriplite.model.SurveyImageField;
import uk.ac.edina.fieldtriplite.model.SurveyRadioField;
import uk.ac.edina.fieldtriplite.model.SurveyTextField;

/**
 * Created by murrayking on 22/12/2015.
 */
public class SurveyViewValidatorVisitor implements SurveyVisitor {

    private ViewGroup container;
    private VisitAll visitAll = new VisitAll(this);
    private boolean valid;
    private Context context;
    public SurveyViewValidatorVisitor(ViewGroup container, Context context){
        valid = true;
        this.container = container;
        this.context = context;
    }

    @Override
    public void visitAll(Iterator<SurveyField> fieldIterator) {
        visitAll.visit(fieldIterator);
    }

    @Override
    public void visit(final SurveyTextField field) {
        if(field.isRequired()){
            EditText editText = (EditText)container.findViewById(field.getFormId());
            if(Strings.isNullOrEmpty(editText.getText().toString())){
                valid = false;
                TextInputLayout textInputLayout = (TextInputLayout) editText.getParent();
                textInputLayout.setError(context.getString(R.string.required));
            }

        }
;

    }

    @Override
    public void visit(SurveyRadioField field) {

    }

    @Override
    public void visit(SurveyImageField field) {

    }

    public boolean isValid() {
        return valid;
    }
}
