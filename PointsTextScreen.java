/**
	This is a class made for making the text in showing player points.
	
	@author Jerold Luther P. Aquino (230413)
    @author Hanzo Ricardo M. Castillo (231365)
	@version March 6, 2024
	
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

public class PointsTextScreen extends Screen{

    /*
     * Drawing of text guided by these references:
     * - https://stackoverflow.com/questions/8802320/draw-text-with-graphics-object-on-jframe
     * - https://www.oreilly.com/library/view/learning-java/1565927184/ch17s06.html
     * - http://www.java2s.com/Tutorials/Java/Java_Swing/1520__Java_Swing_Font.htm
     */
    private Font font1;
    private Font font2;
    private int playerPoints;
    private int opponentPoints;
    private int x, y;
    private boolean isLeft;

    /**
     * Constructor initializes state
     * the state determines which text to draw
     */
    public PointsTextScreen(int x, int y, boolean isLeft){
        font1 = new Font("Monospaced", Font.BOLD, 15);
        font2 = new Font("Monospaced", Font.BOLD, 70);
        this.x = x;
        this.y = y;
        this.isLeft = isLeft;
    }   

    /**
     * Changes the points variable, useful for drawing points
     * @param points points to be drawn later
     */
    public void changePoints(int playerPoints, int opponentPoints) {
        this.playerPoints = playerPoints;
        this.opponentPoints = opponentPoints;
    }

    /**
     * Overrides the setVisible() function of screen to set it
     * to a more appropriate coordinate
     */
    @Override
    public void setVisible() {
        x = 30;
        y = 480;
    }

    /**
     * Draws necessary elements
     */
    public void draw(Graphics2D g2d){
        g2d.setColor(Color.WHITE);
        g2d.setFont(font1);
        g2d.drawString("POINTS: ", x + 20, y + 15);
        g2d.drawString(":POINTS ", x + 1060, y + 15);
        g2d.setFont(font2);
        if (isLeft){
            g2d.drawString("" + playerPoints, x + 90 , y + 15);
            g2d.drawString("" + opponentPoints, x + 1010, y + 15);
        } else {
            g2d.drawString("" + opponentPoints, x + 90 , y + 15);
            g2d.drawString("" + playerPoints, x + 1010, y + 15);
        }
            
    }
}
