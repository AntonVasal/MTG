package com.example.mtg.ui.dialogs.connectionDialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.mtg.databinding.DialogConnectionBinding;

public class ConnectionDialog extends Dialog {
    private Context context;
    private final DialogConnectionBinding binding;
    private final String text;

    public ConnectionDialog(@NonNull Context context, DialogConnectionBinding binding, String text) {
        super(context);
        this.binding = binding;
        this.text = text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        loadData();
        binding.closeButtonConnectionDialog.setOnClickListener(view -> dismiss());
    }

    private void loadData() {
        binding.disconnectText.setText(text);
    }
}
