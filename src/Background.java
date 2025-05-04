import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Background {


    private SpriteSheet backgroundTiles;
    private SpriteSheet decorTiles;
    public static BufferedImage overlay;
    private int[][] backgroundTileID =
            new int[GamePanel.MAX_SCREEN_COL][GamePanel.MAX_SCREEN_ROW];

    private int[][][] decorTileID =
            new int[GamePanel.MAX_SCREEN_COL][GamePanel.MAX_SCREEN_ROW][2];

    public Background() {
        getBackground();
        getDecor();
    }


    public void getBackground() {
        try {
            backgroundTiles = new SpriteSheet(ImageIO.read(new File("assets/background/background.png")),
                    Main.DEFAULT_SPRITE_SIZE, Main.DEFAULT_SPRITE_SIZE, 5, 3);
            decorTiles = new SpriteSheet(ImageIO.read(new File("assets/background/decor.png")),
                    Main.DEFAULT_SPRITE_SIZE, Main.DEFAULT_SPRITE_SIZE,4,2);
            overlay = ImageIO.read(new File("assets/background/backgroundoverlay.png"));
        } catch (IOException ioe) {
            System.out.println("Background spritesheet error");
            ioe.printStackTrace();
            System.exit(1);
        }
    }

    public void getDecor() {
        int tile;

        for (int i = 0; i < GamePanel.MAX_SCREEN_COL; i++) {
            tile = Main.rng.nextInt(0,4);
            if (tile >= 2) {
                //place kelp, random chance of both types
                decorTileID[i][GamePanel.MAX_SCREEN_ROW - 1][0] = 1;
                boolean type = Main.rng.nextBoolean();
                if (type) {
                    decorTileID[i][GamePanel.MAX_SCREEN_ROW - 1][1] = 0;
                } else {
                    decorTileID[i][GamePanel.MAX_SCREEN_ROW - 1][1] = 2;
                }
            } else {
                decorTileID[i][GamePanel.MAX_SCREEN_ROW - 1][0] = -1;
                decorTileID[i][GamePanel.MAX_SCREEN_ROW - 1][1] = -1;
            }
        }

        for (int i = 0; i < GamePanel.MAX_SCREEN_COL; i++) {
            for (int j = GamePanel.MAX_SCREEN_ROW - 2; j >= 0 ; j--) {
                decorTileID[i][j][0] = -1;
                decorTileID[i][j][1] = decorTileID[i][j + 1][1];
                if (decorTileID[i][j+1][0] > 0) {
                    if (Main.rng.nextBoolean()) {
                        decorTileID[i][j][0] = 1;
                    } else {
                        decorTileID[i][j][0] = 0;
                    }
                }
            }
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
                int background;
                if (i < GamePanel.MAX_SCREEN_COL / 3) {
                    background = 0;
                } else if (i < (GamePanel.MAX_SCREEN_COL / 3) * 2) {
                    background = 1;
                } else {
                    background = 2;
                }
                image = backgroundTiles.grabImage(background, backgroundTileID[i][j]);
                g2d.drawImage(image,i*GamePanel.TILE_SIZE,j*GamePanel.TILE_SIZE,
                        GamePanel.TILE_SIZE, GamePanel.TILE_SIZE,null);
                if (decorTileID[i][j][0] >= 0) {
                    image = decorTiles.grabImage(decorTileID[i][j][0], decorTileID[i][j][1]);
                    g2d.drawImage(image,i*GamePanel.TILE_SIZE,j*GamePanel.TILE_SIZE,
                            GamePanel.TILE_SIZE, GamePanel.TILE_SIZE,null);
                }
            }
        }
    }

}
