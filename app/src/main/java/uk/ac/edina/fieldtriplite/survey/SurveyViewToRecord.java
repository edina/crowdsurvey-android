package uk.ac.edina.fieldtriplite.survey;

import android.view.ViewGroup;
import android.widget.EditText;

import uk.ac.edina.fieldtriplite.model.RecordField;
import uk.ac.edina.fieldtriplite.model.RecordModel;
import uk.ac.edina.fieldtriplite.model.SurveyRadioField;
import uk.ac.edina.fieldtriplite.model.SurveyTextField;

/**
 * Created by murrayking on 22/12/2015.
 */
public class SurveyViewToRecord implements SurveyVisitor {
    private  RecordModel model;
    private ViewGroup container;


    public SurveyViewToRecord( ViewGroup container){
        this.model = new RecordModel();
        this.container = container;
    }

    @Override
    public void visit(final SurveyTextField field) {
        RecordField recordField = new RecordField();
        recordField.setLabel(field.getLabel());
        recordField.setId(field.getLabel());
        EditText editText = (EditText)container.findViewById(field.getFormId());
        recordField.setVal(editText.getText().toString());
        model.addRecordField(recordField);
    }

    @Override
    public void visit(SurveyRadioField field) {


    }

    public RecordModel getRecordModel(){
        return model;
    }


}
