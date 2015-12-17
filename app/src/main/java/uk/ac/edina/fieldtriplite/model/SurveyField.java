package uk.ac.edina.fieldtriplite.model;

/**
 * Created by murrayking on 07/12/2015.
 */
interface SurveyField {

    public static final String ID_TOKEN = "id" ;
    public static final String LABEL_TOKEN = "label" ;
    public static final String TYPE_TOKEN = "type" ;
    public static final String REQUIRED_TOKEN = "required" ;
    public static final String PERSISTENT_TOKEN = "persistent" ;
    public static final String PROPERTIES_TOKEN = "properties" ;


    String getId();

    String getLabel();

    String getType();

    Boolean isRequired();

    Boolean isPersistent();

    SurveyFieldProperties getSurveyFieldProperties();


}
