/**
 * 
 */
package rogServer;

import java.io.IOException;
import java.sql.*;
/**
 * Class of database queries for talking to the RogMSG database.
 * NOTE: JDBC Driver mysql-connector-java-5.1.44 must be located in  C:\Program Files
 * 
 * @author Sarah F.
 * 
 */

public class DBQueries 
{
	// Set up from: https://www.tutorialspoint.com/jdbc/jdbc-sample-code.htm 
	
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://127.0.0.1/rogmsg"; //Port: 3306
	
	// DB credentials 
	static final String USER = "root";
	static final String PASS = "";
	
	// DB I/O
	private static Connection conn = null;
	private static Statement stmt = null;
	private static String sql = null;
	private static String err = null;		// eventually set any err catching to use this & return
	
	
	
	// ---- main test ---- //
	public static void main (String args[]) throws IOException 
	{
		connectDB();
		disconnectDB();
		
		addGroup("TestGroup");
	}
	
	
	
	// !!!!!! Have every method return a string
	
	
	
	// ---- DB Queries ---- //
	
	// DB connection & error
	
	public static void connectDB() 
	{
		try 
		{
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} 
		catch (SQLException e) 
		{
			// TODO have work with errLog handling 
			e.printStackTrace();
			System.out.println("Error: Could not connect to DB.");
		}
		
		System.out.println("Connected to : " + DB_URL); // test statement
	}
	
	/*
	 * Close the DB connection
	 */
	public static void disconnectDB()
	{
		try 
		{
			conn.close();
		} 
		catch (SQLException e) 
		{
			// TODO have work with errLog handling 
			e.printStackTrace();
		}
		
		System.out.println("Disconnected from : " + DB_URL); // test statement
	}
	
	public static void getDBErrLog() {}
	
	private static void setDBErrLog() {}
	
	
	
	// Group Queries 
	public static int addGroup(String groupName) 
	{
		try 
		{
			stmt = conn.createStatement(); 
			sql = "";		//TODO write the SQL Statement
			int insert = stmt.executeUpdate(sql);
			return insert;
		} 
		catch (SQLException e) 
		{
			// TODO Have work with errLog handling eventually
			e.printStackTrace();
			return -1;
		}
	}
	
	public static int updateGroup() 
	{
		try 
		{
			stmt = conn.createStatement(); 
			sql = "";		//TODO write the SQL Statement
			int insert = stmt.executeUpdate(sql);
			return insert;
		} 
		catch (SQLException e) 
		{
			// TODO Have work with errLog handling eventually
			e.printStackTrace();
			return -1;
		}
	}
	
	public static int delGroup() 
	{
		try 
		{
			stmt = conn.createStatement(); 
			sql = "";		//TODO write the SQL Statement
			int insert = stmt.executeUpdate(sql);
			return insert;
		} 
		catch (SQLException e) 
		{
			// TODO Have work with errLog handling eventually
			e.printStackTrace();
			return -1;
		}
	}
	
	
	
	// User Queries
	public static void addUser() {}
	
	public static void updateUser() {}
	
	public static void delUser() {}
	
	public static void getUsers() {} // add to documentation
	
	
	
	// List Queries
	public static void addList() {}
	
	public static void updateList() {}
	
	public static void delList() {}
	
	public static void getLists() {} // add to documentation
	
	
	
	// Poll Queries
	public static void addPoll() {}
	
	public static void updatePoll() {}
	
	public static void delPoll() {}
	
	public static void getPolls() {} // add to documentation
	
}
