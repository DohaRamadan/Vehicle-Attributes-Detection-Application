package com.example.figma;

import com.example.figma.entities.Vehicle;
import com.example.figma.entities.Video;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class viewHistoryController implements Initializable {
    java.util.List<Vehicle> vehicles1 = new ArrayList<>() {
        {
            add(new Vehicle("Car", 15, "Corolla", "Toyota", "BLACK", 100, "images/car2.jpg", "5UMH719", "cjsancja"));
            add(new Vehicle("Car", 15, "Corolla", "Toyota", "BLACK", 100, "images/car2.jpg", "5UMH719", "cjsancja"));
            add(new Vehicle("Car", 15, "Corolla", "Toyota", "BLACK", 100, "images/car2.jpg", "5UMH719", "cjsancja"));
            add(new Vehicle("Car", 15, "Corolla", "Toyota", "BLACK", 100, "images/car2.jpg", "5UMH719", "cjsancja"));
            add(new Vehicle("Car", 15, "Corolla", "Toyota", "BLACK", 100, "images/car2.jpg", "5UMH719", "cjsancja"));
            add(new Vehicle("Car", 15, "Corolla", "Toyota", "BLACK", 100, "images/car2.jpg", "5UMH719", "cjsancja"));
            add(new Vehicle("Car", 15, "Corolla", "Toyota", "BLACK", 100, "images/car2.jpg", "5UMH719", "cjsancja"));
            add(new Vehicle("Car", 15, "Corolla", "Toyota", "BLACK", 100, "images/car2.jpg", "5UMH719", "cjsancja"));
            add(new Vehicle("Car", 15, "Corolla", "Toyota", "BLACK", 100, "images/car2.jpg", "5UMH719", "cjsancja"));
            add(new Vehicle("Car", 15, "Corolla", "Toyota", "BLACK", 100, "images/car2.jpg", "5UMH719", "cjsancja"));

            add(new Vehicle("Car", 15, "Corolla", "Toyota", "BLACK", 100, "images/car2.jpg", "5UMH719", "cjsancja"));
            add(new Vehicle("Car", 15, "Corolla", "Toyota", "BLACK", 100, "images/car2.jpg", "5UMH719", "cjsancja"));
            add(new Vehicle("Car", 15, "Corolla", "Toyota", "BLACK", 100, "images/car2.jpg", "5UMH719", "cjsancja"));
            add(new Vehicle("Car", 15, "Corolla", "Toyota", "BLACK", 100, "images/car2.jpg", "5UMH719", "cjsancja"));
            add(new Vehicle("Car", 15, "Corolla", "Toyota", "BLACK", 100, "images/car2.jpg", "5UMH719", "cjsancja"));
            add(new Vehicle("Car", 15, "Corolla", "Toyota", "BLACK", 100, "images/car2.jpg", "5UMH719", "cjsancja"));
            add(new Vehicle("Car", 15, "Corolla", "Toyota", "BLACK", 100, "images/car2.jpg", "5UMH719", "cjsancja"));
            add(new Vehicle("Car", 15, "Corolla", "Toyota", "BLACK", 100, "images/car2.jpg", "5UMH719", "cjsancja"));
            add(new Vehicle("Car", 15, "Corolla", "Toyota", "BLACK", 100, "images/car2.jpg", "5UMH719", "cjsancja"));
            add(new Vehicle("Car", 15, "Corolla", "Toyota", "BLACK", 100, "images/car2.jpg", "5UMH719", "cjsancja"));
        }
    };
    java.util.List<Vehicle> vehicles2 = new ArrayList<>() {
        {
            add(new Vehicle("Car", 10, "BMW", "BMW", "RED", 60, "images/car1.jpg", "kdks", "cjsancja"));
            add(new Vehicle("Car", 10, "BMW", "BMW", "RED", 60, "images/car1.jpg", "kdks", "cjsancja"));
            add(new Vehicle("Car", 10, "BMW", "BMW", "RED", 60, "images/car1.jpg", "kdks", "cjsancja"));
            add(new Vehicle("Car", 10, "BMW", "BMW", "RED", 60, "images/car1.jpg", "kdks", "cjsancja"));
            add(new Vehicle("Car", 10, "BMW", "BMW", "RED", 60, "images/car1.jpg", "kdks", "cjsancja"));
            add(new Vehicle("Car", 10, "BMW", "BMW", "RED", 60, "images/car1.jpg", "kdks", "cjsancja"));
            add(new Vehicle("Car", 10, "BMW", "BMW", "RED", 60, "images/car1.jpg", "kdks", "cjsancja"));
            add(new Vehicle("Car", 10, "BMW", "BMW", "RED", 60, "images/car1.jpg", "kdks", "cjsancja"));
            add(new Vehicle("Car", 10, "BMW", "BMW", "RED", 60, "images/car1.jpg", "kdks", "cjsancja"));
            add(new Vehicle("Car", 10, "BMW", "BMW", "RED", 60, "images/car1.jpg", "kdks", "cjsancja"));

            add(new Vehicle("Car", 10, "BMW", "BMW", "RED", 60, "images/car1.jpg", "kdks", "cjsancja"));
            add(new Vehicle("Car", 10, "BMW", "BMW", "RED", 60, "images/car1.jpg", "kdks", "cjsancja"));
            add(new Vehicle("Car", 10, "BMW", "BMW", "RED", 60, "images/car1.jpg", "kdks", "cjsancja"));
            add(new Vehicle("Car", 10, "BMW", "BMW", "RED", 60, "images/car1.jpg", "kdks", "cjsancja"));
            add(new Vehicle("Car", 10, "BMW", "BMW", "RED", 60, "images/car1.jpg", "kdks", "cjsancja"));
            add(new Vehicle("Car", 10, "BMW", "BMW", "RED", 60, "images/car1.jpg", "kdks", "cjsancja"));
            add(new Vehicle("Car", 10, "BMW", "BMW", "RED", 60, "images/car1.jpg", "kdks", "cjsancja"));
            add(new Vehicle("Car", 10, "BMW", "BMW", "RED", 60, "images/car1.jpg", "kdks", "cjsancja"));
            add(new Vehicle("Car", 10, "BMW", "BMW", "RED", 60, "images/car1.jpg", "kdks", "cjsancja"));
            add(new Vehicle("Car", 10, "BMW", "BMW", "RED", 60, "images/car1.jpg", "kdks", "cjsancja"));

        }
    };
    // CALL API
    // RETURN LIST
    ObservableList<Video> List = FXCollections.observableArrayList(
            new Video(1, "video-1.mp4",  "13/2/2023", "22", vehicles1),
            new Video(2, "video-2.mp4", "15/2/2023", "150", vehicles2),
            new Video(3, "video-3.mp4",  "13/2/2023", "15", vehicles1),
            new Video(4, "video-4.mp4", "15/2/2023", "10", vehicles2),
            new Video(5, "video-5.mp4",  "13/2/2023", "50", vehicles1),
            new Video(6, "video-6.mp4", "15/2/2023", "66", vehicles2),
            new Video(7, "video-7.mp4", "13/2/2023", "73", vehicles1),
            new Video(8, "video-8.mp4",  "15/2/2023", "100", vehicles2),
            new Video(9, "video-9.mp4",  "13/2/2023", "5", vehicles1),
            new Video(10, "video-10.mp4",  "15/2/2023", "7", vehicles2),
            new Video(11, "video-11.mp4",  "13/2/2023", "73", vehicles1),
            new Video(12, "video-12.mp4",  "15/2/2023", "100", vehicles2),
            new Video(13, "video-13.mp4",  "13/2/2023", "5", vehicles1),
            new Video(14, "video-14.mp4",  "15/2/2023", "7", vehicles2)
    );
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
    private ScrollPane scrollPane;
    @FXML
    private VBox videosLayout;
    @FXML
    private HBox mainWindowBox;
    @FXML
    private HBox submitVideoBox;
    @FXML
    private HBox viewHistoryBox;
    @FXML
    private HBox exitBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainWindowBox.addEventHandler(MouseEvent.MOUSE_PRESSED, mainEventHandler);
        submitVideoBox.addEventHandler(MouseEvent.MOUSE_PRESSED, submitEventHandler);
        viewHistoryBox.addEventHandler(MouseEvent.MOUSE_PRESSED, historyEventHandler);
        exitBox.addEventHandler(MouseEvent.MOUSE_PRESSED, exitEventHandler);
        for (Video video : List) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("videoItem.fxml"));
            try {
                Parent root = loader.load();
                videoItemController passwordController = loader.getController();
                passwordController.setData(video);
                videosLayout.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
