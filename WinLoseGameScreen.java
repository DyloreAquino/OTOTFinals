import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

public class WinLoseGameScreen extends Screen{
    
    private int x, y;

    private ImageIcon resultDisplay;
    private String resultFilename;

    public WinLoseGameScreen(int x, int y){
        this.x = x;
        this.y = y;

        resultFilename = " ";
    }

    public void changeState(boolean playerHasWon){
        if (playerHasWon) {
            resultFilename = "you win game.png";
        } else {
            resultFilename = "you lose game.png";
        }
        
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        resultDisplay = new ImageIcon(resultFilename);
        super.updateImage(resultDisplay);
        super.draw(g2d);
    }
}
