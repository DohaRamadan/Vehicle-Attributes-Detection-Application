package com.example.figma;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javafx.concurrent.Task;
import javafx.scene.control.Button;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber.Exception;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class screenshotController implements Initializable {
    Stage stage;

    Scene scene;
    ArrayList<Pair<Integer, Integer>> mouseClicks = new ArrayList<>();
    EventHandler<MouseEvent> submitEventHandler =
            mouseEvent -> Loader.loadScene("submitVideo.fxml", mouseEvent);
    EventHandler<MouseEvent> historyEventHandler =
            mouseEvent -> Loader.loadScene("viewHistory.fxml", mouseEvent);
    EventHandler<MouseEvent> mainEventHandler =
            mouseEvent -> Loader.loadScene("mainWindow.fxml", mouseEvent);
    @FXML
    private ImageView imageView;
    @FXML
    private AnchorPane scenePane;
    EventHandler<MouseEvent> exitEventHandler =
            new EventHandler<>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                    ExitController.exitAlarm(scenePane);
                }
            };
    @FXML
    private HBox mainWindowBox;
    @FXML
    private HBox submitVideoBox;
    @FXML
    private HBox viewHistoryBox;
    @FXML
    private HBox exitBox;
    @FXML
    private Label guideText;
    EventHandler<MouseEvent> clickEventHandler =
            new EventHandler<>() {
                @Override
                public void handle(javafx.scene.input.MouseEvent mouseEvent) {
                    mouseClicks.add(new Pair<>((int) mouseEvent.getX(), (int) mouseEvent.getY()));
                    if (mouseClicks.size() % 4 == 0) {
                        stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                        stage.close();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("submitVideo.fxml"));
                        try {
                            Parent root = loader.load();
                            submitVideoController submitController = loader.getController();
                            submitController.saveCoordinates(mouseClicks);
                            scene = new Scene(root);
                            stage = new Stage();
                            stage.setScene(scene);
                            stage.setResizable(false);
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (mouseClicks.size() % 4 == 1) {
                        guideText.setText("Click where you want the second point of the start line to be.");
                    } else if (mouseClicks.size() % 4 == 2) {
                        guideText.setText("Click where you want the first point of the end line to be.");
                    } else {
                        guideText.setText("Click where you want the second point of the end line to be.");
                    }
                }
            };

    private static Image convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return new ImageView(wr).getImage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainWindowBox.addEventHandler(MouseEvent.MOUSE_PRESSED, mainEventHandler);
        submitVideoBox.addEventHandler(MouseEvent.MOUSE_PRESSED, submitEventHandler);
        viewHistoryBox.addEventHandler(MouseEvent.MOUSE_PRESSED, historyEventHandler);
        exitBox.addEventHandler(MouseEvent.MOUSE_PRESSED, exitEventHandler);
        imageView.addEventHandler(MouseEvent.MOUSE_PRESSED, clickEventHandler);
    }

    public BufferedImage extractFirstFrame(String videoPath) throws FFmpegFrameGrabber.Exception {
        File myObj = new File(videoPath);
        FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(myObj.getAbsoluteFile());
        frameGrabber.start();
        Frame f;
        BufferedImage bi = null;
        try {
            Java2DFrameConverter c = new Java2DFrameConverter();
            f = frameGrabber.grab();
            bi = c.convert(f);
            ImageIO.write(bi, "png", new File("C:\\Users\\DOHA\\IdeaProjects\\Figma\\src\\main\\resources\\com\\example\\figma\\images\\screenshot.jpg"));
            frameGrabber.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bi;
    }

    public void displayScreenShot(Parent root, String videoPath, Button submitBtn, Stage parentStage) throws IOException, AWTException, InterruptedException {
        // Set the text of the submitBtn to "Please wait...".
        submitBtn.setText("Please wait...");
        Task<BufferedImage> task = new Task<>() {
            @Override
            protected BufferedImage call() throws Exception {
                return extractFirstFrame(videoPath);
            }
        };

        task.setOnSucceeded(event -> {
            BufferedImage buff = task.getValue();
            imageView.setImage(convertToFxImage(buff));
            // Change the text of the submitBtn back to its original value.
            submitBtn.setText("Submit");

            Scene scene1 = new Scene(root);
            this.stage = new Stage();
            this.stage.setScene(scene1);
            this.stage.show();

            parentStage.close();
        });

        new Thread(task).start();
    }
}
