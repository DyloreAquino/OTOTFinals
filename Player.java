import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;


public class Player implements Drawable{

    private int x, y;
    private int column, row;
    private int size;
    private int speed;
    
    private int playerNumber;

    private String direction;

    private Color playerColor;

    private Rectangle2D.Double playerSprite;
    
    public Player(int column, int row, int speed, int size, int playerNumber) {
        this.column = column;
        this.row = row;

        x = 150 + ((column * 50) + 10);
        y = ((row * 50) + 10);

        this.speed = speed;
        this.size = size;
    
        direction = " ";

        this.playerNumber = playerNumber;

        if (playerNumber == 1){
            playerColor = Color.RED;
        } else {
            playerColor = Color.GREEN;
        }
    }

    public void draw(Graphics2D g2d) {
        playerSprite = new Rectangle2D.Double(x, y, size, size);
        g2d.setColor(playerColor);
        g2d.fill(playerSprite);
    }

    public void moveLeft(){
        setSpeed(5);
        direction = "L";
        x -= speed;
    }

    public void moveRight(){
        setSpeed(5);
        direction = "R";
        x += speed;
    }

    public void moveDown(){
        setSpeed(5);
        direction = "D";
        y += speed;
    }

    public void moveUp(){
        setSpeed(5);
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
            return true;
        }
        return false;
    }

    public boolean checkWallCollision(Wall wall){
        if ((wall.getX() < x + size && wall.getX() + wall.getSize() > x)
        && (wall.getY() < y + size && wall.getY() + wall.getSize() > y)){
            switchDirection();
            return true;
        }
        return false;
    }

    public int getSpeed(){
        return speed;
    }
    
    public void setX(int x) { this.x = x; }
    public int getX() { return x; }
    public void setY(int y) { this.y = y; }
    public int getY() { return y; }

}