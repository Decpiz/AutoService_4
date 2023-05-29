package com.example.autoservice_4.Model;

public class Users {
    public String name, secName, patronomic, number, email;

    public Users()
    {

    }

    public Users(String name, String secName, String patronomic, String number, String email) {
        this.name = name;
        this.secName = secName;
        this.patronomic = patronomic;
        this.number = number;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getPatronomic() {
        return patronomic;
    }

    public void setPatronomic(String patronomic) {
        this.patronomic = patronomic;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
