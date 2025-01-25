package com.example.myapplication;

public class NameActivity {
    private String nameActivity;

    public NameActivity(String nameActivity) {
        this.nameActivity = nameActivity;
    }

    public String getNameActivity() {
        return nameActivity;
    }

    public void setNameActivity(String nameActivity) {
        this.nameActivity = nameActivity;
    }

    @Override
    public String toString() {
        return nameActivity;
    }
}
