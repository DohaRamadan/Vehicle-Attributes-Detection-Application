package com.example.figma;

import com.example.figma.entities.Vehicle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class vehicleInformationController implements Initializable {
    EventHandler<MouseEvent> exitEventHandler =
            new EventHandler<>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                    ExitController.exitAlarm(scenePane);
                }
            };

    EventHandler<MouseEvent> submitEventHandler =
            mouseEvent -> Loader.loadScene("submitVideo.fxml", mouseEvent);


    EventHandler<MouseEvent> historyEventHandler =
            mouseEvent -> Loader.loadScene("viewHistory.fxml", mouseEvent);

    EventHandler<MouseEvent> mainEventHandler =
            mouseEvent -> Loader.loadScene("mainWindow.fxml", mouseEvent);
    @FXML
    private AnchorPane scenePane;
    @FXML
    private Label color;
    @FXML
    private Label make;
    @FXML
    private Label model;
    @FXML
    private Label number;
    @FXML
    private Label speed;
    @FXML
    private Label vehicleType;
    @FXML
    private ImageView vehicleImage;
    @FXML
    private HBox mainWindowBox;
    @FXML
    private HBox submitVideoBox;
    @FXML
    private HBox viewHistoryBox;
    @FXML
    private HBox exitBox;

    public void setData(Vehicle vehicle) {
        vehicleImage.setImage(new Image(Objects.requireNonNull(getClass().getResource(vehicle.getImageSrc())).toString(), true));
        color.setText(vehicle.getColor());
        make.setText(vehicle.getMake());
        model.setText(vehicle.getMake());
        number.setText(vehicle.getLicencePlateString());
        speed.setText(String.valueOf(vehicle.getSpeed()));
        vehicleType.setText(vehicle.getType());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainWindowBox.addEventHandler(MouseEvent.MOUSE_PRESSED, mainEventHandler);
        submitVideoBox.addEventHandler(MouseEvent.MOUSE_PRESSED, submitEventHandler);
        viewHistoryBox.addEventHandler(MouseEvent.MOUSE_PRESSED, historyEventHandler);
        exitBox.addEventHandler(MouseEvent.MOUSE_PRESSED, exitEventHandler);
    }
}
