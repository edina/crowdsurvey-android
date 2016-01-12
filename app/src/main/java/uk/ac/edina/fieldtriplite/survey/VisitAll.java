package uk.ac.edina.fieldtriplite.survey;

import java.util.Iterator;

import uk.ac.edina.fieldtriplite.model.SurveyField;

public class VisitAll {
    public VisitAll() {
    }

    public void visit(Iterator<SurveyField> surveyFields) {
        while (surveyFields.hasNext()) {
            SurveyField field = surveyFields.next();
            field.accept(null);
        }
    }
}