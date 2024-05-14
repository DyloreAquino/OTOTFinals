import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class Wall implements Drawable{
    
    private final int SIZE = 50;

    private int x, y;
    private int column, row;
    // There are 16 columns, 10 rows
    private String wallSpriteFileName;
    private ImageIcon wallSprite;
    private int imageNum;

    public Wall(int column, int row, int imageNum) {
        this.column = column;
        this.row = row;

        x = 150 + (column * 50);
        y = row * 50;

        this.imageNum = imageNum;
    }

    private void updateImage() {
        switch (imageNum) {
            case 1:
                wallSpriteFileName = "wall.png";
                break;

            case 2: 
                wallSpriteFileName = "wall 2.png";
                break;

            case 3:
                wallSpriteFileName = "wall 3.png";    
                break;

            case 4:
                wallSpriteFileName = "wall 4.png";
                break;

            default:
                break;
        }
    }

    public void draw(Graphics2D g2d){
        updateImage();
        wallSprite = new ImageIcon(wallSpriteFileName);
        Image wallImgSprite = wallSprite.getImage();
        g2d.drawImage(wallImgSprite, x, y, SIZE, SIZE, null);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getSize(){
        return SIZE;
    }
}
