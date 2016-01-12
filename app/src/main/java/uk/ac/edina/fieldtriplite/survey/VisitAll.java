package uk.ac.edina.fieldtriplite.survey;

import java.util.Iterator;

import uk.ac.edina.fieldtriplite.model.SurveyField;

public class VisitAll {
    SurveyVisitor surveyVisitor;
    public VisitAll(SurveyVisitor surveyVisitor) {
        this.surveyVisitor = surveyVisitor;
    }

    public void visit(Iterator<SurveyField> fieldIterator) {
        while (fieldIterator.hasNext()) {
            SurveyField field = fieldIterator.next();
            field.accept(surveyVisitor);
        }
    }
}