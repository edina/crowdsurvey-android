package uk.ac.edina.fieldtriplite.model;

import com.strongloop.android.loopback.ModelRepository;

/**
 * Created by murrayking on 11/12/2015.
 */

public  class SurveyModelRepository extends ModelRepository<SurveyModel> {
    public SurveyModelRepository() {
        super("survey", "survey", SurveyModel.class);
    }
}