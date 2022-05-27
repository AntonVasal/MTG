package com.example.mtg.ui.activities.mainActivity.countFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.mtg.R;
import com.example.mtg.core.baseFragments.BaseBindingFragment;
import com.example.mtg.databinding.FragmentCountBinding;
import com.example.mtg.ui.activities.mainActivity.countFragment.countResultsToFirestoreSetters.CountResultsToFirestoreSettersOperator;
import com.example.mtg.utility.tasksGenerators.AdvantageTasksGenerator;
import com.example.mtg.utility.tasksGenerators.MediumPlusTasksGenerator;
import com.example.mtg.utility.tasksGenerators.MediumTasksGenerator;
import com.example.mtg.utility.tasksGenerators.PrimaryTasksGenerator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CountFragment extends BaseBindingFragment<FragmentCountBinding> {

    private int taskType;
    private int typeNumber;
    FirebaseFirestore mFirebaseFirestore;
    FirebaseAuth mAuth;
    private int intNumberOneForRevision, intNumberTwoForRevision, intUserAnswer, intRightAnswer,resultCounter,amountOfTask;
    private double l;
    private double z;
    private AdvantageTasksGenerator advantageTasksGenerator;
    private MediumPlusTasksGenerator mediumPlusTasksGenerator;
    private MediumTasksGenerator mediumTasksGenerator;
    private PrimaryTasksGenerator primaryTasksGenerator;
    private CountViewsOperator countViewsOperator;
    private CountResultsToFirestoreSettersOperator countResultsToFirestoreSettersOperator;
    private static final String TYPE_NUMBER = "typeNumber";
    private static final String TASK_TYPE = "taskType";
    private BigDecimal numberOneForRevision;
    private BigDecimal numberTwoForRevision;
    private BigDecimal revisionNumber;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        typeNumber = getArguments().getInt(TYPE_NUMBER);
        taskType = getArguments().getInt(TASK_TYPE);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        countViewsOperator = new CountViewsOperator(binding);
        advantageTasksGenerator = new AdvantageTasksGenerator(binding);
        mediumPlusTasksGenerator = new MediumPlusTasksGenerator(binding);
        mediumTasksGenerator = new MediumTasksGenerator(binding);
        primaryTasksGenerator = new PrimaryTasksGenerator(binding);
        countResultsToFirestoreSettersOperator = new CountResultsToFirestoreSettersOperator(mFirebaseFirestore,mAuth,typeNumber,taskType);
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
            tasksComplexity();
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
                    z = revisionNumber.doubleValue();
                    if(z==l){
                        setResults(true);
                        tasksComplexity();
                    }else{
                       mistakeMethod();
                    }
                }else{
                    if(intRightAnswer == intUserAnswer){
                        setResults(true);
                        tasksComplexity();
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
            try {
                binding.notRightImg.setVisibility(View.GONE);
                binding.userAnswerText.setVisibility(View.VISIBLE);
                binding.taskText.setVisibility(View.VISIBLE);
                countViewsOperator.buttonEnabledTrue(typeNumber);
                tasksComplexity();
            }catch (Exception e){
                e.printStackTrace();
            }
        },1000);
    }

    private void tasksComplexity() {
        if (SystemClock.elapsedRealtime() - binding.countTimer.getBase() < -180000 || SystemClock.elapsedRealtime() - binding.countTimer.getBase() > 1){
            primaryTasksGenerator.generateTask(taskType,typeNumber);
        }else if(SystemClock.elapsedRealtime() - binding.countTimer.getBase() < -120000){
            mediumTasksGenerator.generateTask(taskType,typeNumber);
        }else if (SystemClock.elapsedRealtime() - binding.countTimer.getBase() < -60000){
            mediumPlusTasksGenerator.generateTask(taskType,typeNumber);
        }else{
            advantageTasksGenerator.generateTask(taskType,typeNumber);
        }

    }

    private void parseTask() {
        String[] answers;
        if (typeNumber == 2) {
            intUserAnswer = Integer.parseInt(binding.userAnswerText.getText().toString());
            answers = binding.taskText.getText().toString().split(" ");
            intNumberOneForRevision = Integer.parseInt(answers[0].replace("(","").replace(")",""));
            intNumberTwoForRevision = Integer.parseInt(answers[2].replace("(","").replace(")",""));
        } else if (typeNumber == 1){
            intUserAnswer = Integer.parseInt(binding.userAnswerText.getText().toString());
            answers = binding.taskText.getText().toString().split(" ");
            intNumberOneForRevision = Integer.parseInt(answers[0]);
            intNumberTwoForRevision = Integer.parseInt(answers[2]);
        } else{
            l = Double.parseDouble(binding.userAnswerText.getText().toString());
            answers = binding.taskText.getText().toString().split(" ");
            double c = Double.parseDouble(answers[0]);
            double d = Double.parseDouble(answers[2]);
            numberOneForRevision = BigDecimal.valueOf(c);
            numberTwoForRevision = BigDecimal.valueOf(d);
        }
    }

    private void calculateTaskRight() {
        switch (taskType){
            case 1:
                if(typeNumber == 3){
                    revisionNumber = numberOneForRevision.add(numberTwoForRevision);
                }else{
                    intRightAnswer = intNumberOneForRevision + intNumberTwoForRevision;
                }
                break;
            case 2:
                if(typeNumber == 3){
                    revisionNumber = numberOneForRevision.multiply(numberTwoForRevision);
                }else{
                    intRightAnswer = intNumberOneForRevision * intNumberTwoForRevision;
                }
                break;
            case 3:
                if (typeNumber == 2){
                    intRightAnswer = intNumberOneForRevision - intNumberTwoForRevision;
                }else if(typeNumber == 1){
                    if(intNumberOneForRevision > intNumberTwoForRevision){
                        intRightAnswer = intNumberOneForRevision - intNumberTwoForRevision;
                    }else{
                        intRightAnswer = intNumberTwoForRevision - intNumberOneForRevision;
                    }
                }else{
                    if (numberOneForRevision.compareTo(numberTwoForRevision) > 0 ){
                        revisionNumber = numberOneForRevision.subtract(numberTwoForRevision);
                    }else{
                        revisionNumber = numberTwoForRevision.subtract(numberOneForRevision);
                    }
                }
                break;
            case 4:
                if (typeNumber ==3){
                    revisionNumber = numberOneForRevision.divide(numberTwoForRevision,4, RoundingMode.DOWN);
                }else{
                    intRightAnswer = intNumberOneForRevision / intNumberTwoForRevision;
                }
                break;
        }
    }

    private void setResults(boolean b) {
        if (b){
            if (SystemClock.elapsedRealtime() - binding.countTimer.getBase() < -180000 || SystemClock.elapsedRealtime() - binding.countTimer.getBase() > 1){
                resultCounter = resultCounter + 20;
            }else if(SystemClock.elapsedRealtime() - binding.countTimer.getBase() < -120000){
                resultCounter = resultCounter + 30;
            }else if (SystemClock.elapsedRealtime() - binding.countTimer.getBase() < -60000){
                resultCounter = resultCounter + 40;
            }else{
                resultCounter = resultCounter + 50;
            }
        } else{
            resultCounter = resultCounter - 10;
        }
        String score = getResources().getString(R.string.score) + " " + resultCounter;
        binding.scoreText.setText(score);
    }

    public void finishCount(){
        if (resultCounter>0){
            countResultsToFirestoreSettersOperator.resultsToFirestore(resultCounter,amountOfTask);
        }
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
        String score = getResources().getString(R.string.score) + " 0";
        binding.scoreText.setText(score);
        binding.countTimer.stop();
        binding.countTimer.setBase(SystemClock.elapsedRealtime());
    }


    private void userFinishDialog(int scoreForDialog, int amountForDialog) {

        Dialog dialog = new Dialog(requireActivity());
        dialog.setContentView(R.layout.dialog_finish_count);

        TextView score = dialog.findViewById(R.id.score_in_dialog);
        TextView tasks = dialog.findViewById(R.id.amount_in_dialog);

        ImageView imageView = dialog.findViewById(R.id.close_finish_count_dialog_button);
        imageView.setOnClickListener(view -> dialog.dismiss());

        if (scoreForDialog < 0){
            scoreForDialog = 0;
        }

        String scoreForText = getResources().getString(R.string.score) + " " + scoreForDialog;
        score.setText(scoreForText);

        String tasksForText = getResources().getString(R.string.tasks) + " " + amountForDialog;
        tasks.setText(tasksForText);

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_count;
    }
}
