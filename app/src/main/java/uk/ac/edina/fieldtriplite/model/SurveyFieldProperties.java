package uk.ac.edina.fieldtriplite.model;

import java.util.List;

/**
 * Created by murrayking on 07/12/2015.
 */
public interface SurveyFieldProperties {

    public static final String OTHER_TOKEN = "other" ;
    public static final String OPTIONS_TOKEN = "options" ;
    public static final String MAX_TOKEN = "max" ;
    public static final String PREFIX_TOKEN = "prefix" ;
    public static final String RADIOS_TOKEN = "radios" ;
    public static final String STEP_TOKEN = "step" ;
    public static final String MIN_TOKEN = "min" ;

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
