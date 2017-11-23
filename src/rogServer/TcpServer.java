package rogServer;

import java.io.IOException;
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



    public static void main (String args[]) throws IOException{

        try{
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
