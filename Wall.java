import java.awt.*;
import java.awt.geom.*;

public class Wall implements Drawable{
    
    private final int SIZE = 50;

    private int x, y;
    private int column, row;
    // There are 16 columns, 10 rows
    private Color wallColor;
    
    private Rectangle2D.Double wallSprite;

    public Wall(int column, int row) {
        this.column = column;
        this.row = row;

        x = 150 + (column * 50);
        y = row * 50;

        wallColor = Color.BLUE;
    }

    public void draw(Graphics2D g2d){
        wallSprite = new Rectangle2D.Double(x, y, SIZE, SIZE);
        g2d.setColor(wallColor);
        g2d.fill(wallSprite);
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
