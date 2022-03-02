package com.example.mtg.MainActivity.Count;

import com.example.mtg.databinding.FragmentCountBinding;

import java.util.Random;

public class TaskGenerator {
    private final FragmentCountBinding binding;

    public TaskGenerator(FragmentCountBinding binding) {
        this.binding = binding;
    }





    public void generateIntegerDivTask() {

    }


    public void generateIntegerMultiTask() {
        Random random = new Random();
        int a = random.nextInt(20000) - 10000;
        int b = random.nextInt(20000) - 10000;
        String task = a + " x " + b + " = ";
        binding.taskText.setText(task);
    }

    public void generateIntegerSubTask() {
        Random random = new Random();
        int a = random.nextInt(20000) - 10000;
        int b = random.nextInt(20000) - 10000;
        String task = a + " - " + b + " = ";
        binding.taskText.setText(task);
    }

    public void generateIntegerAddTask() {
        Random random = new Random();
        int a = random.nextInt(20000) - 10000;
        int b = random.nextInt(20000) - 10000;
        String task = a + " + " + b + " = ";
        binding.taskText.setText(task);
    }







    public void generateNaturalDivTask() {

    }

    public void generateNaturalSubTask() {
        Random random = new Random();
        int a = random.nextInt(10000);
        int b = random.nextInt(10000);
        String task;
        if (a>b) {
            task = a + " - " + b + " = ";
        }else{
            task = b + " - " + a + " = ";
        }
        binding.taskText.setText(task);

    }

    public void generateNaturalMultiTask() {
        Random random = new Random();
        int a = random.nextInt(100);
        int b = random.nextInt(100);
        String task = a + " x " + b + " = ";
        binding.taskText.setText(task);
    }

    public void generateNaturalAddTask() {
        Random random = new Random();
        int a = random.nextInt(10000);
        int b = random.nextInt(10000);
        String task = a + " + " + b + " = ";
        binding.taskText.setText(task);
    }
}
