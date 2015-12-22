package uk.ac.edina.fieldtriplite.survey;

import uk.ac.edina.fieldtriplite.model.SurveyRadioField;
import uk.ac.edina.fieldtriplite.model.SurveyTextField;

/**
 * Created by murrayking on 21/12/2015.
 */
public interface SurveyVisitor {

    void visit(SurveyTextField field);

    void visit(SurveyRadioField field);
}
