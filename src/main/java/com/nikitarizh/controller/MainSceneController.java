package com.nikitarizh.controller;

import com.nikitarizh.entities.*;
import com.nikitarizh.model.*;
import com.nikitarizh.util.*;
import com.nikitarizh.view.GUI;

import java.sql.SQLException;
import java.sql.ResultSet;

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

    @FXML
    public void initialize() {
        console = new Console(consoleField);

        try {
            console.logWarning("Connecting DB...");
            devicesModel = new DevicesModel();
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
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

    @FXML
    public void loadDataButtonClicked() {
        if (!cleanSearchConfirmation()) {
            return;
        }

        loadData(null);
    }

    @FXML
    public void addButtonClicked() {
        if (!cleanSearchConfirmation()) {
            return;
        }

        try {
            console.logWarning("Trying to add blank value...");
            devicesModel.addData("type", "location", "OK", "");
            loadData(null);
            console.logSuccess("Blank value has been added");
        }
        catch (Exception e) {
            console.logError("Error adding blank value");
            console.logError(e.getMessage());
        }
    }

    @FXML
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

    @FXML
    public void typeColumnChanged(TableColumn.CellEditEvent<Device, String> editEvent) {
        Device d = devicesTable.getSelectionModel().getSelectedItem();
        d.setType(editEvent.getNewValue());
        try {
            console.logWarning("Trying to update table...");
            devicesModel.updateType(d.getId(), d.getType());
            console.logSuccess("Table updated");
        }
        catch (SQLException e) {
            console.logError("Error updating table");
        }
    }

    @FXML
    public void locationColumnChanged(TableColumn.CellEditEvent<Device, String> editEvent) {
        Device d = devicesTable.getSelectionModel().getSelectedItem();
        d.setLocation(editEvent.getNewValue());
        try {
            console.logWarning("Trying to update table...");
            devicesModel.updateLocation(d.getId(), d.getLocation());
            console.logSuccess("Table updated");
        }
        catch (SQLException e) {
            console.logError("Error updating table");
        }
    }

    @FXML
    public void statusColumnChanged(TableColumn.CellEditEvent<Device, String> editEvent) {
        Device d = devicesTable.getSelectionModel().getSelectedItem();
        d.setStatus(editEvent.getNewValue());
        try {
            console.logWarning("Trying to update table...");
            devicesModel.updateStatus(d.getId(), d.getStatus());
            console.logSuccess("Table updated");
        }
        catch (SQLException e) {
            console.logError("Error updating table");
        }
    }

    @FXML
    public void searchInputKeyPressed(ObservableValue<String> observable, String oldValue, String newValue) {
        loadData(newValue);
    }

    @FXML
    public void openDeviceWindowKeyPressed() {
        Device d = devicesTable.getFocusModel().getFocusedItem();
        GUI.showDeviceWindow(d, console);
    }

    /**
     * Loads data from database
     * <p>
     * Performs search if search param is not null and not empty
     * @param search
     */
    public void loadData(String search) {
        ResultSet res = null;
        try {
            console.logWarning("Trying to read DB...");
            res = devicesModel.loadAllData();
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
                String status = res.getString("status");
                String serial = res.getString("serial");
                if (status == null || status.isEmpty()) {
                    status = "OK";
                }

                if (search != null && !search.isEmpty()) {
                    search = search.trim().toLowerCase();
                    String fullData = type + " " + location + " " + status;
                    fullData = fullData.toLowerCase();
                    String[] splittedSearch = search.split(" ");
                    boolean ok = true;
                    for (int i = 0; i < splittedSearch.length; i++) {
                        if (!fullData.contains(splittedSearch[i])) {
                            ok = false;
                        }
                    }
                    
                    if (ok) {
                        data.add(new Device(id, type, location, status, serial));
                    }
                }
                else {
                    data.add(new Device(id, type, location, status, serial));
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

    /**
     * Shows confirmation window (for cases when it's neccessary to reset the search text field)
     * <p>
     * If user agreed with search field reset, resets it
     * @return returns TRUE if user agreed with search field reset, FALSE otherwise
     */
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