package uk.ac.edina.fieldtriplite.model;

import java.util.List;

/**
 * Created by murrayking on 10/12/2015.
 */
public class SurveyFieldPropertiesImpl implements SurveyFieldProperties {

    private Boolean other;
    private String placeholder;
    private Integer maxChars;
    private String prefix;
    private List<Option> options;
    private List<Option> radios;
    private String step;
    private String min;
    private String max;

    @Override
    public String getMax() {
        return max;
    }

    @Override
    public Boolean isOther() {
        return other;
    }

    @Override
    public String getPlaceholder() {
        return placeholder;
    }

    @Override
    public Integer getMaxChars() {
        return maxChars;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public List<Option> getOptions() {
        return options;
    }

    @Override
    public List<Option> getRadios() {
        return radios;
    }

    @Override
    public String getStep() {
        return step;
    }

    @Override
    public String getMin() {
        return min;
    }

    private SurveyFieldPropertiesImpl(Boolean other, String placeholder, Integer maxChars, String prefix,
                                      List<Option> options, List<Option> radios, String step, String min, String max) {
        this.other = other;
        this.placeholder = placeholder;
        this.maxChars = maxChars;
        this.prefix = prefix;
        this.options = options;
        this.radios = radios;
        this.step = step;
        this.min = min;
        this.max = max;
    }

    public static class SurveyFieldPropertiesBuilder {

        private Boolean other;
        private String placeholder;
        private Integer maxChars;
        private String prefix;
        private List<Option> options;
        private List<Option> radios;
        private String step;
        private String min;
        private String max;


        public SurveyFieldPropertiesBuilder() {

        }

        public SurveyFieldPropertiesBuilder other(Boolean other) {
            this.other = other;
            return this;
        }

        public SurveyFieldPropertiesBuilder placeholder(String placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        public SurveyFieldPropertiesBuilder maxChars(Integer maxChars) {
            this.maxChars = maxChars;
            return this;
        }

        public SurveyFieldPropertiesBuilder prefix(String prefix) {
            this.prefix = prefix;
            return this;
        }

        public SurveyFieldPropertiesBuilder options(List<Option> options) {
            this.options = options;
            return this;
        }

        public SurveyFieldPropertiesBuilder radios(List<Option> radios) {
            this.radios = radios;
            return this;
        }

        public SurveyFieldPropertiesBuilder step(String step) {
            this.step = step;
            return this;
        }

        public SurveyFieldPropertiesBuilder min(String min) {
            this.min = min;
            return this;
        }

        public SurveyFieldPropertiesBuilder max(String max) {
            this.max = max;
            return this;
        }

        public SurveyFieldProperties build() {


            return new SurveyFieldPropertiesImpl(other, placeholder, maxChars, prefix,
                    options, radios, step, min, max);
        }

    }


}




