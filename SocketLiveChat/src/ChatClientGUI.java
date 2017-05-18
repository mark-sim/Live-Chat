
import java.awt.Color;
import javax.swing.*;
import java.net.*;
import java.io.*;


public class ChatClientGUI {
    
    
    public static String userName = "Offline User";
    public static ChatClient chatclient;
    
    // Main Window Components
    public static JFrame mainWindow = new JFrame();
    private static JButton aboutButton = new JButton();
    private static JButton connectButton = new JButton();
    private static JButton disconnectButton = new JButton();
    private static JButton helpButton = new JButton();
    private static JButton sendButton = new JButton();
    private static JLabel messageBox = new JLabel("Message:");
    public static JTextField textField = new JTextField(40);
    private static JLabel conversation = new JLabel();
    public static JTextArea taConversation = new JTextArea();
    private static JScrollPane spConversation = new JScrollPane();
    private static JLabel onlineLabel = new JLabel();
    public static JList onlineList = new JList();
    private static JScrollPane spOnline = new JScrollPane();
    private static JLabel loggedInAs = new JLabel();
    private static JLabel loggedInAsBox = new JLabel();
    
    //Login Window Components
    public static JFrame loginWindow = new JFrame();
    public static JTextField userNameTF = new JTextField(20);
    private static JButton enterUN = new JButton("Enter");
    private static JLabel enterUNL = new JLabel("Enter Username:");
    private static JPanel plogin = new JPanel();
    
    public static void main(String args[]) {
        buildMainWindow();
        initialize();
    }
    
    public static void buildLoginWindow() {
        loginWindow.setTitle("Log In");
        loginWindow.setSize(300,130);
        loginWindow.setLocation(500,300);
        loginWindow.setResizable(false);
        loginWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        plogin.add(enterUNL);
        plogin.add(userNameTF);
        plogin.add(enterUN);
        loginWindow.add(plogin);
        
        loginAction();
        loginWindow.setVisible(true);
    }
    
    
    public static void buildMainWindow() {
        mainWindow.setTitle(userName + "'s Chat Box");
        mainWindow.setSize(500,500);
        mainWindow.setLocation(400,180);
        configure();
        mainWindowAction();
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setVisible(true);
    }
    
    public static void configure() {
        mainWindow.getContentPane().setLayout(null);
        
        //send button
        sendButton.setText("Send");
        mainWindow.getContentPane().add(sendButton);
        sendButton.setBounds(420, 445, 80, 25);
        
        //disconnect button
        disconnectButton.setText("Disconnect");
        mainWindow.getContentPane().add(disconnectButton);
        disconnectButton.setBounds(135,10,130,50);
        
        //connect button 
        connectButton.setText("Connect");
        mainWindow.getContentPane().add(connectButton);
        connectButton.setBounds(0,10,130,50);
        
        //help button
        helpButton.setText("Help");
        mainWindow.getContentPane().add(helpButton);
        helpButton.setBounds(270,10,80,25);
        
        //about button
        aboutButton.setText("About");
        mainWindow.getContentPane().add(aboutButton);
        aboutButton.setBounds(270,36,80,25);
        
        //message box
        mainWindow.getContentPane().add(messageBox);
        messageBox.setBounds(5,443,80,25);
        
        //text field
        textField.setForeground(Color.red);
        textField.requestFocus();
        mainWindow.getContentPane().add(textField);
        textField.setBounds(68,442,355,30);
        
        //conversation
        conversation.setText("Conversation");
        mainWindow.getContentPane().add(conversation);
        conversation.setBounds(13,54,355,30);
        
        taConversation.setColumns(20);
        taConversation.setLineWrap(true);
        taConversation.setRows(5);
        taConversation.setEditable(true);
        
        
        spConversation.setHorizontalScrollBarPolicy(
                                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spConversation.setVerticalScrollBarPolicy(
                                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        spConversation.setViewportView(taConversation);
        mainWindow.getContentPane().add(spConversation);
        spConversation.setBounds(10,80,355,355);
        
        onlineLabel.setText("Currently Online");
        onlineLabel.setToolTipText("Number of Users Online");
        mainWindow.getContentPane().add(onlineLabel);
        onlineLabel.setBounds(380, 55, 200, 30);

        
        spOnline.setHorizontalScrollBarPolicy(
                                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spOnline.setVerticalScrollBarPolicy(
                                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        spOnline.setViewportView(onlineList);
        mainWindow.getContentPane().add(spOnline);
        spOnline.setBounds(375,80,120,355);
        
        loggedInAs.setText("Username");
        mainWindow.getContentPane().add(loggedInAs);
        loggedInAs.setBounds(400,5,80,30);
        
        loggedInAsBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        loggedInAsBox.setHorizontalAlignment(SwingConstants.CENTER);
        mainWindow.getContentPane().add(loggedInAsBox);
        loggedInAsBox.setBounds(375,32,120,20);
    }
    
    public static void initialize() {
        sendButton.setEnabled(false);
        connectButton.setEnabled(true);
        disconnectButton.setEnabled(false);
    }
    
    public static void connect() {
        try {
            final int port = 9000;
            final String host = "localhost";
            System.out.println("Starting connection to " + host);
            Socket socket = new Socket(host, port);
            System.out.println("You connected to: " + host);
            
            chatclient = new ChatClient(socket);
            
            PrintWriter output = new PrintWriter(socket.getOutputStream());
            output.println(userName);
            output.flush();
            
            Thread start = new Thread(chatclient);
            start.start();
            
            
        } catch(Exception e) {
            e.getMessage();
        }
    }
    
    //Event Handlers
    
    public static void mainWindowAction() {
        sendButton.addActionListener(
                new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent event) {
                        sendAction();
                    }
                  }
        );
        
        disconnectButton.addActionListener(
                new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent event) {
                        disconnectAction();
                    }
                  }
        );
        
        helpButton.addActionListener(
                new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent event) {
                        helpAction();
                    }
                  }
        );
        
        aboutButton.addActionListener(
                new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent event) {
                        aboutAction();
                    }
                  }
        );
        
        connectButton.addActionListener(
                new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent event) {
                        buildLoginWindow();
                    }
                  }
        );
        
        
        
    }
    
    public static void loginAction() {
        enterUN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enterAction();
            }
        });
    }
    
    public static void enterAction() {
        if(!userNameTF.getText().isEmpty()) {
            userName = userNameTF.getText().trim();
            loggedInAsBox.setText(userName);
            ChatServer.connectedUsers.add(userName);
            mainWindow.setTitle(userName + "'s Chat Box");
            loginWindow.setVisible(false);
            sendButton.setEnabled(true);
            connectButton.setEnabled(false);
            disconnectButton.setEnabled(true);
            connect();
        } else {
            JOptionPane.showMessageDialog(null, "Enter a Username");
        }
    }
    
    public static void sendAction() {
        if(!textField.getText().isEmpty()) {
            chatclient.send(textField.getText());
            textField.requestFocus();
        }
    }
    
    public static void disconnectAction() {
        try {
          chatclient.disconnect();
        } catch(Exception e) {}
    }
    
    public static void helpAction() {
        JOptionPane.showMessageDialog(null, "Contact shsim@uwaterloo.ca");
    }
    
    public static void aboutAction() {
        JOptionPane.showMessageDialog(null, "Made by Mark Sim\nA Live Chat Program");
    }
    
    
    
    
}
