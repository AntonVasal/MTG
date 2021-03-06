package com.example.mtg.utility.textwatchers;

import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputLayout;

public class ValidationTextWatcher implements TextWatcher {

    private final TextInputLayout textInputLayout;

    public ValidationTextWatcher(TextInputLayout textInputLayout) {
        this.textInputLayout = textInputLayout;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (textInputLayout.getError() != null) {
            textInputLayout.setErrorEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }
}
