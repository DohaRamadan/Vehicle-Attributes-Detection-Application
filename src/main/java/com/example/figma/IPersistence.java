package com.example.figma;

import com.example.figma.entities.Vehicle;
import com.example.figma.entities.Video;

import java.util.List;

public interface  IPersistence {
    void addVehicle(Vehicle vehicle, Integer videoID);
    void addVideo(Video video);

    List<Video> getVideos();
    List<Vehicle> getVehicles(Integer videoID);

}
