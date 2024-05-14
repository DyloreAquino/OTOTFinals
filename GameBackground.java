/**
	This is a class made for a more organized playing of audio.
	
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

/* GameBackground class creates the game background
*/

import java.awt.*;
import javax.swing.*;

public class GameBackground implements Drawable{

    private ImageIcon gameBackground;
    private Image toPrint;

    public void draw(Graphics2D g2d){
        gameBackground = new ImageIcon("game background.png");
        toPrint = gameBackground.getImage();
        g2d.drawImage(toPrint, 0, 0, 1200, 600, null);
    }
}
