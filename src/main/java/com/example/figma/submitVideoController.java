package com.example.figma;

import com.example.figma.entities.Vehicle;
import com.example.figma.entities.Video;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.control.Button;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class submitVideoController implements Initializable {
    @FXML
    private TextField distanceField;
    private static String videoPathStr;
    private static String absVideoPath;
    private static ArrayList<Pair<Integer, Integer>> mouseClicks;
    private static float distance;
    Stage stage;
    Scene scene;
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
    private ImageView openFileBtn;
    @FXML
    private AnchorPane scenePane;
    @FXML
    private TextField videoPath;

    @FXML
    private Button submitBtn;
    EventHandler<MouseEvent> openFileHandler =
            new EventHandler<>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                    FileChooser fileChooser = new FileChooser();
                    stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                    File file = fileChooser.showOpenDialog(stage);
                    videoPath.setText(file.getName());
                    videoPathStr = videoPath.getText();
                    absVideoPath = file.getAbsolutePath();
                    if(!submitFormValidator.validateVideoPath(videoPath.getText(), scenePane))
                        return;
                    try {
                        FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("screenshotFrame.fxml"));
                        Parent root = fxmlLoader2.load();
                        screenshotController frameController1 = fxmlLoader2.getController();
                        frameController1.displayScreenShot(root, file.getAbsolutePath(), submitBtn, stage);
                    } catch (IOException | AWTException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
    @FXML
    private TextField startPoint1X;
    @FXML
    private TextField startPoint1Y;
    @FXML
    private TextField endPoint1X;
    @FXML
    private TextField endPoint1Y;
    @FXML
    private TextField startPoint2X;
    @FXML
    private TextField startPoint2Y;
    @FXML
    private TextField endPoint2X;
    @FXML
    private TextField endPoint2Y;
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
        openFileBtn.addEventHandler(MouseEvent.MOUSE_PRESSED, openFileHandler);
    }


    public void switchToHistory(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("viewHistory.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void saveCoordinates(ArrayList<Pair<Integer, Integer>> mouseClicks) {
        submitVideoController.mouseClicks = mouseClicks;
        videoPath.setText(videoPathStr);

        startPoint1X.setText(String.valueOf(mouseClicks.get(mouseClicks.size() - 4).getKey()));
        startPoint1Y.setText(String.valueOf(mouseClicks.get(mouseClicks.size() - 4).getValue()));

        startPoint2X.setText(String.valueOf(mouseClicks.get(mouseClicks.size() - 3).getKey()));
        startPoint2Y.setText(String.valueOf(mouseClicks.get(mouseClicks.size() - 3).getValue()));

        endPoint1X.setText(String.valueOf(mouseClicks.get(mouseClicks.size() - 2).getKey()));
        endPoint1Y.setText(String.valueOf(mouseClicks.get(mouseClicks.size() - 2).getValue()));

        endPoint2X.setText(String.valueOf(mouseClicks.get(mouseClicks.size() - 1).getKey()));
        endPoint2Y.setText(String.valueOf(mouseClicks.get(mouseClicks.size() - 1).getValue()));
    }


    @FXML
    void handleSubmission(ActionEvent actionEvent) {
        if(!com.example.figma.submitFormValidator.validateVideoPath(videoPath.getText(), scenePane))
            return;
        if(!com.example.figma.submitFormValidator.validateDistance(distanceField.getText(), scenePane))
            return;
        distance = Float.parseFloat(distanceField.getText());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String formattedTime = dtf.format(now);
        formattedTime = formattedTime.replace(":", "").replaceAll(" ","").replaceAll("/", "");

        submitBtn.setText("Please wait....");
        final boolean[] failed = {false};

        // Create a new Task to run the API call in a background thread
        Task<ArrayList<Vehicle>> task = new Task<>() {
            @Override
            protected ArrayList<Vehicle> call() {
                 try{
                     return APIController.callApi(absVideoPath, "http://2fa6-35-240-140-21.ngrok-free.app/", dtf.format(now), mouseClicks, distance);
                } catch (IOException e) {
                     e.printStackTrace();
                     submitBtn.setText("Failed. Please try again.");
                     failed[0] = true;
                     return new ArrayList<>();
                 }
            }
        };

        // Set a listener to update the UI after the Task is completed
        String finalFormattedTime = formattedTime;
        task.setOnSucceeded(event -> {
            ArrayList<Vehicle> vehicles = task.getValue();
            if(!failed[0] && vehicles != null){
                submitBtn.setText("Success");
                IPersistence connection = SQLImplementation.getInstance();
                connection.addVideo(new Video(videoPathStr, finalFormattedTime, vehicles.size(), vehicles));
                try {
                    switchToHistory(actionEvent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                submitBtn.setText("Failed. Please try again.");
                scenePane.setDisable(false);
            }

        });

        // Disable the GUI controls to prevent the user from interacting with it
        scenePane.setDisable(true);

        // Start the Task in a new thread
        new Thread(task).start();
    }
}
