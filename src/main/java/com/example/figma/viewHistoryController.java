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
import java.util.List;
import java.util.ResourceBundle;

public class viewHistoryController implements Initializable {

    static ObservableList<Video> List ;

    public ObservableList<Video> getList() {
        return List;
    }

    public void setList(ObservableList<Video> list) {
        List = list;
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
        List = FXCollections.observableArrayList();
        IPersistence connection = SQLImplementation.getInstance();
        java.util.List<Video> videos = connection.getVideos();
        List.addAll(videos);
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
