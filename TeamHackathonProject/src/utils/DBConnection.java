// starting database code Bradley provided to us in the database pdf
package utils;

import java.sql.SQLIntegrityConstraintViolationException;

import java.sql.*;
import java.util.Random;

/**
 * The DBConnector class is a class that creates a connection to our database using our url, username, and password
 * This class allows us to pull from our database so we can get information to pass on to the game.
 */
public class DBConnection {
    /**
     * Name of database we're using
     */
    private final String databaseName = "swd_2024_db09";
    /**
     * JDBC URL to reference the database
     */
    private final String url = "jdbc:mysql://s-l112.engr.uiowa.edu/" + databaseName + "?enabledTLSProtocols=TLSv1.2";
    /**
     * Our Database username
     */
    private final String dbUsername = "swd_2024_09";
    /**
     * Our Database password
     */
    private final String dbPassword = "GoonSquad#9";
    /**
     * The connection to the database
     */
    private Connection connection;

    /**
     * Sets up a connection with the database using the url, username, and password.
     */
    public DBConnection() throws SQLException {

        connection = DriverManager.getConnection(url, dbUsername, dbPassword);

    }

    /**
     * Method:
     *     getCurrentLeaderBoardToString
     *
     * Purpose:
     *     get the current leaders
     *
     * @param resultSet
     * @return string
     * @throws SQLException
     */
    private String getCurrentLeaderBoardToString(ResultSet resultSet) throws SQLException {
        StringBuilder leaderBoard = new StringBuilder();
        // loop through result set and get wanted ids
        while (resultSet.next()) {
            // cols
            String username = resultSet.getString("username");
            int round = resultSet.getInt("current_round");

            // build record
            StringBuilder record = new StringBuilder();
            record.append(username + ":" + round);

            // add to main
            leaderBoard.append(record + "\n");
        }

        // return
        return leaderBoard.toString();
    }

    /**
     * Method:
     *     getAllTimeLeaderBoardToString
     *
     * Purpose:
     *     get the first 5 all time leaders
     *
     * @param resultSet
     * @return string
     * @throws SQLException
     */
    private String getAllTimeLeaderBoardToString(ResultSet resultSet) throws SQLException {
        StringBuilder leaderBoard = new StringBuilder();
        // loop through result set and get wanted ids
        while (resultSet.next()) {
            // cols
            String username = resultSet.getString("username");
            int round = resultSet.getInt("round_exit");

            // build record
            StringBuilder record = new StringBuilder();
            record.append(username + ":" + round);

            // add to main
            leaderBoard.append(record + "\n");
        }


        // return
        return leaderBoard.toString();
    }

    /**
     * Method:
     *     getGameLogOfUserToString
     *
     * Purpose:
     *     get the GameLog of user as string
     *
     * @param resultSet
     * @return string
     * @throws SQLException
     */
    private String getGameLogOfUserToString(ResultSet resultSet) throws SQLException {
        StringBuilder leaderBoard = new StringBuilder();
        // loop through result set and get wanted ids
        while (resultSet.next()) {
            // cols
            String username = resultSet.getString("username");
            int round = resultSet.getInt("round_exit");
            String time = String.valueOf(resultSet.getDate("time_completed"));

            // build record
            StringBuilder record = new StringBuilder();
            record.append(username + ":" + round + ":" + time);

            // add to main
            leaderBoard.append(record + "\n");
        }

        // return
        return leaderBoard.toString();
    }

    /**
     * Method:
     *     userExists
     *
     * Purpose:
     *     run a sql process to check the existence of a user username
     *
     * @param username
     * @return boolean
     * @throws SQLException
     */
    public boolean userExists(String username) throws SQLException {
        // prepare statements & execute
        String query = """
                            SELECT COUNT(*) as COUNT
                            FROM Users
                            WHERE username = ?;
                        """;
        PreparedStatement userExistenceStatement = connection.prepareStatement(query);

        // init to false
        boolean exists = false;

        // try to run sql
        try {
            userExistenceStatement.setString(1, username);
            ResultSet rs = userExistenceStatement.executeQuery();

            // check existence of a count
            if (rs.next()) {
                int count = rs.getInt("COUNT"); // get the value inside the count column for next row (this sql only returns one row as pk is username; therefore this count indicates existence)
                exists = count > 0;
            }
        } catch (SQLException e) { // if something fk up
            System.out.println("-\n- SQL ERROR (userExists): something happened\n-\n");
        } finally {
            // close statement
            userExistenceStatement.close();
        }

        return exists;
    }

    /**
     * Method:
     *     questionExists
     *
     * Purpose:
     *     run a sql process to check the existence of a question id
     *
     * @param id
     * @return
     * @throws SQLException
     */
    private boolean questionExists(int id) throws SQLException {
        // prepare statements & execute
        String query = """
                            SELECT COUNT(*) as COUNT
                            FROM Questions
                            WHERE id = ?;
                        """;
        PreparedStatement questionExistsStatement = connection.prepareStatement(query);

        // init to false
        boolean exists = false;

        // try to run sql
        try {
            questionExistsStatement.setInt(1, id);
            ResultSet rs = questionExistsStatement.executeQuery();

            // return boolean true if there is a row, else false.
            if (rs.next()) {
                int count = rs.getInt("COUNT"); // get the value inside the count column for next row (this sql only returns one row as pk is username; therefore this count indicates existence)
                exists = count > 0;
            }
        } catch (SQLException e) { // if something fk up
            System.out.println("-\n- SQL ERROR (questionExists): something happened\n-\n");
        } finally {
            // close statement
            questionExistsStatement.close();
        }

        // return outcome
        return exists;
    }

    /**
     * Method:
     *     isCorrectCredentials
     *
     * Purpose:
     *     run a sql process to check if the wanted username & password pair exists
     *
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    private boolean isCorrectCredentials(String username, String password) throws SQLException {

        // prepare statement
        String query = """
                            SELECT COUNT(*) AS COUNT FROM Users 
                            WHERE username = ? AND password = ?;
                        """;
        PreparedStatement userCredentialsStatement = connection.prepareStatement(query);

        // init to false
        boolean correct = false;

        // try to run sql
        try {
            userCredentialsStatement.setString(1, username); // check if correct credentials
            userCredentialsStatement.setString(2, password);
            ResultSet rs = userCredentialsStatement.executeQuery();

            // return boolean true if there is a row, else false.
            if (rs.next()) {
                int count = rs.getInt("COUNT"); // get the value inside the count column for next row (this sql only returns one row as pk is username; therefore this count indicates existence)
                correct = count > 0;
            }
        } catch (SQLException e) { // if something fk up
            System.out.println("-\n- SQL ERROR (isCorrectCredentials): something happened\n-\n");
        } finally {
            // close statement
            userCredentialsStatement.close();
        }

        // return outcome
        return correct;
    }

    /**
     * Method:
     *     hasUserLoggedGame
     *
     * Purpose:
     *     run a sql process to check if the wanted username has played a game
     *
     * @param username
     * @return
     * @throws SQLException
     */
    private boolean hasUserLoggedGame(String username) throws SQLException {

        // prepare statement
        String query = """
                            SELECT COUNT(*) AS COUNT FROM GameLog 
                            WHERE username = ?;
                        """;
        PreparedStatement userGameLogsStatement = connection.prepareStatement(query);

        // init to false
        boolean correct = false;

        // try to run sql
        try {
            userGameLogsStatement.setString(1, username); // check if correct credentials
            ResultSet rs = userGameLogsStatement.executeQuery();

            // return boolean true if there is a row, else false.
            if (rs.next()) {
                int count = rs.getInt("COUNT"); // get the value inside the count column for next row (this sql only returns one row as pk is username; therefore this count indicates existence)
                correct = count > 0;
            }
        } catch (SQLException e) { // if something fk up
            System.out.println("-\n- SQL ERROR (hasUserLoggedGame): something happened\n-\n");
        } finally {
            // close statement
            userGameLogsStatement.close();
        }

        // return outcome
        return correct;
    }

    /**
     * Method:
     *     setTimeLastLogin
     *
     * Purpose:
     *     run a sql process to UPDATE the last_login of username passed
     *
     * @param username
     * @return
     * @throws SQLException
     */
    private boolean setTimeLastLogin(String username) throws SQLException {
        // prepare statement
        String query = """
                        UPDATE Users
                        SET last_Login = NOW()
                        WHERE username = ?;
                        """;
        PreparedStatement setTimeLastLoginStatement = connection.prepareStatement(query);

        // init to false
        boolean updated = false;

        try {
            setTimeLastLoginStatement.setString(1, username); // check if correct credentials
            int outcome = setTimeLastLoginStatement.executeUpdate();

            if (outcome > 0) { // changed
                updated = true;
            }
        } catch (SQLException e) { // if something fk up
            System.out.println("-\n- SQL ERROR (setTimeLastLogin): something happened\n-\n");
        } finally {

            // close statement
            setTimeLastLoginStatement.close();
        }

        // return outcome
        return updated;
    }

    /**
     * Method:
     *     setCurrentlyPlaying
     *
     * Purpose:
     *     run a sql process to UPDATE the is_currently_playing  of username passed
     *
     * @param username
     * @param value
     * @return
     * @throws SQLException
     */
    private boolean setCurrentlyPlaying(String username, String value) throws SQLException {
        // prepare statement
        String query = """
                UPDATE Users
                SET currently_playing = ?
                WHERE username = ?;
                """;
        PreparedStatement setCurrentlyPlayingStatement = connection.prepareStatement(query);

        // init false
        boolean updated = false;

        // try to run sql
        try {
            setCurrentlyPlayingStatement.setString(1, value);
            setCurrentlyPlayingStatement.setString(2, username);
            int outcome = setCurrentlyPlayingStatement.executeUpdate();

            if (outcome > 0) { // changed
                updated = true;
            }
        } catch (SQLException e) { // if something fk up (updated is still false here)
            System.out.println("-\n- SQL ERRROR (setCurrentlyPlaying): something happened\n-\n");
        } finally {
            // close statement
            setCurrentlyPlayingStatement.close();
        }

        // return outcome
        return updated;
    }

    /**
     * Method:
     *     setCurrentlyPlaying
     *
     * Purpose:
     *     run a sql process to UPDATE the is_currently_playing  of username passed
     *
     * @param username
     * @param value
     * @return
     * @throws SQLException
     */
    public boolean setCurrentRound(String username, int value) throws SQLException {
        // prepare statement
        String query = """
                UPDATE Users
                SET current_round = ?
                WHERE username = ?;
                """;
        PreparedStatement setCurrentRoundStatement = connection.prepareStatement(query);

        // init false
        boolean updated = false;

        // try to run sql
        try {
            setCurrentRoundStatement.setInt(1, value);
            setCurrentRoundStatement.setString(2, username);
            int outcome = setCurrentRoundStatement.executeUpdate();

            if (outcome > 0) { // changed
                updated = true;
            }
        } catch (SQLException e) { // if something fk up (updated is still false here)
            System.out.println("-\n- SQL ERRROR (setCurrentRound): something happened\n-\n");
        } finally {
            // close statement
            setCurrentRoundStatement.close();
        }

        // return outcome
        return updated;
    }

    /**
     * Method:
     *     loginUser
     *
     * Purpose:
     *     Runs series of sql processes to login user (or reject)
     *
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    public HWTBAMMessage loginUser(String username, String password) throws SQLException {
        // prepare output message
        HWTBAMMessage hwtbamMessage = new HWTBAMMessage(username);
        hwtbamMessage.setAction("login");

        // length constraints
        if (username.length() > 15) {
            hwtbamMessage.setResult("failure");
            hwtbamMessage.setMessage("Username can only be 15 letters long.");
            return hwtbamMessage;
        }

        if (password.length() > 15) {
            hwtbamMessage.setResult("failure");
            hwtbamMessage.setMessage("Password can only be 15 letters long.");
            return hwtbamMessage;
        }

        if (username == null) {
            hwtbamMessage.setResult("failure");
            hwtbamMessage.setMessage("Username cannot be null.");
            return hwtbamMessage;
        }

        if (password == null) {
            hwtbamMessage.setResult("failure");
            hwtbamMessage.setMessage("Password cannot be null.");
            return hwtbamMessage;
        }

        // try to run sql
        try {
            // check if user exists
            if (userExists(username)) { // exists
                if (isCorrectCredentials(username, password)) {
                    boolean updatedTime = setTimeLastLogin(username);
                    boolean updatedPlaying = setCurrentlyPlaying(username, "true");
                    boolean updateRound = setCurrentRound(username, 1);

                    // try to update the last_login field
                    if (updatedTime & updatedPlaying & updateRound) {
                        hwtbamMessage.setResult("success");
                        hwtbamMessage.setMessage("login successful for: " + username);
                    } else { // did not update
                        // output message
                        hwtbamMessage.setResult("failure");
                        hwtbamMessage.setMessage("failed to update 'lastLogin' for username: " + username);
                    }

                } else { // incorrect password
                    hwtbamMessage.setResult("failure");
                    hwtbamMessage.setMessage("Your password is incorrect for: " + username);
                }
            } else { // user does not exist
                hwtbamMessage.setResult("failure");
                hwtbamMessage.setMessage("Username: " + username + " does not exist.");
            }
        } catch (SQLException e) { // if something fk up
            System.out.println("-\n- SQL ERROR (loginUser): something happened\n-\n");
        }

        // return message
        return hwtbamMessage;
    }

    /**
     * Method:
     *     loginUser
     *
     * Purpose:
     *     Runs series of sql processes to login user (or reject)
     *
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    public HWTBAMMessage logoutUser(String username, String password) throws SQLException {
        // prepare output message
        HWTBAMMessage hwtbamMessage = new HWTBAMMessage(username);
        hwtbamMessage.setAction("logout");

        // length constraints
        if (username.length() > 15) {
            hwtbamMessage.setResult("failure");
            hwtbamMessage.setMessage("Username can only be 15 letters long.");
            return hwtbamMessage;
        }

        if (password.length() > 15) {
            hwtbamMessage.setResult("failure");
            hwtbamMessage.setMessage("Password can only be 15 letters long.");
            return hwtbamMessage;
        }

        if (username == null) {
            hwtbamMessage.setResult("failure");
            hwtbamMessage.setMessage("Username cannot be null.");
            return hwtbamMessage;
        }

        if (password == null) {
            hwtbamMessage.setResult("failure");
            hwtbamMessage.setMessage("Password cannot be null.");
            return hwtbamMessage;
        }

        // try to run sql
        try {
            // check if user exists
            if (userExists(username)) { // exists
                if (isCorrectCredentials(username, password)) {
                    //TODO - uncomment after refactor
                    boolean updatedPlaying = setCurrentlyPlaying(username, "false");
                    boolean updatedRound = setCurrentRound(username, 0);

                    // try to update the last_login field
                    if (updatedPlaying) {
                        hwtbamMessage.setResult("success");
                        hwtbamMessage.setMessage("login successful for: " + username);
                    } else { // did not update
                        // output message
                        hwtbamMessage.setResult("failure");
                        hwtbamMessage.setMessage("failed to update 'lastLogin' for username: " + username);
                    }

                } else { // incorrect password
                    hwtbamMessage.setResult("failure");
                    hwtbamMessage.setMessage("Your password is incorrect for: " + username);
                }
            } else { // user does not exist
                hwtbamMessage.setResult("failure");
                hwtbamMessage.setMessage("Username: " + username + " does not exist.");
            }
        } catch (SQLException e) { // if something fk up
            System.out.println("-\n- SQL ERROR (loginUser): something happened\n-\n");
        }

        // return message
        return hwtbamMessage;
    }

    /**
     * Method:
     *     registerUser
     *
     * Purpose:
     *     Runs series of sql processes to register user (or reject)
     *
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    public HWTBAMMessage registerUser(String username, String password) throws SQLException {
        // prepare statements
        // sets first registration date and first login
        // now / null (yet to login)
        String query = """
                    INSERT INTO Users (username, password, balance, time_registered, last_login, currently_playing, current_round)
                    VALUES (?,?, 0, NOW(), 0, false, 0); 
                """;
        PreparedStatement registerStatement = connection.prepareStatement(query);

        // prepare message to be returned
        HWTBAMMessage hwtbamMessage = new HWTBAMMessage(username);
        hwtbamMessage.setAction("register");

        // length constraints
        if (username.length() > 15) {
            hwtbamMessage.setResult("failure");
            hwtbamMessage.setMessage("Username can only be 15 letters long.");
            return hwtbamMessage;
        }

        if (password.length() > 15) {
            hwtbamMessage.setResult("failure");
            hwtbamMessage.setMessage("Password can only be 15 letters long.");
            return hwtbamMessage;
        }

        if (username == null) {
            hwtbamMessage.setResult("failure");
            hwtbamMessage.setMessage("Username cannot be null.");
            return hwtbamMessage;
        }

        if (password == null) {
            hwtbamMessage.setResult("failure");
            hwtbamMessage.setMessage("Password cannot be null.");
            return hwtbamMessage;
        }

        // start sql
        try {
            if (!userExists(username)) { // user doesn't exist -> register user
                // execute register
                registerStatement.setString(1, username);
                registerStatement.setString(2, password);
                registerStatement.executeUpdate();

                hwtbamMessage.setResult("success");
                hwtbamMessage.setMessage("username: " + username + " was added to the database.");
            } else { // user does exist
                hwtbamMessage.setResult("failure");
                hwtbamMessage.setMessage("username: " + username + " already exists.");
            }
        } catch (SQLException e) { // if something fk up
            System.out.println("-\n- SQL ERROR (register): something happened\n-\n");
            e.printStackTrace();
        }

        // return message
        return hwtbamMessage;
    }

    /**
     * Method:
     *     removeUser
     *
     * Purpose:
     *     Runs series of sql processes to remove user (or reject)
     *
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    public HWTBAMMessage removeUser(String username, String password) throws SQLException {
        // prepare statement
        String query = """
                            DELETE FROM Users
                            WHERE username = ?;
                        """;
        PreparedStatement removeUserStatement = connection.prepareStatement(query);

        // prepare output message
        HWTBAMMessage hwtbamMessage = new HWTBAMMessage(username);
        hwtbamMessage.setAction("removeUser");

        // run sql
        try {
            // check if user exists
            if (userExists(username)) { // exists
                if (isCorrectCredentials(username, password)) { // correct credentials -> remove
                    System.out.println("CORRECT");
                    removeUserStatement.setString(1, username);
                    int outcome = removeUserStatement.executeUpdate();

                    if (outcome > 0) { // 1 sql did something
                        hwtbamMessage.setResult("success");
                        hwtbamMessage.setMessage("username: " + username + " was removed from the database");
                    } else { // 0 sql did nothing
                        hwtbamMessage.setResult("failure");
                        hwtbamMessage.setMessage("Failed to remove username: " + username);
                    }
                } else { // incorrect credentials
                    hwtbamMessage.setResult("failure");
                    hwtbamMessage.setMessage("Incorrect password for username: " + username);
                }
            } else { // username does not exist
                hwtbamMessage.setResult("failure");
                hwtbamMessage.setMessage("Username: " + username + " does not exist in database.");
            }
        } catch (SQLException e) { // if something fk up
            System.out.println("-\n- SQL ERROR(removeUser): something happened\n-\n");
        } finally {
            // close statement
            removeUserStatement.close();
        }

        return hwtbamMessage;
    }

    /**
     * Method:
     *     addRoundToGameLog
     *
     * Purpose:
     *     run sql process to add a round to the GameLog under username with round exit
     *
     * @param username
     * @param round
     * @return message
     * @throws SQLException
     */
    public HWTBAMMessage addRoundToGameLog(String username, int round) throws SQLException {
        String query = """
                INSERT INTO GameLog (username, round_exit, time_completed)
                VALUES (?, ?, NOW());
                """;
        PreparedStatement addRoundToGameLogStatement = connection.prepareStatement(query);

        // prepare message to be returned
        HWTBAMMessage hwtbamMessage = new HWTBAMMessage(username);
        hwtbamMessage.setAction("addRound");
        try {
            addRoundToGameLogStatement.setString(1, username);
            addRoundToGameLogStatement.setInt(2, round);
            int outcome = addRoundToGameLogStatement.executeUpdate();

            if (outcome > 0) { // changed
                hwtbamMessage.setResult("success");
                hwtbamMessage.setMessage("username: " + username + ", round: " + round + " added to GameLog");
            } else {
                hwtbamMessage.setResult("failure");
                hwtbamMessage.setMessage("failed to add - username: " + username + ", round: " + round + " added to GameLog");
            }

        } catch (SQLException e) { // if something fk up
            System.out.println("-\n- SQL ERROR (addRoundToGameLog): something happened\n-\n");
            e.printStackTrace();
        } finally {
            // close statement
            addRoundToGameLogStatement.close();
        }

        return  hwtbamMessage;
    }

    /**
     * Method:
     *     getCurrentlyPlayingLeaderBoard
     *
     * Purpose:
     *     run sql process to obtain the leaderboard of current players
     *
     * @param direction ('asc', 'desc', all other)
     * @return message
     * @throws SQLException
     */
    public HWTBAMMessage getCurrentlyPlayingLeaderBoard(String direction) throws SQLException {
        if (direction.equals("asc")) {
            direction = "ORDER BY current_round ASC;";
        } else if (direction.equals("desc")) {
            direction = "ORDER BY current_round DESC;";
        } else {
            direction = ";";
        }

        // prepare statement
        String query = """
                SELECT username, current_round FROM Users
                WHERE currently_playing = "true"
                """ + direction;
        PreparedStatement getCurrentlyPlayingStatement = connection.prepareStatement(query);

        // prepare output message
        HWTBAMMessage hwtbamMessage = new HWTBAMMessage(null);
        hwtbamMessage.setAction("getCurrentlyPlayingLeaderBoard");

        try {
            ResultSet rs = getCurrentlyPlayingStatement.executeQuery();
            hwtbamMessage.setResult("success");
            hwtbamMessage.setMessage((getCurrentLeaderBoardToString(rs)));
        } catch (SQLException e) { // if something fk up
            System.out.println("-\n- SQL ERROR (getCurrentlyPlaying): something happened\n-\n");
        } finally {
            // close statement
            getCurrentlyPlayingStatement.close();
        }

        // return message
        return hwtbamMessage;
    }

    public HWTBAMMessage getUserBalance(String username) throws SQLException {

        // prepare statement
        String query = """
                SELECT balance FROM Users
                WHERE username = ?
                """;
        PreparedStatement getUserBalanceStatement = connection.prepareStatement(query);

        // prepare output message
        HWTBAMMessage hwtbamMessage = new HWTBAMMessage(null);
        hwtbamMessage.setAction("getCurrentUserBalance");

        try {
            getUserBalanceStatement.setString(1, username);
            ResultSet resultSet = getUserBalanceStatement.executeQuery();

            if (resultSet.next()) {
                hwtbamMessage.setResult("success");
                Integer balance = resultSet.getInt("balance");
                hwtbamMessage.setMessage(balance.toString());
            }
        } catch (SQLException e) { // if something fk up
            System.out.println("-\n- SQL ERROR (getUserBalance): something happened\n-\n");
        } finally {
            // close statement
            getUserBalanceStatement.close();
        }

        // return message
        return hwtbamMessage;
    }

    public HWTBAMMessage getUserLevel(String username) throws SQLException {

        // prepare statement
        String query = """
                SELECT current_round FROM Users
                WHERE username = ?
                """;
        PreparedStatement getUserLevelStatement = connection.prepareStatement(query);

        // prepare output message
        HWTBAMMessage hwtbamMessage = new HWTBAMMessage(null);
        hwtbamMessage.setAction("getCurrentUserLevel");

        try {
            getUserLevelStatement.setString(1, username);
            ResultSet resultSet = getUserLevelStatement.executeQuery();

            if (resultSet.next()) {
                hwtbamMessage.setResult("success");
                Integer level = resultSet.getInt("current_round");
                hwtbamMessage.setMessage(level.toString());
            }
        } catch (SQLException e) { // if something fk up
            System.out.println("-\n- SQL ERROR (getUserLevel): something happened\n-\n");
        } finally {
            // close statement
            getUserLevelStatement.close();
        }

        // return message
        return hwtbamMessage;
    }


    /**
     * Method:
     *     getAllTimePlayingLeaderBoard
     *
     * Purpose:
     *     run sql process to obtain the leaderboard of all time
     *
     * @param direction ('asc', 'desc', all other)
     * @return message
     * @throws SQLException
     */
    public HWTBAMMessage getAllTimeLeaderBoard(String direction) throws SQLException {
        if (direction.equals("asc")) {
            direction = "ORDER BY round_exit ASC;";
        } else if (direction.equals("desc")) {
            direction = "ORDER BY round_exit DESC;";
        } else {
            direction = ";";
        }

        // prepare statement
        String query = """
                SELECT username, round_exit FROM GameLog
                """ + direction;
        PreparedStatement getCurrentlyPlayingStatement = connection.prepareStatement(query);

        // prepare output message
        HWTBAMMessage hwtbamMessage = new HWTBAMMessage(null);
        hwtbamMessage.setAction("getAllTimeLeaderBoard");

        try {
            ResultSet rs = getCurrentlyPlayingStatement.executeQuery();
            hwtbamMessage.setResult("success");
            hwtbamMessage.setMessage((getAllTimeLeaderBoardToString(rs)));
        } catch (SQLException e) { // if something fk up
            System.out.println("-\n- SQL ERROR (getCurrentlyPlaying): something happened\n-\n");
        } finally {
            // close statement
            getCurrentlyPlayingStatement.close();
        }

        // return message
        return hwtbamMessage;
    }

    public HWTBAMMessage setBalance(String username, int value) throws SQLException {
        // prepare statement
        String query = """
                        UPDATE Users
                        SET balance = ?
                        WHERE username = ?;
                        """;
        PreparedStatement setBalance = connection.prepareStatement(query);

        // prepare output message
        HWTBAMMessage hwtbamMessage = new HWTBAMMessage(null);
        hwtbamMessage.setAction("incrementCurrentRound");

        // init false
        boolean updated = false;

        // try to run sql
        try {
            setBalance.setInt(1, value);
            setBalance.setString(2, username);
            int outcome = setBalance.executeUpdate();

            if (outcome > 0) { // changed
                updated = true;
                hwtbamMessage.setResult("success");
                hwtbamMessage.setResult("username: " + username + " set balance: " + value);
            }
        } catch (SQLException e) { // if something fk up (updated is still false here)
            System.out.println("-\n- SQL ERRROR (incrementCurrentRound): something happened\n-\n");
        } finally {
            // close statement
            setBalance.close();
        }

        // return outcome
        return hwtbamMessage;
    }

    /**
     * Method:
     *     getGameLogOfUser
     *
     * Purpose:
     *     get all games in GameLog for user
     *
     * @param username
     * @param direction
     * @return
     * @throws SQLException
     */
    public HWTBAMMessage getGameLogOfUser(String username, String direction) throws SQLException {
        if (direction.equals("asc")) {
            direction = "ORDER BY round_exit ASC;";
        } else if (direction.equals("desc")) {
            direction = "ORDER BY round_exit DESC;";
        } else {
            direction = ";";
        }

        // prepare statement
        String query = """
                SELECT * FROM GameLog
                WHERE username = ?
                """ + direction;
        PreparedStatement getGameLogOfUserStatement = connection.prepareStatement(query);

        // prepare output message
        HWTBAMMessage hwtbamMessage = new HWTBAMMessage(null);
        hwtbamMessage.setAction("getGameLogOfUser");

        try {
            // check if user exists
            if (userExists(username)) {
                // check game log exists
                if (hasUserLoggedGame(username)) {
                    getGameLogOfUserStatement.setString(1, username);
                    ResultSet rs = getGameLogOfUserStatement.executeQuery();
                    hwtbamMessage.setResult("success");
                    hwtbamMessage.setMessage((getGameLogOfUserToString(rs)));
                } else {
                    hwtbamMessage.setResult("failure");
                    hwtbamMessage.setMessage("user: " + username + " has not completed any games");
                }
            } else {
                hwtbamMessage.setResult("failure");
                hwtbamMessage.setMessage("user: " + username + " does not exists; cannot return GameLog");
            }
        } catch (SQLException e) { // if something fk up
            System.out.println("-\n- SQL ERROR (getGameLogOfUser): something happened\n-\n");
        } finally {
            // close statement
            getGameLogOfUserStatement.close();
        }

        // return message
        return hwtbamMessage;
    }

    /**
     * Method:
     *     parseQuestionResults
     *
     * Purpose:
     *     parse question result record into a string formatted for h.w.t.b.a.m message
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private String[] parseQuestionResults(ResultSet resultSet) throws SQLException {
        // IMPORTANT: result set not 0 indexed...
        // record[1] - question id
        // record[2] - prompt
        // record[3] - correct answer
        // record[4] - incorrect 1
        // record[5] - incorrect 2
        // record[6] - incorrect 3

        ResultSetMetaData metaData = resultSet.getMetaData();
        int numberOfColumns = metaData.getColumnCount();

        StringBuilder rssb = new StringBuilder();

        while(resultSet.next()) {
            for (int i = 1; i <= numberOfColumns; i++) {
                rssb.append(("%-8s\t" + resultSet.getObject(i)));
            }
        }

        String results = rssb.toString();

        return results.split("%-8s\t");
    }

    /**
     * Method:
     *     getRandomQuestion
     *
     * Purpose:
     *     get a random question from Questions
     *
     * @param difficulty
     * @return
     * @throws SQLException
     */
    public HWTBAMMessage getRandomQuestion(String difficulty) throws SQLException {
        // prepare statement
        String query = """
                           SELECT * FROM Questions
                           WHERE id = ? AND difficulty = ?;
                        """;
        PreparedStatement getQuestion = connection.prepareStatement(query);

        // prepare output
        HWTBAMMessage hwtbamMessage = new HWTBAMMessage(null);
        hwtbamMessage.setAction("getQuestion");

        try {
            // new random for rand questions
            Random random = new Random();
            int id;
            int diffInt;

            // map difficulty to quesiton randomizer
            if (difficulty.equals("easy")) {
                id = random.nextInt(149);
                diffInt = 0;
            } else if (difficulty.equals("medium")) {
                id = random.nextInt(150,222);
                diffInt = 1;
            } else if (difficulty.equals("hard")) {
                id = random.nextInt(223,353);
                diffInt = 2;
            } else {
                id = random.nextInt(149);
                diffInt = 0;
            }

            if (questionExists(id)) { // get question
                // get result set
                getQuestion.setInt(1, id);
                getQuestion.setInt(2, diffInt);
                ResultSet rs = getQuestion.executeQuery();

                String[] record = parseQuestionResults(rs);
                hwtbamMessage.setQuestion(record[2]);
                hwtbamMessage.setCorrectAnswer(record[3]);
                hwtbamMessage.setFirstIncorrectAnswer(record[4]);
                hwtbamMessage.setSecondIncorrectAnswer(record[5]);
                hwtbamMessage.setThirdIncorrectAnswer(record[6]);
                hwtbamMessage.setResult("success");
                hwtbamMessage.setMessage("New Question!");
            } else {
                hwtbamMessage.setResult("failure");
                hwtbamMessage.setMessage("Question: " + id + " not in Questions");
            }
        } catch (SQLException e) { // if something fk up
            System.out.println("-\n- SQL ERROR (getRandomQuestion): something happened\n-\n");
        } finally {
            // close statement
            getQuestion.close();
        }

        // return outcome
        return hwtbamMessage;
    }

    /**
     * Method:
     *     main (testing)
     *
     * Purpose:
     *     used for development testing
     *
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {
        DBConnection db = new DBConnection();
//        System.out.println(db.registerUser("asdlkfajdl21dl24", "askdfjl12").getMessage()); ;
//        System.out.println(db.loginUser("asdlkfajdl21dl24", "asdlk41ldfkjasdasdfa").getMessage()); ;
//      System.out.println(db.registerUser("lbj", "123").getMessage());
//        System.out.println(db.loginUser("lbj", "asdlk41ldfkjasdasdfa").getMessage()); ;
//        System.out.println(db.loginUser("lbj", "123").getMessage()); ;
//        System.out.println(db.removeUser("lbj", "lkaj").getMessage()); ;
//        System.out.println(db.removeUser("lbj", "12cw3").getMessage()); ;
//        System.out.println(db.registerUser("currentTest", "1").getMessage()); ;
//        System.out.println(db.loginUser("currentTest", "1").getMessage()); ;
//        System.out.println(db.logoutUser("currentTest", "2").getMessage()); ;
//        System.out.println(db.logoutUser("currentTest", "1").getMessage()); ;
//        System.out.println(db.registerUser("p1", "1").getMessage()); ;
//        System.out.println(db.loginUser("p1", "1").getMessage()); ;
//        System.out.println(db.registerUser("p2", "1").getMessage()); ;
//        System.out.println(db.loginUser("p2", "1").getMessage()); ;
//        System.out.println(db.registerUser("p3", "1").getMessage()); ;
//        System.out.println(db.loginUser("p3", "1").getMessage()); ;
//        System.out.println(db.getCurrentlyPlayingLeaderBoard().getMessage()); ;
//        System.out.println(db.addRoundToGameLog("lbj", 5).getMessage());
//        System.out.println(db.addRoundToGameLog("lbj", 20).getMessage());
//        System.out.println(db.addRoundToGameLog("lbj", 7).getMessage());
//        System.out.println(db.addRoundToGameLog("lbj", 30).getMessage());
//        System.out.println(db.getAllTimeLeaderBoard("desc").getMessage());
//        System.out.println(db.setBalance("lbj", 20).getMessage());
//        System.out.println(db.getGameLogOfUser("JohnB", "Pass33").getMessage());
//        System.out.println(db.getGameLogOfUser("lbj", "desc").getMessage());
//        System.out.println(db.getGameLogOfUser("lbj",  "").getMessage());
//        System.out.println(db.registerUser( "lbj", "test").getMessage());
//        System.out.println(db.setTimeLastLogin("2"));
//        System.out.print(db.setCurrentRound( "NewUser1",  4));
//       System.out.print(db.getUserBalance( "Joe").getMessage());
//       System.out.print(db.getUserLevel( "Joe").getMessage());
    }
}