package uk.ac.edina.fieldtriplite.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by murray on 08/12/15.
 */
public class ParseSurvey {



    public List<SurveyField> buildFields(SurveyModel surveyModel){

        List<SurveyField> surveyFields = new ArrayList<>();
        List<Map<String,Object>> fields = surveyModel.getFields();
        for (Map<String,Object> map: fields) {

            SurveyFieldImpl.RecordFieldBuilder surveyField = new SurveyFieldImpl.RecordFieldBuilder();
            for (Map.Entry<String, Object> field : map.entrySet()){
                String key = field.getKey();
                Object value = field.getValue();


                switch (key) {

                    case "id" :
                        surveyField.id(value.toString());
                        break;
                    case "type" :
                        surveyField.type(value.toString());
                        break;
                    case "label":
                        surveyField.label(value.toString());
                        break;
                    case "required":
                        surveyField.required(Boolean.valueOf(value.toString()));
                        break;
                    case "persistent":
                        surveyField.persistent(Boolean.valueOf(value.toString()));
                        break;
                    case "properties":
                        break;


                }

            }
            surveyFields.add(surveyField.build());


        }
        return surveyFields;
    }


}
