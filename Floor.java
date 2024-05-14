import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class Floor implements Drawable{
    
    private final int SIZE = 50;

    private int x, y;
    private int column, row;
    // There are 16 columns, 10 rows
    private ImageIcon floorSprite;
    private String floorSpriteFileName;
    private int imageNum;

    public Floor(int column, int row, int imageNum) {
        this.column = column;
        this.row = row;
        this.imageNum = imageNum;

        x = 150 + (column * 50);
        y = row * 50;
    }

    private void updateImage() {
        switch (imageNum) {
            case 1:
                floorSpriteFileName = "floor.png";
                break;

            case 2: 
                floorSpriteFileName = "floor 2.png";
                break;

            case 3:
                floorSpriteFileName = "floor 3.png";    
                break;

            case 4:
                floorSpriteFileName = "floor 4.png";
                break;

            default:
                break;
        }
    }

    public void draw(Graphics2D g2d){
        updateImage();
        floorSprite = new ImageIcon(floorSpriteFileName);
        Image floorImgSprite = floorSprite.getImage();
        g2d.drawImage(floorImgSprite, x, y, SIZE, SIZE, null);
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
