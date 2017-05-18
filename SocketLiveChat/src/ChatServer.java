import java.util.ArrayList;
import java.util.Scanner;
import java.net.*;
import java.io.*;

public class ChatServer {
    //Global Variables
    
    //Array of sockets to clients
    public static ArrayList<Socket> connectionArray = new ArrayList<Socket>();
    //Array of connected User Names
    public static ArrayList<String> connectedUsers = new ArrayList<String>();
    
    public static void main(String[] args) throws IOException {
        try {
            final int port = 9000;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Waiting for Clients to Connect...");
            
            while(true) {
                // waiting for clients to connect
                Socket sock = serverSocket.accept();
                System.out.println("Connected");
                connectionArray.add(sock); 
                
                System.out.println("Client connected from: " + sock.getLocalAddress().getHostName());
                
                addUserName(sock);
                ChatServerR chat = new ChatServerR(sock);
                Thread start = new Thread(chat);
                start.start();
            } // end of while
        }   catch (Exception e) {
                    System.out.println(e);
        } // end of try-catch
    }
    
    public static void addUserName(Socket socket) throws IOException {
        // client sends name to server
        Scanner nameInput = new Scanner(socket.getInputStream());
        String name = nameInput.nextLine();
        connectedUsers.add(name);
        
        for(int i=0; i<connectedUsers.size(); i++) {
            Socket tempSocket = connectionArray.get(i);
            PrintWriter output = new PrintWriter(tempSocket.getOutputStream());
            output.println("$#@!" + connectedUsers);
            output.flush();
        }
    }
}
    

