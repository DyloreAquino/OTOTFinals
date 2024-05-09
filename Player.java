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
    private Image playerImgSprite;

    private int points;
    private boolean hasPlayerWon;

    private Audio eatingSFX;
    private Audio vomitSFX;

    //Needs a function specifically for resetting all needed attributes of the player.
    //This can require parameters for column and row for easy spawning
    //Every time we reset a round, the player gets reset to that coordinate.
    
    public Player(int column, int row, int speed, int size, int playerNumber) {
        this.column = column;
        this.row = row;

        points = 0;

        x = 150 + ((column * 50) + 10);
        y = ((row * 50) + 10);

        this.speed = speed;
        this.size = size;

        setSpeed(speed);
    
        direction = " ";

        this.playerNumber = playerNumber;

        if (playerNumber == 1){
            playerColor = Color.RED;
        } else {
            playerColor = Color.GREEN;
        }

        hasBlob = false;

        hasPlayerWon = false;

        player1Down = new ImageIcon("p1 down.png");
        player1Up = new ImageIcon("p1 up.png");
        player1Left = new ImageIcon("p1 left.png");
        player1Right = new ImageIcon("p1 right.png");

        player1DownBlobbed = new ImageIcon("player 1 blobed-down.png");
        player1UpBlobbed = new ImageIcon("player 1 blobed-up.png");
        player1LeftBlobbed = new ImageIcon("player 1 blobed-left.png");
        player1RightBlobbed = new ImageIcon("player 1 blobed-right.png");

        updateImage(player1Down);

        eatingSFX = new Audio();
        vomitSFX = new Audio();

        
        
    }

    public void updateImage(ImageIcon img){
        // Helped by Alba the GOAT
        playerImgSprite = img.getImage();
    }

    public void draw(Graphics2D g2d) {
        
        g2d.drawImage(playerImgSprite, x, y-20, size, size + 20, null);
    }

    public void moveLeft(){
        direction = "L";
        x -= speed;
        if (checkHasBlob()){
            updateImage(player1LeftBlobbed);
        } else{
            updateImage(player1Left);
        }
    }

    public void moveRight(){
        direction = "R";
        x += speed;
        if (checkHasBlob()){
            updateImage(player1RightBlobbed);
        } else{
            updateImage(player1Right);
        }
    }

    public void moveDown(){
        direction = "D";
        y += speed;
        if (checkHasBlob()){
            updateImage(player1DownBlobbed);
        } else{
            updateImage(player1Down);
        }
    }

    public void moveUp(){
        direction = "U";
        y -= speed;
        if (checkHasBlob()){
            updateImage(player1UpBlobbed);
        } else{
            updateImage(player1Up);
        }
    }
    
    public void setSpeed( int speed ) {
        this.speed = speed;
    }

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

    public boolean checkBorderCollision(){
        if ((x + size > 1000 || x < 200) || (y + size > 550 || y < 50) ){ 
            switchDirection();
            if (checkHasBlob()){
                updateImage(player1DownBlobbed);
            } else{
                updateImage(player1Down);
            }
            return true;
        }
        return false;
    }

    public boolean checkWallCollision(Wall wall){
        if ((wall.getX() < x + size && wall.getX() + wall.getSize() > x)
        && (wall.getY() < y + size && wall.getY() + wall.getSize() > y)){
            switchDirection();
            if (checkHasBlob()){
                updateImage(player1DownBlobbed);
            } else{
                updateImage(player1Down);
            }
            return true;
        }
        return false;
    }

    public boolean checkBlobCollision(Blob blob){
        if ((blob.getX() < x + size && blob.getX() + blob.getCollisionBorders() > x)
        && (blob.getY() < y + size && blob.getY() + blob.getCollisionBorders() > y)){
            return true;
        }
        return false;
    }

    public void eatBlob(Blob blob){
        eatenBlob = blob;
        hasBlob = true;
        blob.setOutOFBounds();
        eatingSFX.setFile("eating sfx.wav");
        eatingSFX.play();
    }

    public void vomitBlob(){
        if (eatenBlob != null){

            eatenBlob.setRow(getRowofPlayer());
            eatenBlob.setColumn(getColumnofPlayer());

            eatenBlob = null;
            hasBlob = false;

            vomitSFX.setFile("vomit sfx.wav");
            vomitSFX.play();
        }
    }
    
    public int getSpeed(){
        return speed;
    }

    public int getColumnofPlayer(){
        int columnofPlayer = 0;
        for (int i = 200; i < 1000; i += 50){
            if (x >= i && x < i + 50){
                columnofPlayer = ((i - 200)/50) + 1;
            }
        }
        return columnofPlayer;
    }

    public int getRowofPlayer(){
        int rowofPlayer = 0;
        for (int i = 50; i < 550; i += 50){
            if (y >= i && y < i + 50){
                rowofPlayer = ((i - 50)/50) + 1;
            }
        }
        return rowofPlayer;
    }

    public boolean checkHasBlob(){
        return hasBlob;
    }

    public Blob getBlob() {
        return eatenBlob;
    }

    public void hasWon(){
        hasPlayerWon = true;
    }

    public void incrementPoints() {
        points++;
    }

    public int getPoints() {
        return points;
    }
    
    public void setX(int x) { this.x = x; }
    public int getX() { return x; }
    public void setY(int y) { this.y = y; }
    public int getY() { return y; }

}