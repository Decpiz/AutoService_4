package com.example.autoservice_4.Model;

public class Users {
    private String name, secName, patronomic, number, email, password;

    public Users()
    {

    }

    public Users(String name, String secName, String patronomic, String number, String email, String password) {
        this.name = name;
        this.secName = secName;
        this.patronomic = patronomic;
        this.number = number;
        this.email = email;
        this.password = password;
    }
}
