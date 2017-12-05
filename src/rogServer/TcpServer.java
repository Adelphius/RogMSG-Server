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
    //public static ArrayList<UserListener> connThreads =new ArrayList<UserListener>();
	public static ArrayList<Thread> connThreads =new ArrayList<Thread>();
    // Array of online users
    public static ArrayList<String> onlineUsers =new ArrayList<String>();


    public static void main (String args[]) throws IOException{
    	
    	LoginListener ll = new LoginListener(1023);
    	Thread listenerThread = new Thread(ll);
    	listenerThread.start();
    	
    	
    }
    
    public static void AddUser(UserListener newUser)
    {
    	Thread userThread = new Thread(newUser);
    	userThread.start();
    	connThreads.add(userThread); //not sure about this stuff. needs testing
    	
    	
    }
    
    
    
        /*public static void AddUserName(Socket msgGrp) throws IOException     {
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
    }*/
	
}
