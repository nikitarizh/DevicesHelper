package com.nikitarizh;

import java.io.IOException;
import java.io.InputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GUI extends Application {

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage stage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/template.fxml"));
        }
        catch (Exception e) {
            System.out.println("No file");
            System.exit(1);
        }
    
        Scene scene = new Scene(root, 300, 275);
    
        stage.setTitle("Devices Helper");
        stage.setScene(scene);
        stage.show();
    }
}