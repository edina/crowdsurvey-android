package uk.ac.edina.fieldtriplite.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by murray on 08/12/15.
 */
public class SurveyParser {


    public List<SurveyField> buildFields(SurveyModel surveyModel) {

        List<SurveyField> surveyFields = new ArrayList<>();
        List<Map<String, Object>> fields = surveyModel.getFields();


        SurveyFieldBase.SurveyFieldBuilder recordFieldBuilder = new SurveyFieldBase.SurveyFieldBuilder();


        for (Map<String, Object> map : fields) {

            SurveyFieldBase.SurveyFieldBuilder surveyFieldBuilder = new SurveyFieldBase.SurveyFieldBuilder();


                SurveyField surveyField = surveyFieldBuilder
                        .id(coerceToString(map.get(SurveyField.ID_TOKEN)))
                        .type(coerceToString(map.get(SurveyField.TYPE_TOKEN)))
                        .label(coerceToString(map.get(SurveyField.LABEL_TOKEN)))
                        .required(coerceToBoolean(map.get(SurveyField.REQUIRED_TOKEN)))
                        .persistent(coerceToBoolean(map.get(SurveyField.PERSISTENT_TOKEN)))
                        .properties(buildFieldProperties((Map<String, Object>) map.get(SurveyField.PROPERTIES_TOKEN)))
                        .build() ;

                 surveyFields.add(surveyField);


        }
        return surveyFields;
    }




    private SurveyFieldProperties buildFieldProperties(Map<String, Object> properties) {

        SurveyFieldPropertiesImpl.SurveyFieldPropertiesBuilder surveyFieldPropertiesBuilder = new SurveyFieldPropertiesImpl.SurveyFieldPropertiesBuilder();


        return surveyFieldPropertiesBuilder
                .other(coerceToBoolean(properties.get(SurveyFieldProperties.OTHER_TOKEN)))
                .maxChars(coerceToString(properties.get(SurveyFieldProperties.MAX_CHARS_TOKEN)))
                .placeholder(coerceToString(properties.get(SurveyFieldProperties.PLACE_HOLDER_TOKEN)))
                .options(buildOptions((List<Object>) properties.get(SurveyFieldProperties.OPTIONS_TOKEN)))
                .prefix(coerceToString(properties.get(SurveyFieldProperties.PREFIX_TOKEN)))
                .build() ;

    }


    private List<Option> buildOptions(List<Object> options) {

        List<Option> propertyOptions = new ArrayList<>();
        //boundary
        if(options == null) {
            return propertyOptions;
        }
        for (Object option : options) {
            if(option instanceof String){
                propertyOptions.add(new OptionImpl(option.toString(), null));
            } else if(option instanceof List){
                List<String> optionWithImageLocation = (List<String>) option;
                String label = optionWithImageLocation.get(0);
                String imageLocation = optionWithImageLocation.get(1);
                propertyOptions.add(new OptionImpl(label, imageLocation));
            }
        }
        return propertyOptions;
    }

    private String coerceToString(Object possibleNullValue){
        return possibleNullValue == null ? "" : possibleNullValue.toString();
    }
    private Boolean coerceToBoolean(Object possibleNullValue){
        return possibleNullValue == null ? Boolean.FALSE : Boolean.valueOf(possibleNullValue.toString());
    }


}
