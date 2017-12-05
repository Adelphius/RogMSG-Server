package rogServer;

import java.util.ArrayList;

/**
 * Library of functions to break apart the given objects and call the necessary queries to the DB. 
 * 
 * @author Sarah F.
 *
 */
public class ServerLogic 
{	
	//TODO: each of these should call connect() and disconnect() from DBQueries 
	/**
	 * Authentication process to have a user log into their client application. 
	 * <p>
	 * Returns the User object of the authenticated user. 
	 * 
	 * @param email != null
	 * @param pass != null
	 * @return User object of the authenticated user. Returns null if failed login. 
	 */
	static public User authenticate(String email, String pass) 
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
	static public User newUser(String username, String email, String groupName) 
	{
		//TODO: if the group does not exist, make a new one. 
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
	 * @param sender != null.
	 * @param msg != null. 
	 * @param recipients !isEmpty. 
	 * @returns 1 if success, -1 if failed.
	 */
	static public int newMsg(User sender, Message msg, ArrayList<User> recipients) 
	{
		if(sender!=null && !recipients.isEmpty() && msg!=null)
		{
			int i = 0;
			int r = -1;
			
			while(i < recipients.size())
			{
				String text = msg.getStringMsg();
				String image = msg.getImageLoc();
				String audio = msg.getAudioLoc();
				
				r = DBQueries.addMsg(recipients.get(i), text, image, audio);
				i++;
			}
			return r;
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
	 * @param user !=null
	 * @returns Array of all the pending Messages for the user. Returns null if no Messages were found.
	 */
	static public ArrayList<Message> getMsgs(User user) 
	{
		if(user!=null)
		{
			//retrieve msgs
			ArrayList<Message> pending = DBQueries.getMsg(user.getIDNo());
			
			// delete msgs
			DBQueries.delMsg(user.getIDNo());
			
			return pending;
		}
		else
			return null;
	}
	
}
