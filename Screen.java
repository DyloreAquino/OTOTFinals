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

/* Screen class displays the screen boundaries and contains methods to update screen images
*/
import java.awt.*;
import javax.swing.*;

public class Screen implements Drawable {

    private int x, y;

    private int WIDTH = 800;
    private int HEIGHT = 500;

    private Image toPrint;

    /**
     * sets the screen visible
     */
    public void setVisible(){
        x = 200;
        y = 50;
    }
    /**
     * sets the screen invisible
     */
    public void setInvisible() {
        x = -9999;
        y = -9999;
    }
    /**
     * updates the screen with an image
     * @param imgIcon
     */
    protected void updateImage(ImageIcon imgIcon){
        toPrint = imgIcon.getImage();
    }
    /**
     * draws the screen
     */
    public void draw(Graphics2D g2d){
        g2d.drawImage(toPrint, x, y, WIDTH, HEIGHT, null);
    }
}
