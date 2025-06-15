package game;

import network.Client;
import utils.HWTBAMMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameChat extends JFrame implements Runnable {
    private Client client;
    private JTextArea chatLog;
    private JTextField inputField;
    private JButton sendButton;
    private JScrollPane scrollPane;

    public GameChat(Client client){
        this.client = client;
        setTitle("Chat Room - " + client.getUsername());
        setSize(400, 400);
        setLayout(new BorderLayout());
        chatLog = new JTextArea();

        // wrap in scrollpane for scrolling
        scrollPane = new JScrollPane(chatLog);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        String welcomeMessage = ">>> SERVER\n>>> Welcome to the game chat!\n>>> to DM a specific user\n>>>\n>>>use the following format:\n>>>      '@username::your message'\n\n";

        chatLog.append(welcomeMessage);
        chatLog.setEnabled(false);
        chatLog.setDisabledTextColor(Color.BLACK);
        chatLog.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel inputPanel = new JPanel(new GridLayout(1,2,5,0));
        inputField = new JTextField();
        inputPanel.add(inputField);
        sendButton = new JButton("Send Message");
        SendHandler sendHandler = new SendHandler();
        sendButton.addActionListener(sendHandler);
        inputPanel.add(sendButton);

        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
    }

    public void logMessage() {

    }

    @Override
    public void run() {
        try {
            client.setGameChat(this);

            HWTBAMMessage message;
            while (true) {
                try {
                    if (((message = (HWTBAMMessage) client.getInput().readObject()) != null)){
                        System.out.println("\n\n -- Message --");
                        System.out.println(message + "-- \n\n");

                        if ((message.getAction().equals("gameChatRECEIVE")) && (message.getAction() != null) && (message.getSendingUser() != null)) {
                            if (!(message.isBroadcast())) {
                                if (!(message.getSendingUser().equals(client.getUsername()))) {
                                    chatLog.append("\n" + "(DM from " + message.getSendingUser() + ")\n" + message.getMessage() + "\n");
                                }
                            } else {
                                if (!(message.getSendingUser().equals(client.getUsername()))) {
                                    chatLog.append("\n" + "(" + message.getSendingUser() + " -> ALL)\n" + message.getMessage() + "\n");
                                }
                            }
                        }
                    }
                }
                catch (NullPointerException e) {
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private class SendHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String currentMessage = inputField.getText();
            inputField.setText(""); // remove text

            JTextPane yourMessage = new JTextPane();
            yourMessage.setText(currentMessage);

            // outputs
            HWTBAMMessage message = new HWTBAMMessage(client.getUsername());
            String outputMessage;
            message.setAction("gameChatSEND");
            message.setSendingUser(client.getUsername());

            // parse for user to send to
            String toUser = "ALL";

            if (currentMessage != null) {
                try {
                    if (currentMessage.contains("@")) {
                        String[] split1 = currentMessage.split("@");
                        if (split1[1].contains("::")) {
                            String[] split2 = split1[1].split("::");
                            toUser = split2[0];
                            outputMessage = split2[1];
                            message.setReceivingUser(toUser);
                        } else {
                            outputMessage = currentMessage;
                            message.setReceivingUser("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"); //invalid
                        }
                    } else {
                        outputMessage = currentMessage;
                    }
                } catch (Exception e) {
                    toUser = "All";
                    outputMessage = currentMessage;
                    message.setBroadcast(true);
                }

                chatLog.append("\n(you -> " + toUser + ")\n" + currentMessage + "\n");
                message.setMessage(outputMessage);

                try {
                    client.sendMessage(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}