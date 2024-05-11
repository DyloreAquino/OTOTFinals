import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

public class WaitingScreen extends Screen{

    private int x, y;

    private ImageIcon waitingImg;

    public WaitingScreen(int x, int y){
        this.x = x;
        this.y = y;

        waitingImg = new ImageIcon("you lose game.png");
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        super.updateImage(waitingImg);
        super.draw(g2d);
    }
}
