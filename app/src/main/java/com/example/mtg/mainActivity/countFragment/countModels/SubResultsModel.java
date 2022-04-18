package com.example.mtg.mainActivity.countFragment.countModels;

public class SubResultsModel {
    private int subNaturalScore;
    private int subNaturalTasksAmount;
    private int subIntegerScore;
    private int subIntegerTasksAmount;
    private int subDecimalScore;
    private int subDecimalTasksAmount;
    private String nickname;
    private String imageUrl;
    private String id;

    public SubResultsModel() {
    }

    public SubResultsModel(int subNaturalScore, int subNaturalTasksAmount, int subIntegerScore, int subIntegerTasksAmount, int subDecimalScore, int subDecimalTasksAmount, String nickname, String imageUrl, String id) {
        this.subNaturalScore = subNaturalScore;
        this.subNaturalTasksAmount = subNaturalTasksAmount;
        this.subIntegerScore = subIntegerScore;
        this.subIntegerTasksAmount = subIntegerTasksAmount;
        this.subDecimalScore = subDecimalScore;
        this.subDecimalTasksAmount = subDecimalTasksAmount;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.id = id;
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
