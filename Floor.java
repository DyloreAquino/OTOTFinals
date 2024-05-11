import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class Floor implements Drawable{
    
    private final int SIZE = 50;

    private int x, y;
    private int column, row;
    // There are 16 columns, 10 rows
    private ImageIcon floorSprite;
    private Image floorImgSprite;

    public Floor(int column, int row) {
        this.column = column;
        this.row = row;

        x = 150 + (column * 50);
        y = row * 50;
    }

    public void draw(Graphics2D g2d){
        floorSprite = new ImageIcon("floor.png");
        floorImgSprite = floorSprite.getImage();
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
