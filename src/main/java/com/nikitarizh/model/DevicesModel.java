package com.nikitarizh.model;

import java.sql.*;

public class DevicesModel extends Model {
    
    public DevicesModel() throws ClassNotFoundException, SQLException {
        super();
        createTable();
    }

    public void createTable() throws ClassNotFoundException, SQLException {
        stmt = conn.prepareStatement("CREATE TABLE if not exists 'devices' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'type' TEXT NOT NULL, 'location' TEXT NOT NULL, 'status' TEXT, 'serial' TEXT);");
        stmt.execute();
    }
	
	public ResultSet loadAllData() throws ClassNotFoundException, SQLException {
        stmt = conn.prepareStatement("SELECT * FROM devices");
        stmt.execute();
        resSet = stmt.getResultSet();
		
		return resSet;
    }

    public ResultSet loadData(int id) throws ClassNotFoundException, SQLException {
        stmt = conn.prepareStatement("SELECT * FROM devices WHERE id = ?");
        stmt.setInt(1, id);
        resSet = stmt.getResultSet();

        return resSet;
    }

    public void addData(String type, String location, String status, String serial) throws SQLException {
        stmt = conn.prepareStatement("INSERT INTO 'devices' ('type', 'location', 'status', 'serial') VALUES (?, ?, ?, ?);");
        stmt.setString(1, type);
        stmt.setString(2, location);
        stmt.setString(3, status);
        stmt.setString(4, serial);
        stmt.execute();
    }
    
    public void updateType(int id, String newValue) throws SQLException {
        stmt = conn.prepareStatement("UPDATE 'devices' SET type = ? WHERE id = ?");
        stmt.setString(1, newValue);
        stmt.setInt(2, id);
        stmt.execute();
    }

    public void updateLocation(int id, String newValue) throws SQLException {
        stmt = conn.prepareStatement("UPDATE 'devices' SET location = ? WHERE id = ?");
        stmt.setString(1, newValue);
        stmt.setInt(2, id);
        stmt.execute();
    }

    public void updateStatus(int id, String newValue) throws SQLException {
        stmt = conn.prepareStatement("UPDATE 'devices' SET status = ? WHERE id = ?");
        stmt.setString(1, newValue);
        stmt.setInt(2, id);
        stmt.execute();
    }

    public void updateSerial(int id, String newValue) throws SQLException {
        stmt = conn.prepareStatement("UPDATE 'devices' SET serial = ? WHERE id = ?");
        stmt.setString(1, newValue);
        stmt.setInt(2, id);
        stmt.execute();
    }

    public void removeData(int id) throws SQLException {
        stmt = conn.prepareStatement("DELETE FROM 'devices' WHERE id = ?");
        stmt.setInt(1, id);
        stmt.execute();
    }
	
    public void CloseDB() throws ClassNotFoundException, SQLException {
        conn.close();
        stmt.close();
        resSet.close();
        
        System.out.println("Connections closed");
    }
}
