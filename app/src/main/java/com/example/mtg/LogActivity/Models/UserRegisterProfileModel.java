package com.example.mtg.LogActivity.Models;

public class UserRegisterProfileModel {

    String name = null;
    String surname = null;
    String nickname = null;
    String email = null;
    String country = null;

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
