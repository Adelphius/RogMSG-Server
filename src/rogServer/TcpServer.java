package rogServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class TcpServer {

	// Array of all connection sockets
    public static ArrayList<Socket> connThreads =new ArrayList<Socket>();
    // Array of online users
    public static ArrayList<String> onlineUsers =new ArrayList<String>();

    final int listenPort = 1023; //listen for initial connections on this port

    public static void main (String args[]) throws IOException{

        
    	
    	
    	
    	//original code commented out while i move code into my implementation.
    	/*try{
            final int port = 1023; // pick a port. Any port the client/server use.
            // instantiate a server object
            ServerSocket server = new ServerSocket(port);
            System.out.println("Waiting for client connection..");

            // iterater for the connThreads
            while(true){
                Socket socket = server.accept();
                connThreads.add(socket);

                 // Check user ip and hostname
                System.out.println("Client connected from:"+socket.getLocalAddress().getHostName());

                AddUserName(socket);

                // The chat return socket. This will return to all users. A Second thread
                // will be required for one on one chat.

                ServerConnection groupMsg = new ServerConnection(socket);
                //Takes input from the the current socket and sends to all the clients continuously
                Thread msgGrp = new Thread(groupMsg);
                msgGrp.start();

            }
        }
        catch(Exception msgGrp){
            System.out.println(msgGrp);
        }*/
    }
    
    //will be added to a thread once all connection is working properly
    public void loginListener()
    {
    	try {
			ServerSocket loginListener = new ServerSocket(listenPort);
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
	        	
	        	User testUser = new User();
	        	testUser.setEmail("testemail@email.com");
	        	testUser.setIDNo(1);
	        	testUser.setName("test name");
	        	
	        	ObjectOutputStream outToClient = new ObjectOutputStream(socket.getOutputStream());
	        	
	        	outToClient.writeObject(testUser); 
	        	
	        	
	        	
	        }else {
	        	
	        }
	        
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
        public static void AddUserName(Socket msgGrp) throws IOException     {
            Scanner input = new Scanner(msgGrp.getInputStream());
            String Username = input.nextLine();
            onlineUsers.add(Username);

        // Updates an informs when user leaves or joins the chat application.
        for (int i = 1; i <= TcpServer.connThreads.size(); i++) {
            Socket tmpsocket = TcpServer.connThreads.get(i -1);
            PrintWriter out = new PrintWriter(tmpsocket.getOutputStream());
            out.println("?#!" + onlineUsers);
            out.flush();
        }
    }
	
}
