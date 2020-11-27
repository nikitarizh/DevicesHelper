// copy-pasted code

package com.nikitarizh;

import java.sql.*;

public class Model {
	public static Connection conn;
	public static Statement statmt;
	public static ResultSet resSet;
	
	public Model() throws ClassNotFoundException, SQLException  {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:db.db");
        statmt = conn.createStatement();
	}
	
	public void createTable() throws ClassNotFoundException, SQLException {
        statmt.execute("CREATE TABLE if not exists 'devices' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'type' TEXT NOT NULL, 'location' TEXT NOT NULL, 'toFixes' TEXT);");
    }
	
	public void writeTestData() throws SQLException {
		   statmt.execute("INSERT INTO 'devices' ('type', 'location') VALUES ('Phone', '314'); ");
		   statmt.execute("INSERT INTO 'devices' ('type', 'location') VALUES ('PC', '315'); ");
		   statmt.execute("INSERT INTO 'devices' ('type', 'location') VALUES ('Laptop', 'hall'); ");
	}
	
	public ResultSet readData() throws ClassNotFoundException, SQLException {
		resSet = statmt.executeQuery("SELECT * FROM devices");
		
		return resSet;
    }

    public void addData(String type, String location, String toFixes) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO 'devices' ('type', 'location', 'toFixes') VALUES (?, ?, ?);");
        stmt.setString(1, type);
        stmt.setString(2, location);
        stmt.setString(3, toFixes);
        stmt.execute();
    }
    
    public void updateData(int id, String column, String newValue) throws SQLException {
        statmt.execute("UPDATE 'devices' SET " + column + " = '" + newValue + "' WHERE id = " + id);
    }

    public void removeData(int id) throws SQLException {
        statmt.execute("DELETE FROM 'devices' WHERE id = " + id);
    }
	
    public void CloseDB() throws ClassNotFoundException, SQLException {
        conn.close();
        statmt.close();
        resSet.close();
        
        System.out.println("Connections closed");
    }
}