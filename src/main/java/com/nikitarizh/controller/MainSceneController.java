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
    private TableView<Device> storageDevicesTable;
    @FXML
    private TableColumn<Device, String> storageTypeColumn;
    @FXML
    private TableColumn<Device, String> storageSerialColumn;
    @FXML
    private TableColumn<Device, String> storageStatusColumn;
    @FXML
    private TextField storageSearchTextField;

    @FXML
    private TableView<Device> operatingDevicesTable;
    @FXML
    private TableColumn<Device, String> operatingTypeColumn;
    @FXML
    private TableColumn<Device, String> operatingLocationColumn;
    @FXML
    private TableColumn<Device, String> operatingStatusColumn;
    @FXML
    private TextField operatingSearchTextField;
    
    private Console console;
    private DevicesModel devicesModel;

    @FXML
    public void initialize() {
        console = new Console(consoleField);
        try {
            console.logWarning("Connecting DB...");
            devicesModel = new DevicesModel();

            storageTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            storageSerialColumn.setCellValueFactory(new PropertyValueFactory<>("serial"));
            storageStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

            operatingTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            operatingLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            operatingStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

            console.logSuccess("DB connected!");
        }
        catch (SQLException e) {
            console.logError("Connecting DB failed (File not found or access error occured)");
        }
        catch (ClassNotFoundException e) {
            console.logError("Connecting DB failed (Class exception)");
        }

        storageDevicesTable.setEditable(true);
        storageTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        storageSerialColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        storageStatusColumn.setCellFactory(TextAreaTableCell.forTableColumn());

        operatingDevicesTable.setRowFactory( tv -> {
            TableRow<Device> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Device d = row.getItem();
                    operatingColumnClicked(d);
                }
            });
            return row ;
        });

        loadData(null, "operating");
        loadData(null, "storage");
    }

    // ***************
    // *** STORAGE ***
    // ***************

    @FXML
    public void loadDataButtonClicked() {
        if (!cleanSearchConfirmation("all")) {
            return;
        }

        loadData(null, "operating");
        loadData(null, "storage");
    }

    @FXML
    public void addButtonClicked() {
        if (!cleanSearchConfirmation("storage")) {
            return;
        }

        try {
            console.logWarning("Trying to add blank value...");
            devicesModel.addData("type", "location", "OK", "");
            loadData(null, "storage");
            console.logSuccess("Blank value has been added");
        }
        catch (Exception e) {
            console.logError("Error adding blank value");
            console.logError(e.getMessage());
        }
    }

    @FXML
    public void removeButtonClicked() {
        Device d = storageDevicesTable.getSelectionModel().getSelectedItem();
        
        if (GUI.showConfirmation("Delete record", "Are you sure want to remove this record?")) {
            if (!cleanSearchConfirmation("storage")) {
                return;
            }

            try {
                console.logWarning("Trying to remove record...");
                devicesModel.removeData(d.getId());
                loadData(null, "storage");
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
        Device d = storageDevicesTable.getSelectionModel().getSelectedItem();
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
    public void serialColumnChanged(TableColumn.CellEditEvent<Device, String> editEvent) {
        Device d = storageDevicesTable.getSelectionModel().getSelectedItem();
        d.setSerial(editEvent.getNewValue());
        try {
            console.logWarning("Trying to update table...");
            devicesModel.updateSerial(d.getId(), d.getSerial());
            console.logSuccess("Table updated");
        }
        catch (SQLException e) {
            console.logError("Error updating table");
        }
    }

    @FXML
    public void statusColumnChanged(TableColumn.CellEditEvent<Device, String> editEvent) {
        Device d = storageDevicesTable.getSelectionModel().getSelectedItem();
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
    public void storageSearchInputKeyPressed(ObservableValue<String> observable, String oldValue, String newValue) {
        loadData(newValue, "storage");
    }

    @FXML
    public void openDeviceWindowKeyPressed() {
        Device d = storageDevicesTable.getFocusModel().getFocusedItem();
        GUI.showDeviceWindow(d, console);
    }

    @FXML
    public void storageTabSelected() {
        loadData(storageSearchTextField.textProperty().get(), "storage");
    }

    // *****************
    // *** OPERATING ***
    // *****************

    @FXML
    public void operatingSearchInputKeyPressed(ObservableValue<String> observable, String oldValue, String newValue) {
        loadData(newValue, "operating");
    }

    @FXML
    public void operatingColumnClicked(Device d) {
        GUI.showDeviceWindow(d, console);
    }

    @FXML
    public void operatingDevicesTabSelected() {
        // this event is fired before the initialize() method so the console is null at first
        if (console != null) {
            loadData(operatingSearchTextField.textProperty().get(), "operating");
        }
    }

    /**
     * Loads data from database
     * <p>
     * Performs search if search param is not null and not empty
     * @param search
     * @param tab
     */
    public void loadData(String search, String tab) {
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
                    String fullData = "";
                    if (tab == "operating") {
                        fullData = type + " " + location + " " + status;
                    }
                    else {
                        fullData = type + " " + serial + " " + status;
                    }
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
            if (tab.equals("operating")) {
                operatingDevicesTable.setItems(data);
            }
            else {
                storageDevicesTable.setItems(data);
            }
            
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
    public boolean cleanSearchConfirmation(String tab) {
        String search = "";
        if (tab.equals("operating")) {
            operatingSearchTextField.textProperty().get();
        }
        else {
            storageSearchTextField.textProperty().get();
        }

        if (search != null && !search.isEmpty()) {
            if (!GUI.showConfirmation("Are you sure?", "Search parameters will be reset. Continue?")) {
                return false;
            }
        }

        if (tab.equals("operating")) {
            operatingSearchTextField.clear();
        }
        else if (tab.equals("storage")) {
            storageSearchTextField.clear();
        }
        else {
            operatingSearchTextField.clear();
            storageSearchTextField.clear();
        }
        return true;
    }
}