package com.example.mtg.models.countModels;

public class MultiResultsModel {
    private int multiNaturalScore;
    private int multiNaturalTasksAmount;
    private int multiIntegerScore;
    private int multiIntegerTasksAmount;
    private int multiDecimalScore;
    private int multiDecimalTasksAmount;
    private String nickname;
    private String imageUrl;
    private String id;

    public MultiResultsModel() {
    }

    public MultiResultsModel(int multiNaturalScore, int multiNaturalTasksAmount, int multiIntegerScore, int multiIntegerTasksAmount, int multiDecimalScore, int multiDecimalTasksAmount, String nickname, String imageUrl, String id) {
        this.multiNaturalScore = multiNaturalScore;
        this.multiNaturalTasksAmount = multiNaturalTasksAmount;
        this.multiIntegerScore = multiIntegerScore;
        this.multiIntegerTasksAmount = multiIntegerTasksAmount;
        this.multiDecimalScore = multiDecimalScore;
        this.multiDecimalTasksAmount = multiDecimalTasksAmount;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.id = id;
    }

    public int getMultiNaturalScore() {
        return multiNaturalScore;
    }

    public void setMultiNaturalScore(int multiNaturalScore) {
        this.multiNaturalScore = multiNaturalScore;
    }

    public int getMultiNaturalTasksAmount() {
        return multiNaturalTasksAmount;
    }

    public void setMultiNaturalTasksAmount(int multiNaturalTasksAmount) {
        this.multiNaturalTasksAmount = multiNaturalTasksAmount;
    }

    public int getMultiIntegerScore() {
        return multiIntegerScore;
    }

    public void setMultiIntegerScore(int multiIntegerScore) {
        this.multiIntegerScore = multiIntegerScore;
    }

    public int getMultiIntegerTasksAmount() {
        return multiIntegerTasksAmount;
    }

    public void setMultiIntegerTasksAmount(int multiIntegerTasksAmount) {
        this.multiIntegerTasksAmount = multiIntegerTasksAmount;
    }

    public int getMultiDecimalScore() {
        return multiDecimalScore;
    }

    public void setMultiDecimalScore(int multiDecimalScore) {
        this.multiDecimalScore = multiDecimalScore;
    }

    public int getMultiDecimalTasksAmount() {
        return multiDecimalTasksAmount;
    }

    public void setMultiDecimalTasksAmount(int multiDecimalTasksAmount) {
        this.multiDecimalTasksAmount = multiDecimalTasksAmount;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
