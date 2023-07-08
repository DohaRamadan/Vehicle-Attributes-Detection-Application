package com.example.figma;

import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;

import java.util.Objects;

public class submitFormValidator {


    private static void showAlert(Window owner, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Form Error!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public static boolean validateVideoPath(String videoPath, AnchorPane scenePane){
        if(videoPath.isEmpty()) {
            showAlert(scenePane.getScene().getWindow(), "Please enter your video path");
            return false;
        }

        String[] splitVideoPath = videoPath.split("\\.");
        if(!Objects.equals(splitVideoPath[splitVideoPath.length - 1], "mp4")){
            showAlert(scenePane.getScene().getWindow(), "Please enter an .mp4 video");
            return false;
        }
        return true;
    }

    public static boolean validateDistance(String distance, AnchorPane scenePane){
        if(distance.isEmpty()) {
            showAlert(scenePane.getScene().getWindow(), "Please enter the distance between start line and end line");
            return false;
        }

        if(Float.parseFloat(distance) <= 0){
            showAlert(scenePane.getScene().getWindow(), "Please enter a valid distance [>= 1]");
            return false;
        }
        return true;

    }
}
