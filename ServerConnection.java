// ServerConnection class
// Checks to see if users are connected or not.

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;



public class ServerConnection implements Runnable {

    private Socket socket;

    public ServerConnection(Socket msgGrp){

        this.socket = msgGrp;

    }

    private void getUserStatus() throws IOException {
        // Iterates when user is not connected. This gets called in run().
        if(!socket.isConnected()) {

            for (int i = 1; i <= TcpServer.connThreads.size(); i++) {

                if (TcpServer.connThreads.get(i) == socket){
                    TcpServer.connThreads.remove(i);
                }

            }

            for (int i = 1; i <= TcpServer.connThreads.size(); i++) {
                Socket tempSocket = TcpServer.connThreads.get(i-1);
                PrintWriter tempOut = new PrintWriter(tempSocket.getOutputStream());
                tempOut.println(tempSocket.getLocalAddress().getHostName() + " disconnected!");
                tempOut.flush();
                // Show the disconnection.
                System.out.println(tempSocket.getLocalAddress().getHostName() + " disconnected!");

            }
        }

    }

    public void run() {
        try {
            try {
                Scanner input = new Scanner(socket.getInputStream());

                /*
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                Not even used.... The output stream is used in object tempOut.
                */

                while(true){
                    getUserStatus();

                    if(!input.hasNext()) {
                        return;
                    }
                    String msg = input.nextLine();
                    System.out.println("Client Said: " + msg);

                    for (int i = 1; i < TcpServer.connThreads.size(); i++){

                        Socket tempSocket = TcpServer.connThreads.get(i-1);
                        PrintWriter tempOut = new PrintWriter(tempSocket.getOutputStream());
                        tempOut.println(msg);
                        tempOut.flush();
                        System.out.println("Sent to: " + tempSocket.getLocalAddress().getHostName());
                    }
                }
            }
            finally {
                socket.close();
            }
        }
        catch (Exception msgGrp){
            System.out.print(msgGrp);
        }
    }
}
