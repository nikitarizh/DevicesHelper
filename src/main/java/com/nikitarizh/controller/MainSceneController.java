package com.nikitarizh.controller;

import com.nikitarizh.entities.*;
import com.nikitarizh.model.*;
import com.nikitarizh.util.*;
import com.nikitarizh.view.GUI;

import java.sql.*;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.TextFlow;

public class MainSceneController {

    @FXML
    private TextFlow consoleField;

    @FXML
    private TableView<Device> devicesTable;
    @FXML
    private TableColumn<Device, String> typeColumn;
    @FXML
    private TableColumn<Device, String> locationColumn;
    @FXML
    private TableColumn<Device, String> statusColumn;
    @FXML
    private TextField searchTextField;
    
    private Console console;
    private DevicesModel devicesModel;

    public void initialize() {
        console = new Console(consoleField);

        try {
            console.logWarning("Connecting DB...");
            devicesModel = new DevicesModel();
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("toFixes"));
            console.logSuccess("DB connected!");
        }
        catch (SQLException e) {
            console.logError("Connecting DB failed (File not found or access error occured)");
        }
        catch (ClassNotFoundException e) {
            console.logError("Connecting DB failed (Class exception)");
        }

        devicesTable.setEditable(true);
        typeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        locationColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        statusColumn.setCellFactory(TextAreaTableCell.forTableColumn());

        loadData(null);
    }

    public void loadDataButtonClicked() {
        if (!cleanSearchConfirmation()) {
            return;
        }

        loadData(null);
    }

    public void addButtonClicked() {
        if (!cleanSearchConfirmation()) {
            return;
        }

        try {
            console.logWarning("Trying to add blank value...");
            devicesModel.addData("type", "location", "OK");
            loadData(null);
            console.logSuccess("Blank value has been added");
        }
        catch (Exception e) {
            console.logError("Error adding blank value");
            console.logError(e.getMessage());
        }
    }

    public void removeButtonClicked() {
        Device d = devicesTable.getSelectionModel().getSelectedItem();
        
        if (GUI.showConfirmation("Delete record", "Are you sure want to remove this record?")) {
            if (!cleanSearchConfirmation()) {
                return;
            }

            try {
                console.logWarning("Trying to remove record...");
                devicesModel.removeData(d.getId());
                loadData(null);
                console.logSuccess("Successfully removed record");
            }
            catch (SQLException e) {
                console.logError("Error removing record");
                console.logError(e.getMessage());
            }
        } 
    }

    public void typeColumnChanged(TableColumn.CellEditEvent<Device, String> editEvent) {
        Device d = devicesTable.getSelectionModel().getSelectedItem();
        d.setType(editEvent.getNewValue());
        try {
            console.logWarning("Trying to update table...");
            devicesModel.updateData(d.getId(), "type", d.getType());
            console.logSuccess("Table updated");
        }
        catch (SQLException e) {
            console.logError("Error updating table");
        }
    }

    public void locationColumnChanged(TableColumn.CellEditEvent<Device, String> editEvent) {
        Device d = devicesTable.getSelectionModel().getSelectedItem();
        d.setLocation(editEvent.getNewValue());
        try {
            console.logWarning("Trying to update table...");
            devicesModel.updateData(d.getId(), "location", d.getLocation());
            console.logSuccess("Table updated");
        }
        catch (SQLException e) {
            console.logError("Error updating table");
        }
    }

    public void statusColumnChanged(TableColumn.CellEditEvent<Device, String> editEvent) {
        Device d = devicesTable.getSelectionModel().getSelectedItem();
        d.setToFixes(editEvent.getNewValue());
        try {
            console.logWarning("Trying to update table...");
            devicesModel.updateData(d.getId(), "toFixes", d.getToFixes());
            console.logSuccess("Table updated");
        }
        catch (SQLException e) {
            console.logError("Error updating table");
        }
    }

    public void searchInputKeyPressed(ObservableValue<String> observable, String oldValue, String newValue) {
        loadData(newValue);
    }

    public void loadData(String search) {
        ResultSet res = null;
        try {
            console.logWarning("Trying to read DB...");
            res = devicesModel.readAllData();
            console.logSuccess("Data read");
        }
        catch (Exception e) {
            console.logError("Error reading data from DB");
            return;
        }

        try {
            console.logWarning("Trying to read ResultSet...");
            ObservableList<Device> data = FXCollections.observableArrayList();
            while (res.next()) {
                int id = res.getInt("id");
                String type = res.getString("type");
                String location = res.getString("location");
                String fixes = res.getString("toFixes");
                if (fixes == null || fixes.trim() == "") {
                    fixes = "OK";
                }

                if (search != null && !search.isEmpty()) {
                    search = search.trim().toLowerCase();
                    String fullData = type + " " + location + " " + fixes;
                    fullData = fullData.toLowerCase();
                    String[] splittedSearch = search.split(" ");
                    boolean ok = true;
                    for (int i = 0; i < splittedSearch.length; i++) {
                        if (!fullData.contains(splittedSearch[i])) {
                            ok = false;
                        }
                    }
                    
                    if (ok) {
                        data.add(new Device(id, type, location, fixes));
                    }
                }
                else {
                    data.add(new Device(id, type, location, fixes));
                }
                
            }
            devicesTable.setItems(data);
            console.logSuccess("ResultSet read");
        }
        catch (SQLException e) {
            console.logError("Error reading ReultSet");
            return;
        }
    }

    public boolean cleanSearchConfirmation() {
        String search = searchTextField.textProperty().get();
        if (search != null && !search.isEmpty()) {
            if (!GUI.showConfirmation("Are you sure?", "Search parameters will be reset. Continue?")) {
                return false;
            }
        }
        searchTextField.clear();
        return true;
    }
}