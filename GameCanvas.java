import java.awt.*;
import javax.swing.*;
import java.util.*;

public class GameCanvas extends JComponent {

    private int width, height;

    private Player player;
    private Player opponent;

    private Wall wall1, wall2, wall3, wall4;
    private Blob blob1;

    private WaitingScreen waitingScreen;

    private ArrayList<Drawable> drawables;
    private ArrayList<Wall> walls;
    private ArrayList<Blob> blobs;

    private LevelMaker lvlMaker;
    
    public GameCanvas(int width, int height, int playerID){
        // When initializing the coordinates for a player, take note:
        // There are 16 columns, 10 rows
        this.width = width;
        this.height = height;

        lvlMaker = new LevelMaker(1);

        drawables = new ArrayList<Drawable>();
        walls = new ArrayList<Wall>();
        blobs = new ArrayList<Blob>();

        lvlMaker.setUpFloor(drawables);
        setUpWalls();
        setUpPlayer(playerID);
        setUpBlobs();
        setUpWaitingScreen(); 

    }

    public void setUpPlayer(int playerID) {
        if (playerID == 1){
            player = new Player(1, 1, 2, 40, 1);
            opponent = new Player(16, 1, 3, 40, 2);
        } else {
            player = new Player(16, 1, 3, 40, 2);
            opponent = new Player(1, 1, 2, 40, 1);
        }
        drawables.add(player);
        drawables.add(opponent);

    }

    private void setUpWalls(){
        wall1 = new Wall(2, 1);
        wall2 = new Wall(3, 1);
        wall3 = new Wall(3, 2);
        wall4 = new Wall(4, 3);
        walls.add(wall1);
        walls.add(wall2);
        walls.add(wall3);
        walls.add(wall4);
        drawables.add(wall1);
        drawables.add(wall2);
        drawables.add(wall3);
        drawables.add(wall4);
    }

    private void setUpBlobs(){
        blob1 = new Blob(7, 5, "rock");
        blobs.add(blob1);
        drawables.add(blob1);
    }

    private void setUpWaitingScreen() {
        waitingScreen = new WaitingScreen(-9999, -9999);
        drawables.add(waitingScreen);
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
    
    public Player getOpponent(){
        return opponent;
    }

    public ArrayList<Wall> getWalls(){
        return walls;
    }

    public ArrayList<Blob> getBlobs(){
        return blobs;
    }

    public WaitingScreen getWaitingScreen() {
        return waitingScreen;
    }
}
