package com.nikitarizh;

import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    }

    public void buttonClicked() {
        try {
            console.logWarning("Trying to create table...");
            model.CreateDB();
            console.logSuccess("Table has been created");
        }
        catch (Exception SQLException) {
            console.logError("Error creating table");
            return;
        }

        try {
            console.logWarning("Trying to write data to table...");
            model.WriteDB();
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

    public void loadData() {
        ResultSet res = null;
        try {
            console.logWarning("Trying to read DB...");
            res = model.ReadDB();
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
