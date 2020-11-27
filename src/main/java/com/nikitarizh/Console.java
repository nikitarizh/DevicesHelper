package com.nikitarizh;

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
    
    public Console(TextFlow c) {
        console = c;
        dtf = DateTimeFormatter.ofPattern("dd.MM.yyy HH:mm:ss");  
    }

    public void logSuccess(String text) {
        Text t = new Text();
        t.setStyle("-fx-fill: " + COLOR_GREEN + ";-fx-font-weight:200;\n");
        t.setText(dtf.format(LocalDateTime.now()) + " >>> " + text + "\n");
        pushText(t);
    }

    public void logWarning(String text) {
        Text t = new Text();
        t.setStyle("-fx-fill: " + COLOR_WARNING + ";-fx-font-weight:200;\n");
        t.setText(dtf.format(LocalDateTime.now()) + " >>> " + text + "\n");
        pushText(t);
    }

    public void logError(String text) {
        Text t = new Text();
        t.setStyle("-fx-fill: " + COLOR_RED + ";-fx-font-weight:200;\n");
        t.setText(dtf.format(LocalDateTime.now()) + " >>> " + text + "\n");
        pushText(t);
    }

    private void pushText(Text t) {
        if (console.getChildren().size() >= maxLength) {
            console.getChildren().remove(0);
        }
        console.getChildren().add(t);
    }
}
