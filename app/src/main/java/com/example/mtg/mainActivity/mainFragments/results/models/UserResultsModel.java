package com.example.mtg.mainActivity.mainFragments.results.models;

public class UserResultsModel {
    private String name;
    private String image;
    private int score;

    public UserResultsModel(String name, String image, int score) {
        this.name = name;
        this.image = image;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
