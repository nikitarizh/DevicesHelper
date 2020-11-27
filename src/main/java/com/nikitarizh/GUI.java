package com.nikitarizh;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
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
            System.out.println("Error loading template");
            System.exit(1);
        }
    
        Scene scene = new Scene(root);
    
        stage.setTitle("Devices Helper");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}