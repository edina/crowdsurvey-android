package uk.ac.edina.fieldtriplite.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by murray on 08/12/15.
 */
public class ParseEditor {



    public List<RecordField> buildFields(RecordModel recordModel){

        List<RecordField> recordFields = new ArrayList<>();
        List<Map<String,Object>> fields = recordModel.getFields();
        for (Map<String,Object> map: fields) {

            RecordFieldImpl.RecordFieldBuilder recordField = new RecordFieldImpl.RecordFieldBuilder();
            for (Map.Entry<String, Object> field : map.entrySet()){
                String key = field.getKey();
                Object value = field.getValue();


                switch (key) {

                    case "id" :
                        recordField.id(value.toString());
                        break;
                    case "type" :
                        recordField.type(value.toString());
                        break;
                    case "label":
                        recordField.label(value.toString());
                        break;
                    case "required":
                        recordField.required(Boolean.valueOf(value.toString()));
                        break;
                    case "persistent":
                        recordField.persistent(Boolean.valueOf(value.toString()));
                        break;
                    case "properties":
                        break;


                }

            }
            recordFields.add(recordField.build());


        }
        return recordFields;
    }


}
