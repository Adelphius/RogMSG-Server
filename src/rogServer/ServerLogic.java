package rogServer;

import java.util.ArrayList;
//import rogServer.DBQueries;

/**
 * Library of functions to break apart the given objects and call the necessary queries to the DB. 
 * 
 * @author Sarah F.
 *
 */
public class ServerLogic 
{	
	
	/**
	 * Authentication process to have a user log into their client application. 
	 * <p>
	 * Returns the User object of the authenticated user. 
	 * 
	 * @param email != null
	 * @param pass != null
	 * @return User object of the authenticated user. Returns null if failed login. 
	 */
	static public User Authenticate(String email, String pass) 
	{
		if(email != null && pass != null)
		{
			User user = DBQueries.authentUser(email, pass);
			
			return user;
		}
		else
			return null;
	}
	
	/**
	 * Adds a User to the database.
	 * <p>
	 * Password of the new user is default set to the email of the account.
	 * 
	 * @param username size > 0. Username to assign the new user. 
	 * @param email size > 0. Email of the account. MUST BE UNIQUE. Any repeats will result in returning null. 
	 * @param groupName size > 0. The name of the group to add the User to. 
	 * If the group does not already exist, a new group will be made. 
	 * @returns User object of new user, null if failed.
	 */
	static public User NewUser(String username, String email, String groupName) 
	{
		if(username!=null && email!=null && groupName!=null)
		{
			User newUser = DBQueries.addUser(groupName, username, email);
			return newUser;
		}
		else
			return null;
		
	}
	
	/**
	 * Breaks down the Message into it's components and adds it to the Database.
	 * 
	 * @param sender != null. User the message is coming from. 
	 * @param msg Can be null. The Message object to add to the database.
	 * @param recipients != null. The list of users to send the Message to.
	 * @returns 1 if success, -1 if failed.
	 */
	static public int NewMsg(User sender, Message msg, ArrayList<User> recipients) 
	{
		if(msg!=null && recipients!=null)
		{
			//TODO: implement this
			return -1;
		}
		else
			return -1;
	}
	
	/**
	 * Queries the DB for all pending Messages for the given user.
	 * <p>
	 * Once the messages have been found, they are then deleted from the DB 
	 * before being returned. 
	 * 
	 * @param user The user to find all the Messages for.
	 * @return an array of all the pending Messages for the user. Returns null if no Messages were found.
	 */
	static public ArrayList<Message> GetMsgs(User user) 
	{
		if(user!=null)
		{
			//TODO: implement this
			return null;
		}
		else
			return null;
	}
	
}
