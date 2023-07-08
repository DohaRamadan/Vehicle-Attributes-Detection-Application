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
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
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


    public void setData(Vehicle vehicle) throws IOException {
        this.vehicle = vehicle;
        vehicleID.setText("#" + vehicle.getID());
        vehicleType.setText(vehicle.getType());
        InputStream in = new FileInputStream(vehicle.getImageSrc());
        Image image = convertToFxImage(ImageIO.read(in));
        vehicleImg.setImage(image);
        vehiclePane.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
    }
}
