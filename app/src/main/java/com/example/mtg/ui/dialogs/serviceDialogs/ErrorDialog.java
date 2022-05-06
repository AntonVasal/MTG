package com.example.mtg.ui.dialogs.serviceDialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.mtg.databinding.DialogErrorOccurBinding;

public class ErrorDialog extends Dialog {

    private String message;
    private final String title;
    private final DialogErrorOccurBinding binding;

    public ErrorDialog(@NonNull Context context, String message, String title, DialogErrorOccurBinding binding) {
        super(context);
        this.message = message;
        this.title = title;
        this.binding = binding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        binding.errorDialogTitle.setText(title);
        binding.errorDialogText.setText(message);
    }

    public void setMessage(String message){
        this.message = message;
    }
}
