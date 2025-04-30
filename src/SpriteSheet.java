import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class SpriteSheet {

    private BufferedImage image;
    int width;
    int height;
    int rows;
    int columns;
    BufferedImage[] sprites;


    public SpriteSheet(BufferedImage image, int width, int height, int rows, int columns) {
        this.image = image;
        this.width = width;
        this.height = height;
        this.rows = rows;
        this.columns = columns;
        sprites = new BufferedImage[rows * columns];

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                sprites[(i * columns) + j] = image.getSubimage(i * width, j * height, width, height);
            }
        }

    }

    public BufferedImage getImage() {
        return this.image;
    }

    public BufferedImage grabImage(int row, int col) {
        return sprites[row + col];
    }

}
