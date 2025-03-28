/**
<<<<<<< HEAD
	Wall class creates the characteristics of a wall
=======
>>>>>>> 09fa670037b4d23ce9c7c6f206e520812096caaf
	
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

import java.awt.*;
import javax.swing.*;

public class Wall implements Drawable{
    
    private final int SIZE = 50;

    private int x, y;
    private int column, row;
    // There are 16 columns, 10 rows
    private String wallSpriteFileName;
    private ImageIcon wallSprite;
    private int imageNum;

    /**
     * Constructor for a wall
     * @param column
     * @param row
     * @param imageNum which Image to send
     */
    public Wall(int column, int row, int imageNum) {
        this.column = column;
        this.row = row;

        x = 150 + (column * 50);
        y = row * 50;

        this.imageNum = imageNum;
    }

    /**
     * Updates the image based on the imageNum
     * There are four main images to choose from
     */
    private void updateImage() {
        switch (imageNum) {
            case 1:
                wallSpriteFileName = "wall.png";
                break;

            case 2: 
                wallSpriteFileName = "wall 2.png";
                break;

            case 3:
                wallSpriteFileName = "wall 3.png";    
                break;

            case 4:
                wallSpriteFileName = "wall 4.png";
                break;

            default:
                break;
        }
    }

    /**
     * Draws the wall
     */
    public void draw(Graphics2D g2d){
        updateImage();
        wallSprite = new ImageIcon(wallSpriteFileName);
        Image wallImgSprite = wallSprite.getImage();
        g2d.drawImage(wallImgSprite, x, y, SIZE, SIZE, null);
    }

    /**
     * Gets the x
     * @return x value
     */
    public int getX(){
        return x;
    }

    /**
     * Gets the y
     * @return y value
     */
    public int getY(){
        return y;
    }

    /**
     * Gets the size
     * @return size value
     */
    public int getSize(){
        return SIZE;
    }
}
