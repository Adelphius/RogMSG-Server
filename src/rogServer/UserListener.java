package rogServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import rogShared.Message;
import rogShared.User;

public class UserListener implements Runnable {
	
	private User _connectedUser;
	private int _port;
	
	public UserListener(User user, int port)
	{
		_connectedUser = user;
		_port = port;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			try {
				ServerSocket userListener = new ServerSocket(_port);
				Socket socket = userListener.accept();
				
				ObjectInputStream fromClient = new ObjectInputStream(socket.getInputStream());
				
				Message msg = (rogShared.Message)fromClient.readObject();
				
				//ServerLogic.newMsg(, msg, recipients);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
