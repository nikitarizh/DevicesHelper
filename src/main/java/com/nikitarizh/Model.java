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
	}
	
	// --------Table creation--------
	public void CreateDB() throws ClassNotFoundException, SQLException {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'devices' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'type' TEXT NOT NULL, 'location' TEXT NOT NULL, 'tofixes' TEXT);");
    }
	
	// --------Table filling--------
	public void WriteDB() throws SQLException {
		   statmt.execute("INSERT INTO 'users' ('name', 'phone') VALUES ('Petya', 125453); ");
		   statmt.execute("INSERT INTO 'users' ('name', 'phone') VALUES ('Vasya', 321789); ");
		   statmt.execute("INSERT INTO 'users' ('name', 'phone') VALUES ('Masha', 456123); ");
	}
	
	// --------Table output--------
	public ResultSet ReadDB() throws ClassNotFoundException, SQLException {
		resSet = statmt.executeQuery("SELECT * FROM users");
		
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