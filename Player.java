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

    public void updateImage(ImageIcon img){
        // Helped by Alba the GOAT
        playerImgSprite = img.getImage();
    }

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

    public void draw(Graphics2D g2d) {
        
        g2d.drawImage(playerImgSprite, x, y-20, size, size + 20, null);
    }

    public void moveLeft(){
        direction = "L";
        x -= speed;
    }

    public void moveRight(){
        direction = "R";
        x += speed;
        
    }

    public void moveDown(){
        direction = "D";
        y += speed; 
    }

    public void moveUp(){
        direction = "U";
        y -= speed;
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

    public String getDirection(){
        return direction;
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

    public void setColumnofPlayer(int column){
        x = 150 + ((column * 50) + 10);
    }

    public void setRowofPlayer(int row){
        y = ((row * 50) + 10);
    }

    public boolean checkHasBlob(){
        return hasBlob;
    }

    public Blob getBlob() {
        return eatenBlob;
    }

    public void incrementPoints() {
        points++;
    }

    public int getPoints() {
        return points;
    }
    
    public void setX(int xval) { 
        this.x = xval; 
    }

    public int getX() { 
        return this.x; 
    }

    public void setY(int yval) { 
        this.y = yval; 
    }

    public int getY() { 
        return this.y; 
    }

}