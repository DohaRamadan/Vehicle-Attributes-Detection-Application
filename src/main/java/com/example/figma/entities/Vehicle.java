package com.example.figma.entities;

public class Vehicle {
    private String type;
    private String model;
    private String make;
    private String color;
    private double speed;
    private String imageSrc;
    private String licencePlateString;
    private String licencePlateImage;
    private Integer ID;
    private Integer videoID;

    public Integer getVideoID() {
        return videoID;
    }

    public void setVideoID(Integer videoID) {
        this.videoID = videoID;
    }

    public Vehicle(){

    }

    public Vehicle(String type, Integer ID, String model, String make, String color, double speed, String imageSrc, String licencePlateString, String licencePlateImage) {
        this.model = model;
        this.type = type;
        this.ID = ID;
        this.make = make;
        this.color = color;
        this.speed = speed;
        this.imageSrc = imageSrc;
        this.licencePlateString = licencePlateString;
        this.licencePlateImage = licencePlateImage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getLicencePlateString() {
        return licencePlateString;
    }

    public void setLicencePlateString(String licencePlateString) {
        this.licencePlateString = licencePlateString;
    }

    public String getLicencePlateImage() {
        return licencePlateImage;
    }

    public void setLicencePlateImage(String licencePlateImage) {
        this.licencePlateImage = licencePlateImage;
    }
}
