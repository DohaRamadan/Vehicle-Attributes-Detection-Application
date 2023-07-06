package com.example.figma;

import com.example.figma.entities.Vehicle;
import com.example.figma.entities.Video;

import java.util.List;

public interface  IPersistence {
    public void addVehicle(Vehicle vehicle, Integer videoID);
    public void addVideo(Video video);

    public List<Video> getVideos();
    public List<Vehicle> getVehicles(Integer videoID);

}
