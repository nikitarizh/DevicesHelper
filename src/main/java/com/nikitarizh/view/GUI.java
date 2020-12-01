package com.nikitarizh.view;

import com.nikitarizh.controller.DeviceSceneController;
import com.nikitarizh.entities.Device;
import com.nikitarizh.util.Console;

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
            root = FXMLLoader.load(getClass().getResource("/FXML/mainTemplate.fxml"));
        }
        catch (Exception e) {
            System.out.println("Error loading main template");
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

    public static void showDeviceWindow(Device device, Console console) {
        FXMLLoader loader = new FXMLLoader(GUI.class.getResource("/FXML/deviceTemplate.fxml"));

        Stage stage = new Stage();
    
        stage.setTitle("Device");
        stage.setResizable(false);

        try {
            stage.setScene(new Scene(loader.load()));
        }
        catch (Exception e) {
            System.out.println("Error loading device template");
            e.printStackTrace();
            System.exit(1);
        }

        DeviceSceneController controller = (DeviceSceneController) loader.getController();
        controller.initData(device, console);

        stage.show();
    }
}