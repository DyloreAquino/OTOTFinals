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

/* WinLoseGameScreen class displays the screen of winning or losing for the whole
*/

import java.awt.*;
import javax.swing.*;

public class WinLoseGameScreen extends Screen{
    
    private int x, y;

    private ImageIcon resultDisplay;
    private String resultFilename;

    /**
     * instantiates the win lose screen characteristics for the game result
     * @param x
     * @param y
     */
    public WinLoseGameScreen(int x, int y){
        this.x = x;
        this.y = y;

        resultFilename = " ";
    }
    /**
     * changes the screen based on result
     * @param playerHasWon
     */
    public void changeState(boolean playerHasWon){
        if (playerHasWon) {
            resultFilename = "you win game.png";
        } else {
            resultFilename = "you lose game.png";
        }
        
    }
    /** draws the win lose screen for the game */
    @Override
    public void draw(Graphics2D g2d) {
        resultDisplay = new ImageIcon(resultFilename);
        super.updateImage(resultDisplay);
        super.draw(g2d);
    }
}
