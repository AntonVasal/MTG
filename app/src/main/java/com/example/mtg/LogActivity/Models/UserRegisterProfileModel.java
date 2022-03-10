package com.example.mtg.LogActivity.Models;

public class UserRegisterProfileModel {



    private String name = null;
    private String surname = null;
    private String nickname = null;
    private String email = null;
    private String country = null;
    private String imageUrl = null;



    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getCountry() {
        return country;
    }

    public UserRegisterProfileModel() {

    }

    public UserRegisterProfileModel(String name, String surname, String nickname, String email, String country, String imageUrl) {
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        this.email = email;
        this.country = country;
        this.imageUrl = imageUrl;
    }
}
