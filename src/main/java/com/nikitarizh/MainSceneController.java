package com.nikitarizh;

import java.sql.*;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
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
    
    private Console console;
    private Model model;

    public void initialize() {
        console = new Console(consoleField);

        try {
            console.logWarning("Connecting DB...");
            model = new Model();
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

        loadData();
    }

    public void writeTestDataButtonClicked() {
        try {
            console.logWarning("Trying to create table...");
            model.createTable();
            console.logSuccess("Table has been created");
        }
        catch (Exception SQLException) {
            console.logError("Error creating table");
            return;
        }

        try {
            console.logWarning("Trying to write data to table...");
            model.writeTestData();
            console.logSuccess("Data has been written");
        }
        catch (Exception SQLException) {
            console.logError("Error writing data to table");
            return;
        }
        loadData();
    }

    public void loadDataButtonClicked() {
        loadData();
    }

    public void addButtonClicked() {
        try {
            console.logWarning("Trying to add blank value...");
            model.addData("type", "location", "toFixes");
            loadData();
            console.logSuccess("Blank value has been added");
        }
        catch (Exception e) {
            console.logError("Error adding blank value");
            console.logError(e.getMessage());
        }
    }

    public void removeButtonClicked() {
        Device d = devicesTable.getSelectionModel().getSelectedItem();
        
        Alert confirmation = new Alert(AlertType.CONFIRMATION);
        confirmation.setTitle("Delete record");
        confirmation.setHeaderText("Are you sure want to remove this record?");
        
        Optional<ButtonType> option = confirmation.showAndWait();
        if (option.get() == ButtonType.OK) {
            try {
                console.logWarning("Trying to remove record...");
                model.removeData(d.getId());
                loadData();
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
            model.updateData(d.getId(), "type", d.getType());
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
            model.updateData(d.getId(), "location", d.getLocation());
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
            model.updateData(d.getId(), "toFixes", d.getToFixes());
            console.logSuccess("Table updated");
        }
        catch (SQLException e) {
            console.logError("Error updating table");
        }
    }

    public void loadData() {
        ResultSet res = null;
        try {
            console.logWarning("Trying to read DB...");
            res = model.readData();
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

                data.add(new Device(id, type, location, fixes));
            }
            devicesTable.setItems(data);
            console.logSuccess("ResultSet read");
        }
        catch (SQLException e) {
            console.logError("Error reading ReultSet");
            return;
        }
    }
}
