package uk.ac.edina.fieldtriplite.model;

import com.google.common.base.Optional;

/**
 * Created by murrayking on 10/12/2015.
 */
public class OptionImpl implements Option {

    private String label;

    private Optional<String> imageLocation;

    public OptionImpl(String label, String imageLocation) {
        this.imageLocation =  Optional.fromNullable(imageLocation);
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public Optional<String> getImageLocation() {
        return imageLocation;
    }
}
