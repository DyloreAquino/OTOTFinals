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

/* BlobIconScreen class displays the corresponding icon for a certain blob type.
*/

import java.awt.*;
import javax.swing.*;

public class BlobIconScreen extends Screen {
    
    private int x, y, width, height;

    private ImageIcon blobDisplay;
    private String blobFileName;
    private boolean isLeft;
    /**
     * instantiates the blob icon characteristics
     * @param x
     * @param y
     * @param isLeft
     */
    public BlobIconScreen(int x, int y, boolean isLeft){
        this.x = x;
        this.y = y;

        width = 150;
        height = 150;

        this.isLeft = isLeft;

        blobFileName = " ";
    }
    /**
     * changes the icon that will be displayed
     * @param playerblobType
     * @param doesItWin
     */
    public void changeState(String playerblobType, boolean doesItWin) {

        if (doesItWin) {
            switch (playerblobType) {
                case "rock":
                    blobFileName = "rock-green.png";
                    break;

                case "paper":
                    blobFileName = "paper-green.png";
                    break;
    
                case "scissors":
                    blobFileName = "scissors-green.png";
                    break;

                case " ":
                    blobFileName = "nothing-white.png";
                    break;

                case "stick":
                    blobFileName = "stick-white.png";
                    break;

                default:
                    break;
            }
        } else {
            switch (playerblobType) {
                case "rock":
                    blobFileName = "rock-red.png";
                    break;

                case "paper":
                    blobFileName = "paper-red.png";
                    break;
    
                case "scissors":
                    blobFileName = "scissors-red.png";
                    break;

                case " ":
                    blobFileName = "nothing-white.png";
                    break;

                case "stick":
                    blobFileName = "stick-white.png";
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * makes the icon visible
     */
    @Override
    public void setVisible() {
        if (isLeft){
            x = 15;
            y = 300;
        } else {
            x = 1035;
            y = 300;
        }
    }
    /**
     * draws the blob icon
     */
    @Override
    public void draw(Graphics2D g2d) {
        blobDisplay = new ImageIcon(blobFileName);
        Image toPrint = blobDisplay.getImage();
        g2d.drawImage(toPrint, x, y, width, height, null);
    }

}
