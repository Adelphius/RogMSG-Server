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
	        String email = input.readLine();
	        String password = input.readLine();
	        
	        
	        
	        User toAuth = ServerLogic.Authenticate(email, password);
	        
	        PrintWriter responce = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
	        
	        if(toAuth!=null) 
	        {
	        	
	        	responce.println("authenticated");
	        	int newPort = 1024; //only 1024 for now will make it dynamic soon
	        	responce.println(newPort);
	        	responce.flush();
	        	
	        	ObjectOutputStream outToClient = new ObjectOutputStream(socket.getOutputStream());
	        	
	        	outToClient.writeObject(toAuth); 
	        	
	        	UserListener ul = new UserListener(toAuth, newPort);
	        	
	        	TcpServer.AddUser(ul);
	        	
	        }else {
	        	responce.println("invalid");
	        }
	        
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
