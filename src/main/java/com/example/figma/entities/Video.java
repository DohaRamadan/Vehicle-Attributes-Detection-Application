package com.example.figma.entities;

import java.util.List;

public class Video {
    String name, date, numberOfVehicles;
    Integer ID;
    List<Vehicle> detectedVehicles;

    public Video(Integer ID, String name, String date, String numberOfVehicles, List<Vehicle> detectedVehicles) {
        this.name = name;
        this.date = date;
        this.numberOfVehicles = numberOfVehicles;
        this.detectedVehicles = detectedVehicles;
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Vehicle> getDetectedVehicles() {
        return detectedVehicles;
    }

    public void setDetectedVehicles(List<Vehicle> detectedVehicles) {
        this.detectedVehicles = detectedVehicles;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNumberOfVehicles() {
        return numberOfVehicles;
    }

    public void setNumberOfVehicles(String numberOfVehicles) {
        this.numberOfVehicles = numberOfVehicles;
    }
}
