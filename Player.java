import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;


public class Player implements Drawable{

    private int x, y;
    private int size;
    private int speed;

    private String direction;

    private Color playerColor;

    private Rectangle2D.Double playerSprite;
    
    public Player(int x, int y, int speed, int size) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.size = size;
        playerColor = Color.RED;
    }

    public void draw(Graphics2D g2d) {
        playerSprite = new Rectangle2D.Double(x, y, size, size);
        g2d.setColor(playerColor);
        g2d.fill(playerSprite);
    }

    public void moveLeft(){
        setSpeed(5);
        x -= speed;
    }

    public void moveRight(){
        setSpeed(5);
        x += speed;
    }

    public void moveDown(){
        setSpeed(5);
        y += speed;
    }

    public void moveUp(){
        setSpeed(5);
        y -= speed;
    }

    public void moveNowhere(){
        setSpeed(0);
        x += speed;
        y += speed;
    }

    public void setSpeed( int speed ) {
        this.speed = speed;
    } 


    public boolean checkBorderCollision(){
        if ((x + size > 1000 || x < 200) || (y + size > 580 || y < 20) ){ 
            return true;
        }
        return false;
    }

    public boolean checkWallCollision(Wall wall){
        if ((wall.getX() <= x + size && wall.getX() + wall.getSize() >= x)
        && (wall.getY() <= y + size && wall.getY() + wall.getSize() >= y)){
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