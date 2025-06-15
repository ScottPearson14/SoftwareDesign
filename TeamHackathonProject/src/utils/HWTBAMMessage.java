package utils;

import java.io.Serializable;

/**
 * Class:
 *     HWTBAMMessage
 *
 * Purpose:
 *     Object to be serialized and sent as I/O across Client/Server.
 *     Holds fields for any tasks/purposes; this is universal for all messages.
 *
 *     This object acts as a JSON with fields:
 *          username,
 *          password,
 *          action,
 *          createQuestion,
 *          getNewQuestion,
 *          correctAnswer,
 *          firstIncorrectAnswer,
 *          secondIncorrectAnswer,
 *          thirdIncorrectAnswer,
 *          message,
 *          processingResult,
 *          powerUp
 *          result
 *          difficulty
 *
 * @implements Serializable
 */
public class HWTBAMMessage implements Serializable {
    /** String that represents username of users */
    private String username; // field present for all messages
    /** String that represents password of users */
    private String password; // field present in login/register messages
    /** String that represents action user takes */
    private String action; // field SHOULD be present for all messages; empty will result in close() for Client/Worker
    /** String that represents a question to be created for levels in the game */
    private String question; // field present to create new question

    /** String that represents correct answer to question */
    private String correctAnswer; // field present in getNewQuestion and createQuestion
    /** String that represents first incorrect answer to question */
    private String firstIncorrectAnswer; // field present in getNewQuestion and createQuestion
    /** String that represents second incorrect answer to question */
    private String secondIncorrectAnswer; // field present in getNewQuestion and createQuestion
    /** String that represents third incorrect answer to question */
    private String thirdIncorrectAnswer; // field present in getNewQuestion and createQuestion
    /** String that represents messages to be displayed for users and system */
    private String message; // field present in user & system messages shown in toast messages
    /** String that represents result of user answer for question */
    private String result; // field present in all messages from server. either 'success' or 'failure'
    /** String that represents power-up of user */
    private String powerUp; // field present in powerUp buying and activating

    /** boolean representing whether to send message to all clients */
    private boolean broadcast;

    /** int representing the difficulty of the wanted question */
    private int difficulty;


    /** send message to */
    private String receivingUser;

    /** message sent from */
    private String sendingUser;

    /**
     * Constructor:
     *     hwtbamJSON
     *
     * Purpose:
     *     Set attribute of the username of the Client creating the message.
     *
     * @param username String that represents username of player
     */
    public HWTBAMMessage(String username) {
        this.username = username;
    }

    /**
     * Getter that retrieves username
     *
     * @return String representation of username
     */
    public String getUsername() {
        return username;
    }
    /**
     * Getter that retrieves password for user
     *
     * @return String that represents password of user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter that sets password for user
     *
     * @param password String that represents password of user
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Getter that retrieves action user takes
     *
     * @return String that represents action user takes
     */
    public String getAction() {
        return action;
    }

    /**
     * Setter that sets action user takes
     *
     * @param action String that represents action user takes
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Getter that retrieves correct answer for questions
     *
     * @return String that represents correct answer
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Setter that sets correct answer for questions
     *
     * @param correctAnswer String that represents correct answer for questions
     */
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    /**
     * Getter that retrieves first incorrect answer to question
     *
     * @return String that represents first incorrect answer to question
     */
    public String getFirstIncorrectAnswer() {
        return firstIncorrectAnswer;
    }
    /**
     * Setter that sets first incorrect answer to question
     *
     * @return String that represents first incorrect answer to question
     */
    public void setFirstIncorrectAnswer(String firstIncorrectAnswer) {
        this.firstIncorrectAnswer = firstIncorrectAnswer;
    }
    /**
     * Getter that retrieves second incorrect answer to question
     *
     * @return String that represents second incorrect answer to question
     */
    public String getSecondIncorrectAnswer() {
        return secondIncorrectAnswer;
    }
    /**
     * Setter that sets second incorrect answer to question
     *
     * @return String that represents second incorrect answer to question
     */
    public void setSecondIncorrectAnswer(String secondIncorrectAnswer) {
        this.secondIncorrectAnswer = secondIncorrectAnswer;
    }
    /**
     * Getter that retrieves third incorrect answer to question
     *
     * @return String that represents third incorrect answer to question
     */
    public String getThirdIncorrectAnswer() {
        return thirdIncorrectAnswer;
    }
    /**
     * Setter that sets third incorrect answer to question
     *
     * @return String that represents third incorrect answer to question
     */
    public void setThirdIncorrectAnswer(String thirdIncorrectAnswer) {
        this.thirdIncorrectAnswer = thirdIncorrectAnswer;
    }

    /**
     * Getter that retrieves messages for user and system
     *
     * @return String that represents messages for user and system
     */
    public String getMessage() {
        return message;
    }
    /**
     * Setter that sets messages for user and system
     *
     * @return String that represents messages for user and system
     */
    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * Setter that sets username for user
     *
     * @return String that represents username for user
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Getter that retrieves a question to display for user to answer
     *
     * @return String that represent question to be answered by user
     */
    public String getQuestion() {
        return question;
    }
    /**
     * Setter that sets a question to display for user to answer
     *
     * @return String that represent question to be answered by user
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Getter that gets result of user answer for questions
     *
     * @return String that represents result of user answer for questions
     */
    public String getResult() {
        return result;
    }
    /**
     * Setter that sets result of user answer for questions
     *
     * @return String that represents result of user answer for questions
     */
    public void setResult(String result) {
        this.result = result;
    }
    /**
     * Getter that gets power-up user can purchase
     *
     * @return String that represents power-up user can purchase
     */
    public String getPowerUp() {
        return powerUp;
    }
    /**
     * Setter that sets power-up user can purchase
     *
     * @return String that represents power-up user can purchase
     */
    public void setPowerUp(String powerUp) {
        this.powerUp = powerUp;
    }

    /** Getter that returns broadcast flag */
    public boolean isBroadcast() {
        return broadcast;
    }

    /** Setter that sets broadcast flag */
    public void setBroadcast(boolean broadcast) {
        this.broadcast = broadcast;
    }

    /**
     * Getter that returns question difficulty
     *
     * @return
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Setter for wanted question difficulty
     *
     * @param difficulty
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * getter for wanted user to send to
     */
    public String getReceivingUser() {
        return receivingUser;
    }

    /**
     * set user to send message to
     *
     * @param receivingUser
     */
    public void setReceivingUser(String receivingUser) {
        this.receivingUser = receivingUser;
    }

    /**
     * getter for wanted user send from
     */
    public String getSendingUser() {
        return sendingUser;
    }

    /**
     * set user to send message from
     *
     * @param sendingUser
     */
    public void setSendingUser(String sendingUser) {
        this.sendingUser = sendingUser;
    }

    /**
     * USE FOR DEBUGGING
     *
     * @return String
     */
    public String toString() {
        return "username: " + username
                + "\npassword: " + password
                + "\naction: " + action
                + "\nquestion: " + question
                + "\ncorrectAnswer: " + correctAnswer
                + "\nfirstIncorrectAnswer: " + firstIncorrectAnswer
                + "\nsecondIncorrectAnswer: " + secondIncorrectAnswer
                + "\nthirdIncorrectAnswer: " + thirdIncorrectAnswer
                + "\nmessage: " + message
                + "\nresult: " + result
                + "\npowerUp: " + powerUp
                + "\nbroadcast: " + broadcast
                + "\ndifficulty: " + difficulty
                + "\nsendingUser: " + sendingUser
                + "\nreceivingUser: " + receivingUser;
    }
}