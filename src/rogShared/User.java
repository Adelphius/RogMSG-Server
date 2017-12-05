package rogShared;

import java.io.Serializable;

public class User implements Serializable
{
	/**
	 * Notice this class implements Serializable. 
	 * This allows use to serialize an object into 
	 * a file to be sent over the socket.
	 * 
	 * The serialVersionUID is an ID that Serializable will use to deserialize the object.
	 * We may want to change the UID from 1 to something else. I am unsure if it matters. 
	 */
	private static final long serialVersionUID = 1L;
	private String _name;
	private String _email;
	private int _idNo;

	/**
	 * Makes a basic user
	 * @param name, name.Trim().length() > 0
	 * @param email, email.Trim().length() > 0
	 * @param idNo, > 0
	 */
	public User(String name, String email, int idNo)
	{
		if (name.trim().length() > 0 && email.trim().length() > 0 && idNo > 0)
		{
			_name = name;
			_email = email;
			_idNo = idNo;
		}
	}

	/**
	 * which returns the name of the user object
	 * @return a name, a string
	 */
	public String getName()
	{
		String copyOfName = this._name;
		return copyOfName;
	}

	/**
	 * sets the name of the user object
	 * @param _name, must be null, must have length > 0
	 */
	public void setName(String name)
	{
		if (name != null) 
		{
			this._name = name;
		}
	}

	/**
	 * gets a string of the email
	 * @return the email for the user object, can be null
	 */
	public String getEmail()
	{
		String copyOfEmail = this._email;
		return copyOfEmail;
	}

	/**
	 * sets an email for the user object
	 * @param _email, non-null
	 */
	public void setEmail(String email)
	{
		if (email != null) 
		{
			this._email = email;
		}	
	}

	/**
	 * gets the id number in the form of an int
	 * @return the id number, must be int, must be > 0
	 */
	public int getIDNo()
	{
		int IDCopy = this._idNo;
		return IDCopy;
	}

	/**
	 * sets the id number for the user object
	 * @param _IDno, must be int, must be >0
	 */
	public void setIDNo(int iDno)
	{
		if (iDno > 0)
		{
			this._idNo = iDno;
		}
	}

	/**
	 * 
	 */
	public User()
	{
		// TODO Auto-generated constructor stub
	}
}