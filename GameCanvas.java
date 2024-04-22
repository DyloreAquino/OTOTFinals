import java.awt.*;
import javax.swing.*;
import java.util.*;

public class GameCanvas extends JComponent {

    private int width, height;

    private Player player;
    
    public GameCanvas(int width, int height){
        this.width = width;
        this.height = height;

        player = new Player(450, 100, 10, 10, 50);
    }

    @Override
    protected void paintComponent(Graphics g){

        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );
        g2d.setRenderingHints(rh);

        player.draw(g2d);
    }

    public Player getPlayer(){
        return player;
    }
}
