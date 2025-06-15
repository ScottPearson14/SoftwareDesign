package game;

import network.Client;
import utils.DBConnection;
import utils.HWTBAMMessage;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * The Game class is a class that represents our team's version of Who Wants to Be a Millionaire. The class creates the
 * GUI for the game
 *
 */
public class Game extends JFrame {
    /** Client that represents a user that will play the game */
    private Client client;
    /** String that represents the username of the player */
    private String username;
    /** JButton that for guess button 1 */
    private JButton guessButton1;
    /** JButton that for guess button 2 */
    private JButton guessButton2;
    /** JButton that for guess button 3 */
    private JButton guessButton3;
    /** JButton that for guess button 1 */
    private JButton guessButton4;
    /** JButton that for menu button */
    private JButton menuButton;
    /** JButton that for fiftyFiftyButton */
    private JButton fiftyFiftyButton;
    /** JButton that for ask audience button */
    private JButton askAudienceButton;
    /** JButton that for ask audience button */
    private JButton phoneAFriendButton;
    /** JLabel for the value for a user's current balance */
    private JLabel balanceText;
    /** array of JLabels for the price labels for money earned from completing different questions */
    private JLabel[] priceLabels;
    /** JLabel for the questions/general info for the game */
    private JTextPane displayText;
    /**
     * static, final array of strings for prices of different question levels
     * Declared static since it is shared by all instances of the class and can be accessed w/o creation
     * Declared final since the prices should be immutable and constant
     */
    private static final String priceLevels[] = {"$100", "$200", "$300", "$400", "$500", "$1,000",
            "$2,000","$4,000","$8,000","$16,000","$32,000","$64,000","$125,000","$250,000","$500,000","$1,000,000"};
    /** int for the current level/related question the user is on */
    private int currentLevel;
    /** boolean value for whether user is in menu
     * true if user is in the menu, false otherwise
     */
    private boolean inMenu;
    /**
     * boolean value for whether user is in the shop
     * true if user is in the shop, false otherwise
     */
    private boolean inShop;
    /** boolean value for whether user has fifty/fifty power-up item
     * true if user has power-up, false otherwise
     */
    private boolean hasFiftyFifty;
    /** boolean value for whether user has ask audience power-up item
     * true if user has power-up, false otherwise
     */
    private boolean hasAskAudience;
    /** boolean value for whether user has phone a friend power-up item
     * true if user has power-up, false otherwise
     */
    private boolean hasPhoneFriend;
    /** int that represents the current currency balance of a user */
    private int currencyBalance;
    /**
     * GameLogin member provides GUI for user to submit username/password
     * member is used to access user's username, password, and login state
     */
    private GameLogin gameLogin;
    /** Connection to DataBase */
    private DBConnection dbConnection;
    /** current question message information */
    private HWTBAMMessage currentQuestionMessage;
    /** String of current question */
    private String currentQuestion;
    /** array of Strings for current guesses */
    private String[] currentGuesses =  new String [4];
    /** JTextPane for results posted after answering question/using Ask Audience/Phone Friend power-up */
    private JTextPane resultLabel;
    /** int that represents correct guess counter */
    private int correctGuessCounter;
    /** int that represents the index of the correct guess */
    private int correctGuessIndex;
    /** array of JButtons for guesses */
    private JButton[] guessButtons;
    /** Integer array for indices to help with getting correct/incorrect guesses */
    private Integer[] indices;
    /** chat for users */
    private GameChat chat;
    /** String representation of leaderboard */
    private String leaderboard;
    /** String representation of leaderboard position */
    private String currentLeaderboardPosition;
    /** bool for if leaderboard is open */
    private boolean leaderboardOpen;
    /** bool for if game is over */
    private boolean gameOver;
    /** executor for login Worker */
    private ExecutorService executor;
    /** bool for if Configurations is open */
    private boolean inConfig;
    /** bool for if dark mode is on */
    private boolean darkMode;
    /** displayPanel for question and result display */
    private JPanel displayPanel;
    /** JPanel for menu */
    private JPanel menuPanel;
    /** JPanel for buttons */
    private JPanel buttonPanel;
    /** JPanel for levels */
    private JPanel levelPanel;
    /** JPnael for power-ups */
    private JPanel powerUpPanel;
    /** array of colors for level colors */
    private final Color[] levelColors = {Color.YELLOW, Color.magenta, Color.CYAN,Color.pink,Color.orange};
    /** index for level colors */
    private int levelColorIndex;

    /**
     * Method we use for debugging
     */
    private void debugInfo() {
        System.out.println("currentLevel: " + currentLevel);
        System.out.println("inMenu: " + inMenu);
        System.out.println("inShop: " + inShop);
        System.out.println("hasFiftyFifty: " + hasFiftyFifty);
        System.out.println("hasAskAudience: " + hasAskAudience);
        System.out.println("hasPhoneFriend: " + hasPhoneFriend);
        System.out.println("currencyBalance: " + currencyBalance);

    }

    /**
     * Constructor that initializes the Who Wants To Be a Millionaire Game GUI.
     *
     *
     */
    public Game(Client client){
        // username of player
        this.client = client;

        levelColorIndex = 0;

        try {
            dbConnection = new DBConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Setup frame basics
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Set up panel for guess buttons
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2,2,5,5));

        //set up each guess button w/handler, add to panel
        guessButton1 = new JButton();
        Guess1Handler guess1Handler = new Guess1Handler();
        guessButton1.addActionListener(guess1Handler);
        //guessButton1.setBackground(Color.BLUE);
        buttonPanel.add(guessButton1);

        guessButton2 = new JButton();
        Guess2Handler guess2Handler = new Guess2Handler();
        guessButton2.addActionListener(guess2Handler);
        //guessButton2.setBackground(Color.BLUE);
        buttonPanel.add(guessButton2);

        guessButton3 = new JButton();
        Guess3Handler guess3Handler = new Guess3Handler();
        guessButton3.addActionListener(guess3Handler);
        //guessButton3.setBackground(Color.BLUE);
        buttonPanel.add(guessButton3);

        guessButton4 = new JButton();
        Guess4Handler guess4Handler = new Guess4Handler();
        guessButton4.addActionListener(guess4Handler);
        //guessButton4.setBackground(Color.BLUE);
        buttonPanel.add(guessButton4);

        //add guess button panel to bottom of GUI
        add(buttonPanel, BorderLayout.SOUTH);


        //Set up top menu panel
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(1,2,5,5));

        //Set up menu button
        menuButton = new JButton("Menu");
        MenuButtonHandler menuButtonHandler = new MenuButtonHandler();
        menuButton.addActionListener(menuButtonHandler);
        //menuButton.setBackground(Color.BLUE);
        menuPanel.add(menuButton);

        //Set up balance display
        balanceText = new JLabel("");
        menuPanel.add(balanceText);

        //Add menu button and balance display to top of screen
        add(menuPanel, BorderLayout.NORTH);


        //Set up price level display
        levelPanel = new JPanel();
        levelPanel.setLayout(new GridLayout(16,1,0,10));
        levelPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));


        //Set up each price label
        priceLabels = new JLabel[16];
        for (int i=0;i<16;i++){
            priceLabels[i] = new JLabel(priceLevels[i]);
            priceLabels[i].setBackground(Color.BLUE);
            if(i == 0 || i == 5 || i == 10){
                priceLabels[i].setBorder(BorderFactory.createLineBorder(Color.GREEN));
            }
            else{
                priceLabels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
            priceLabels[0].setOpaque(true);
            levelPanel.add(priceLabels[i]);
        }

        //Add price levels to right side of window
        add(levelPanel, BorderLayout.EAST);

        //Set up panel for displaying question/general info`
        displayPanel = new JPanel(new GridLayout(2,1));

        // Create result label (initially hidden)
        resultLabel = new JTextPane();
        resultLabel.setEnabled(false);
        resultLabel.setDisabledTextColor(Color.BLACK);

        resultLabel.setText("");
        displayPanel.add(resultLabel);

        // Add display panel to the center of the GUI
        add(displayPanel, BorderLayout.CENTER);

        displayText = new JTextPane();
        displayText.setEnabled(false);
        displayText.setDisabledTextColor(Color.BLACK);
        StyledDocument doc = displayText.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        displayPanel.add(displayText, BorderLayout.CENTER);
        add(displayPanel, BorderLayout.CENTER);

        //Set up panel for power up buttons
        powerUpPanel = new JPanel(new GridLayout(3,1,0,5));

        //Setup power-up buttons with handlers
        fiftyFiftyButton = new JButton("50/50");
        FiftyFiftyHandler fiftyFiftyHandler = new FiftyFiftyHandler();
        fiftyFiftyButton.addActionListener(fiftyFiftyHandler);
        powerUpPanel.add(fiftyFiftyButton);

        askAudienceButton = new JButton("Ask Audience");
        AskAudienceHandler askAudienceHandler = new AskAudienceHandler();
        askAudienceButton.addActionListener(askAudienceHandler);
        powerUpPanel.add(askAudienceButton);

        phoneAFriendButton = new JButton("Phone Friend");
        PhoneFriendHandler phoneFriendHandler = new PhoneFriendHandler();
        phoneAFriendButton.addActionListener(phoneFriendHandler);
        powerUpPanel.add(phoneAFriendButton);

        //Add power-up panel to left side of window
        add(powerUpPanel, BorderLayout.WEST);
        setVisible(false);

        startLogin(); // start the login process

        /**
         * Use swing worker to wait until login to finish constructor
         */

        SwingWorker<Void, Void> loginWorker = new SwingWorker<>() {
            /**
             * doInBackground sleeps thread (waits) until user logs in
             * @return nothing
             */
            @Override
            public Void doInBackground() {
                // Wait until the user has valid log in
                while (!gameLogin.isLoggedIn()) {
                    try {
                        Thread.sleep(500); //check login every half second
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            /**
             * done is called when user is logged in
             * continues with rest of game setup
             */
            @Override
            public void done() {
                username = gameLogin.getLoggedInUsername();

                try {
                    currencyBalance = Integer.parseInt(dbConnection.getUserBalance(username).getMessage());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                //Make window visible
                setVisible(true);

                inMenu = false; //default to not being in menu
                inShop = false;

                //Start without power-ups
                hasAskAudience = false;
                hasFiftyFifty = false;
                hasPhoneFriend = false;

                //Initialize correct counter (used for token dispensing)
                correctGuessCounter = 0;

                //Load guess buttons into an array for later accessing
                initializeGuessButtonArray();

                //Updates level display to reflect current level

                currentLevel = 0;
                updateLevelDisplay();
                updateBalanceDisplay();


                try {
                    loadQuestion();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                //Start the chat GUI
                chat = new GameChat(client);
                executor.execute(chat);

                //default to leaderboard being closed
                leaderboardOpen = false;

                //Default to not be in config
                inConfig = false;

                //Default config settings
                darkMode = false;

                updatePowerupColors();
            }
        };

        //Execute login worker
        executor = Executors.newCachedThreadPool();
        executor.execute(loginWorker);
    }


    /**
     * The Guess1Handler class is a class that handles game execution when a user selects guessButton1. The class
     * implements ActionLister to add an actionListener to the guessButton1 to "listen" for when the button is clicked
     *
     */
    private class Guess1Handler implements ActionListener {
        /**
         * Overridden method that handles the action event triggered when the first guess button is clicked.
         * This method performs the following actions based on the current game state:
         *  1.Purchases 50/50 power-up:
         *    If the player is in the shop, does not have the 50/50 power-up, and has
         *    sufficient currency, it deducts one coin from the balance and sets the `hasFiftyFifty` flag.
         *  2.Opens the shop:
         *    If the player is in the main menu and the shop is not currently open, it updates the button
         *    text to display the available power-ups and sets the `inShop` flag to true.
         *
         * @param event the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            //System.out.println("button 1");
            updatePowerupColors();

            if (!inMenu){
                evaluateGuess(guessButton1.getText());
            }

            if(leaderboardOpen){
                closeLeaderboard();
            }

            //Lets user buy 50/50 power-up for 1 coin
            if (inShop && !hasFiftyFifty && currencyBalance >=1){
                currencyBalance --;
                hasFiftyFifty = true;
                updateBalanceDisplay();
            }

            if(inConfig){
                toggleDarkMode();
            }

            //Open shop when shop button pressed in menu
            if (inMenu && !inShop && !inConfig){
                guessButton1.setText("50/50 (1 coin)");
                guessButton2.setText("Ask Audience (2 coins)");
                guessButton3.setText("Phone Friend (3 coins)");
                guessButton4.setText("Back to menu");
                inShop = true;
            }
            updatePowerupColors();
            //debugInfo();
        }
    }
    /**
     * The Guess2Handler class is a class that handles game execution when a user selects guessButton2. The class
     * implements ActionLister to add an actionListener to the guessButton2 to "listen" for when the button is clicked
     *
     */
    private class Guess2Handler implements ActionListener {
        /**
         * Overridden method that handles the action event triggered when the second guess button is clicked.
         * This method performs the following action:
         * 1. Purchases Ask Audience power-up:
         *   If the player is in the shop, does not have the Ask Audience power-up, and has sufficient currency, i
         *   t deducts two coins from the balance and sets the `hasAskAudience` flag.
         *
         * @param event the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            //System.out.println("button 2");
            //Lets user buy audience power-up for 2 coin
            updatePowerupColors();
            if(inConfig){
                toggleLevelHighlighting();
            }
            if(leaderboardOpen){
                closeLeaderboard();
            }
            if (inShop && !hasAskAudience && currencyBalance >=2){
                currencyBalance -= 2;
                hasAskAudience = true;
                updateBalanceDisplay();
            }

            if (!inMenu){
                evaluateGuess(guessButton2.getText());
            }

            if(inMenu && !inShop && !leaderboardOpen && !inConfig){
                displayLeaderboard();
            }
            updatePowerupColors();
            //debugInfo();
        }
    }

    /**
     * The Guess3Handler class is a class that handles game execution when a user selects guessButton3. The class
     * implements ActionLister to add an actionListener to the guessButton3 to "listen" for when the button is clicked
     *
     */
    private class Guess3Handler implements ActionListener {
        /**
         * Overridden method that handles the action event triggered when the third guess button is clicked.
         * This method performs the following action:
         * 1. Purchases Phone a Friend power-up:
         *   If the player is in the shop, does not have the Phone a Friend power-up, and has sufficient currency,
         *   it deducts three coins from the balance and sets the `hasPhoneFriend` flag.
         *
         * @param event the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            //System.out.println("button 3");
            updatePowerupColors();

            //Lets user buy phone friend power up for 3 coins
            if (inShop && !hasPhoneFriend){
                if (currencyBalance >=3){
                    currencyBalance -= 3;
                    hasPhoneFriend = true;
                    updateBalanceDisplay();
                }
            }
            if (!inMenu){
                evaluateGuess(guessButton3.getText());
            }

            if (inMenu && !inShop && !inConfig){
                guessButton1.setText("Toggle Dark Mode");
                guessButton2.setText("Toggle Level Highlight");
                guessButton3.setText("-");
                guessButton4.setText("Back to menu");
                inConfig = true;
            }

            if(leaderboardOpen){
                closeLeaderboard();
            }
            updatePowerupColors();
            //debugInfo();
        }
    }

    /**
     * The Guess4Handler class is a class that handles game execution when a user selects guessButton4. The class
     * implements ActionLister to add an actionListener to the guessButton4 to "listen" for when the button is clicked
     *
     */
    private class Guess4Handler implements ActionListener {
        /**
         * Overridden method that handles the action event triggered when the fourth guess button is clicked.
         * This method performs the following actions based on the current game state:
         * 1. Opens chat window from menu
         *   If the player is in the main menu and the shop is not open, it adds one coin to the balance.
         * 2. Closes the shop:
         *   If the player is in the main menu and the shop is open, it closes the shop by setting the `inShop`
         *   flag to false and updating the button text.
         *
         * @param event the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            //System.out.println("button 4");
            updatePowerupColors();
            if (!inMenu){
                evaluateGuess(guessButton4.getText());
            }

            //Open/close chat
            if (inMenu && !inShop && !inConfig){
                if (chat.isVisible()){
                    chat.setVisible(false);
                    System.out.println("Close Chat");
                }
                else{
                    chat.setVisible(true);
                    System.out.println("Open chat");
                }
            }
            if(leaderboardOpen){
                closeLeaderboard();
            }

            //Returns user from shop to main menu
            if (inMenu && inShop || inMenu && inConfig){
                inShop = false;
                inConfig = false;

                guessButton1.setText("Shop");
                guessButton2.setText("Leaderboard");
                guessButton3.setText("Game Configuration");
                guessButton4.setText("Toggle Chat");
            }
            updatePowerupColors();
            //debugInfo();
        }
    }

    /**
     * The MenuButtonHandler class is a class that handles game execution when a user selects the Menu button. The
     * class implements ActionLister to add an actionListener to the Menu button to "listen" for when
     * the button is clicked
     */
    private class MenuButtonHandler implements ActionListener {
        /**
         * Overridden method that handles the action event triggered when the menu button is clicked.
         * This method toggles the game state between the main game and the menu.
         * When the menu is open, the button text changes to "Back to game" and the guess buttons display menu options.
         * When the menu is closed, the button text changes back to "Menu" and the guess buttons display their original
         * text.
         *
         * @param event the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            //debugInfo();
            //System.out.println("menu");
            updatePowerupColors();
            if(leaderboardOpen){
                closeLeaderboard();
                leaderboardOpen = false;
            }

            if (!inMenu){
                inMenu = true;
                menuButton.setText("Back to game");
                guessButton1.setText("Shop");
                guessButton2.setText("Leaderboard");
                guessButton3.setText("Game Configuration");
                guessButton4.setText("Toggle Chat");
            }
            else{
                inMenu = false;
                inShop = false;
                inConfig = false;
                leaderboardOpen = false;
                menuButton.setText("Menu");
                for (int i=0; i<4; i++){
                    guessButtons[indices[i]].setText(currentGuesses[i]);;
                    guessButtons[indices[i]].setBackground(null);
                }
            }
        }
    }


    /**
     * The FiftyFiftyHandler class is a class that handles game execution when a user selects the 50/50 button. The
     * class implements ActionLister to add an actionListener to the 50/50 button to "listen" for when the button is
     * clicked.
     * If you have the power-up, it will eliminate 2 incorrect answer choices, so you have a 50/50 shot at being right
     * If you don't have the power-up, a message will appear saying that the power-up was clicked but not used
     */
    private class FiftyFiftyHandler implements ActionListener {
        /**
         * Overridden method that handles the action event triggered when the 50/50 power-up button is clicked.
         * This method checks if the player has the 50/50 power-up and if the game is not in the menu.
         *  If both conditions are met, the power-up is used, and the `hasFiftyFifty` flag is set to false.
         *  Otherwise, a message is printed indicating that the power-up is not available.
         *
         * @param actionEvent the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            updatePowerupColors();
            if(leaderboardOpen){
                closeLeaderboard();
                leaderboardOpen = false;
            }

            if(hasFiftyFifty && !inMenu){
                useFiftyFifty();
                //System.out.println("50/50 pressed, used");
                hasFiftyFifty = false;
            }
            else{
                //System.out.println("50/50 pressed, not used");
            }
            updatePowerupColors();
            //debugInfo();
        }
    }

    /**
     * The AskAudienceHandler class is a class that handles game execution when a user selects the AskAudience
     * button. The class implements ActionLister to add an actionListener to the Ask Audience button to "listen" for
     * when the button is clicked.
     * If you have the power-up,      TODO finish this later
     * If you don't have the power-up, a message will appear saying that the power-up was clicked but not used
     */
    private class AskAudienceHandler implements ActionListener {
        /**
         * Overridden method that handles the action event triggered when the Ask the Audience power-up button is
         * clicked.
         * This method checks if the player has the Ask the Audience power-up and if the game is not in the menu.
         * If both conditions are met, the power-up is used, and the `hasAskAudience` flag is set to false.
         * Otherwise, a message is printed indicating that the power-up is not available.
         *
         * @param actionEvent the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            updatePowerupColors();
            if(leaderboardOpen){
                closeLeaderboard();
                leaderboardOpen = false;
            }
            if(hasAskAudience && !inMenu){
                //System.out.println("Audience pressed, used");
                useAskAudience();
                hasAskAudience = false;
            }
            else{
                //System.out.println("Audience pressed, not used");
            }
            //debugInfo();
        }
    }

    /**
     * The PhoneFriendHandler class is a class that handles game execution when a user selects the AskAudience
     * button. The class implements ActionLister to add an actionListener to the Phone a Friend button to "listen" for
     * when the button is clicked.
     * If you have the power-up,      TODO finish this later
     * If you don't have the power-up, a message will appear saying that the power-up was clicked but not used
     */
    private class PhoneFriendHandler implements ActionListener {
        /**
         * Overridden method that handles the action event triggered when Phone a Friend power-up button is clicked.
         * This method checks if the player has the Phone a Friend power-up and if the game is not in the menu.
         * If both conditions are met, the power-up is used, and the `hasPhoneFriend` flag is set to false.
         * Otherwise, a message is printed indicating that the power-up is not available.
         *
         *
         *
         * @param actionEvent the event to be processed
         */
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            updatePowerupColors();
            if(leaderboardOpen){
                closeLeaderboard();
                leaderboardOpen = false;
            }
            if(hasPhoneFriend && !inMenu){
                //power-up functionality
                //System.out.println("Phone pressed, used");
                usePhoneAFriend();
                hasPhoneFriend = false;
            }
            else{
                //System.out.println("Phone pressed, not used");
            }
            updatePowerupColors();
            //debugInfo();
        }
    }

    /**
     * Method updates the balance display with the current currency balance of the user
     * Method gives user a coin for every 2 correct guesses (dont need to be consecutive)
     */
    private void updateBalanceDisplay(){
        if (correctGuessCounter % 2 ==0 && correctGuessCounter> 0 ){
            currencyBalance++;
        }
        balanceText.setText("Balance: " + currencyBalance);
    }


    /**
     * Method that updates the level display's highlighting
     */
    private void updateLevelDisplay(){
        for (int i = 0; i<16; i++){
            priceLabels[i].setBackground(Color.LIGHT_GRAY);
            priceLabels[i].setOpaque(true);
        }
        if (currentLevel<16){
            priceLabels[currentLevel].setBackground(levelColors[levelColorIndex]);
        }
        else{
            System.out.println("You win");
            endGame();
        }

    }

    /**
     * method opens and initializes GameLogin member
     */
    private void startLogin(){
        gameLogin = new GameLogin(client);
        gameLogin.setTitle("Register/Login");
        gameLogin.setSize(400, 100);
    }

    /**
     * Method that uses 50/50 power-up by redding out 2 wrong guesses
     */
    private void useFiftyFifty() {
        if (hasFiftyFifty) {
            guessButtons[indices[2]].setBackground(Color.red);
            guessButtons[indices[3]].setBackground(Color.red);

        }
    }

    /**
     * Method that uses Phone Friend power-up by using algorithm to help users select correct answer
     * This algorithm is more accurate than the Ask Audience power-up
     * The accuracy of the algorithm reduces when users get past certain checkpoints
     */
    private void usePhoneAFriend() {
        // Create an array with 10 elements
        int[] phoneFriendChances = new int[10];
        // iterate through the array
        for (int i = 0; i < phoneFriendChances.length; i++) {
            phoneFriendChances[i] = i; // assign the index as the value for simplicity
        }

        // create a random object to generate random numbers
        Random random = new Random();
        // Generate a random index between 0 (inclusive) and the array's length (exclusive)
        // Simulates randomly picking one of the elements in the array
        int selectedIndex = random.nextInt(phoneFriendChances.length);

        if(currentLevel<5){
            // 100% accurate
            resultLabel.setText("Your friend said the answer is: " + guessButtons[indices[0]].getText());
        }
        else if(currentLevel<10){
            //80% accurate
            if (selectedIndex >= 2) {
                resultLabel.setText("Your friend said the answer is: " + guessButtons[indices[0]].getText());
            } else {
                resultLabel.setText("Your friend said the answer is: " + guessButtons[indices[2]].getText());
            }
        }
        else{
            //60% accurate
            if (selectedIndex >= 4) {
                resultLabel.setText("Your friend said the answer is: " + guessButtons[indices[0]].getText());
            } else {
                resultLabel.setText("Your friend said the answer is: " + guessButtons[indices[2]].getText());
            }
        }
    }

    /**
     * Method that uses Ask Audience power-up by using algorithm to help users select correct answer
     * This algorithm is less accurate than the Phone Friend power-up
     * The accuracy of the algorithm reduces when users get past certain checkpoints
     */
    private void useAskAudience() {
        // Create an array with 10 elements
        int[] chances = new int[10];
        // iterate through the array
        for (int i = 0; i < chances.length; i++) {
            chances[i] = i; // assign the index as the value for simplicity
        }

        // create a random object to generate random numbers
        Random random = new Random();
        // Generate a random index between 0 (inclusive) and the array's length (exclusive)
        // Simulates randomly picking one of the elements in the array
        int selectedIndex = random.nextInt(chances.length);

        if(currentLevel<5){
            // 100% accurate
            if (selectedIndex >= 2) {
                resultLabel.setText("The audience thinks the answer is: " + guessButtons[indices[0]].getText());
            } else {
                resultLabel.setText("The audience thinks the answer is: " + guessButtons[indices[2]].getText());
            }
        }
        else if(currentLevel<10){
            //60% accurate
            if (selectedIndex >= 4) {
                resultLabel.setText("The audience thinks the answer is: " + guessButtons[indices[0]].getText());
            } else {
                resultLabel.setText("The audience thinks the answer is: " + guessButtons[indices[2]].getText());
            }
        }
        else{
            //40% accurate
            if (selectedIndex >= 6) {
                resultLabel.setText("The audience thinks the answer is: " + guessButtons[indices[0]].getText());
            } else {
                resultLabel.setText("The audience thinks the answer is: " + guessButtons[indices[2]].getText());
            }
        }
    }

    /**
     * Method that evaluates user guesses
     *
     * @param guess String of user guess
     * @return true if guess is correct, false if guess is wrong
     */
    private boolean evaluateGuess(String guess){
        pushInfoToDB();
        if (guess.equals(currentGuesses[0])){
            resultLabel.setText("You were correct!");
            currentLevel++;
            updateLevelDisplay();
            try {
                loadQuestion();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            correctGuessCounter++;
            updateBalanceDisplay();
            return true;
        }

        //Checkpoints
        if(currentLevel < 5){
            currentLevel = 0;
        }
        else if (currentLevel<10){
            currentLevel = 5;
        }
        else{
            currentLevel = 10;
        }
        updateLevelDisplay();
        resultLabel.setText("You were wrong! Correct answer was: " + currentGuesses[0]);

        try {
            loadQuestion();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * Method that simulates end of game
     */
    private void endGame() {
        gameOver = true;
        displayText.setEnabled(false);
        // make new game over display to display winner frame
        new GameOverDisplay();

        resultLabel.setEnabled(false);
        fiftyFiftyButton.setEnabled(false);
        phoneAFriendButton.setEnabled(false);
        askAudienceButton.setEnabled(false);
        guessButton1.setEnabled(false);
        guessButton2.setEnabled(false);
        guessButton3.setEnabled(false);
        guessButton4.setEnabled(false);

//        leaderboard = "Sample leaderboard";
//        resultLabel.setText(leaderboard);

        try {
            leaderboard = dbConnection.getAllTimeLeaderBoard("desc").getMessage();
            displayText.setText(leaderboard);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that loads questions by pulling questions from Questions database base on currentLevel checkpoint difficulties
     *
     * @throws SQLException
     */
    private void loadQuestion() throws SQLException {
        pushInfoToDB();
        if (!gameOver){

            if (currentLevel < 5){
                currentQuestionMessage = dbConnection.getRandomQuestion("easy");
            }
            else if (currentLevel <10){
                currentQuestionMessage = dbConnection.getRandomQuestion("medium");
            }
            else{
                currentQuestionMessage = dbConnection.getRandomQuestion("hard");
            }

            currentQuestion = currentQuestionMessage.getQuestion();
            currentGuesses[0] = currentQuestionMessage.getCorrectAnswer();
            currentGuesses[1] = currentQuestionMessage.getFirstIncorrectAnswer();
            currentGuesses[2] = currentQuestionMessage.getSecondIncorrectAnswer();
            currentGuesses[3] = currentQuestionMessage.getThirdIncorrectAnswer();

            System.out.println("(debug) Correct: " + currentGuesses[0]);

            //shuffle guess button order
            indices = new Integer[]{0, 1, 2, 3};
            List<Integer> list = Arrays.asList(indices);
            Collections.shuffle(list);
            indices = list.toArray(new Integer[0]);


            for (int i=0; i<4; i++){
                guessButtons[indices[i]].setText(currentGuesses[i]);;
                guessButtons[indices[i]].setBackground(null);
            }

            correctGuessIndex = indices[0];
            displayText.setText(currentQuestion);
        }
    }


    /**
     * Method that initializes guess button array so that the correct guess is always in a randomized location
     */
    private void initializeGuessButtonArray(){
        guessButtons = new JButton[4];
        guessButtons[0] = guessButton1;
        guessButtons[1] = guessButton2;
        guessButtons[2] = guessButton3;
        guessButtons[3] = guessButton4;
    }

    /**
     * Method to push current round and balance to the database
     */
    private void pushInfoToDB(){
        try {
            dbConnection.setCurrentRound(username, currentLevel);
            dbConnection.setBalance(username, currentLevel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to display the leaderboard
     */
    public void displayLeaderboard(){
        // pull in most recent leaderboard info from DB: leaderboard = dbConnection.getLeaderboard;
        //  and currentLeaderboardPosition = getLeaderboardPosition(username);
        try {
            leaderboard = dbConnection.getAllTimeLeaderBoard("desc").getMessage();
            currentLeaderboardPosition = dbConnection.getCurrentlyPlayingLeaderBoard("desc").getMessage();
            System.out.println(leaderboard);
            System.out.println(currentLeaderboardPosition);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //Fake leaderboard info:
//        leaderboard = "1. Player 17: $500,000\n1. Player 8: $100,000\n1. Player 13: $64,000\n" +
//                "14. Player 2: $8,000\n5. Player 3: $4,000\n";
//        currentLeaderboardPosition = "You are in 17th! Fucking idiot you suck ass!";

        //Display leaderboard
        resultLabel.setText(leaderboard);
        displayText.setText(currentLeaderboardPosition);
        leaderboardOpen = true;
    }

    /**
     * Method to close the leaderboard
     */
    public void closeLeaderboard(){
        resultLabel.setText("");
        displayText.setText(currentQuestion);
        for (int i=0; i<4; i++){
            guessButtons[indices[i]].setText(currentGuesses[i]);;
            guessButtons[indices[i]].setBackground(null);
        }
        leaderboardOpen = false;
    }

    /**
     * Method to turn on dark mode for game configurations
     */
    public void toggleDarkMode(){
        //TODO
        System.out.println("Dark mode toggle");
        if(darkMode){
            for (int i=0; i<4; i++){
                guessButtons[indices[i]].setBackground(null);
            }
            menuPanel.setBackground(Color.WHITE);
            //levelPanel.setBackground(Color.WHITE);
            displayPanel.setBackground(Color.WHITE);
            powerUpPanel.setBackground(Color.WHITE);
            displayText.setBackground(Color.WHITE);
            resultLabel.setBackground(Color.WHITE);
            updateLevelDisplay();
            darkMode = false;
        }
        else{
            for (int i=0; i<4; i++){
                guessButtons[indices[i]].setBackground(null);
            }
            menuPanel.setBackground(Color.LIGHT_GRAY);
            //levelPanel.setBackground(Color.LIGHT_GRAY);
            displayPanel.setBackground(Color.LIGHT_GRAY);
            powerUpPanel.setBackground(Color.LIGHT_GRAY);
            displayText.setBackground(Color.LIGHT_GRAY);
            resultLabel.setBackground(Color.LIGHT_GRAY);
            updateLevelDisplay();
            darkMode = true;
        }

    }

    /**
     * Method to turn on level highlighting color changes for game configurations
     */
    public void toggleLevelHighlighting(){
        //TODO
        System.out.println("Level thing toggle");
        levelColorIndex++;
        if (levelColorIndex==5){
            levelColorIndex = 0;
        }
        updateLevelDisplay();
    }

    /**
     * Method to change the color of power-ups based on when you have one vs. not having one
     */
    public void updatePowerupColors(){
        if(!hasFiftyFifty){
           fiftyFiftyButton.setBackground(new Color(187, 56, 56));
        }
        else{
            fiftyFiftyButton.setBackground(new Color(59, 141, 42));
        }

        if(!hasAskAudience){
            askAudienceButton.setBackground(new Color(187, 56, 56));
        }
        else{
            askAudienceButton.setBackground(new Color(59, 141, 42));
        }

        if(!hasPhoneFriend){
            phoneAFriendButton.setBackground(new Color(187, 56, 56));
        }
        else{
            phoneAFriendButton.setBackground(new Color(59, 141, 42));
        }

    }


}
