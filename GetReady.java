import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

public class GetReady extends Screen {
    
    private int x, y;

    private ImageIcon gettingReady;

    public GetReady(int x, int y){
        this.x = x;
        this.y = y;

        gettingReady = new ImageIcon("get ready.png");
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        super.updateImage(gettingReady);
        super.draw(g2d);
    }

}
