package com.nikitarizh;

import java.sql.*;

public class MainSceneController {
    public void buttonClicked() {
        try {
            Model m = new Model();
        }
        catch (SQLException e) {
            System.out.println("Connecting DB failed (File not found or access error occured)");
        }
        catch (ClassNotFoundException e) {
            System.out.println("Connecting DB failed (Class exception)");
        }
    }
}
