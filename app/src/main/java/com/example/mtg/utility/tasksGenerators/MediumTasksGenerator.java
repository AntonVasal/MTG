package com.example.mtg.utility.tasksGenerators;

import com.example.mtg.databinding.FragmentCountBinding;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Random;

public class MediumTasksGenerator {

    private final FragmentCountBinding binding;

    public MediumTasksGenerator(FragmentCountBinding binding) {
        this.binding = binding;
    }

    public void generateIntegerDivTask() {
        Random random = new Random();
        int a;
        int b;
        do {
            a = random.nextInt(5000) - 2500;
            b = random.nextInt(48) + 2;
            if (b != 25 && b != 26) {
                b = b - 25;
            }
        } while (a % b != 0);
        String task;
        if (a >= 0 && b >= 0) {
            task = a + " : " + b + " = ";
        } else if (a >= 0) {
            task = a + " : " + "(" + b + ")" + " = ";
        } else if (b >= 0) {
            task = "(" + a + ")" + " : " + b + " = ";
        } else {
            task = "(" + a + ")" + " : " + "(" + b + ")" + " = ";
        }
        binding.taskText.setText(task);
    }

    public void generateIntegerMultiTask() {
        Random random = new Random();
        int a = random.nextInt(50) - 25;
        int b = random.nextInt(50) - 25;
        String task;
        if (a >= 0 && b >= 0) {
            task = a + " x " + b + " = ";
        } else if (a >= 0) {
            task = a + " x " + "(" + b + ")" + " = ";
        } else if (b >= 0) {
            task = "(" + a + ")" + " x " + b + " = ";
        } else {
            task = "(" + a + ")" + " x " + "(" + b + ")" + " = ";
        }
        binding.taskText.setText(task);
    }

    public void generateIntegerSubTask() {
        Random random = new Random();
        int a = random.nextInt(5000) - 2500;
        int b = random.nextInt(5000) - 2500;
        String task;
        if (a >= 0 && b >= 0) {
            task = a + " - " + b + " = ";
        } else if (a >= 0) {
            task = a + " - " + "(" + b + ")" + " = ";
        } else if (b >= 0) {
            task = "(" + a + ")" + " - " + b + " = ";
        } else {
            task = "(" + a + ")" + " - " + "(" + b + ")" + " = ";
        }
        binding.taskText.setText(task);
    }

    public void generateIntegerAddTask() {
        Random random = new Random();
        int a = random.nextInt(5000) - 2500;
        int b = random.nextInt(5000) - 2500;
        String task;
        if (a >= 0 && b >= 0) {
            task = a + " + " + b + " = ";
        } else if (a >= 0) {
            task = a + " + " + "(" + b + ")" + " = ";
        } else if (b >= 0) {
            task = "(" + a + ")" + " + " + b + " = ";
        } else {
            task = "(" + a + ")" + " + " + "(" + b + ")" + " = ";
        }
        binding.taskText.setText(task);
    }

    public void generateNaturalDivTask() {
        Random random = new Random();
        int a;
        int b;
        do {
            a = random.nextInt(2500)+1;
            b = random.nextInt(23) + 2;
        } while (a % b != 0);
        String task = a + " : " + b + " = ";
        binding.taskText.setText(task);
    }

    public void generateNaturalSubTask() {
        Random random = new Random();
        int a = random.nextInt(2500)+1;
        int b = random.nextInt(2500)+1;
        String task;
        if (a > b) {
            task = a + " - " + b + " = ";
        } else {
            task = b + " - " + a + " = ";
        }
        binding.taskText.setText(task);
    }

    public void generateNaturalMultiTask() {
        Random random = new Random();
        int a = random.nextInt(25)+1;
        int b = random.nextInt(25)+1;
        String task = a + " x " + b + " = ";
        binding.taskText.setText(task);
    }

    public void generateNaturalAddTask() {
        Random random = new Random();
        int a = random.nextInt(2500)+1;
        int b = random.nextInt(2500)+1;
        String task = a + " + " + b + " = ";
        binding.taskText.setText(task);
    }

    private void generateDecimalDivTask() {
        double a;
        double b;
        double random1;
        double random2;
        double c;
        String doubleForTask;
        String[] doubles;
        do {
            random1 = new Random().nextDouble();
            a = 1.0 + (random1 * (250.0 - 0.0));
            a = Double.parseDouble(String.format(Locale.US, "%.1f", a));
            random2 = new Random().nextDouble();
            b = 1.0 + (random2 * (25.0 - 0.0));
            b = Double.parseDouble(String.format(Locale.US, "%.1f", b));
            c = a / b;
            doubleForTask = String.valueOf(c);
            doubles = doubleForTask.split("\\.");
        } while (doubles.length ==1 || doubles[1].length() != 1);
        String task = BigDecimal.valueOf(a).stripTrailingZeros().toPlainString() + " : " +BigDecimal.valueOf(b).stripTrailingZeros().toPlainString()+ " = ";
        binding.taskText.setText(task);
    }

    private void generateDecimalSubTask() {
        double random1 = new Random().nextDouble();
        double a = 0.0 + (random1 * (250.0 - 0.0));
        a = Double.parseDouble(String.format(Locale.US, "%.1f", a));
        double random2 = new Random().nextDouble();
        double b = 0.0 + (random2 * (250.0 - 0.0));
        b = Double.parseDouble(String.format(Locale.US, "%.1f", b));
        String task;
        String s = BigDecimal.valueOf(a).stripTrailingZeros().toPlainString();
        String s1 = BigDecimal.valueOf(b).stripTrailingZeros().toPlainString();
        if (a > b) {
            task = s + " - " + s1 + " = ";
        } else {
            task = s1 + " - " + s + " = ";
        }
        binding.taskText.setText(task);
    }

    private void generateDecimalMultiTask() {
        double random1 = new Random().nextDouble();
        double a = 0.0 + (random1 * (3.0 - 0.0));
        a = Double.parseDouble(String.format(Locale.US, "%.1f", a));
        double random2 = new Random().nextDouble();
        double b = 0.0 + (random2 * (2.0 - 0.0));
        b = Double.parseDouble(String.format(Locale.US, "%.1f", b));
        String task = BigDecimal.valueOf(a).stripTrailingZeros().toPlainString() + " * " + BigDecimal.valueOf(b).stripTrailingZeros().toPlainString() + " = ";
        binding.taskText.setText(task);
    }

    private void generateDecimalAddTask() {
        double random1 = new Random().nextDouble();
        double a = 0.0 + (random1 * (250.0 - 0.0));
        a = Double.parseDouble(String.format(Locale.US, "%.1f", a));
        double random2 = new Random().nextDouble();
        double b = 0.0 + (random2 * (250.0 - 0.0));
        b = Double.parseDouble(String.format(Locale.US, "%.1f", b));
        String task = BigDecimal.valueOf(a).stripTrailingZeros().toPlainString() + " + " + BigDecimal.valueOf(b).stripTrailingZeros().toPlainString() + " = ";
        binding.taskText.setText(task);
    }

    public void generateTask(int taskType, int typeNumber) {
        switch (typeNumber) {
            case 1:
                switch (taskType) {
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
            case 2:
                switch (taskType) {
                    case 1:
                        generateIntegerAddTask();
                        break;
                    case 2:
                        generateIntegerMultiTask();
                        break;
                    case 3:
                        generateIntegerSubTask();
                        break;
                    case 4:
                        generateIntegerDivTask();
                        break;
                }
                break;
            case 3:
                switch (taskType) {
                    case 1:
                        generateDecimalAddTask();
                        break;
                    case 2:
                        generateDecimalMultiTask();
                        break;
                    case 3:
                        generateDecimalSubTask();
                        break;
                    case 4:
                        generateDecimalDivTask();
                        break;
                }
                break;
        }
    }
}
