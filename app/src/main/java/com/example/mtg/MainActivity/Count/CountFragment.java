package com.example.mtg.MainActivity.Count;


import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mtg.databinding.FragmentCountBinding;

import java.util.Random;

public class CountFragment extends Fragment {

    private FragmentCountBinding binding;
    int taskType;
    int typeNumber;
    int resultCounter;
    private String[] answers;
    private int k;

    public CountFragment( int taskType, int typeNumber) {
        this.taskType = taskType;
        this.typeNumber = typeNumber;
    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
        buttonEnabledFalse();
        binding.countTimer.setOnChronometerTickListener(chronometer -> {
          if(binding.countTimer.getDrawingTime() == 0 ){
              buttonEnabledFalse();
              binding.startButton.setVisibility(View.VISIBLE);
              binding.finishButton.setVisibility(View.GONE);
              binding.notRightImg.setVisibility(View.GONE);
              binding.userAnswerText.setText("");
              binding.taskText.setText("");
              resultCounter = 0;
              binding.scoreText.setText("");
              binding.countTimer.stop();
              binding.countTimer.setBase(SystemClock.elapsedRealtime());
          }
        });
    }


    public void buttonEnabledFalse() {
        binding.deleteButton.setEnabled(false);
        binding.okButton.setEnabled(false);
        binding.button0.setEnabled(false);
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

    public void buttonEnabledTrue(){
        binding.deleteButton.setEnabled(true);
        binding.okButton.setEnabled(true);
        binding.button0.setEnabled(true);
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

    public void initListeners() {
        binding.deleteButton.setOnClickListener(view -> {
            if(binding.userAnswerText.getText().toString().length() != 0){
                String delete = binding.userAnswerText.getText().toString();
                binding.userAnswerText.setText(delete.substring(0,delete.length()-1));
            }
        });


        binding.button0.setOnClickListener(view -> binding.userAnswerText.setText(binding.userAnswerText.getText().toString().concat("0")));
        binding.button1.setOnClickListener(view -> binding.userAnswerText.setText(binding.userAnswerText.getText().toString().concat("1")));
        binding.button2.setOnClickListener(view -> binding.userAnswerText.setText(binding.userAnswerText.getText().toString().concat("2")));
        binding.button3.setOnClickListener(view -> binding.userAnswerText.setText(binding.userAnswerText.getText().toString().concat("3")));
        binding.button4.setOnClickListener(view -> binding.userAnswerText.setText(binding.userAnswerText.getText().toString().concat("4")));
        binding.button5.setOnClickListener(view -> binding.userAnswerText.setText(binding.userAnswerText.getText().toString().concat("5")));
        binding.button6.setOnClickListener(view -> binding.userAnswerText.setText(binding.userAnswerText.getText().toString().concat("6")));
        binding.button7.setOnClickListener(view -> binding.userAnswerText.setText(binding.userAnswerText.getText().toString().concat("7")));
        binding.button8.setOnClickListener(view -> binding.userAnswerText.setText(binding.userAnswerText.getText().toString().concat("8")));
        binding.button9.setOnClickListener(view -> binding.userAnswerText.setText(binding.userAnswerText.getText().toString().concat("9")));


        binding.startButton.setOnClickListener(view -> {
            buttonEnabledTrue();
            binding.startButton.setVisibility(View.GONE);
            binding.finishButton.setVisibility(View.VISIBLE);
            generateTask(taskType,typeNumber);
            binding.countTimer.setBase(SystemClock.elapsedRealtime()+240240);
            binding.countTimer.setCountDown(true);
            binding.countTimer.start();
        });

        binding.finishButton.setOnClickListener(view -> {
            buttonEnabledFalse();
            binding.startButton.setVisibility(View.VISIBLE);
            binding.finishButton.setVisibility(View.GONE);
            binding.notRightImg.setVisibility(View.GONE);
            binding.userAnswerText.setText("");
            binding.taskText.setText("");
            resultCounter = 0;
            binding.scoreText.setText("");
            binding.countTimer.stop();
            binding.countTimer.setBase(SystemClock.elapsedRealtime());
        });




        binding.okButton.setOnClickListener(view -> {

            if (binding.userAnswerText.getText().toString().length() != 0 ){
                int g = Integer.parseInt(binding.userAnswerText.getText().toString());
                answers = binding.taskText.getText().toString().split(" ");
                int a = Integer.parseInt(answers[0]);
                int b = Integer.parseInt(answers[2]);

                binding.userAnswerText.setText("");
                binding.taskText.setText("");

                switch (taskType){
                    case 1:
                        k = a + b;
                        break;
                    case 2:
                        k = a * b;
                        break;
                    default:
                }
                if(k==g){
                    setResults(true);
                    generateTask(taskType,typeNumber);
                }else{
                    setResults(false);

                    binding.userAnswerText.setVisibility(View.GONE);
                    binding.taskText.setVisibility(View.GONE);
                    binding.notRightImg.setVisibility(View.VISIBLE);

                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        binding.notRightImg.setVisibility(View.GONE);
                        binding.userAnswerText.setVisibility(View.VISIBLE);
                        binding.taskText.setVisibility(View.VISIBLE);

                        generateTask(taskType,typeNumber);
                    },1000);
                }

            }


        });

    }

    private void setResults(boolean b) {
        if (b){
            resultCounter = resultCounter + 50;
        } else{
            resultCounter = resultCounter - 10;
        }
        String score = Integer.toString(resultCounter);
        binding.scoreText.setText(score);
    }

    private void generateTask(int taskType, int typeNumber) {
        switch (typeNumber){
            case 1:
                switch (taskType){
                    case 1:
                        generateNaturalAddTask();
                        break;
                    case 2:
                        generateNaturalMultiTask();
                        break;
                    case 3:
                        generateNaturalSubTask();
                        break;
                    case 4:
                        generateNaturalDivTask();
                        break;
                }
                break;
            default:
        }
    }

    private void generateNaturalDivTask() {
    }

    private void generateNaturalSubTask() {
    }

    private void generateNaturalMultiTask() {
        Random random = new Random();
        int a = random.nextInt(100);
        int b = random.nextInt(100);
        String task = a + " x " + b + " = ";
        binding.taskText.setText(task);
    }

    private void generateNaturalAddTask() {
        Random random = new Random();
        int a = random.nextInt(10000);
        int b = random.nextInt(10000);
        String task = a + " + " + b + " = ";
        binding.taskText.setText(task);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
