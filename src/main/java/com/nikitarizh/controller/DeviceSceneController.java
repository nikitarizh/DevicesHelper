package com.nikitarizh.controller;

import java.io.File;

import com.nikitarizh.entities.*;
import com.nikitarizh.model.*;
import com.nikitarizh.util.*;
import com.nikitarizh.view.GUI;

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

    public void initialize() {}

    public void initData(Device d) {
        typeField.setText(d.getType());
        locationField.setText(d.getLocation());
        serialField.setText(d.getSerial());
        if (d.getStatus() == null) {
            d.setStatus("");
        }
        statusField.setText(d.getStatus());

        try {
            Image image = new Image(DeviceSceneController.class.getResource("/img/" + d.getType() + ".png").toString());
            deviceThumbnail.setImage(image);
        }
        catch (Exception e) {
            System.out.println("Device thumb loading error: " + e.getMessage());
        }
        
    }
}