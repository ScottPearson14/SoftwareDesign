package game;

import network.Client;
import utils.DBConnection;
import utils.HWTBAMMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

/**
 * GameLogin class represents login window used to get registration/login from users
 * Has members storing submitted user and pass to be retrieved from object after user has submitted them
 */
public class GameLogin extends JFrame {
    /**
     * user String contains submitted username
     * string is set when valid username/password combo is submitted
     * user can be accessed by other classes with getUser method
     */
    private String user;
    /**
     * pass String contains submitted password
     * string is set when valid username/password combo is submitted
     * user can be accessed by other classes with getPass method
     */
    private String pass;
    /**
     * loggedIn boolean represents if user has/hasn't successfully logged in
     * boolean is set to true when valid username/password combo is submitted
     * window is hidden when loggedIn is true
     */
    private boolean loggedIn;
    /**
     * area for user to input their username
     */
    private JTextArea userInputArea;
    /**
     * area for user to input their password
     */
    private JTextArea passInputArea;
    /**
     * area for error text to be displayed to the user
     */
    private JLabel infoText;
    /**
     * connection to database for managing user/pass
     */
    private DBConnection dbConnection;

    private Client client;

    private String loggedInUsername;
    private String loggedInPassword;

    /**
     * GameLogin default constructor
     * sets up GUI elements
     */
    public GameLogin(Client client){
        this.client = client;
        loggedIn = false;

        //Setup frame basics
        setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(2,2,5,5));
        JLabel userLabel = new JLabel("Username: ");
        JLabel passLabel = new JLabel("Password: ");
        userInputArea = new JTextArea();
        passInputArea = new JTextArea();

        inputPanel.add(userLabel);
        inputPanel.add(userInputArea);
        inputPanel.add(passLabel);
        inputPanel.add(passInputArea);

        add(inputPanel, BorderLayout.CENTER);

        JPanel submitPanel = new JPanel(new GridLayout(1,2,0,0));
        JButton submitButton = new JButton("Submit");
        SubmissionHandler submissionHandler = new SubmissionHandler();
        submitButton.addActionListener(submissionHandler);
        infoText = new JLabel();
        infoText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        infoText.setFont(new Font("Serif", Font.PLAIN, 12));
        submitPanel.add(submitButton);
        submitPanel.add(infoText);

        add(submitPanel,BorderLayout.SOUTH);


        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    /**
     * TODO:Implement functionality w/database
     * SubmissionHandler is an ActionListener for the GUI's submission button
     * when button is clickethe user's username and password are read in from the text fields and evaluated
     * If valid user/pass combo is passed, GameLogin's member variables are updated to reflect submitted information
     */
    private class SubmissionHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String submittedUser = userInputArea.getText();
            String submittedPass = passInputArea.getText();
            try {
                dbConnection = new DBConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (!submittedUser.equals("debug")){
                try {

                    //Try to login
                    HWTBAMMessage login = dbConnection.loginUser(submittedUser, submittedPass);
                    if (login.getResult().equals("success")){
                        System.out.println("Successful Login");
                        loggedIn = true;
                        loggedInUsername = submittedUser;
                        loggedInPassword = submittedPass;
                    }

                    //If cant login, try to register
                    HWTBAMMessage register;
                   if(!dbConnection.userExists(submittedUser)){
                       register = dbConnection.registerUser(submittedUser, submittedPass);
                       if (register.getResult().equals("success")){
                           System.out.println("Successful Login");
                           loggedIn = true;
                           loggedInUsername = submittedUser;
                           loggedInPassword = submittedPass;
                       }
                       infoText.setText(register.getMessage());
                   }
                   else{
                       infoText.setText(login.getMessage()); //if neither work, wrong password is entered
                   }




/*
                    dbConnection.registerUser(submittedUser, submittedPass);
                    if (login.getResult() == "success"){
                        loggedIn = true;
                    }

 */



                    /*
                    if (login.getResult().equals("failure")) {
                        System.out.println("Recognized failure");
                        if (!dbConnection.userExists(submittedUser)) {  // if user doesn't exist
                            dbConnection.registerUser(submittedUser, submittedPass);
                            loggedIn = true;
                        } else { // if wrong password
                            System.out.println("Failed to register");
                        }
                    }

                     */
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            if (submittedUser.equals("debug") && submittedPass.equals("debug")){
                user = "debug";
                pass = "debug";
                loggedIn = true;
                loggedInUsername = "debug";
                loggedInPassword = "password";
            }

            if (loggedIn){
                client.setUsername(loggedInUsername);
                setVisible(false);

                // send a first message to the server
                HWTBAMMessage message = new HWTBAMMessage(loggedInUsername);
                message.setAction("firstMessage");
                message.setMessage("** " + loggedInUsername + " has entered the server.");
                try {
                    client.sendMessage(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * getter method for user member
     * @return valid submitted username
     */
            public String getUser () {
                return user;
            }
            /**
             * getter method for pass member
             * @return valid submitted password
             */
            public String getPass () {
                return pass;
            }

            /**
             * getter method for loggedIn boolean
             * @return true if user has logged in, false otherwise
             */
            public Boolean isLoggedIn () {
                return loggedIn;
            }
            public String getLoggedInUsername(){
                return loggedInUsername;
            }
        }