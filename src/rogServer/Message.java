package rogServer;

import java.io.Serializable;

public class Message implements Serializable
{
	/**
	 * Notice this class implements Serializable. 
	 * This allows use to serialize an object into 
	 * a file to be sent over the socket.
	 * 
	 * The serialVersionUID is an ID that Serializable will use to deserialize the object.
	 * We may want to change the UID from 2 to something else. I am unsure if it matters. 
	 */
	private static final long serialVersionUID = 2L;

	private String _stringMsg;
	private String _imageLoc;
	private String _audioLoc;


	/**
	 * gets the string in the message
	 * @returns the string in the message
	 */
	public String getStringMsg()
	{
		String copy = _stringMsg;
		return copy;
	}

	/**
	 * sets the data for a text message to the given string
	 * @param stringLoc string that is being passed, non-null
	 */
	public void setStringMsg(String stringMsg)
	{
		this._stringMsg = stringMsg;
	}

	/**
	 * gets the image file location to the given string
	 * @return a string of the image file location, can be null, can be empty
	 */
	public String getImageLoc()
	{
		String copy = _imageLoc;
		return copy;
	}

	/**
	 * sets the image file location to the given string
	 * @param imageLoc is the desired location of the image, must be >0
	 */
	public void setImageLoc(String imageLoc)
	{
		this._imageLoc = imageLoc;
	}

	/**
	 * gets the audio file location to the given string
	 * @return a string of the audio file location, can be null, can be empty
	 */
	public String getAudioLoc()
	{
		String copy = _audioLoc;
		return copy;
	}

	/**
	 * sets the audio file location to the given string
	 * @param audioLoc is the desired location of the audio file, must be >0
	 */
	public void setAudioLoc(String audioLoc)
	{
		this._audioLoc = audioLoc;
	}

	/**
	 * 
	 */
	public Message()
	{
		// TODO Auto-generated constructor stub
	}
}
