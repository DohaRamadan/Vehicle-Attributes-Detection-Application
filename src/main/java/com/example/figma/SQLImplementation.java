package com.example.figma;

import com.example.figma.entities.Vehicle;
import com.example.figma.entities.Video;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLImplementation implements IPersistence{
    private static SQLImplementation instance;
    private Connection conn;

    public SQLImplementation() {

    }

    public static SQLImplementation getInstance() {
        if (instance == null) {
            instance = new SQLImplementation();
        }
        return instance;
    }


    @Override
    public void addVehicle(Vehicle vehicle, Integer videoID) {

        String sqlStatement = "INSERT INTO vehicle(LicencePlateString, make, model, color, speed, trackerID, vehicleImage, vehicleImagePath, videoID, vehicleType) Values(?,?,?,?,?,?,?,?,?,?)";
        try {
            conn = SQLDatabaseConnection.getConnectionToDataBase();
            PreparedStatement preStatement = conn.prepareStatement(sqlStatement);
            preStatement.setString(1, vehicle.getLicencePlateString());
            preStatement.setString(2, vehicle.getMake());
            preStatement.setString(3, vehicle.getModel());
            preStatement.setString(4, vehicle.getColor());
            preStatement.setDouble(5, vehicle.getSpeed());
            preStatement.setInt(6, vehicle.getID());
            InputStream in = new FileInputStream(vehicle.getImageSrc());
            preStatement.setBlob(7, in);
            preStatement.setString(8, vehicle.getImageSrc());
            preStatement.setInt(9, videoID);
            preStatement.setString(10, vehicle.getType());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void addVideo(Video video) {
        String sqlStatement = "INSERT INTO video(videoName, numberOfVehicles, date) Values(?,?,?,?)";
        try {
            conn = SQLDatabaseConnection.getConnectionToDataBase();
            PreparedStatement preStatement = conn.prepareStatement(sqlStatement);
//            preStatement.setInt(1, video.getID());
            preStatement.setString(1, video.getName());
            preStatement.setInt(2, video.getNumberOfVehicles());
            preStatement.setString(3, video.getDate());
            for (Vehicle vehicle : video.getDetectedVehicles()){
                addVehicle(vehicle, video.getID());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<Video> getVideos() {
        String sqlStatement = "SELECT * FROM video";
        List<Video> videos = new ArrayList<>();
        try {
            conn = SQLDatabaseConnection.getConnectionToDataBase();
            Statement preStatement = conn.createStatement();
            ResultSet result = preStatement.executeQuery(sqlStatement);
            while (result.next()) {
                Video video = new Video();
                video.setID(result.getInt("videoID"));
                video.setName(result.getString("VideoName"));
                video.setNumberOfVehicles(result.getInt("numberOfVehicles"));
                video.setDetectedVehicles((ArrayList<Vehicle>) getVehicles(video.getID()));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return videos;
    }

    @Override
    public List<Vehicle> getVehicles(Integer videoID) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sqlStatement = "SELECT * FROM vehicle WHERE videoID=" + videoID;
        try {
            conn = SQLDatabaseConnection.getConnectionToDataBase();
            Statement preStatement = conn.createStatement();
            ResultSet result = preStatement.executeQuery(sqlStatement);
            while (result.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setVideoID(videoID);
                vehicle.setColor(result.getString("color"));
                vehicle.setMake(result.getString("make"));
                vehicle.setModel(result.getString("model"));
                vehicle.setLicencePlateString(result.getString("LicencePlateString"));
                vehicle.setType(result.getString("vehicleType"));
                vehicle.setSpeed(result.getDouble("speed"));
                vehicle.setImageSrc(result.getString("vehicleImagePath"));
                vehicles.add(vehicle);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return vehicles;
    }
}
