package uk.ac.edina.fieldtriplite.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by murray on 08/12/15.
 */
public class ParseSurvey {


    public List<SurveyField> buildFields(SurveyModel surveyModel) {

        List<SurveyField> surveyFields = new ArrayList<>();
        List<Map<String, Object>> fields = surveyModel.getFields();


        SurveyFieldImpl.RecordFieldBuilder recordFieldBuilder = new SurveyFieldImpl.RecordFieldBuilder();


        for (Map<String, Object> map : fields) {

            SurveyFieldImpl.RecordFieldBuilder surveyFieldBuilder = new SurveyFieldImpl.RecordFieldBuilder();


                SurveyField surveyField = surveyFieldBuilder
                        .id(map.get(SurveyField.ID_TOKEN).toString())
                        .type(map.get(SurveyField.TYPE_TOKEN).toString())
                        .label(map.get(SurveyField.LABEL_TOKEN).toString())
                        .required(Boolean.valueOf(map.get(SurveyField.REQUIRED_TOKEN).toString()))
                        .persistent(Boolean.valueOf(map.get(SurveyField.PERSISTENT_TOKEN).toString()))
                        .properties(buildFieldProperties((Map<String, Object>) map.get(SurveyField.PROPERTIES_TOKEN)))
                        .build() ;

                 surveyFields.add(surveyField);


        }
        return surveyFields;
    }


    private SurveyFieldProperties buildFieldProperties(Map<String, Object> properties) {

        SurveyFieldPropertiesImpl.SurveyFieldPropertiesBuilder surveyFieldPropertiesBuilder = new SurveyFieldPropertiesImpl.SurveyFieldPropertiesBuilder();


        return surveyFieldPropertiesBuilder
                .other(Boolean.valueOf(properties.get(SurveyFieldProperties.OTHER_TOKEN).toString()))
                .options(buildOptions( (List<Object>) properties.get(SurveyFieldProperties.OPTIONS_TOKEN) ))
                .build() ;

    }


    private List<Option> buildOptions(List<Object> options) {
        List<Option> propertyOptions = new ArrayList<>();
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


}
