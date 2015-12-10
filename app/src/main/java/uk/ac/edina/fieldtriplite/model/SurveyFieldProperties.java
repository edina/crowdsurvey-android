package uk.ac.edina.fieldtriplite.model;

import java.util.List;

/**
 * Created by murrayking on 07/12/2015.
 */
public interface SurveyFieldProperties {

    String getMax();

    Boolean isOther();

    String getPlaceholder();

    Integer getMaxChars();

    String getPrefix();

    List<Option> getOptions();

    List<Option> getRadios();

    String getStep();

    String getMin();
}
