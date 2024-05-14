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

/* TimerScreen class contains the timer respective to each player
*/
import java.awt.*;
import javax.swing.*;

public class TimerScreen extends Screen {
    
    private int x, y, width, height;

    private ImageIcon timerDisplay;
    private String timerFileName;
    private int playerID;

    /**
     * creates the timer based on the player's client
     * @param x
     * @param y
     * @param playerID
     */
    public TimerScreen(int x, int y, int playerID){
        this.x = x;
        this.y = y;
        this.playerID = playerID;

        width = 100;
        height = 50;

        timerFileName = " ";
    }

    /**
     * updates the countdown of the timer
     * @param clientTimer
     */
    public void changeState(int clientTimer) {
        int timer = clientTimer - 5;

        switch (timer) {
            case 10:
                timerFileName = "1.png";
                break;
            case 9:
                timerFileName = "2.png";   
                break;
            case 8:
                timerFileName = "3.png";
                break;
            case 7:
                timerFileName = "4.png";
                break;
            case 6:
                timerFileName = "5.png";
                break;
            case 5:
                timerFileName = "6.png";
                break;
            case 4:
                timerFileName = "7.png";
                break;
            case 3:
                timerFileName = "8.png";
                break;
            case 2:
                timerFileName = "9.png";
                break;
            case 1:
                timerFileName = "10.png";
                break;
            default:
                timerFileName = " ";
                break;
        }
        
    }

    /**
     * sets the timer visible
     */
    @Override
    public void setVisible() {
        if (playerID == 1){
            x = 30;
            y = 100;
        } else if (playerID == 2){
            x = 1070;
            y = 100;
        }
        
    }
    
    /**
     * draws the timer
     */
    @Override
    public void draw(Graphics2D g2d) {
        timerDisplay = new ImageIcon(timerFileName);
        Image toPrint = timerDisplay.getImage();
        g2d.drawImage(toPrint, x, y, width, height, null);
    }

}
