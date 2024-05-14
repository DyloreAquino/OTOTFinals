/**
	
	@author Jerold Luther P. Aquino (230413)
    @author Hanzo Ricardo M. Castillo (231365)
	@version May 14, 2024
	
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.
**/

/* GameCanvas class draws out all the drawable objects
*/

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class GameCanvas extends JComponent {

    private int width, height;

    private GameBackground gameBackground;

    private Player player;
    private Player opponent;

    private int playerID;

    private Wall wall1, wall2, wall3, wall4;
    private Blob blob1;

    private WaitingScreen waitingScreen;
    private WaitingForOtherPlayerScreen waitingForOtherPlayerScreen;
    private GetReadyScreen getReadyScreen;
    private WinLoseScreen winLoseScreen;
    private WinLoseGameScreen winLoseGameScreen;
    private TimerScreen timerScreen;
    private BlobIconScreen myBlobIconScreen;
    private BlobIconScreen theirBlobIconScreen;
    private PointsTextScreen pointsTextScreen;

    private ArrayList<Drawable> drawables;
    private ArrayList<Wall> walls;
    private ArrayList<Blob> blobs;
    private ArrayList<Screen> screens;

    private LevelMaker lvlMaker;
    
    
    /**
     * instantiates the draw methods through different types
     * @param width
     * @param height
     * @param playerID
     */
    public GameCanvas(int width, int height, int playerID){
        // When initializing the coordinates for a player, take note:
        // There are 16 columns, 10 rows
        this.width = width;
        this.height = height;

        lvlMaker = new LevelMaker(1);

        drawables = new ArrayList<Drawable>();

        gameBackground = new GameBackground();

        walls = new ArrayList<Wall>();
        blobs = new ArrayList<Blob>();
        screens = new ArrayList<Screen>();


        waitingForOtherPlayerScreen = new WaitingForOtherPlayerScreen(-9999, -9999);
        waitingScreen = new WaitingScreen(-9999, -9999);
        winLoseScreen = new WinLoseScreen(-9999, -9999);
        getReadyScreen = new GetReadyScreen(-9999, -9999);
        winLoseGameScreen = new WinLoseGameScreen(-9999, -9999);
        timerScreen = new TimerScreen(-9999, -9999, playerID);
        
        
        if (playerID == 1) {
            pointsTextScreen = new PointsTextScreen(-9999, -9999, true);
            myBlobIconScreen = new BlobIconScreen(-9999, -9999, true);
            theirBlobIconScreen = new BlobIconScreen(-9999, -9999, false);
        } else if (playerID == 2) {
            pointsTextScreen = new PointsTextScreen(-9999, -9999, false);
            myBlobIconScreen = new BlobIconScreen(-9999, -9999, false);
            theirBlobIconScreen = new BlobIconScreen(-9999, -9999, true);
        }

        screens.add(waitingForOtherPlayerScreen);
        screens.add(waitingScreen);
        screens.add(winLoseScreen);
        screens.add(getReadyScreen);
        screens.add(winLoseGameScreen);
        screens.add(timerScreen);
        screens.add(myBlobIconScreen);
        screens.add(theirBlobIconScreen);
        screens.add(pointsTextScreen);

        drawables.add(gameBackground);

        this.playerID = playerID;
    }

    public void setUpLevel(int level){
        drawables.add(gameBackground);
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

    /**
     * clears the level
     */
    public void clearLevel(){
        walls.clear();
        blobs.clear();
        drawables.clear();
    }

    /**
     * picks the screens
     */
    public void setUpScreens() {
        for (Screen screen: screens){
            drawables.add(screen);
        }
    }

    /**
     * removes the screens
     */
    public void removeScreens(){
        for (Screen screen: screens){
            screen.setInvisible();
        }
    }

    /**
     * draws out all the objects of the game
     */
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

    /**
     * gets player
     * @return
     */
    public Player getPlayer(){
        return player;
    }
    
    /**
     * gets opponent
     * @return
     */
    public Player getOpponent(){
        return opponent;
    }
    /**
     * get walls
     * @return
     */
    public ArrayList<Wall> getWalls(){
        return walls;
    }

    /**
     * get blobs
     * @return
     */
    public ArrayList<Blob> getBlobs(){
        return blobs;
    }
    /**
     * gets waiting screen
     * @return
     */
    public WaitingScreen getWaitingScreen() {
        return waitingScreen;
    }
    /**
     * gets waiting screen
     * @return
     */
    public WaitingForOtherPlayerScreen getWaitingForOtherPlayerScreen(){
        return waitingForOtherPlayerScreen;
    }
    /**gets win lose screen for the round
     * @return
     */
    public WinLoseScreen getWinLoseScreen(){
        return winLoseScreen;
    }

    /**
     * gets get ready screen
     * @return
     */
    public GetReadyScreen getReadyScreen(){
        return getReadyScreen;
    }

    /**
     * gets win lose screen for the whole game
     * @return
     */
    public WinLoseGameScreen getWinLoseGameScreen(){
        return winLoseGameScreen;
    }
    /**
     * gets timer screen
     * @return
     */
    public TimerScreen getTimerScreen(){
        return timerScreen;
    }

    /**
     * gets blob icon screen
     * @return
     */
    public BlobIconScreen getMyBlobIconScreen(){
        return myBlobIconScreen;
    }

    /**
     * gets opponent blob icon screen
     * @return
     */
    public BlobIconScreen getTheirBlobIconScreen(){
        return theirBlobIconScreen;
    }

    /**
     * gets point state
     * @return
     */
    public PointsTextScreen getPointsTextScreen() {
        return pointsTextScreen;
    }
}
