package uk.ac.edina.fieldtriplite.model;

/**
 * Created by murrayking on 07/12/2015.
 */
interface RecordField {

    String getId();

    String getLabel();

    String getType();

    Boolean isRequired();

    Boolean isPersistent();

    RecordProperties getRecordProperties();


}
