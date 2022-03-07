package com.example.mtg.MainActivity.Count.CountModels;

public class SubResultsModel {
    private int subNaturalScore;
    private int subNaturalTasksAmount;
    private int subIntegerScore;
    private int subIntegerTasksAmount;
    private int subDecimalScore;
    private int subDecimalTasksAmount;

    public SubResultsModel() {
    }

    public SubResultsModel(int subNaturalScore, int subNaturalTasksAmount, int subIntegerScore, int subIntegerTasksAmount, int subDecimalScore, int subDecimalTasksAmount) {
        this.subNaturalScore = subNaturalScore;
        this.subNaturalTasksAmount = subNaturalTasksAmount;
        this.subIntegerScore = subIntegerScore;
        this.subIntegerTasksAmount = subIntegerTasksAmount;
        this.subDecimalScore = subDecimalScore;
        this.subDecimalTasksAmount = subDecimalTasksAmount;
    }

    public int getSubNaturalScore() {
        return subNaturalScore;
    }

    public void setSubNaturalScore(int subNaturalScore) {
        this.subNaturalScore = subNaturalScore;
    }

    public int getSubNaturalTasksAmount() {
        return subNaturalTasksAmount;
    }

    public void setSubNaturalTasksAmount(int subNaturalTasksAmount) {
        this.subNaturalTasksAmount = subNaturalTasksAmount;
    }

    public int getSubIntegerScore() {
        return subIntegerScore;
    }

    public void setSubIntegerScore(int subIntegerScore) {
        this.subIntegerScore = subIntegerScore;
    }

    public int getSubIntegerTasksAmount() {
        return subIntegerTasksAmount;
    }

    public void setSubIntegerTasksAmount(int subIntegerTasksAmount) {
        this.subIntegerTasksAmount = subIntegerTasksAmount;
    }

    public int getSubDecimalScore() {
        return subDecimalScore;
    }

    public void setSubDecimalScore(int subDecimalScore) {
        this.subDecimalScore = subDecimalScore;
    }

    public int getSubDecimalTasksAmount() {
        return subDecimalTasksAmount;
    }

    public void setSubDecimalTasksAmount(int subDecimalTasksAmount) {
        this.subDecimalTasksAmount = subDecimalTasksAmount;
    }
}
