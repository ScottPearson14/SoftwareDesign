package game;

import network.Client;

/**
 * The GameDriver class is a class that is responsible for initiating and managing a game of Who Wants to Be a
 * Millionaire. It creates a new `Game` instance and sets its title and size.
 */
public class GameDriver implements Runnable {
    /** Client that represents a player connected to the game server */
    private Client client;

    /**
     * Constructor that creates a new `GameDriver` instance with the specified client.
     *
     * @param client The client object representing the player.
     */
    public GameDriver(Client client) {
        this.client = client;
    }

    /**
     * Starts the game by creating a new `Game` instance and setting its title and size.
     */
    public void start(){
        Game game = new Game(client);
        game.setTitle("Who Wants To be a Millionaire USER: " + client.getUsername());
        game.setSize(700, 500);
    }

    @Override
    public void run() {
        start();
    }
}