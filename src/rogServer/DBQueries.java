/**
 * 
 */
package rogServer;

/**
 * Class of database queries for talking to the RogMSG database.
 * 
 * @author Sarah F.
 * 
 */
import java.sql.*;

public class DBQueries {
	// Set up from: https://www.tutorialspoint.com/jdbc/jdbc-sample-code.htm 
	
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://127.0.0.1/rogdb";
	
	// DB credentials 
	
	
	// ---- DB Queries ---- //
	
	// DB connection & error
	
	public static void connectDB() {}
	
	public static void disconnectDB() {}
	
	public static void getDBErrLog() {}
	
	private static void setDBErrLog() {}
	
	// Group Queries 
	public static void addGroup() {}
	
	public static void updateGroup() {}
	
	public static void delGroup() {}
	
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
