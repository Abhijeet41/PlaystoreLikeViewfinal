package com.example.abhijeetmule.playstorelikeview;

public class SingleItemModel {

    private String name, url, description,nameArray;

    public SingleItemModel() {

    }

    public SingleItemModel(String name) {
        this.name = name;

    }

    public String getNameArray() {
        return nameArray;
    }

    public void setNameArray(String nameArray) {
        this.nameArray = nameArray;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
