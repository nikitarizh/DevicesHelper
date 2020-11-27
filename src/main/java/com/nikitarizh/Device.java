package com.nikitarizh;

import javafx.beans.property.*;

public class Device {
    private SimpleIntegerProperty id;
    private SimpleStringProperty type;
    private SimpleStringProperty location;
    private SimpleStringProperty toFixes;

    public Device(String type, String location, String toFixes) {
        this.id = null;
        this.type = new SimpleStringProperty(type);
        this.location = new SimpleStringProperty(location);
        this.toFixes = new SimpleStringProperty(toFixes);
    }

    public Device(int id, String type, String location, String toFixes) {
        this.id = new SimpleIntegerProperty(id);
        this.type = new SimpleStringProperty(type);
        this.location = new SimpleStringProperty(location);
        this.toFixes = new SimpleStringProperty(toFixes);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public StringProperty typeProperty() {
        return type;
    }

    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public StringProperty locationProperty() {
        return location;
    }

    public String getToFixes() {
        return toFixes.get();
    }

    public void setToFixes(String toFixes) {
        this.toFixes.set(toFixes);
    }

    public StringProperty toFixesProperty() {
        return toFixes;
    }
}
