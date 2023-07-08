package com.example.figma;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class pleaseWaitController implements Initializable {

    private Stage pleaseWaitStage;

    @FXML
    private AnchorPane scenePane;

    @FXML
    private Label message;


    public void alterMessage(String msg){
        message.setText(msg);
    }

    public void close() throws InterruptedException {
        if(scenePane.getScene() == null)
            TimeUnit.SECONDS.sleep(5);
        pleaseWaitStage = (Stage) scenePane.getScene().getWindow();;
        pleaseWaitStage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
