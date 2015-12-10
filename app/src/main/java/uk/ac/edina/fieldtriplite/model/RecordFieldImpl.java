package uk.ac.edina.fieldtriplite.model;

/**
 * Created by murrayking on 09/12/2015.
 */

import com.google.common.base.Strings;

public class RecordFieldImpl implements  RecordField {

    private String id;

    private String label;

    private String type;

    private Boolean required;

    private Boolean persistent;

    private RecordProperties recordProperties;

    private RecordFieldImpl(String id, String label, String type, Boolean required, Boolean persistent, RecordProperties recordProperties) {
        this.id = id;
        this.label = label;
        this.type = type;
        this.required = required;
        this.persistent = persistent;
        this.recordProperties = recordProperties;
    }

    @Override
    public RecordProperties getRecordProperties() {
        return recordProperties;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Boolean isRequired() {
        return required;
    }

    @Override
    public Boolean isPersistent() {
        return persistent;
    }


    public static class RecordFieldBuilder {
        private String id;

        private String label;

        private String type;

        private Boolean required;

        private Boolean persistent;

        private RecordProperties recordProperties;

        public RecordFieldBuilder(){}

        public RecordFieldBuilder id(String id) {
            this.id = id;
            return this;
        }

        public RecordFieldBuilder type(String type) {
            this.type = type;
            return this;
        }

        public RecordFieldBuilder label(String label) {
            this.label = label;
            return this;
        }


        public RecordFieldBuilder required(Boolean required) {
            this.required = required;
            return this;
        }

        public RecordFieldBuilder persistent(Boolean persistent){
            this.persistent = persistent;
            return this;
        }
        public RecordFieldBuilder required(RecordProperties recordProperties){
            this.recordProperties = recordProperties;
            return this;
        }



        public RecordField build()
        {
            if(Strings.isNullOrEmpty(id) || Strings.isNullOrEmpty(label) || Strings.isNullOrEmpty(type)){
                throw new IllegalArgumentException("Missing required Param for RecordField");
            }
            return new RecordFieldImpl( id, label, type, required, persistent, recordProperties);
        }
    }

}
