package com.example.mtg.MainActivity.Count;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
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
    int typeNumber;
    int resultCounter;
    private String[] answers;
    private int k;
    private TaskGenerator taskGenerator;
    private CountViewsOperator countViewsOperator;

    public CountFragment( int taskType, int typeNumber) {
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

        countViewsOperator.typeInButtons(typeNumber);

        binding.startButton.setOnClickListener(view -> {
            countViewsOperator.buttonEnabledTrue(typeNumber);
            binding.startButton.setVisibility(View.GONE);
            binding.finishButton.setVisibility(View.VISIBLE);
            taskGenerator.generateTask(taskType,typeNumber);
            binding.countTimer.setBase(SystemClock.elapsedRealtime()+240240);
            binding.countTimer.setCountDown(true);
            binding.countTimer.start();
        });

        binding.finishButton.setOnClickListener(view -> finishCount());

        binding.okButton.setOnClickListener(view -> {

            if (binding.userAnswerText.getText().toString().length() != 0 ){
                int a;
                int b;
                int g;
                if (typeNumber == 2) {
                    g = Integer.parseInt(binding.userAnswerText.getText().toString());
                    answers = binding.taskText.getText().toString().split(" ");
                    a = Integer.parseInt(answers[0].replace("(","").replace(")",""));
                    b = Integer.parseInt(answers[2].replace("(","").replace(")",""));
                } else {
                    g = Integer.parseInt(binding.userAnswerText.getText().toString());
                    answers = binding.taskText.getText().toString().split(" ");
                    a = Integer.parseInt(answers[0]);
                    b = Integer.parseInt(answers[2]);
                }

                binding.userAnswerText.setText("");
                binding.taskText.setText("");

                switch (taskType){
                    case 1:
                        k = a + b;
                        break;
                    case 2:
                        k = a * b;
                        break;
                    case 3:
                        if (typeNumber == 2){
                            k = a-b;
                        }else{
                            if(a>b){
                                k = a-b;
                            }else{
                                k = b-a;
                            }
                        }
                        break;
                    default:
                }
                if(k==g){
                    setResults(true);
                    taskGenerator.generateTask(taskType,typeNumber);
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

                        taskGenerator.generateTask(taskType,typeNumber);
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

    public void finishCount(){
        countViewsOperator.buttonEnabledFalse(typeNumber);
        binding.startButton.setVisibility(View.VISIBLE);
        binding.finishButton.setVisibility(View.GONE);
        binding.notRightImg.setVisibility(View.GONE);
        binding.userAnswerText.setText("");
        binding.taskText.setText("");
        resultCounter = 0;
        binding.scoreText.setText("");
        binding.countTimer.stop();
        binding.countTimer.setBase(SystemClock.elapsedRealtime());
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            binding.userAnswerText.setText("");
            binding.taskText.setText("");
        },1000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
