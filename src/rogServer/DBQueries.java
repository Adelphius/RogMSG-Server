
package rogServer;

import java.sql.*;
import java.util.ArrayList;


/**
 * Class of database queries for talking to the RogMSG database.
 * <p>
 * NOTE: JDBC Driver mysql-connector-java-5.1.44 must be located in  C:\Program Files
 * 
 * @author Sarah F.
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
	/*
	public static void main (String args[]) throws IOException 
	{
		connectDB();
		test();
		disconnectDB();
	}
	*/
	
	
	
	
	
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
		
		//System.out.println("Connected to : " + DB_URL); // test statement
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
		
		//System.out.println("Disconnected from : " + DB_URL); // test statement
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
			sql = "UPDATE msggroup SET name='"+ name +"' WHERE groupID=" + groupID + ";";
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
			String name = null;
			
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
	 * @returns the new User object.
	 */
	public static User addUser(String groupName, String username, String email) 
	{
		try 
		{
			stmt = conn.createStatement(); 
			sql = "INSERT INTO users (groupID, username, email, password)VALUES ('" 
			+ getGroupID(groupName) + "', '" + username + "', '" + email + "', '" + email +"');"; 
			stmt.executeUpdate(sql);  
			
			int id = getUserID(groupName, username);
			
			return getUser(id, username);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return null;
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
			sql = "SELECT userID FROM users WHERE groupID = '"
					+ getGroupID(groupName) +"' AND username='" + username + "';";
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
	 * @returns ArrayList of all the users in the group. Returns null if no users were found. 
	 */
	public static ArrayList<User> getUsers(int groupID) 
	{
		try 
		{
			stmt = conn.createStatement(); 
			sql = "SELECT * FROM users WHERE groupID='" + groupID + "';";
			ResultSet rs = stmt.executeQuery(sql);
			
			// Extract data from result set
			ArrayList<User> user = new ArrayList<User>();
			String username = null; 
			String email = null;
			int userID = 0;
			
			while(rs.next())
			{
				userID = rs.getInt("userID");
				username = rs.getString("username");
				email = rs.getString("email");
				
				user.add(new User(username, email, userID));
			}
			rs.close();
			
			return user;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return null;
		}
	} 
	
	/**
	 * Get a users username and userID.
	 * 
	 * @param groupID ID of the group the user is in.
	 * @param username Username of the user to find. 
	 * @returns Specified users ID. Returns -1 if query failed.
	 */
	public static User getUser(int groupID, String username) 
	{
		try 
		{
			stmt = conn.createStatement(); 
			sql = "SELECT * FROM users WHERE groupID='" + groupID + "' AND username='"+ username +"';";
			ResultSet rs = stmt.executeQuery(sql);
			
			// Extract data from result set
			int id = -1;
			String name = null;
			String email = null;
			
			while(rs.next())
			{
				//Retrieve by column name
				id  = rs.getInt("userID");
				name = rs.getString("username");
				email = rs.getString("email");
			}
			rs.close();
			
			User user = new User(name, email, id);
			
			return user;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return null;
		}
	} 
	
	/**
	 * Get a specified user's groupID. 
	 * 
	 * @param userID >0
	 * @return >0. Users groupID, or -1 if failed. 
	 */
	public static int getUserGroupID(int userID)
	{
		try 
		{
			stmt = conn.createStatement(); 
			sql = "SELECT groupID FROM users WHERE userID = '" + userID +"';";
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
	 * Get the name and ID of a user who shares the login credentials given.
	 * 
	 * @param email Email of the user. 
	 * @param pass Password of the account. 
	 * @returns User object. Null if authentication failed. 
	 */
	public static User authentUser(String email, String pass) 
	{
		try 
		{
			stmt = conn.createStatement(); 
			sql = "SELECT userID, username FROM users WHERE email='"+ email +
					"' AND password='" + pass +"';";
			ResultSet rs = stmt.executeQuery(sql);
			User user = null;
			
			while(rs.next())
			{
				int userID = rs.getInt("userID");
				String username = rs.getString("username");
				
				user = new User(username, email, userID);
			}
			rs.close();
			
			return user;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return null;
		}
	} 
	
	
	
	// Messaging  Queries
	/**
	 * Adding a message to the DB to be sent out to recipients.
	 * 
	 * @param recipient != null.
	 * @param msg can be null.
	 * @param image can be null.
	 * @param audio can be null.
	 * @return 1 if successful, -1 if failed.
	 */
	public static int addMsg(User recipient, String msg, String image, String audio) 
	{
		try 
		{
			int recipID = recipient.getIDNo();
			int groupID = getUserGroupID(recipID);
			
			stmt = conn.createStatement(); 
			sql = "INSERT INTO messages (groupID, msg, imageLoc, audioLoc) "
					+ "VALUES ('" + groupID + "', '" + msg + "', '" + image + "', '" + audio + "');";
			stmt.executeUpdate(sql);
			
			//get the id of the new msg created, and add it to messages_users.
			stmt = conn.createStatement(); 
			
			sql = "SELECT msgID FROM messages WHERE "
					+ "groupID='"+ groupID +"' AND msg = '" + msg + "';";
			ResultSet rs = stmt.executeQuery(sql);
			int msgID = 0;
			
			while(rs.next())
			{
				msgID = rs.getInt("msgID");
			}
			rs.close();
			
			// add to n:m relationship table. 
			stmt = conn.createStatement(); 
			sql = "INSERT INTO messages_users (Messages_msgID, user_userID) "
					+ "VALUES ('"+ msgID +"', '"+ recipID +"');";
			stmt.executeUpdate(sql);
			
			return 1;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * Returns any Messages that the specified userID may have.
	 * 
	 * @param userID The ID of the user you want messages for.
	 * @return List of all the Msg's that were found. Returns null if none were found. 
	 */
	public static ArrayList<Message> getMsg(int userID) 
	{
		try 
		{
			stmt = conn.createStatement(); 
			sql = "SELECT messages.msg, messages.imageLoc, messages.audioLoc FROM messages LEFT JOIN messages_users "
					+ "ON messages.msgID = messages_users.Messages_msgID WHERE messages_users.user_userID = '"+userID+"';";
			ResultSet rs = stmt.executeQuery(sql);
			
			// Extract data from result set
			ArrayList<Message> msg = new ArrayList<Message>();
			String strMsg = null; 
			String imgLoc = null; 
			String audLoc = null; 
			
			while(rs.next())
			{
				strMsg = rs.getString("msg");
				imgLoc = rs.getString("imageLoc");
				audLoc = rs.getString("audioLoc");
				
				msg.add(new Message(strMsg, imgLoc, audLoc));
			}
			rs.close();
			
			return msg;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return null;
		}
	} 
	
	/**
	 * Delete messages for a specific user. 
	 * <p>
	 * So far, this only deletes messages from the Messages_Users table. 
	 * 
	 * @param userID The ID of the user to delete messages for. 
	 * @return Exit status. 1 if successful, -1 if error.
	 */
	public static int delMsg(int userID) 
	{
		// first, delete from n:m relationship messages_users table
		
		// TODO: then delete from messages table
		
		try 
		{
			stmt = conn.createStatement(); 
			
			sql = "SELECT Messages_msgID FROM messages_users WHERE user_userID='"+ userID +"';";
			ResultSet rs = stmt.executeQuery(sql);
			int msgID = 0;
			
			while(rs.next())
			{
				msgID = rs.getInt("Messages_msgID");
			}
			rs.close();
			
			stmt = conn.createStatement(); 
			sql = "DELETE FROM messages_users WHERE user_userID ='" +userID+ "' "
					+ "AND Messages_msgID= '" + msgID + "';";
			stmt.executeUpdate(sql);
			
			/*
			stmt = conn.createStatement(); 
			// Test if there are anymore recipients of that message. If not, then perminently delete it.
			sql = "SELECT msgID FROM messages_users WHERE userID='"+ userID +"';";
			rs = stmt.executeQuery(sql);
			
			if(!rs.next())
			{
				stmt = conn.createStatement(); 
				sql = "DELETE FROM messages WHERE msgID ='" +msgID+ "';";
				stmt.executeUpdate(sql);
			}
			*/
			
			return 1;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return -1;
		}
		
	}
	
	
	
	
	// List Queries
	public static void addList() {}
	
	public static void updateList() {}
	
	public static void delList() {}
	
	public static void getLists() {} 
	
	
	
	// Poll Queries
	public static void addPoll() {}
	
	public static void updatePoll() {}
	
	public static void delPoll() {}
	
	public static void getPolls() {} 
	
	
	/*
	private static void test()
	{
		// Group & users finished
		
		int groupID = 1;
		int userID1 = 1;
		int userID2 = 2; 
		
		User user1 = getUser(groupID, "Sarah");
		User user2 = getUser(groupID, "Jake");
		
		int r = addMsg(user1, "This is a message.", "", "");
		int rs = addMsg(user2, "This is another message.","","");
		
		System.out.println("Message 1 return: " + r);
		System.out.println("Message 2 return: " + rs);
		
		ArrayList<Message> msgs = new ArrayList<Message>();
		msgs = getMsg(userID2);
		
		delMsg(userID1);
		
	}
	*/
	
}
