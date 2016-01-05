package uk.ac.edina.fieldtriplite.model;

/**
 * Created by murrayking on 09/12/2015.
 */

import com.google.common.base.Strings;

public abstract class SurveyFieldBase implements SurveyField {

    private String id;

    private String label;

    private SurveyField.Type type;

    private Boolean required;

    private Boolean persistent;

    private SurveyFieldProperties surveyFieldProperties;

    protected SurveyFieldBase(String id, String label, SurveyField.Type type, Boolean required, Boolean persistent, SurveyFieldProperties surveyFieldProperties) {
        this.id = id;
        this.label = label;
        this.type = type;
        this.required = required;
        this.persistent = persistent;
        this.surveyFieldProperties = surveyFieldProperties;
    }

    @Override
    public SurveyFieldProperties getSurveyFieldProperties() {
        return surveyFieldProperties;
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
    public SurveyField.Type getType() {
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


    public static class SurveyFieldBuilder {
        private String id;

        private String label;

        private SurveyField.Type type;

        private Boolean required;

        private Boolean persistent;

        private SurveyFieldProperties surveyFieldProperties;

        public SurveyFieldBuilder() {
        }

        public SurveyFieldBuilder id(String id) {
            this.id = id;
            return this;
        }

        public SurveyFieldBuilder type(String type) {

            this.type = SurveyField.Type.get(type);
            return this;
        }

        public SurveyFieldBuilder label(String label) {
            this.label = label;
            return this;
        }


        public SurveyFieldBuilder required(Boolean required) {
            this.required = required;
            return this;
        }

        public SurveyFieldBuilder persistent(Boolean persistent) {
            this.persistent = persistent;
            return this;
        }

        public SurveyFieldBuilder properties(SurveyFieldProperties surveyFieldProperties) {
            this.surveyFieldProperties = surveyFieldProperties;
            return this;
        }


        public SurveyField build() {
            if (Strings.isNullOrEmpty(id) || Strings.isNullOrEmpty(label)) {
                throw new IllegalArgumentException("Missing required Param for SurveyField");
            }
            SurveyField surveyField = null;
            switch (type){

                case TEXT:
                    surveyField = new SurveyTextField(id, label, type, required, persistent, surveyFieldProperties);
                    break;
                case RADIO:
                    surveyField = new SurveyRadioField(id, label, type, required, persistent, surveyFieldProperties);
                    break;

            }

            return surveyField;

        }


    }

}