package uk.ac.edina.fieldtriplite.model;

import java.util.List;
import java.util.Map;

/**
 * Created by murray on 08/12/15.
 */
public class ParseEditor {



    public void buildFields(RecordModel recordModel){
        List<Map<String,Object>> fields = recordModel.getFields();
        for (Map<String,Object> map: fields) {
            for (Map.Entry<String, Object> field : map.entrySet()){
                String key = field.getKey();
                Object value = field.getValue();

                switch (key) {

                    case "key" :

                }

            }
        }
    }

}
