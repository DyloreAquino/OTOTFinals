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

/* WinLoseScreen class displays the screen of winning or losing in the round
*/

import java.awt.*;
import javax.swing.*;

public class WinLoseScreen extends Screen{
    
    private int x, y;

    private ImageIcon resultDisplay;
    private String resultFilename;

    /**
     * instantiates the win lose screen characteristics for the round
     * @param x
     * @param y
     */
    public WinLoseScreen(int x, int y){
        this.x = x;
        this.y = y;

        resultFilename = " ";
    }
    /**
     * sets the screen based on the result of the round
     * @param playerBlobType
     * @param opponentBlobType
     */
    public void changeState(String playerBlobType, String opponentBlobType){
        switch (playerBlobType) {
            case "rock":
                if (opponentBlobType.equals("rock")) {
                    resultFilename = "rock tie.png";
                } else if (opponentBlobType.equals("paper")) {
                    resultFilename = "rock paper lose.png";
                } else if (opponentBlobType.equals("scissors")) {
                    resultFilename = "rock scissors win.png";
                } else if (opponentBlobType.equals("stick")) {
                    resultFilename = "rock stick lose.png";
                } else if (opponentBlobType.equals(" ")) {
                    resultFilename = "rock nothing win.png";
                }
                break;

            case "paper":
                if (opponentBlobType.equals("rock")) {
                    resultFilename = "paper rock win.png";
                } else if (opponentBlobType.equals("paper")) {
                    resultFilename = "paper tie.png";
                } else if (opponentBlobType.equals("scissors")) {
                    resultFilename = "paper scissors lose.png";
                } else if (opponentBlobType.equals("stick")) {
                    resultFilename = "paper stick lose.png";
                } else if (opponentBlobType.equals(" ")) {
                    resultFilename = "paper nothing win.png";
                }
                break;
            
            case "scissors":
                if (opponentBlobType.equals("rock")) {
                    resultFilename = "scissors rock lose.png";
                } else if (opponentBlobType.equals("paper")) {
                    resultFilename = "scissors paper win.png";
                } else if (opponentBlobType.equals("scissors")) {
                    resultFilename = "scissors tie.png";
                } else if (opponentBlobType.equals("stick")) {
                    resultFilename = "scissors stick lose.png";
                } else if (opponentBlobType.equals(" ")) {
                    resultFilename = "scissors nothing win.png";
                }
                break;

            case "stick":
                if (opponentBlobType.equals("rock")) {
                    resultFilename = "stick rock win.png";
                } else if (opponentBlobType.equals("paper")) {
                    resultFilename = "stick paper win.png";
                } else if (opponentBlobType.equals("scissors")) {
                    resultFilename = "stick scissors win.png";
                } else if (opponentBlobType.equals("stick")) {
                    resultFilename = "stick tie.png";
                } else if (opponentBlobType.equals(" ")) {
                    resultFilename = "stick nothing win.png";
                }
                break;
            
            case " ":
                if (opponentBlobType.equals("rock")) {
                    resultFilename = "rock nothing lose.png";
                } else if (opponentBlobType.equals("paper")) {
                    resultFilename = "paper nothing lose.png";
                } else if (opponentBlobType.equals("scissors")) {
                    resultFilename = "scissors nothing lose.png";
                } else if (opponentBlobType.equals("stick")) {
                    resultFilename = "stick nothing lose.png";
                } else if (opponentBlobType.equals(" ")) {
                    resultFilename = "nothing tie.png";
                }
            default:
                break;
        }
        
    }
    /**
     * draws the win lose screen for the round
     */
    @Override
    public void draw(Graphics2D g2d) {
        resultDisplay = new ImageIcon(resultFilename);
        super.updateImage(resultDisplay);
        super.draw(g2d);
    }
}
