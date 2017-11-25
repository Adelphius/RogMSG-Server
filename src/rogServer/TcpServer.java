package rogServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class TcpServer {

    public static void main (String args[]) throws IOException{

    	 ServerSocket listener = new ServerSocket(1245);
         try {
             while (true) {
                 Socket socket = listener.accept();
                 try {
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                     	out.println("WORDS");
                 } finally {
                     socket.close();
                 }
             }
         }
         finally {
             listener.close();
         }
        
   
	}
    
}
