/**
 * 
 */
package rogServer;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
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
	//private static String err = null;		// eventually set any err catching to use this & return
	
	
	
	// ---- main test ---- //
	public static void main (String args[]) throws IOException 
	{
		connectDB();
		
		String groupName = "TestGroup";
		
		int addGpReturn = addGroup(groupName);
		System.out.println("Added Group Return ID: " + addGpReturn);
		
		int id = getGroupID(groupName);
		System.out.println("Get Group ID Test Return: " + id);
		
		int upGpReturn = updateGroup(id, "NewName");
		System.out.println("Updated Group Return : " + upGpReturn);
		
		//int delGpReturn = delGroup();
		//System.out.println("Updated Group Return : " + delGpReturn);
		
		disconnectDB();
	}
	
	
	
	
	
	
	// ---- DB Queries ---- //
	
	// DB connection & error
	
	/**
	 * Open a connection to the Database. 
	 * All connection parameters are static invariants.
	 */
	public static void connectDB() 
	{
		try 
		{
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("Error: Could not connect to DB.");
		}
		
		System.out.println("Connected to : " + DB_URL); // test statement
	}
	
	/**
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
			e.printStackTrace();
		}
		
		System.out.println("Disconnected from : " + DB_URL); // test statement
	}
	
	public static void getDBErrLog() {}
	
	//private static void setDBErrLog() {}
	
	
	
	// Group Queries 
	/**
	 * Adds a new group to the database.
	 * 
	 * @param groupName The name of the group created
	 * @return The ID of the group just created. 
	 */
	public static int addGroup(String groupName) 
	{
		try 
		{
			stmt = conn.createStatement(); 
			sql = "INSERT INTO msggroup (name) VALUES ('" + groupName + "');";
			stmt.executeUpdate(sql);
			
			//returns groupID number
			return getGroupID(groupName);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Update a group's name given the groupID.
	 * groupID's cannot be changed.
	 * 
	 * @param groupID The id of the group in the DB to change.
	 * @param name The name that you wish to change the group to.
	 * @return Exit status. Returns -1 if failed.
	 */
	public static int updateGroup(int groupID, String name) 
	{
		try 
		{
			stmt = conn.createStatement(); 
			sql = "UPDATE msggroup SET name='"+ name +" WHERE groupID=" + groupID + ";";
			int rs = stmt.executeUpdate(sql);
			
			return rs;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Delete a group from the Database given the groupID.
	 * 
	 * @param groupID The ID of the group to delete
	 * @return Exit status. Returns -1 if failed.
	 */
	public static int delGroup(int groupID) 
	{
		try 
		{
			stmt = conn.createStatement(); 
			sql = "DELETE FROM msggroup WHERE groupID = " + groupID + ";";
			int rs = stmt.executeUpdate(sql);
			return rs;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Get the ID of the group. 
	 * 
	 * @param groupName Name of the group to get the ID of. Group names are unique, so there will be no duplicates.
	 * @return the ID of the group.
	 */
	private static int getGroupID(String groupName) 
	{
		try 
		{
			stmt = conn.createStatement(); 
			sql = "SELECT groupID FROM msggroup WHERE name='" + groupName + "';";
			ResultSet rs = stmt.executeQuery(sql);
			
			// Extract data from result set
			int id = -1;
			while(rs.next())
			{
				//Retrieve by column name
				id  = rs.getInt("groupID");
			}
			rs.close();
			
			return id;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Get the name of a specific group.
	 * 
	 * @param groupID The ID of the group to get the name of.
	 * @return The name of the group.
	 */
	public static String getGroupName(int groupID) 
	{
		try 
		{
			stmt = conn.createStatement(); 
			sql = "SELECT name FROM msggroup WHERE groupID='" + groupID + "';";
			ResultSet rs = stmt.executeQuery(sql);
			
			// Extract data from result set
			String name = "";
			while(rs.next())
			{
				//Retrieve by column name
				name  = rs.getString("name");
			}
			rs.close();
			
			return name;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	// User Queries
	
	/**
	 * Adds a user to the database.
	 * <p>
	 * The email of the user will also be set as the default password. 
	 * 
	 * @param groupName The name of the group to add the user to. 
	 * @param username The name of the new user.
	 * @param email The email of the user created. Will also be set as the default password for the user.
	 * @returns the ID of the new user created.
	 */
	public static int addUser(String groupName, String username, String email) 
	{
		try 
		{
			stmt = conn.createStatement(); 
			sql = "INSERT INTO users (groupID, username, email, password)VALUES ('" 
			+ getGroupID(groupName) + "," + username + "," + email + "," + email +"');";
			stmt.executeUpdate(sql);
			
			//returns groupID number
			return getUserID(groupName, username);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Get the ID of the user in the given group. 
	 * 
	 * @param groupName Name of the group to get the user from.
	 * @param username Name of the user account.
	 * @return The ID of the user. Returns -1 if query failed. 
	 */
	private static int getUserID(String groupName, String username) {
		try 
		{
			stmt = conn.createStatement(); 
			sql = "SELECT groupID FROM msggroup WHERE groupID = '" + getGroupID(groupName) +"' AND username='" + username + "';";
			ResultSet rs = stmt.executeQuery(sql);
			
			// Extract data from result set
			int id = -1;
			while(rs.next())
			{
				//Retrieve by column name
				id  = rs.getInt("userID");
			}
			rs.close();
			
			return id;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Update a user's name.
	 */
	public static void updateUserName() 
	{
		
	}
	
	/**
	 * Update a user's email.
	 */
	public static void updateUserEmail() 
	{
		
	}
	
	/**
	 * Update a user's password.
	 * 
	 * @param userID ID of the user to update.
	 * @param pass The new password for the user account.
	 * @returns Exit status. Returns -1 if failed.
	 */
	public static int updateUserPass(int userID, String pass) 
	{
		try 
		{
			stmt = conn.createStatement(); 
			sql = "UPDATE msggroup SET password='"+ pass +" WHERE userID=" + userID + ";";
			int rs = stmt.executeUpdate(sql);
			
			return rs;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Delete a user, given their ID.
	 */
	public static void delUser() {}
	
	/**
	 * Get all the users in a specific group.
	 * 
	 * @param groupID The ID of the group to get list of users from
	 * @returns ArrayList of all the users in the group. List contains ID's and username's only
	 */
	public static ArrayList<User> getUsers(int groupID) 
	{
		try 
		{
			stmt = conn.createStatement(); 
			sql = "SELECT userID, username FROM users WHERE groupID='" + groupID + "' AND username='"+ username +
					"'" + pass +"';";
			ResultSet rs = stmt.executeQuery(sql);
			
			// Extract data from result set
			int id = -1;
			while(rs.next())
			{
				//Retrieve by column name
				id  = rs.getInt("userID");
			}
			rs.close();
			
			return id;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return -1;
		}
	} 
	
	/**
	 * Get a specific user's ID based off of their username and groupID
	 * 
	 * @param groupID ID of the group the user is in.
	 * @param username Username of the user to find. 
	 * @returns Specified users ID. Returns -1 if query failed.
	 */
	public static int getUser(int groupID, String username) 
	{
		try 
		{
			stmt = conn.createStatement(); 
			sql = "SELECT userID FROM users WHERE groupID='" + groupID + "' AND username='"+ username +"';";
			ResultSet rs = stmt.executeQuery(sql);
			
			// Extract data from result set
			int id = -1;
			while(rs.next())
			{
				//Retrieve by column name
				id  = rs.getInt("userID");
			}
			rs.close();
			
			return id;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return -1;
		}
	} 
	
	/**
	 * Get the name and ID of a user who shares the login credentials given
	 */
	public static ArrayList<User> authenticate(int groupID, String username, String pass) 
	{
		try 
		{
			stmt = conn.createStatement(); 
			sql = "SELECT userID, username FROM users WHERE groupID='" + groupID + "' AND username='"+ username +
					"'" + pass +"';";
			ResultSet rs = stmt.executeQuery(sql);
			
			// Extract data from result set
			int id = -1;
			while(rs.next())
			{
				//Retrieve by column name
				id  = rs.getInt("userID");
			}
			rs.close();
			
			return id;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return -1;
		}
	} 
	
	
	
	// Messaging  Queries
	public static void addMsg() 
	{
		
	}
	
	public static void updateMsg() 
	{
		
	}
	
	public static void delMsg() 
	{
		
	}
	
	public static void getMsg() 
	{
		
	} 
	
	
	
	// List Queries
	public static void addList() 
	{
	}
	
	public static void updateList() {}
	
	public static void delList() {}
	
	public static void getLists() 
	{
	} 
	
	
	
	// Poll Queries
	public static void addPoll() 
	{
	}
	
	public static void updatePoll() {}
	
	public static void delPoll() {}
	
	public static void getPolls() 
	{
	} 
	
}
