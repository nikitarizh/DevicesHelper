package com.nikitarizh;

import java.sql.*;
import javafx.fxml.FXML;
import javafx.scene.text.TextFlow;

public class MainSceneController {

    @FXML
    private TextFlow consoleField;
    
    private Console console;
    private Model model;

    public void initialize() {
        console = new Console(consoleField);

        try {
            console.logWarning("Connecting DB...");
            model = new Model();
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
        console.logSuccess("Click");
    }
}
