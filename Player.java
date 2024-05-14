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

/* Player class contains the characteristics, image animations, and uses 
    draw methods that involves movement and player updates.
*/

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class Player implements Drawable{

    private int x, y;
    private int column, row;
    private int size;
    private int speed;
    private String direction;
    
    private int playerNumber;

    private Blob eatenBlob;
    private boolean hasBlob;

    private Color playerColor;
    private Rectangle2D.Double playerSprite;

    private ImageIcon player1Down;
    private ImageIcon player1Up;
    private ImageIcon player1Left;
    private ImageIcon player1Right;
    private ImageIcon player1DownBlobbed;
    private ImageIcon player1UpBlobbed;
    private ImageIcon player1LeftBlobbed;
    private ImageIcon player1RightBlobbed;
    private ImageIcon player2Down;
    private ImageIcon player2Up;
    private ImageIcon player2Left;
    private ImageIcon player2Right;
    private ImageIcon player2DownBlobbed;
    private ImageIcon player2UpBlobbed;
    private ImageIcon player2LeftBlobbed;
    private ImageIcon player2RightBlobbed;
    private Image playerImgSprite;

    private int points;

    private Audio eatingSFX;
    private Audio vomitSFX;

    //Needs a function specifically for resetting all needed attributes of the player.
    //This can require parameters for column and row for easy spawning
    //Every time we reset a round, the player gets reset to that coordinate.
    
    /**
     * instantiates position via row and column, speed, player images, animations, server number, and sfx.
     * 
     * @param column
     * @param row
     * @param playerNumber
     */
    public Player(int column, int row, int playerNumber) {
        this.column = column;
        this.row = row;

        points = 0;

        setColumnofPlayer(column);
        setRowofPlayer(row);

        speed = 3;

        size = 30;
        setSpeed(speed);
    
        direction = " ";

        this.playerNumber = playerNumber;

        if (playerNumber == 1){
            playerColor = Color.RED;
        } else {
            playerColor = Color.GREEN;
        }

        hasBlob = false;

        player1Down = new ImageIcon("p1 down.png");
        player1Up = new ImageIcon("p1 up.png");
        player1Left = new ImageIcon("p1 left.png");
        player1Right = new ImageIcon("p1 right.png");

        player1DownBlobbed = new ImageIcon("player 1 blobed-down.png");
        player1UpBlobbed = new ImageIcon("player 1 blobed-up.png");
        player1LeftBlobbed = new ImageIcon("player 1 blobed-left.png");
        player1RightBlobbed = new ImageIcon("player 1 blobed-right.png");

        player2Down = new ImageIcon("p2 down.png");
        player2Up = new ImageIcon("p2 up.png");
        player2Left = new ImageIcon("p2 left.png");
        player2Right = new ImageIcon("p2 right.png");

        player2DownBlobbed = new ImageIcon("player 2 blobed-down.png");
        player2UpBlobbed = new ImageIcon("player 2 blobed-up.png");
        player2LeftBlobbed = new ImageIcon("player 2 blobed-left.png");
        player2RightBlobbed = new ImageIcon("player 2 blobed-right.png");

        if (playerNumber == 1){
            updateImage(player1Down);
        } else if (playerNumber == 2){
            updateImage(player2Down);
        }

        eatingSFX = new Audio();
        vomitSFX = new Audio();
    }

    /**
     * updates the image
     * @param img
     */
    public void updateImage(ImageIcon img){
        // Helped by Alba the GOAT
        playerImgSprite = img.getImage();
    }

    /**
     * changes the image of the player according to its direction
     * @param dir
     */
    public void changeSpriteState( String dir ){
        switch (dir) {
            case "L":
                if (checkHasBlob()){
                    if (playerNumber == 1){
                        updateImage(player1LeftBlobbed);
                    } else if (playerNumber == 2){
                        updateImage(player2LeftBlobbed);
                    }
                } else{
                    if (playerNumber == 1){
                        updateImage(player1Left);
                    } else if (playerNumber == 2){
                        updateImage(player2Left);
                    }
                }
                break;
            
            case "R":
                if (checkHasBlob()){
                    if (playerNumber == 1){
                        updateImage(player1RightBlobbed);
                    } else if (playerNumber == 2){
                        updateImage(player2RightBlobbed);
                    }
                } else{
                    if (playerNumber == 1){
                        updateImage(player1Right);
                    } else if (playerNumber == 2){
                        updateImage(player2Right);
                    }
                }
                break;

            case "D":
                if (checkHasBlob()){
                    if (playerNumber == 1){
                        updateImage(player1DownBlobbed);
                    } else if (playerNumber == 2){
                        updateImage(player2DownBlobbed);
                    }
                } else{
                    if (playerNumber == 1){
                        updateImage(player1Down);
                    } else if (playerNumber == 2){
                        updateImage(player2Down);
                    }
                }
                break;
            
            case "U":
                if (checkHasBlob()){
                    if (playerNumber == 1){
                        updateImage(player1UpBlobbed);
                    } else if (playerNumber == 2){
                        updateImage(player2UpBlobbed);
                    }
                } else{
                    if (playerNumber == 1){
                        updateImage(player1Up);
                    } else if (playerNumber == 2){
                        updateImage(player2Up);
                    }
                }
                break;


            default:
                break;
        }
    }

    /**
     * draws the player
     */
    public void draw(Graphics2D g2d) {
        
        g2d.drawImage(playerImgSprite, x-10, y-40, 50, 70, null);
    }

    /**
     * moves the player to the left
     */
    public void moveLeft(){
        direction = "L";
        x -= speed;
    }

    /**
     * moves the player to the right
     */
    public void moveRight(){
        direction = "R";
        x += speed;
        
    }

    /**
     * moves the player downwards
     */
    public void moveDown(){
        direction = "D";
        y += speed; 
    }

    /**
     * moves the player upwards
     */
    public void moveUp(){
        direction = "U";
        y -= speed;
    }
    
    /**
     * sets the speed of the player
     * @param speed
     */
    public void setSpeed( int speed ) {
        this.speed = speed;
    }

    /**
     * switches the direction of the player
     */
    private void switchDirection(){
        switch (direction) {
            case "L":
                moveRight();
                break;
            case "R":
                moveLeft();
                break;
            case "U":
                moveDown();
                break;
            case "D":
                moveUp();
                break;
            default:
                break;
        }
    }

    /**
     * checks collision with other elements
     * @return
     */
    public boolean checkBorderCollision(){
        if ((x + size > 1000 || x < 200) || (y + size > 550 || y < 50) ){ 
            switchDirection();
            if (checkHasBlob()){
                if (playerNumber == 1){
                    updateImage(player1DownBlobbed);
                } else if (playerNumber == 2){
                    updateImage(player2DownBlobbed);
                }
            } else{
                if (playerNumber == 1){
                    updateImage(player1Down);
                } else if (playerNumber == 2){
                    updateImage(player2Down);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * checks if player collided with a wall
     * @param wall
     * @return
     */
    public boolean checkWallCollision(Wall wall){
        if ((wall.getX() < x + size && wall.getX() + wall.getSize() > x)
        && (wall.getY() < y + size && wall.getY() + wall.getSize() > y)){
            switchDirection();
            if (checkHasBlob()){
                if (playerNumber == 1){
                    updateImage(player1DownBlobbed);
                } else if (playerNumber == 2){
                    updateImage(player2DownBlobbed);
                }
            } else{
                if (playerNumber == 1){
                    updateImage(player1Down);
                } else if (playerNumber == 2){
                    updateImage(player2Down);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * checks of player collided with a blob
     * @param blob
     * @return
     */
    public boolean checkBlobCollision(Blob blob){
        if ((blob.getX() < x + size && blob.getX() + blob.getCollisionBorders() > x)
        && (blob.getY() < y + size && blob.getY() + blob.getCollisionBorders() > y)){
            return true;
        }
        return false;
    }

    /**
     * sets the player condition to have a blob
     * @param blob
     */
    public void eatBlob(Blob blob){
        eatenBlob = blob;
        hasBlob = true;
        blob.setOutOFBounds();
        eatingSFX.setFile("eating sfx.wav");
        eatingSFX.play();
    }

    /**
     * makes the player lose or vomit the blob
     * @return
     */
    public Blob vomitBlob(){
        if (eatenBlob != null){

            eatenBlob.setRow(getRowofPlayer());
            eatenBlob.setColumn(getColumnofPlayer());

            Blob toReturnBlob = eatenBlob;

            eatenBlob = null;
            hasBlob = false;

            vomitSFX.setFile("vomit sfx.wav");
            vomitSFX.play();

            return toReturnBlob;
        }

        return null;
    }

    /**
     * gets the direction
     * @return
     */
    public String getDirection(){
        return direction;
    }
    
    /**
     * gets the speed
     * @return
     */
    public int getSpeed(){
        return speed;
    }

    /**
     * gets the column of the player
     * @return
     */
    public int getColumnofPlayer(){
        int columnofPlayer = 0;
        for (int i = 200; i < 1000; i += 50){
            if (x >= i && x < i + 50){
                columnofPlayer = ((i - 200)/50) + 1;
            }
        }
        return columnofPlayer;
    }

    /**
     * gets the row of the player
     * @return
     */
    public int getRowofPlayer(){
        int rowofPlayer = 0;
        for (int i = 50; i < 550; i += 50){
            if (y >= i && y < i + 50){
                rowofPlayer = ((i - 50)/50) + 1;
            }
        }
        return rowofPlayer;
    }

    /**
     * sets the column of the player
     * @param column
     */
    public void setColumnofPlayer(int column){
        x = 150 + ((column * 50) + 10);
    }

    /**
     * sets the row of the player
     * @param row
     */
    public void setRowofPlayer(int row){
        y = ((row * 50) + 10);
    }

    /**
     * gets if player has blob
     * @return
     */
    public boolean checkHasBlob(){
        return hasBlob;
    }

    /**
     * gets player's blob
     * @return
     */
    public Blob getBlob() {
        return eatenBlob;
    }

    /**
     * clears the player blob
     */
    public void clearBlob() {
        eatenBlob = null;
    }

    /**
     * adds the player's points
     */
    public void incrementPoints() {
        points++;
    }

    /**
     * gets the player's points
     * @return
     */
    public int getPoints() {
        return points;
    }

    /**
     * sets the player's points
     * @param pointsVal
     */
    public void setPoints(int pointsVal) {
        points = pointsVal;
    }

    /**
     * sets the player's x-position
     * @param xval
     */
    public void setX(int xval) { 
        this.x = xval; 
    }

    /**
     * gets the player's x-position
     * @return
     */
    public int getX() { 
        return this.x; 
    }

    /**
     * sets the player's y-position
     * @param yval
     */
    public void setY(int yval) { 
        this.y = yval; 
    }

    /**
     * sets the player's y-position
     * @return
     */
    public int getY() { 
        return this.y; 
    }

}