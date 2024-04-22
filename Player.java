import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;


public class Player implements Drawable{

    private int x, y;
    private int size;
    private int xSpeed, ySpeed;
    private int speed;

    private Color playerColor;

    private Rectangle2D.Double playerSprite;

    private boolean movingRight, movingUp;
    
    public Player(int x, int y, int xSpeed, int ySpeed, int size) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.size = size;

        this.speed = xSpeed;

        playerColor = Color.RED;
    }

    public void draw(Graphics2D g2d) {
        playerSprite = new Rectangle2D.Double(x, y, size, size);
        g2d.setColor(playerColor);
        g2d.fill(playerSprite);
    }

    public void moveLeft(){
        x -= xSpeed;
    }

    public void moveRight(){
        x += xSpeed;
    }

    public void moveDown(){
        y += ySpeed;  
    }

    public void moveUp(){
        y -= xSpeed;
    }

    public void moveIdle(){
        x += 0;
        y += 0;
    }

    public void checkWallCollision(){
        if(x + size >= 800 || x <= 200){ 
            xSpeed *= -1;
        }
        if(y + size >= 520 || y <= 20){ 
            ySpeed *= -1;
        }
    }

    

}