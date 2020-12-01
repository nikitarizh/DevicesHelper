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
    
    /**
     * Creates a table if it doesn't exist
     * <p>
     * Field of the table are specified in function's implementation
     * @throws ClassNotFoundException
     * @throws SQLException
    */
    public abstract void createTable() throws ClassNotFoundException, SQLException;

    /**
     * Loads all data from the table specified in {@link #createTable() createTable} method
     * @return ResultSet that contains result of sql query
     * @throws ClassNotFoundException
     * @throws SQLException
    */
    public abstract ResultSet loadAllData() throws ClassNotFoundException, SQLException;
}