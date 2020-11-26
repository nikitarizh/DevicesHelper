// copy-pasted code

package com.nikitarizh;

import java.sql.*;

public class Model {
	public static Connection conn;
	public static Statement statmt;
	public static ResultSet resSet;
	
	// --------Connection to DB--------
	public Model() throws ClassNotFoundException, SQLException 
	   {
		   conn = null;
           Class.forName("org.sqlite.JDBC");
           System.out.println("Connecting DB...");
		   conn = DriverManager.getConnection("jdbc:sqlite:db.db");
		   
		   System.out.println("DB connected!");
	   }
	
	// --------Table creation--------
	public static void CreateDB() throws ClassNotFoundException, SQLException
	   {
		statmt = conn.createStatement();
		statmt.execute("CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'phone' INT);");
		
		System.out.println("Table created or already exists");
	   }
	
	// --------Table filling--------
	public static void WriteDB() throws SQLException
	{
		   statmt.execute("INSERT INTO 'users' ('name', 'phone') VALUES ('Petya', 125453); ");
		   statmt.execute("INSERT INTO 'users' ('name', 'phone') VALUES ('Vasya', 321789); ");
		   statmt.execute("INSERT INTO 'users' ('name', 'phone') VALUES ('Masha', 456123); ");
		  
		   System.out.println("Table filled");
	}
	
	// --------Table output--------
	public static void ReadDB() throws ClassNotFoundException, SQLException
	   {
		resSet = statmt.executeQuery("SELECT * FROM users");
		
		while(resSet.next())
		{
			int id = resSet.getInt("id");
			String  name = resSet.getString("name");
			String  phone = resSet.getString("phone");
	         System.out.println( "ID = " + id );
	         System.out.println( "name = " + name );
	         System.out.println( "phone = " + phone );
	         System.out.println();
		}	
	    }
	
		// --------Closing--------
		public static void CloseDB() throws ClassNotFoundException, SQLException
		   {
			conn.close();
			statmt.close();
			resSet.close();
			
			System.out.println("Connections closed");
		   }

}