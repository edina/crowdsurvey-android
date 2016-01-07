package uk.ac.edina.fieldtriplite.model;

import java.util.List;

/**
 * Created by murrayking on 07/12/2015.
 */
public interface SurveyFieldProperties {

    String OTHER_TOKEN = "other" ;
    String OPTIONS_TOKEN = "options" ;
    String MAX_TOKEN = "max" ;
    String MAX_CHARS_TOKEN = "max-chars";
    String PREFIX_TOKEN = "prefix" ;
    String RADIOS_TOKEN = "radios" ;
    String STEP_TOKEN = "step" ;
    String MIN_TOKEN = "min" ;
    String PLACE_HOLDER_TOKEN = "placeholder";

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
