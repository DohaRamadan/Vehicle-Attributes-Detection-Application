package com.example.figma;

import com.example.figma.entities.Vehicle;
import com.example.figma.entities.Video;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLImplementation implements IPersistence{
    private static SQLImplementation instance;
    private static Connection conn;

    public SQLImplementation() {

    }

    public static void createVideoTable(){
        String sqlStatement = "CREATE TABLE IF NOT EXISTS \"Video\" (\n" +
                "\t\"videoID\"\tINTEGER NOT NULL,\n" +
                "\t\"videoName\"\tTEXT NOT NULL,\n" +
                "\t\"numberOfVehicles\"\tINTEGER NOT NULL,\n" +
                "\t\"date\"\tTEXT NOT NULL,\n" +
                "\tPRIMARY KEY(\"videoID\" AUTOINCREMENT)\n" +
                ");";
        try {
            conn = SQLDatabaseConnection.getConnectionToDataBase();
            Statement preStatement = conn.createStatement();
            preStatement.executeQuery(sqlStatement);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createVehicleTable(){
        String sqlStatement = "CREATE TABLE IF NOT EXISTS \"Vehicle\" (\n" +
                "\t\"vehicleID\"\tINTEGER NOT NULL,\n" +
                "\t\"licencePlateString\"\tTEXT NOT NULL,\n" +
                "\t\"make\"\tTEXT,\n" +
                "\t\"model\"\tTEXT,\n" +
                "\t\"color\"\tTEXT NOT NULL,\n" +
                "\t\"speed\"\tNUMERIC NOT NULL,\n" +
                "\t\"vehicleType\"\tTEXT NOT NULL,\n" +
                "\t\"vehicleImagePath\"\tTEXT NOT NULL,\n" +
                "\t\"videoID\"\tINTEGER NOT NULL,\n" +
                "\tPRIMARY KEY(\"vehicleID\" AUTOINCREMENT),\n" +
                "\tFOREIGN KEY(\"videoID\") REFERENCES \"Video\"\n" +
                ");";
        try {
            conn = SQLDatabaseConnection.getConnectionToDataBase();
            Statement preStatement = conn.createStatement();
            preStatement.executeQuery(sqlStatement);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static SQLImplementation getInstance() {
        if (instance == null) {
            instance = new SQLImplementation();
        }
        return instance;
    }


    @Override
    public void addVehicle(Vehicle vehicle, Integer videoID) {

        String sqlStatement = "INSERT INTO Vehicle(LicencePlateString, make, model, color, speed, vehicleImagePath, videoID, vehicleType) Values(?,?,?,?,?,?,?,?)";
        try {
            conn = SQLDatabaseConnection.getConnectionToDataBase();
            PreparedStatement preStatement = conn.prepareStatement(sqlStatement);
            preStatement.setString(1, vehicle.getLicencePlateString());
            preStatement.setString(2, vehicle.getMake());
            preStatement.setString(3, vehicle.getModel());
            preStatement.setString(4, vehicle.getColor());
            preStatement.setDouble(5, vehicle.getSpeed());
            preStatement.setString(6, vehicle.getImageSrc());
            preStatement.setInt(7, videoID);
            preStatement.setString(8, vehicle.getType());
            preStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void addVideo(Video video) {
        String sqlStatement = "INSERT INTO Video(videoName, numberOfVehicles, date) Values(?,?,?)";
        try {
            conn = SQLDatabaseConnection.getConnectionToDataBase();
            PreparedStatement preStatement = conn.prepareStatement(sqlStatement);
//            preStatement.setInt(1, video.getID());
            preStatement.setString(1, video.getName());
            preStatement.setInt(2, video.getNumberOfVehicles());
            preStatement.setString(3, video.getDate());
            int affectedRows =  preStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Insert failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    video.setID((int) generatedKeys.getLong(1));
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            for (Vehicle vehicle : video.getDetectedVehicles()){
                addVehicle(vehicle, video.getID());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<Video> getVideos() {
        String sqlStatement = "SELECT * FROM Video";
        List<Video> videos = new ArrayList<>();
        try {
            conn = SQLDatabaseConnection.getConnectionToDataBase();
            Statement preStatement = conn.createStatement();
            ResultSet result = preStatement.executeQuery(sqlStatement);
            while (result.next()) {
                Video video = new Video();
                video.setID(result.getInt("videoID"));
                video.setName(result.getString("videoName"));
                video.setNumberOfVehicles(result.getInt("numberOfVehicles"));
                video.setDetectedVehicles((ArrayList<Vehicle>) getVehicles(video.getID()));
                video.setDate(result.getString("date"));
                videos.add(video);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return videos;
    }

    @Override
    public List<Vehicle> getVehicles(Integer videoID) {
        List<Vehicle> vehicles = new ArrayList<>();
        String sqlStatement = "SELECT * FROM Vehicle WHERE videoID=" + videoID;
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
                vehicle.setID(result.getInt("vehicleID"));
                vehicles.add(vehicle);
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
        return vehicles;
    }
}
