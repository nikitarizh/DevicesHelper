package com.nikitarizh.controller;

import com.nikitarizh.entities.*;
import com.nikitarizh.model.*;
import com.nikitarizh.util.*;
import com.nikitarizh.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DeviceSceneController {

    @FXML
    private TextField typeField;
    @FXML
    private TextField locationField;
    @FXML
    private TextField serialField;
    @FXML
    private TextArea statusField;

    public void initialize() {}

    public void initData(Device d) {
        typeField.setText(d.getType());
        locationField.setText(d.getLocation());
        serialField.setText(d.getSerial());
        if (d.getStatus() == null) {
            d.setStatus("");
        }
        statusField.setText(d.getStatus());
    }
}