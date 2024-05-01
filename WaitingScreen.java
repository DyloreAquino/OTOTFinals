import java.awt.*;
import java.awt.geom.*;


public class WaitingScreen implements Drawable {

    private int x, y;

    private int WIDTH = 800;
    private int HEIGHT = 500;

    private Rectangle2D.Double waitingScreenSprite;
    private Color waitingScreenColor;

    public WaitingScreen(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setVisible(){
        x = 200;
        y = 50;
    }

    public void setInvisible() {
        x = -9999;
        y = -9999;
    }

    public void draw(Graphics2D g2d){
        waitingScreenColor = Color.BLACK;
        waitingScreenSprite = new Rectangle2D.Double(x, y, WIDTH, HEIGHT);
        g2d.setColor(waitingScreenColor);
        g2d.fill(waitingScreenSprite);
    }
}
