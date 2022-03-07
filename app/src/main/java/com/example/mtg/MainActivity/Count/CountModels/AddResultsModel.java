package com.example.mtg.MainActivity.Count.CountModels;

public class AddResultsModel {
    private int addNaturalScore;
    private int addNaturalTasksAmount;
    private int addIntegerScore;
    private int addIntegerTasksAmount;
    private int addDecimalScore;
    private int addDecimalTasksAmount;

    public AddResultsModel() {
    }

    public AddResultsModel(int addNaturalScore, int addNaturalTasksAmount, int addIntegerScore, int addIntegerTasksAmount, int addDecimalScore, int addDecimalTasksAmount) {
        this.addNaturalScore = addNaturalScore;
        this.addNaturalTasksAmount = addNaturalTasksAmount;
        this.addIntegerScore = addIntegerScore;
        this.addIntegerTasksAmount = addIntegerTasksAmount;
        this.addDecimalScore = addDecimalScore;
        this.addDecimalTasksAmount = addDecimalTasksAmount;
    }

    public int getAddNaturalScore() {
        return addNaturalScore;
    }

    public void setAddNaturalScore(int addNaturalScore) {
        this.addNaturalScore = addNaturalScore;
    }

    public int getAddNaturalTasksAmount() {
        return addNaturalTasksAmount;
    }

    public void setAddNaturalTasksAmount(int addNaturalTasksAmount) {
        this.addNaturalTasksAmount = addNaturalTasksAmount;
    }

    public int getAddIntegerScore() {
        return addIntegerScore;
    }

    public void setAddIntegerScore(int addIntegerScore) {
        this.addIntegerScore = addIntegerScore;
    }

    public int getAddIntegerTasksAmount() {
        return addIntegerTasksAmount;
    }

    public void setAddIntegerTasksAmount(int addIntegerTasksAmount) {
        this.addIntegerTasksAmount = addIntegerTasksAmount;
    }

    public int getAddDecimalScore() {
        return addDecimalScore;
    }

    public void setAddDecimalScore(int addDecimalScore) {
        this.addDecimalScore = addDecimalScore;
    }

    public int getAddDecimalTasksAmount() {
        return addDecimalTasksAmount;
    }

    public void setAddDecimalTasksAmount(int addDecimalTasksAmount) {
        this.addDecimalTasksAmount = addDecimalTasksAmount;
    }
}
