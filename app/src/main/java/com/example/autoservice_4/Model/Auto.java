package com.example.autoservice_4.Model;

public class Auto {
    public String marka, modelAuto, year, email;

    public Auto() {
    }

    public Auto(String marka, String modelAuto, String year, String email) {
        this.marka = marka;
        this.modelAuto = modelAuto;
        this.year = year;
        this.email = email;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModelAuto() {
        return modelAuto;
    }

    public void setModelAuto(String modelAuto) {
        this.modelAuto = modelAuto;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


