import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

public class WaitingForOtherPlayerScreen extends Screen{
    
    private int x, y;

    private ImageIcon waitingForOther;

    public WaitingForOtherPlayerScreen(int x, int y){
        this.x = x;
        this.y = y;

        waitingForOther = new ImageIcon("waiting for other player.png");
        
    }

    @Override
    public void draw(Graphics2D g2d) {
        super.updateImage(waitingForOther);
        super.draw(g2d);
    }
}
