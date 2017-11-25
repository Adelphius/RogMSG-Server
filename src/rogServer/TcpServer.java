package rogServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class TcpServer {

    public static void main (String args[]) throws IOException{
    	System.out.println("starting server");
    	 ServerSocket listener = new ServerSocket(1245);
    	 System.out.println("server started on port 1245");
         try {
             while (true) {
                 Socket socket = listener.accept();
                 System.out.println("accepted connection");
                 try {
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                     	out.println("WORDS");
                     	System.out.println("sending string");
                     	
                 } finally {
                     socket.close();
                     System.out.println("closing socket");
                 }
             }
         }
         finally {
             listener.close();
         }
        
   
	}
    
}
