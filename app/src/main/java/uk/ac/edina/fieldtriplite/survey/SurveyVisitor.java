package uk.ac.edina.fieldtriplite.survey;

import java.util.Iterator;

import uk.ac.edina.fieldtriplite.model.SurveyField;
import uk.ac.edina.fieldtriplite.model.SurveyImageField;
import uk.ac.edina.fieldtriplite.model.SurveyRadioField;
import uk.ac.edina.fieldtriplite.model.SurveyTextField;

/**
 * Created by murrayking on 21/12/2015.
 */
public interface SurveyVisitor {

    void visitAll(Iterator<SurveyField> fieldIterator);

    void visit(SurveyTextField field);

    void visit(SurveyRadioField field);

    void visit(SurveyImageField field);
}
