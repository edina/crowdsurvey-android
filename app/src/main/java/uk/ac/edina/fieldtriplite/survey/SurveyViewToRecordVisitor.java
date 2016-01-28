package uk.ac.edina.fieldtriplite.survey;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Iterator;

import uk.ac.edina.fieldtriplite.model.RecordField;
import uk.ac.edina.fieldtriplite.model.RecordModel;
import uk.ac.edina.fieldtriplite.model.SurveyField;
import uk.ac.edina.fieldtriplite.model.SurveyImageField;
import uk.ac.edina.fieldtriplite.model.SurveyRadioField;
import uk.ac.edina.fieldtriplite.model.SurveyTextField;

/**
 * Created by murrayking on 22/12/2015.
 */
public class SurveyViewToRecordVisitor implements SurveyVisitor {
    private RecordModel model;
    private ViewGroup container;
    private VisitAll visitAll = new VisitAll(this);

    public SurveyViewToRecordVisitor(ViewGroup container){
        this.model = new RecordModel();
        this.container = container;
    }

    @Override
    public void visitAll(Iterator<SurveyField> fieldIterator) {
        visitAll.visit(fieldIterator);
    }

    @Override
    public void visit(final SurveyTextField field) {
        RecordField recordField = new RecordField();
        recordField.setLabel(field.getLabel());
        recordField.setId(field.getId());
        EditText editText = (EditText)container.findViewById(field.getFormId());
        recordField.setVal(editText.getText().toString());
        model.addRecordField(recordField);
    }

    @Override
    public void visit(SurveyRadioField field) {

    }

    @Override
    public void visit(SurveyImageField field) {
        RecordField recordField = new RecordField();
        recordField.setId(field.getId());
        recordField.setLabel(field.getLabel());
        View thumbnailImage = container.findViewById(field.getFormId());
        String pathToImage = thumbnailImage.getTag().toString();
        recordField.setVal(pathToImage);
        model.addRecordField(recordField);
    }

    public RecordModel getRecordModel(){
        return model;
    }



}
