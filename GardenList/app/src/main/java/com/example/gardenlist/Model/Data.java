package com.example.gardenlist.Model;

public class Data {

    String id;
    String date;
    String name;

    public Data(){}

    public Data(String id, String date, String name) {
        this.id = id;
        this.date = date;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
