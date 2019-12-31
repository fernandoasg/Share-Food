package com.example.sharefood.entity;

public class Options {

    private String mainText;
    private String secondaryText;
    private String image;
    private String whereToOpen;

    public Options(String mainText, String secondaryText, String image, String whereToOpen) {
        this.mainText = mainText;
        this.secondaryText = secondaryText;
        this.image = image;
        this.whereToOpen = whereToOpen;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public String getSecondaryText() {
        return secondaryText;
    }

    public void setSecondaryText(String secondaryText) {
        this.secondaryText = secondaryText;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWhereToOpen() {
        return whereToOpen;
    }

    public void setWhereToOpen(String whereToOpen) {
        this.whereToOpen = whereToOpen;
    }
}
