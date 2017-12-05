package rogServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import rogShared.User;

public class LoginListener implements Runnable {
	
	private int _listenPort;
	
	public LoginListener(int port)
	{
		_listenPort = port;
	}
	
	

	@Override
	public void run() {
		while(true)
		{
			try {
				
				
				System.out.println("Creating socket");
				ServerSocket loginListener = new ServerSocket(_listenPort);
				System.out.println("Socket Created. Waiting for connection.");
				Socket socket = loginListener.accept();
				
				System.out.println("Socket connected.");
		        BufferedReader input =
		            new BufferedReader(new InputStreamReader(socket.getInputStream()));
		        
		        String authType = input.readLine();
		        
		        if(authType.equals("login"))
		        {
			        System.out.println("Waiting for email.");
			        String email = input.readLine();
			        System.out.println("Waiting for password.");
			        String password = input.readLine();
			        
			        
			        System.out.println("Authenticating.");
			        User toAuth = ServerLogic.authenticate(email, password);
			        
			        PrintWriter responce = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			        
			        if(toAuth!=null) 
			        {
				        System.out.println("Authenticated.");
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
			        	System.out.println("invalid");
			        }
		        
		        }else if(authType.equals("register"))
		        {
		        	System.out.println("Waiting for email.");
			        String email = input.readLine();
			        System.out.println("Waiting for username.");
			        String name = input.readLine();
			        System.out.println("Waiting for password.");
			        String password = input.readLine();
			        
			        User toAuth = ServerLogic.newUser(name, email, "nogroup");
			        
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
			        	
			        }
		        }
		        socket.close();
		        loginListener.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

	}

}