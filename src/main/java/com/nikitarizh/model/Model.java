package com.nikitarizh.model;

import java.sql.*;

public abstract class Model {
	protected Connection conn;
	protected PreparedStatement stmt;
	protected ResultSet resSet;
	
	public Model() throws ClassNotFoundException, SQLException  {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:db.db");
    }
    
    public abstract void createTable() throws ClassNotFoundException, SQLException;

    public abstract ResultSet readAllData() throws ClassNotFoundException, SQLException;
}