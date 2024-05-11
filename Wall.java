import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class Wall implements Drawable{
    
    private final int SIZE = 50;

    private int x, y;
    private int column, row;
    // There are 16 columns, 10 rows
    private ImageIcon wallSprite;
    private Image wallImgSprite;

    public Wall(int column, int row) {
        this.column = column;
        this.row = row;

        x = 150 + (column * 50);
        y = row * 50;
    }

    public void draw(Graphics2D g2d){
        wallSprite = new ImageIcon("wall.png");
        wallImgSprite = wallSprite.getImage();
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
