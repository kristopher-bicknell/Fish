import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

public class Fish {
    GamePanel gp;
    private int fishType, fishSize;
    SpriteSheet fishSheet;

    int x = 50;
    int y = 50;

    // Fish sizes:
    // 1: 24x24 sprite (1 sprite)
    // 2: 48x24 sprite (2 sprites)

    public Fish(int fishType, int fishSize) {
        this.fishType = fishType;
        this.fishSize = fishSize;

        x = Main.rng.nextInt(50,250);
        y = Main.rng.nextInt(50,250);

        loadSpriteSheet();
    }

    private void loadSpriteSheet() {
        try {
            fishSheet = new SpriteSheet(ImageIO.read(new File(getFishSheetLocation())),Main.DEFAULT_SPRITE_SIZE*fishSize,Main.DEFAULT_SPRITE_SIZE,2,2);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private String getFishSheetLocation() {
        String returnString = "assets/fish/";
        returnString = returnString.concat(fishSize + "/");
        returnString = returnString.concat(fishType + ".png");

        return returnString;
    }

    public void draw(Graphics2D g2d) {
        BufferedImage image = null;

        int spriteRow = 0;
        if (xMove < 0) {
            spriteRow = 1;
        }

        image = fishSheet.grabImage(spriteRow,0);

        g2d.drawImage(image,x,y, Main.DEFAULT_SPRITE_SIZE * Main.DEFAULT_SCALE_FACTOR * fishSize, Main.DEFAULT_SPRITE_SIZE * Main.DEFAULT_SCALE_FACTOR,null);

    }

    private int swimCount = 0;
    private int xMove = 0;
    private int yMove = 0;
    boolean moveDir = true;

    public void update() {
        if (swimCount == 0) {
            swimCount = Main.rng.nextInt(30,60);
            moveDir = Main.rng.nextBoolean();
            xMove = Main.rng.nextInt(-3, 3);
            if (moveDir) {
                yMove = 0;
            } else {
                yMove = Main.rng.nextInt(-3, 3);
            }
        } else {
            x += xMove;
            if (x > GamePanel.SCREEN_WIDTH) {
                x = 0 + Main.DEFAULT_SPRITE_SIZE * Main.DEFAULT_SCALE_FACTOR;
            } else if (x < 0) {
                x = GamePanel.SCREEN_WIDTH - Main.DEFAULT_SPRITE_SIZE * Main.DEFAULT_SCALE_FACTOR;
            }
            y += yMove;
            y = Math.clamp(y,0,600);
            swimCount--;
        }
    }

}
