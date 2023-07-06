package com.example.figma.entities;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Video {
    String name, date;
    int numberOfVehicles;
    int ID;
    ArrayList<Vehicle> detectedVehicles;

    public Video(String name, String date, int numberOfVehicles, ArrayList<Vehicle>  detectedVehicles) {
        this.name = name;
        this.date = date;
        this.numberOfVehicles = numberOfVehicles;
        this.detectedVehicles = detectedVehicles;
    }

    public Video(){

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
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

    public void setDetectedVehicles(ArrayList<Vehicle>  detectedVehicles) {
        this.detectedVehicles = detectedVehicles;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumberOfVehicles() {
        return numberOfVehicles;
    }

    public void setNumberOfVehicles(int numberOfVehicles) {
        this.numberOfVehicles = numberOfVehicles;
    }
}
