package com.example.mtg.MainActivity.Count;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mtg.R;
import com.example.mtg.databinding.FragmentCountBinding;

public class CountFragment extends Fragment {

    private FragmentCountBinding binding;
    int taskType;
    int typeNumber;
    int amountOfTask = 0;
    int resultCounter;
    private int k;
    private double z;
    int a;
    int b;
    int g;
    double c;
    double d;
    double l;
    private TaskGenerator taskGenerator;
    private CountViewsOperator countViewsOperator;

    public CountFragment(int taskType, int typeNumber) {
        this.taskType = taskType;
        this.typeNumber = typeNumber;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        countViewsOperator = new CountViewsOperator(binding);
        taskGenerator = new TaskGenerator(binding);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
        if(typeNumber == 2){
            binding.buttonMinus.setVisibility(View.VISIBLE);
        } else if (typeNumber == 3){
            binding.buttonForDecimals.setVisibility(View.VISIBLE);
        }
        countViewsOperator.buttonEnabledFalse(typeNumber);
        binding.countTimer.setOnChronometerTickListener(chronometer -> {
          if (SystemClock.elapsedRealtime() - binding.countTimer.getBase() > 0 ){
              finishCount();
          }
        });
    }



    public void initListeners() {
        ////////////////////////////////////////////////////////////////////////
        countViewsOperator.typeInButtons(typeNumber);
        ////////////////////////////////////////////////////////////////////////
        binding.startButton.setOnClickListener(view -> {
            countViewsOperator.buttonEnabledTrue(typeNumber);
            binding.startButton.setVisibility(View.GONE);
            binding.finishButton.setVisibility(View.VISIBLE);
            taskGenerator.generateTask(taskType,typeNumber);
            binding.countTimer.setBase(SystemClock.elapsedRealtime()+240240);
            binding.countTimer.setCountDown(true);
            binding.countTimer.start();
        });
        /////////////////////////////////////////////////////////////////////////
        binding.finishButton.setOnClickListener(view -> finishCount());
        ////////////////////////////////////////////////////////////////////////
        binding.okButton.setOnClickListener(view -> {
            if (binding.userAnswerText.getText().toString().length() != 0 ){
                amountOfTask++;
                parseTask();
                binding.userAnswerText.setText("");
                binding.taskText.setText("");
                calculateTaskRight();
                if(typeNumber == 3){
                    if(z==l){
                        setResults(true);
                        taskGenerator.generateTask(taskType,typeNumber);
                    }else{
                       mistakeMethod();
                    }
                }else{
                    if(k==g){
                        setResults(true);
                        taskGenerator.generateTask(taskType,typeNumber);
                    }else{
                        mistakeMethod();
                    }
                }
            }
        });
    }




    private void mistakeMethod() {
        setResults(false);
        countViewsOperator.buttonEnabledFalse(typeNumber);
        binding.userAnswerText.setVisibility(View.GONE);
        binding.taskText.setVisibility(View.GONE);
        binding.notRightImg.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            binding.notRightImg.setVisibility(View.GONE);
            binding.userAnswerText.setVisibility(View.VISIBLE);
            binding.taskText.setVisibility(View.VISIBLE);
            countViewsOperator.buttonEnabledTrue(typeNumber);
            taskGenerator.generateTask(taskType,typeNumber);
        },1000);
    }




    private void parseTask() {
        String[] answers;
        if (typeNumber == 2) {
            g = Integer.parseInt(binding.userAnswerText.getText().toString());
            answers = binding.taskText.getText().toString().split(" ");
            a = Integer.parseInt(answers[0].replace("(","").replace(")",""));
            b = Integer.parseInt(answers[2].replace("(","").replace(")",""));
        } else if (typeNumber == 1){
            g = Integer.parseInt(binding.userAnswerText.getText().toString());
            answers = binding.taskText.getText().toString().split(" ");
            a = Integer.parseInt(answers[0]);
            b = Integer.parseInt(answers[2]);
        } else{
            l = Double.parseDouble(binding.userAnswerText.getText().toString());
            answers = binding.taskText.getText().toString().split(" ");
            c = Double.parseDouble(answers[0]);
            d = Double.parseDouble(answers[2]);
        }
    }




    private void calculateTaskRight() {
        switch (taskType){
            case 1:
                if(typeNumber == 3){
                    z = c + d;
                }else{
                    k = a + b;
                }
                break;
            case 2:
                if(typeNumber == 3){
                    z = c * d;
                }else{
                    k = a * b;
                }
                break;
            case 3:
                if (typeNumber == 2){
                    k = a-b;
                }else if(typeNumber == 1){
                    if(a>b){
                        k = a-b;
                    }else{
                        k = b-a;
                    }
                }else{
                    if (c>d){
                        z = c - d;
                    }else{
                        z = d - c;
                    }
                }
                break;
            case 4:
                if (typeNumber ==3){
                    z = c / d;
                }else{
                    k = a / b;
                }
                break;
        }
    }




    private void setResults(boolean b) {
        if (b){
            resultCounter = resultCounter + 50;
        } else{
            resultCounter = resultCounter - 10;
        }
        String score = "Score: " + resultCounter;
        binding.scoreText.setText(score);
    }




    public void finishCount(){
        countViewsOperator.buttonEnabledFalse(typeNumber);
        binding.startButton.setVisibility(View.VISIBLE);
        binding.finishButton.setVisibility(View.GONE);
        binding.notRightImg.setVisibility(View.GONE);
        binding.userAnswerText.setText("");
        binding.taskText.setText("");
        int scoreForDialog = resultCounter;
        int amountForDialog = amountOfTask;
        userFinishDialog(scoreForDialog,amountForDialog);
        amountOfTask = 0;
        resultCounter = 0;
        String score = "Score: 0";
        binding.scoreText.setText(score);
        binding.countTimer.stop();
        binding.countTimer.setBase(SystemClock.elapsedRealtime());
    }

    private void userFinishDialog(int scoreForDialog, int amountForDialog) {

        Dialog dialog = new Dialog(requireActivity());
        dialog.setContentView(R.layout.finish_count_dialog);

        TextView score = dialog.findViewById(R.id.score_in_dialog);
        TextView tasks = dialog.findViewById(R.id.amount_in_dialog);

        if (scoreForDialog < 0){
            scoreForDialog = 0;
        }

        String scoreForText = getResources().getString(R.string.score) + " " + scoreForDialog;
        score.setText(scoreForText);

        String tasksForText = getResources().getString(R.string.tasks) + " " + amountForDialog;
        tasks.setText(tasksForText);

        ImageButton closeDialogButton = dialog.findViewById(R.id.close_dialog_button);
        closeDialogButton.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }




}
