package com.example.mtg.MainActivity.MainFragments.Results.Models;

public class UserResultsModel {
    private String name = null;
    private String image = null;
    private String score = null;

    public UserResultsModel(String name, String image, String score) {
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
