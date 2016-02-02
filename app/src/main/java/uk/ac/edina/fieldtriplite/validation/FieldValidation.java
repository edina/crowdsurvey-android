package uk.ac.edina.fieldtriplite.validation;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by murrayking on 08/01/2016.
 */
public class FieldValidation {

    public void maxNumberOfChars(final TextInputLayout dynamic, final Integer maxChars, final EditText dynamicEditText, final String errorMessage) {
        dynamicEditText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String textToValidate = dynamicEditText.getText().toString();
                if (textToValidate.length() > maxChars) {

                    dynamic.setError(errorMessage + " " + maxChars );
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}


            public void onTextChanged(CharSequence s, int start, int before, int count) {}

        });
    }
}
