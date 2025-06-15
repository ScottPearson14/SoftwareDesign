package game;

import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * The GameOverDisplay is a class that creates a window that will be displayed when users successfully complete the game
 * Once users exit out of this window, the game terminates
 */
public class GameOverDisplay extends JFrame{
    /**
     * Method that displays Game Over JFrame when user completes game
     */
    public GameOverDisplay() {
        setTitle("You are now a millionaire!!!!!!!!!!!!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1250,900);

        // Load the image
        try {
            // use buffered image to hold the png to be displayed
            BufferedImage image = ImageIO.read(new File("Final_Project_SWD/res/moneyMoneyMoney.jpg"));
            JLabel imageLabel = new JLabel(new ImageIcon(image)); // attach the image
            getContentPane().add(imageLabel); //add imageLabel to the content pane
        } catch (IOException e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    /**
     * Method to display the game over screen
     */
    public static void showGameOverScreen() {
        new GameOverDisplay();
    }
}
