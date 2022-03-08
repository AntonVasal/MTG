package com.example.mtg.MainActivity.Count.CountModels;

public class DivResultsModel {
    private int divNaturalScore;
    private int divNaturalTasksAmount;
    private int divIntegerScore;
    private int divIntegerTasksAmount;
    private int divDecimalScore;
    private int divDecimalTasksAmount;
    private String id;

    public DivResultsModel(int divNaturalScore, int divNaturalTasksAmount, int divIntegerScore, int divIntegerTasksAmount, int divDecimalScore, int divDecimalTasksAmount, String id) {
        this.divNaturalScore = divNaturalScore;
        this.divNaturalTasksAmount = divNaturalTasksAmount;
        this.divIntegerScore = divIntegerScore;
        this.divIntegerTasksAmount = divIntegerTasksAmount;
        this.divDecimalScore = divDecimalScore;
        this.divDecimalTasksAmount = divDecimalTasksAmount;
        this.id = id;
    }

    public DivResultsModel() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDivNaturalScore() {
        return divNaturalScore;
    }

    public void setDivNaturalScore(int divNaturalScore) {
        this.divNaturalScore = divNaturalScore;
    }

    public int getDivNaturalTasksAmount() {
        return divNaturalTasksAmount;
    }

    public void setDivNaturalTasksAmount(int divNaturalTasksAmount) {
        this.divNaturalTasksAmount = divNaturalTasksAmount;
    }

    public int getDivIntegerScore() {
        return divIntegerScore;
    }

    public void setDivIntegerScore(int divIntegerScore) {
        this.divIntegerScore = divIntegerScore;
    }

    public int getDivIntegerTasksAmount() {
        return divIntegerTasksAmount;
    }

    public void setDivIntegerTasksAmount(int divIntegerTasksAmount) {
        this.divIntegerTasksAmount = divIntegerTasksAmount;
    }

    public int getDivDecimalScore() {
        return divDecimalScore;
    }

    public void setDivDecimalScore(int divDecimalScore) {
        this.divDecimalScore = divDecimalScore;
    }

    public int getDivDecimalTasksAmount() {
        return divDecimalTasksAmount;
    }

    public void setDivDecimalTasksAmount(int divDecimalTasksAmount) {
        this.divDecimalTasksAmount = divDecimalTasksAmount;
    }
}
