package uk.ac.edina.fieldtriplite.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import uk.ac.edina.fieldtriplite.survey.SurveyVisitor;

/**
 * Created by murrayking on 07/12/2015.
 */
public interface SurveyField {

    public static final String ID_TOKEN = "id" ;
    public static final String LABEL_TOKEN = "label" ;
    public static final String TYPE_TOKEN = "type" ;
    public static final String REQUIRED_TOKEN = "required" ;
    public static final String PERSISTENT_TOKEN = "persistent" ;
    public static final String PROPERTIES_TOKEN = "properties" ;

     enum Type{
        TEXT("text"),
        RADIO("radio"),
        SELECT("select"),
        RANGE("range");

        private static final Map<String,Type> lookup = new HashMap<>();

        static {
            for(Type s : EnumSet.allOf(Type.class))
                lookup.put(s.getCode(), s);
        }

        private String code;


         Type(String code) {
            this.code = code;
        }

        public String getCode() { return code; }

        public static Type get(String code) {
            Type t =  lookup.get(code);
            if(t != null ){
                return t;

            } else {
                throw new IllegalArgumentException("Illegal Survey Field Type: " + code);
            }
        }
    }


    String getId();

    String getLabel();

    SurveyField.Type getType();

    Boolean isRequired();

    Boolean isPersistent();

    SurveyFieldProperties getSurveyFieldProperties();


    void convert(SurveyVisitor vistor);


}
