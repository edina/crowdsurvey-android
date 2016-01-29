package uk.ac.edina.fieldtriplite.survey;

import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Iterator;

import uk.ac.edina.fieldtriplite.R;
import uk.ac.edina.fieldtriplite.activity.SurveyActivity;
import uk.ac.edina.fieldtriplite.model.SurveyField;
import uk.ac.edina.fieldtriplite.model.SurveyImageField;
import uk.ac.edina.fieldtriplite.model.SurveyRadioField;
import uk.ac.edina.fieldtriplite.model.SurveyTextField;
import uk.ac.edina.fieldtriplite.utils.DisplayUtil;
import uk.ac.edina.fieldtriplite.validation.FieldValidation;

/**
 * Created by murrayking on 22/12/2015.
 */
public class SurveyModelToViewVisitor implements SurveyVisitor {
    private final VisitAll visitAll = new VisitAll(this);
    ViewGroup layoutContainer;
    SurveyActivity activity;
    FieldValidation fieldValidation = new FieldValidation();

    public SurveyModelToViewVisitor(SurveyActivity activity, ViewGroup layoutContainer){
        this.activity = activity;
        this.layoutContainer = layoutContainer;
    }

    public void visitAll(Iterator<SurveyField> fieldIterator){
        visitAll.visit(fieldIterator);
    }

    @Override
    public void visit(final SurveyTextField field) {

        final TextInputLayout dynamic = new TextInputLayout(activity);
        final Integer maxChars = field.getSurveyFieldProperties().getMaxChars();

        final EditText dynamicEditText = new EditText(activity);
        dynamicEditText.setHint(field.getLabel());
        dynamicEditText.setId(field.getFormId());

        dynamic.addView(dynamicEditText, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layoutContainer.addView(dynamic, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        fieldValidation.maxNumberOfChars(dynamic, maxChars, dynamicEditText);

    }



    @Override
    public void visit(SurveyRadioField field) {

    }

    @Override
    public void visit(SurveyImageField field) {
        LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);

        Button takePhotoButton = new Button(activity);
        takePhotoButton.setText(activity.getString(R.string.take_photo_button));
        Button photoGalleryButton = new Button(activity);
        photoGalleryButton.setText(activity.getString(R.string.gallery_photo_button));
        final ImageView thumbImage = new ImageView(activity);

        thumbImage.setId(field.getFormId());
        thumbImage.setBackgroundColor(Color.GRAY);
        linearLayout.addView(takePhotoButton, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.addView(photoGalleryButton, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        int dpThumbnailSize = DisplayUtil.convertDpToPixel(75, activity);
        linearLayout.addView(thumbImage, new LinearLayout.LayoutParams(dpThumbnailSize, dpThumbnailSize));
        layoutContainer.addView(linearLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));


        final CameraFieldHelper cameraFieldHelper = new CameraFieldHelper(activity, thumbImage);
        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                cameraFieldHelper.takePhoto();

            }
        });
        photoGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cameraFieldHelper.chooseFromGallery();

            }
        });


    }


}
