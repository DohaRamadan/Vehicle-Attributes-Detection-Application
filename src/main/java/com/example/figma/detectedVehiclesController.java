package com.example.figma;

import com.example.figma.entities.Vehicle;
import com.example.figma.entities.Video;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class detectedVehiclesController implements Initializable {

    private Video video;

    @FXML
    private GridPane grid;
    @FXML
    private ScrollPane scroll;
    @FXML
    private AnchorPane scenePane;

    @FXML
    private HBox mainWindowBox;
    @FXML
    private HBox submitVideoBox;
    @FXML
    private HBox viewHistoryBox;
    @FXML
    private HBox exitBox;
    @FXML
    private Label title;

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainWindowBox.addEventHandler(MouseEvent.MOUSE_PRESSED, mainEventHandler);
        submitVideoBox.addEventHandler(MouseEvent.MOUSE_PRESSED, submitEventHandler);
        viewHistoryBox.addEventHandler(MouseEvent.MOUSE_PRESSED, historyEventHandler);
        exitBox.addEventHandler(MouseEvent.MOUSE_PRESSED, exitEventHandler);
        title.setText("Detected Vehicles In Video #" + video.getID());
        int column = 0;
        int row = 1;
        for (Vehicle vehicle : video.getDetectedVehicles()) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("vehicleItem.fxml"));
            try {
                AnchorPane vehicleBox = fxmlLoader.load();
                vehicleItemController vehicleItemController = fxmlLoader.getController();
                vehicleItemController.setData(vehicle);
                if (column == 4) {
                    column = 0;
                    ++row;
                }
                grid.add(vehicleBox, column++, row);
                GridPane.setMargin(vehicleBox, new Insets(10));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
