import java.awt.*;
import javax.swing.*;
import java.util.*;

public class GameCanvas extends JComponent {

    private int width, height;

    private Player player;
    private Player opponent;

    private int playerID;

    private Wall wall1, wall2, wall3, wall4;
    private Blob blob1;

    private WaitingScreen waitingScreen;
    private WaitingForOtherPlayerScreen waitingForOtherPlayerScreen;
    private GetReadyScreen getReadyScreen;
    private WinLoseScreen winLoseScreen;

    private ArrayList<Drawable> drawables;
    private ArrayList<Wall> walls;
    private ArrayList<Blob> blobs;
    private ArrayList<Screen> screens;

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
        screens = new ArrayList<Screen>();

        waitingForOtherPlayerScreen = new WaitingForOtherPlayerScreen(-9999, -9999);
        waitingScreen = new WaitingScreen(-9999, -9999);
        winLoseScreen = new WinLoseScreen(-9999, -9999);
        getReadyScreen = new GetReadyScreen(-9999, -9999);

        screens.add(waitingForOtherPlayerScreen);
        screens.add(waitingScreen);
        screens.add(winLoseScreen);
        screens.add(getReadyScreen);

        this.playerID = playerID;
    }

    public void setUpLevel(int level){
        lvlMaker.setUpFloor(drawables);
        lvlMaker.setUpLevel(level,
                            playerID,
                            drawables, 
                            walls,
                            blobs);
        player = lvlMaker.getPlayer();
        opponent = lvlMaker.getOpponent();
        setUpScreens();
    }

    public void clearLevel(){
        drawables.clear();
    }

    public void setUpScreens() {
        for (Screen screen: screens){
            drawables.add(screen);
        }
    }

    public void removeScreens(){
        for (Screen screen: screens){
            screen.setInvisible();
        }
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

    public WaitingForOtherPlayerScreen getWaitingForOtherPlayerScreen(){
        return waitingForOtherPlayerScreen;
    }

    public WinLoseScreen getWinLoseScreen(){
        return winLoseScreen;
    }

    public GetReadyScreen getReadyScreen(){
        return getReadyScreen;
    }
}
