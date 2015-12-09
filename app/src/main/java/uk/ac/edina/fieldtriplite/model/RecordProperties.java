package uk.ac.edina.fieldtriplite.model;

import java.util.List;

/**
 * Created by murrayking on 07/12/2015.
 */
public interface RecordProperties {

    String getPlaceholder();
    Integer getMaxChars();
    String getPrefix();
    List<Option> getOptions();
}
