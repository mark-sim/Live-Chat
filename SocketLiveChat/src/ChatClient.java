/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.net.*;
import java.util.Scanner;
import javax.swing.JOptionPane;
import java.util.ArrayList;

/**
 *
 * @author marksim
 */
public class ChatClient implements Runnable {
    
    private Socket socket;
    private Scanner input;
    private Scanner reader = new Scanner(System.in);
    private PrintWriter output;
    
    public ChatClient(Socket socket) {
        this.socket = socket;
        
    }
    
    public void run() {
        
         try {
             try {
                 
                 output = new PrintWriter(socket.getOutputStream());
                 input = new Scanner(socket.getInputStream());
                 output.flush();
                 checkStream();
             }
             finally {
                 socket.close();
             }
         } catch (Exception e) {}
    }
    
   public void checkStream() {
       while(true) {
           receive();
       }
   }
   
   public void receive() {
       System.out.println("a");
       if(input.hasNext()) {
           
           String message = input.nextLine();
           if(message.contains("$#@!")) {
               String temp = message.substring(4);
               temp = temp.replace("[", "");
               temp = temp.replace("]", "");
               String[] currentUsers = temp.split(", ");
               ChatClientGUI.onlineList.setListData(currentUsers);
           } else {
               ChatClientGUI.taConversation.append(message + "\n");
           }
       }
   }
   
   public void send(String message) {
       output.println(ChatClientGUI.userName + ":" + message);
       output.flush();
       ChatClientGUI.textField.setText("");
   }
   
   public void disconnect() throws IOException {
       
       output.println(ChatClientGUI.userName + " has disconnected.");
       output.flush();
       socket.close();
       JOptionPane.showMessageDialog(null, "Disconnected");
       System.exit(0);
   }
   
   
    
    
    
}
