// copy-pasted code

package com.nikitarizh;

import java.sql.*;

public class Model {
	public static Connection conn;
	public static Statement statmt;
	public static ResultSet resSet;
	
	// --------Connection to DB--------
	public Model() throws ClassNotFoundException, SQLException  {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:db.db");
        statmt = conn.createStatement();
	}
	
	// --------Table creation--------
	public void CreateDB() throws ClassNotFoundException, SQLException {
        statmt.execute("CREATE TABLE if not exists 'devices' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'type' TEXT NOT NULL, 'location' TEXT NOT NULL, 'toFixes' TEXT);");
    }
	
	// --------Table filling--------
	public void WriteDB() throws SQLException {
		   statmt.execute("INSERT INTO 'devices' ('type', 'location') VALUES ('Phone', '314'); ");
		   statmt.execute("INSERT INTO 'devices' ('type', 'location') VALUES ('PC', '315'); ");
		   statmt.execute("INSERT INTO 'devices' ('type', 'location') VALUES ('Laptop', 'hall'); ");
	}
	
	// --------Table output--------
	public ResultSet ReadDB() throws ClassNotFoundException, SQLException {
		resSet = statmt.executeQuery("SELECT * FROM devices");
		
		return resSet;
	}
	
    // --------Closing--------
    public void CloseDB() throws ClassNotFoundException, SQLException {
        conn.close();
        statmt.close();
        resSet.close();
        
        System.out.println("Connections closed");
    }
}