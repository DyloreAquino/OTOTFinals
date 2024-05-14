import java.awt.*;
import javax.swing.*;

public class GameBackground implements Drawable{

    private ImageIcon gameBackground;
    private Image toPrint;

    public void draw(Graphics2D g2d){
        gameBackground = new ImageIcon("game background.png");
        toPrint = gameBackground.getImage();
        g2d.drawImage(toPrint, 0, 0, 1200, 600, null);
    }
}
