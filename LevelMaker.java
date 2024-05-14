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

/* LevelMaker class makes the level of each round with integer arrays as their formula
*/
import java.util.*;

public class LevelMaker {
    // In this Object will be the logic for random arrangement of blobs
    // In this Object will also be the random arrangement of walls
    private int phase; 

    private Floor floor;
    private Wall wall;
    private Blob blob;
    private Player player;
    private Player opponent;

    private Random rand;
    /**
     * instantiates level
     * @param phase
     */
    public LevelMaker(int phase){
        this.phase = phase;
        rand = new Random();
    }
    /**
     * set ups the flooring
     * @param drawables
     */
    public void setUpFloor(ArrayList<Drawable> drawables){
        for (int row = 1; row <= 10; row++){
            for (int column = 1; column <= 16; column++){
                int randomNum = rand.nextInt(4) + 1;
                floor = new Floor(column, row, randomNum);
                drawables.add(floor);
            }
        }
    }
    /**
     * sets up the possible objects that the player can collide
     * @param level
     * @param playerID
     * @param drawables
     * @param walls
     * @param blobs
     */
    public void setUpLevel( int level, 
                            int playerID,
                            ArrayList<Drawable> drawables, 
                            ArrayList<Wall> walls, 
                            ArrayList<Blob> blobs){
        /**
         * Level Making!!!
         * 0 for nothing
         * 1 for Walls
         * 2 for Player 1
         * 3 for Player 2
         * 4 for Blobs
         */
        int level1[] = 
        {
            4, 0, 0, 0, 0, 0, 0, 1, 1, 4, 0, 0, 0, 0, 0, 4,
            1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1,
            0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0,
            1, 1, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 1,
            2, 0, 1, 0, 0, 1, 1, 4, 0, 1, 1, 0, 0, 1, 0, 3,
            1, 0, 0, 4, 0, 1, 1, 0, 4, 1, 1, 0, 4, 0, 0, 1,
            1, 1, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 1,
            0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0,
            1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1,
            4, 0, 0, 0, 0, 0, 4, 1, 1, 0, 0, 0, 0, 0, 0, 4
        };

        int level2[] = 
        {
            
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 4, 1, 1, 1, 0, 0,
            0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0,
            0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 4, 0, 1, 0,
            0, 1, 4, 0, 1, 1, 4, 0, 0, 4, 1, 1, 1, 0, 1, 0,
            2, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 3,
            0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 4, 1, 0,
            0, 1, 0, 0, 4, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0,
            0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0,
            4, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0

        };

        int columnCounter = 1;
        int rowCounter = 1;

        int levelMap[] = {};

        if (level == 1){
            levelMap = level1;
        } else if (level == 2){
            levelMap = level2;
        };


        for (int i: levelMap) {
            // checking columns
            if (columnCounter > 16){
                columnCounter = 1;
                rowCounter++;
            }
            // spawning walls
            if (i == 1){
                int randomNum = rand.nextInt(4) + 1;
                wall = new Wall(columnCounter, rowCounter, randomNum);
                drawables.add(wall);
                walls.add(wall);
            } else
            // spawning players
            if (i == 2){
                if (playerID == 1){
                    player = new Player(columnCounter, rowCounter, 1);
                } else {
                    opponent = new Player(columnCounter, rowCounter, 1);
                }
            } else
            // spawning opponents
            if (i == 3){
                if (playerID == 1){
                    opponent = new Player(columnCounter, rowCounter, 2);
                } else {
                    player = new Player(columnCounter, rowCounter, 2);
                }
            } else
            // spawning blobs
            if (i == 4){

                String blobType = " ";
                int randomNum = rand.nextInt(100);

                System.out.println(randomNum);

                if (randomNum <= 10){
                    blobType = "stick";
                } else if (randomNum <= 40) {
                    blobType = " ";
                } else if (randomNum <= 60) {
                    blobType = "rock";
                } else if (randomNum <= 80) {
                    blobType = "paper";
                } else if (randomNum < 100) {
                    blobType = "scissors";
                }

                blobs.add(new Blob(columnCounter, rowCounter, blobType));
            }
            columnCounter++;
        }
        
        drawables.add(player);
        drawables.add(opponent);

        for (Blob blob: blobs){
            drawables.add(blob);
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
}
