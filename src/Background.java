import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Background {


    private SpriteSheet backgroundTiles;
    private int[][] backgroundTileID =
            new int[GamePanel.MAX_SCREEN_COL][GamePanel.MAX_SCREEN_ROW];

    public Background() {
        getBackground();
    }


    public void getBackground() {
        try {
            backgroundTiles = new SpriteSheet(ImageIO.read(new File("assets/background.png")),
                    Main.DEFAULT_SPRITE_SIZE, Main.DEFAULT_SPRITE_SIZE, 5, 1);
        } catch (IOException ioe) {
            System.out.println("Background spritesheet error");
            ioe.printStackTrace();
            System.exit(1);
        }
    }

    private int count = 0;

    public void update() {
        if (count == 0) {
            for (int i = 0; i < GamePanel.MAX_SCREEN_COL; i++) {
                for (int j = 0; j < GamePanel.MAX_SCREEN_ROW; j++) {
                    backgroundTileID[i][j] = Main.rng.nextInt(0,10);
                    backgroundTileID[i][j] = Math.clamp(backgroundTileID[i][j],0,4);
                }
            }
            count = 10;
        } else {
            count--;
        }
    }

    public void draw(Graphics2D g2d) {
        BufferedImage image = null;
        for (int i = 0; i < GamePanel.MAX_SCREEN_COL; i++) {
            for (int j = 0; j < GamePanel.MAX_SCREEN_ROW; j++) {
                image = backgroundTiles.grabImage(0,backgroundTileID[i][j]);
                g2d.drawImage(image,i*Main.DEFAULT_SPRITE_SIZE*Main.DEFAULT_SCALE_FACTOR,j*Main.DEFAULT_SPRITE_SIZE*Main.DEFAULT_SCALE_FACTOR,
                        Main.DEFAULT_SPRITE_SIZE * Main.DEFAULT_SCALE_FACTOR, Main.DEFAULT_SPRITE_SIZE * Main.DEFAULT_SCALE_FACTOR,null);
            }
        }
    }

}
