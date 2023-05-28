package com.example.autoservice_4.Model;

public class Appointment {
    String uslugaName, nameUser, secNameUser, patronomicUser, numberUser;

    public Appointment()
    {

    }

    public Appointment(String uslugaName, String nameUser, String secNameUser, String patronomicUser, String numberUser) {
        this.uslugaName = uslugaName;
        this.nameUser = nameUser;
        this.secNameUser = secNameUser;
        this.patronomicUser = patronomicUser;
        this.numberUser = numberUser;
    }

    public String getUslugaName() {
        return uslugaName;
    }

    public void setUslugaName(String uslugaName) {
        this.uslugaName = uslugaName;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getSecNameUser() {
        return secNameUser;
    }

    public void setSecNameUser(String secNameUser) {
        this.secNameUser = secNameUser;
    }

    public String getPatronomicUser() {
        return patronomicUser;
    }

    public void setPatronomicUser(String patronomicUser) {
        this.patronomicUser = patronomicUser;
    }

    public String getNumberUser() {
        return numberUser;
    }

    public void setNumberUser(String numberUser) {
        this.numberUser = numberUser;
    }
}
