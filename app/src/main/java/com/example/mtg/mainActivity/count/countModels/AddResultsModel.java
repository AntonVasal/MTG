package com.example.mtg.mainActivity.count.countModels;

public class AddResultsModel {
    private int addNaturalScore;
    private int addNaturalTasksAmount;
    private int addIntegerScore;
    private int addIntegerTasksAmount;
    private int addDecimalScore;
    private int addDecimalTasksAmount;
    private String id;

    public AddResultsModel(int addNaturalScore, int addNaturalTasksAmount, int addIntegerScore, int addIntegerTasksAmount, int addDecimalScore, int addDecimalTasksAmount, String id) {
        this.addNaturalScore = addNaturalScore;
        this.addNaturalTasksAmount = addNaturalTasksAmount;
        this.addIntegerScore = addIntegerScore;
        this.addIntegerTasksAmount = addIntegerTasksAmount;
        this.addDecimalScore = addDecimalScore;
        this.addDecimalTasksAmount = addDecimalTasksAmount;
        this.id = id;
    }

    public AddResultsModel() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
