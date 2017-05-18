/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author marksim
 */
public class ChatServerR implements Runnable {
    
    //Global Variables
    //socket declared global so it doesn't close when socket goes out of scope
    public static Socket socket;
    public static String message = "";
    
    private Scanner input;
    private PrintWriter output;
    
    public ChatServerR(Socket socket) {
        this.socket = socket;
        
    }
    
    public void run() {
        try {
            try {
                
                output = new PrintWriter(socket.getOutputStream());
                input = new Scanner(socket.getInputStream());
                
                while(true) {
                    // check if the client disconnected
                    checkConnect();
                    
                    if(!input.hasNext()) {
                       return;
                    }
                    
                    message = input.nextLine();
                    
                    
                    
                 
                    System.out.println("client said: " + message );
                    
                    for(int i=0; i<ChatServer.connectionArray.size(); i++) {
                        Socket tempSocket = ChatServer.connectionArray.get(i);
                        PrintWriter output = new PrintWriter(tempSocket.getOutputStream());
                        output.println(message);
                        output.flush();
                        System.out.println("Sent to:" + tempSocket.getLocalAddress().getHostName());
                    }
                    
                }
            } finally {
                System.out.println("Socket CLOSED");
                socket.close();
            }
        } catch (Exception e ) {
            System.out.println(e);
        }
    }
    
    public void checkConnect() throws IOException {
        if (!socket.isConnected()) {
            for (int i=0; i<ChatServer.connectionArray.size(); i++) {
                if(ChatServer.connectionArray.get(i) == socket) {
                    ChatServer.connectionArray.remove(i);
                }
            }
            
            for (int i=0; i<ChatServer.connectionArray.size(); i++) {
                Socket tempSocket = ChatServer.connectionArray.get(i);
                PrintWriter out = new PrintWriter(tempSocket.getOutputStream());
                out.println(tempSocket.getLocalAddress().getHostName() + " disconnected");
                out.flush();
                
                //shows to server console
                System.out.println(tempSocket.getLocalAddress().getHostName() + " disconnected"); 
            }
        }
        
            
    }
    
}

