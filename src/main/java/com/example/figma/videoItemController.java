package com.example.figma;

import com.example.figma.entities.Video;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class videoItemController implements Initializable {

    @FXML
    private Label numberOfVehicles;

    @FXML
    private Label videoDate;

    @FXML
    private Label videoID;

    @FXML
    private Label videoName;

    @FXML
    private ImageView searchBtn;

    private Video video;

    public void setData(Video video) {
        this.video = video;
        videoID.setText(String.valueOf(video.getID()));
        videoDate.setText(video.getDate());
        videoName.setText(video.getName());
        numberOfVehicles.setText(String.valueOf(video.getNumberOfVehicles()));
        searchBtn.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/search.png"))));
    }


    @FXML
    void viewDetectedVehicles(ActionEvent event) throws IOException {
        detectedVehiclesController detectedVehiclesController = new detectedVehiclesController();
        detectedVehiclesController.setVideo(video);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("detectedVehicles.fxml"));
        loader.setController(detectedVehiclesController);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
