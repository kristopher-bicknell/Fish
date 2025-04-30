import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    final static int ORIGINAL_TILE_SIZE = 24;
    final static double SCALE = 4;

    final static int TILE_SIZE = (int)(ORIGINAL_TILE_SIZE * SCALE);
    final static int MAX_SCREEN_COL = 8;
    final static int MAX_SCREEN_ROW = 5;
    public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;

    int FPS = 30;
    //TODO: set to 10
    public static int gameState = 11; //for the title scene, because i plan ahead
    //public static int gameState = 0;
    public int totalScore = 0;

    Thread gameThread;
    //KeyHandler keyHandler = new KeyHandler(this);
    Main mainWindow;

    public GamePanel(Main mainWindow) {
        this.mainWindow = mainWindow;
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        //this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
        this.requestFocus();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        //game loop :)
        while (gameThread != null) {
            //Update game info
            update();
            //Draw screen
            repaint();
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;
                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
            } catch (Exception e) {
                e.printStackTrace();
            }

            nextDrawTime += drawInterval;
        }
    }

    //TODO: debug
    Fish fish = new Fish(1,1);
    Fish fish2 = new Fish(2,1);
    Fish fish3 = new Fish(3,1);
    Fish perch = new Fish(2,2);
    Background background = new Background();

    public void update() {
        fish.update();
        fish2.update();
        fish3.update();
        perch.update();
        background.update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;

        background.draw(g2d);
        fish.draw(g2d);
        fish2.draw(g2d);
        fish3.draw(g2d);
        perch.draw(g2d);

    }
}
