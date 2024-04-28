import java.awt.*;
import javax.swing.*;
import java.util.*;

public class GameCanvas extends JComponent {

    private int width, height;

    private Player player;

    private Wall wall1, wall2, wall3, wall4;

    private ArrayList<Drawable> drawables;
    private ArrayList<Wall> walls;
    
    public GameCanvas(int width, int height){
        this.width = width;
        this.height = height;

        walls = new ArrayList<Wall>();

        player = new Player(450, 100, 3, 40);
        setUpWalls();

        drawables = new ArrayList<Drawable>();
        drawables.add(player);
        drawables.add(wall1);
        drawables.add(wall2);
        drawables.add(wall3);
        drawables.add(wall4);
    }

    public void setUpWalls(){
        wall1 = new Wall(700, 100);
        wall2 = new Wall(400, 500);
        wall3 = new Wall(350, 300);
        wall4 = new Wall(500, 40);
        walls.add(wall1);
        walls.add(wall2);
        walls.add(wall3);
        walls.add(wall4);
    }

    @Override
    protected void paintComponent(Graphics g){

        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );
        g2d.setRenderingHints(rh);

        for (Drawable obj: drawables){
            obj.draw(g2d);
        }
    }

    public Player getPlayer(){
        return player;
    }

    public ArrayList<Wall> getWalls(){
        return walls;
    }
}
