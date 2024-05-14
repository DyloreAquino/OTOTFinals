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

/* Floor class creates and displayes the characteristics of a floor.
*/

import java.awt.*;
import javax.swing.*;

public class Floor implements Drawable{
    
    private final int SIZE = 50;

    private int x, y;
    private int column, row;
    // There are 16 columns, 10 rows
    private ImageIcon floorSprite;
    private String floorSpriteFileName;
    private int imageNum;
    /**
     * instantiates the floor characteristics
     * @param column
     * @param row
     * @param imageNum
     */
    public Floor(int column, int row, int imageNum) {
        this.column = column;
        this.row = row;
        this.imageNum = imageNum;

        x = 150 + (column * 50);
        y = row * 50;
    }
    /**
     * changes the filename to draw for variety
     */
    private void updateImage() {
        switch (imageNum) {
            case 1:
                floorSpriteFileName = "floor.png";
                break;

            case 2: 
                floorSpriteFileName = "floor 2.png";
                break;

            case 3:
                floorSpriteFileName = "floor 3.png";    
                break;

            case 4:
                floorSpriteFileName = "floor 4.png";
                break;

            default:
                break;
        }
    }

    /** draws the floor */
    public void draw(Graphics2D g2d){
        updateImage();
        floorSprite = new ImageIcon(floorSpriteFileName);
        Image floorImgSprite = floorSprite.getImage();
        g2d.drawImage(floorImgSprite, x, y, SIZE, SIZE, null);
    }
    /**
     * gets the x-position of the floor
     * @return
     */
    public int getX(){
        return x;
    }

    /**
     * gets the y-position of the floor
     * @return
     */
    public int getY(){
        return y;
    }
    /**
     * gets the size of the floor
     * @return
     */
    public int getSize(){
        return SIZE;
    }
}
