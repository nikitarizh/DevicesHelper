package com.nikitarizh.entities;

import javafx.beans.property.*;

/**
 * Represents Device entity
 */
public class Device {
    private SimpleIntegerProperty id;
    private SimpleStringProperty type;
    private SimpleStringProperty location;
    private SimpleStringProperty status;
    private SimpleStringProperty serial;
    private SimpleIntegerProperty parent;

    public Device(int id, String type, String location, String status, String serial, int parent) {
        this.id = new SimpleIntegerProperty(id);
        this.type = new SimpleStringProperty(type);
        this.location = new SimpleStringProperty(location);
        this.status = new SimpleStringProperty(status);
        this.serial = new SimpleStringProperty(serial);
        this.parent = new SimpleIntegerProperty(parent);
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

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public StringProperty statusProperty() {
        return status;
    }

    public String getSerial() {
        return serial.get();
    }

    public void setSerial(String serial) {
        this.serial.set(serial);
    }

    public StringProperty serialProperty() {
        return serial;
    }

    public int getParent() {
        return parent.get();
    }

    public void setParent(int parent) {
        this.parent.set(parent);
    }

    public IntegerProperty parentProperty() {
        return parent;
    }
}
