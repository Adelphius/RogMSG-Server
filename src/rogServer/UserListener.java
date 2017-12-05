package rogServer;

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

	}

}
