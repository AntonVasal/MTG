package com.example.mtg.Count;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mtg.databinding.FragmentCountBinding;

public class CountFragment extends Fragment {

    private FragmentCountBinding binding;
    int taskType;

    public CountFragment( int taskType, int typeNumber) {
        this.taskType = taskType;
        this.typeNumber = typeNumber;
    }

    int typeNumber;
    String userAnswer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCountBinding.inflate(inflater, container, false);
        initListeners();
        View view = binding.getRoot();
        return view;
    }



    private void buttonEnabledFalse() {
        binding.button1.setEnabled(false);
        binding.button2.setEnabled(false);
        binding.button3.setEnabled(false);
        binding.button4.setEnabled(false);
        binding.button5.setEnabled(false);
        binding.button6.setEnabled(false);
        binding.button7.setEnabled(false);
        binding.button8.setEnabled(false);
        binding.button9.setEnabled(false);
    }

    private void buttonEnabledTrue(){
        binding.button1.setEnabled(true);
        binding.button2.setEnabled(true);
        binding.button3.setEnabled(true);
        binding.button4.setEnabled(true);
        binding.button5.setEnabled(true);
        binding.button6.setEnabled(true);
        binding.button7.setEnabled(true);
        binding.button8.setEnabled(true);
        binding.button9.setEnabled(true);
    }

    private void initListeners() {
        binding.button0.setOnClickListener(view -> binding.userAnswerText.setText(userAnswer.concat("0")));
        binding.button1.setOnClickListener(view -> binding.userAnswerText.setText(userAnswer.concat("1")));
        binding.button2.setOnClickListener(view -> binding.userAnswerText.setText(userAnswer.concat("2")));
        binding.button3.setOnClickListener(view -> binding.userAnswerText.setText(userAnswer.concat("3")));
        binding.button4.setOnClickListener(view -> binding.userAnswerText.setText(userAnswer.concat("4")));
        binding.button5.setOnClickListener(view -> binding.userAnswerText.setText(userAnswer.concat("5")));
        binding.button6.setOnClickListener(view -> binding.userAnswerText.setText(userAnswer.concat("6")));
        binding.button7.setOnClickListener(view -> binding.userAnswerText.setText(userAnswer.concat("7")));
        binding.button8.setOnClickListener(view -> binding.userAnswerText.setText(userAnswer.concat("8")));
        binding.button9.setOnClickListener(view -> binding.userAnswerText.setText(userAnswer.concat("9")));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
