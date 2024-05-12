import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

public class WinLoseDisplay extends Screen{
    
    private int x, y;

    private ImageIcon ResultDisplay;
    private String ResultFilename;

    public WinLoseDisplay(int x, int y){
        this.x = x;
        this.y = y;

        ResultFilename = "";

        ResultDisplay = new ImageIcon(ResultFilename);
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        
        /*
        switch (player 1 win bool, player 2 win bool, player 1 throw, player 2 throw ) {
            case  :

                ResultFilename = "insert filename of respective case";
                super.updateImage(ResultDisplay);
                super.draw(g2d);
                break;
        
            default:
                break;
        }
        */
    }
}
