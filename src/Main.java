import javax.swing.*;
import java.util.Random;

public class Main extends JFrame {

    public static int DEFAULT_SPRITE_SIZE = 24;
    public static int DEFAULT_SCALE_FACTOR = 4;

    public static Random rng = new Random();

    public Main() {
        super("Feesh");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
    }

    public static void main(String[] args) {
        Main mainWindow = new Main();
        GamePanel gamePanel = new GamePanel(mainWindow);
        mainWindow.add(gamePanel);
        mainWindow.pack(); //Causes this Window to be sized to fit the preferred size and layouts of its subcomponents.
        mainWindow.setLocationRelativeTo(null);
        gamePanel.startGameThread();

        mainWindow.revalidate();
    }
}