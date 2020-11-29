package com.nikitarizh.view;

import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
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
            e.printStackTrace();
            System.exit(1);
        }
    
        Scene scene = new Scene(root);
    
        stage.setTitle("Devices Helper");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static boolean showConfirmation(String title, String text) {
        Alert confirmation = new Alert(AlertType.CONFIRMATION);
        confirmation.setTitle(title);
        confirmation.setHeaderText(text);
        Optional<ButtonType> option = confirmation.showAndWait();
        if (option.get() != ButtonType.OK) {
            return false;
        }
        return true;
    }
}