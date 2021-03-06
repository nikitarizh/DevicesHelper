package com.nikitarizh.util;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;   

public class Console {

    private TextFlow console;
    private int maxLength = 21;

    private String COLOR_GREEN = "#44cc00";
    private String COLOR_WARNING = "#ffc400";
    private String COLOR_RED = "#ff2e2e";

    private DateTimeFormatter dtf;
    
    /**
     * Initializes Console instance
     * @param c (TextFlow instance)
     */
    public Console(TextFlow c) {
        console = c;
        dtf = DateTimeFormatter.ofPattern("dd.MM.yyy HH:mm:ss");  
    }

    /**
     * Logs success (green text) in Console
     * @param text
     */
    public void logSuccess(String text) {
        Text t = new Text();
        t.setStyle("-fx-fill: " + COLOR_GREEN + ";-fx-font-weight:200;\n");
        t.setText(dtf.format(LocalDateTime.now()) + " >>> " + text + "\n");
        pushText(t);
    }

    /**
     * Logs warning (yellow text) in Console
     * @param text
     */
    public void logWarning(String text) {
        Text t = new Text();
        t.setStyle("-fx-fill: " + COLOR_WARNING + ";-fx-font-weight:200;\n");
        t.setText(dtf.format(LocalDateTime.now()) + " >>> " + text + "\n");
        pushText(t);
    }

    /**
     * Logs error (green text) in Console
     * @param text
     */
    public void logError(String text) {
        Text t = new Text();
        t.setStyle("-fx-fill: " + COLOR_RED + ";-fx-font-weight:200;\n");
        t.setText(dtf.format(LocalDateTime.now()) + " >>> " + text + "\n");
        pushText(t);
    }

    /**
     * Borders max size of Console TextFlow
     * @param t (Text instance)
     */
    private void pushText(Text t) {
        t.setFont(Font.font("Courier New", FontWeight.NORMAL, 13));
        if (console.getChildren().size() >= maxLength) {
            console.getChildren().remove(0);
        }
        console.getChildren().add(t);
    }
}
