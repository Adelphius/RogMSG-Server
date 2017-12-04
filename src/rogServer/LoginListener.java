package rogServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class LoginListener implements Runnable {
	
	private int _listenPort;
	
	public LoginListener(int port)
	{
		_listenPort = port;
	}
	
	

	@Override
	public void run() {
		
		try {
			ServerSocket loginListener = new ServerSocket(_listenPort);
			Socket socket = loginListener.accept();
			
	        BufferedReader input =
	            new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        String username = input.readLine();
	        String password = input.readLine();
	        
	        //put stuff here to check if the user is valid. if so, send the user object for that user. (need serverlogic.
	        
	        if(username.equals("test")&&password.equals("pass")) //in future check if user exists and password is valid for user.
	        {
	        	PrintWriter responce = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
	        	responce.println("authenticated");
	        	int newPort = 1024;
	        	responce.println(newPort);
	        	
	        	User testUser = new User();
	        	testUser.setEmail("testemail@email.com");
	        	testUser.setIDNo(1);
	        	testUser.setName("test name");
	        	
	        	ObjectOutputStream outToClient = new ObjectOutputStream(socket.getOutputStream());
	        	
	        	outToClient.writeObject(testUser); 
	        	
	        	
	        	TcpServer.AddUser(testUser, newPort);
	        	
	        }else {
	        	
	        }
	        
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
