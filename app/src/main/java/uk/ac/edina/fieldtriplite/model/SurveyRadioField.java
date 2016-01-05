package uk.ac.edina.fieldtriplite.model;

import uk.ac.edina.fieldtriplite.survey.SurveyVisitor;

/**
 * Created by murrayking on 21/12/2015.
 */
public class SurveyRadioField extends SurveyFieldBase {
    protected SurveyRadioField(String id, String label, Type type, Boolean required, Boolean persistent, SurveyFieldProperties surveyFieldProperties, int formId) {
        super(id, label, type, required, persistent, surveyFieldProperties, formId);
    }

    @Override
    public void convertToView(SurveyVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void convertToRecord(SurveyVisitor visitor) {
        visitor.visit(this);
    }
}
