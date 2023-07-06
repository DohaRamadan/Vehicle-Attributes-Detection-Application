package com.example.figma;

import com.example.figma.entities.Vehicle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class vehicleItemController {

    Stage stage;
    Scene scene;
    Parent root;
    Vehicle vehicle;
    EventHandler<MouseEvent> eventHandler =
            new EventHandler<>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("vehicleInformation.fxml"));
                    try {
                        root = loader.load();
                        vehicleInformationController vehicleInformationController = loader.getController();
                        vehicleInformationController.setData(vehicle);
                        scene = new Scene(root);
                        stage = new Stage();
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            };
    @FXML
    private Label vehicleID;
    @FXML
    private ImageView vehicleImg;
    @FXML
    private Label vehicleType;
    @FXML
    private AnchorPane vehiclePane;

    public void setData(Vehicle vehicle) {
        this.vehicle = vehicle;
        vehicleID.setText("#" + vehicle.getID());
        vehicleType.setText(vehicle.getType());
        vehicleImg.setImage(new Image(getClass().getResource(vehicle.getImageSrc()).toExternalForm()));
        vehiclePane.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
    }
}
