import java.awt.*;
import java.awt.geom.*;

public class Wall implements Drawable{
    
    private final int SIZE = 50;

    private int x, y;
    private Color wallColor;
    
    private Rectangle2D.Double wallSprite;

    public Wall(int x, int y) {
        this.x = x;
        this.y = y;

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
