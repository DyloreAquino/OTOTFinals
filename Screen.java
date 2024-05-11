import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class Screen implements Drawable {

    private int x, y;

    private int WIDTH = 800;
    private int HEIGHT = 550;

    private Image toPrint;

    public void setVisible(){
        x = 200;
        y = 25;
    }

    public void setInvisible() {
        x = -9999;
        y = -9999;
    }

    protected void updateImage(ImageIcon imgIcon){
        toPrint = imgIcon.getImage();
    }

    public void draw(Graphics2D g2d){
        g2d.drawImage(toPrint, x, y, WIDTH, HEIGHT, null);
    }
}
