package com.example.mtg.LogActivity.Models;

public class UserRegisterProfileModel {

    private String name = null;
    private String surname = null;
    private String nickname = null;
    private String email = null;
    private String country = null;

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

    public UserRegisterProfileModel(String name, String surname, String nickname, String email, String country) {
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        this.email = email;
        this.country = country;
    }
}
