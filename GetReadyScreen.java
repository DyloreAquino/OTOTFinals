import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

public class GetReadyScreen extends Screen {
    
    private int x, y;

    private ImageIcon gettingReady;

    public GetReadyScreen(int x, int y){
        this.x = x;
        this.y = y;

        gettingReady = new ImageIcon("get ready.png");
        super.updateImage(gettingReady);
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        
        super.draw(g2d);
    }

}
