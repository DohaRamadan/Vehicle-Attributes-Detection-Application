module com.example.figma {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.desktop;
    requires org.bytedeco.javacv;
    requires java.net.http;

    opens com.example.figma to javafx.fxml;
    exports com.example.figma;
}