package com.nikitarizh.controller;

import java.sql.SQLException;

import com.nikitarizh.entities.*;
import com.nikitarizh.model.*;
import com.nikitarizh.util.*;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DeviceSceneController {

    @FXML
    private TextField typeField;
    @FXML
    private TextField locationField;
    @FXML
    private TextField serialField;
    @FXML
    private TextArea statusField;
    @FXML
    private ImageView deviceThumbnail;

    private Device device;
    private DevicesModel devicesModel;
    private Console console;

    @FXML
    public void initialize() {
    }

    /**
     * Gets information about specified Device
     * @param d
     * @param c
     */
    public void initData(Device d, Console c) {
        
        console = c;
        device = d;

        try {
            devicesModel = new DevicesModel();
        }
        catch (SQLException e) {
            console.logError("Connecting DB failed (File not found or access error occured)");
        }
        catch (ClassNotFoundException e) {
            console.logError("Connecting DB failed (Class exception)");
        }

        typeField.setText(device.getType());
        locationField.setText(device.getLocation());
        serialField.setText(device.getSerial());
        if (device.getStatus() == null) {
            device.setStatus("");
        }
        statusField.setText(device.getStatus());

        try {
            Image image = new Image(DeviceSceneController.class.getResource("/img/" + device.getType().toLowerCase() + ".png").toString());
            deviceThumbnail.setImage(image);
        }
        catch (Exception e) {
            try {
                System.out.println("Trying to load localized thumbnail...");
                String fileName = "";
                if (device.getType().toLowerCase().equals("клавиатура")) {
                    fileName = "/img/keyboard.png";
                }
                else if (device.getType().toLowerCase().equals("мышь")) {
                    fileName = "/img/mouse.png";
                }
                else if (device.getType().toLowerCase().equals("компьютер")) {
                    fileName = "/img/pc.png";
                }
                else if (device.getType().toLowerCase().equals("ноутбук")) {
                    fileName = "/img/laptop.png";
                }
                Image image = new Image(DeviceSceneController.class.getResource(fileName).toString());
                deviceThumbnail.setImage(image);
            }
            catch (Exception e1) {
                System.out.println("Device thumb loading error: " + e.getMessage());
                System.out.println("Trying to load default thumbnail...");
                try {
                    Image image = new Image(DeviceSceneController.class.getResource("/img/default.png").toString());
                    deviceThumbnail.setImage(image);
                    System.out.println("Default thumbnail loaded");
                }
                catch (Exception e2) {
                    System.out.println("Error loading default thumbnail: " + e1.getMessage());
                }
            }
        }
    }

    @FXML
    public void typeFieldChanged(ObservableValue<String> observable, String oldValue, String newValue) {

        device.setType(newValue);
        try {
            console.logWarning("Trying to update type... New value " + device.getType());
            devicesModel.updateType(device.getId(), device.getType());
            console.logSuccess("Table updated");
        }
        catch (SQLException e) {
            console.logError("Error updating table");
        }
    }

    @FXML
    public void locationFieldChanged(ObservableValue<String> observable, String oldValue, String newValue) {

        device.setLocation(newValue);
        try {
            console.logWarning("Trying to update location... New value " + device.getLocation());
            devicesModel.updateLocation(device.getId(), device.getLocation());
            console.logSuccess("Table updated");
        }
        catch (SQLException e) {
            console.logError("Error updating table");
        }
    }

    @FXML
    public void serialFieldChanged(ObservableValue<String> observable, String oldValue, String newValue) {

        device.setSerial(newValue);
        try {
            console.logWarning("Trying to update serial... New value " + device.getSerial());
            devicesModel.updateSerial(device.getId(), device.getSerial());
            console.logSuccess("Table updated");
        }
        catch (SQLException e) {
            console.logError("Error updating table");
        }
    }

    @FXML
    public void statusFieldChanged(ObservableValue<String> observable, String oldValue, String newValue) {

        device.setStatus(newValue);
        try {
            console.logWarning("Trying to update status... New value " + device.getStatus());
            devicesModel.updateStatus(device.getId(), device.getStatus());
            console.logSuccess("Table updated");
        }
        catch (SQLException e) {
            console.logError("Error updating table");
        }
    }
}