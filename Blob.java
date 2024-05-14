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

/* Blob class contains the characteristics and methods of a blob.
*/

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class Blob implements Drawable{

    private int x, y;
    private int column, row;
    private String type;

    private int SIZE = 30;

    private ImageIcon blobSprite;
    private Image blobImgSprite;

    private boolean isSpecial;
    
    public Blob(int column, int row, String type){
        this.column = column;
        this.row = row;

        setColumn(column);
        setRow(row);

        this.type = type;
    }

    public void draw(Graphics2D g2d){

        blobSprite = new ImageIcon("green blob.png");

        blobImgSprite = blobSprite.getImage();
        g2d.drawImage(blobImgSprite, x, y, SIZE, SIZE, null);
    }

    public boolean doesItWinAgainst(String blobType){
        boolean doesWinAgainst = false;
        switch (blobType) {
            case "rock":
                if (this.type.equals("paper") ||
                    this.type.equals("stick")){
                    doesWinAgainst = true;
                } 
                break;

            case "paper":
                if (this.type.equals("scissors") ||
                    this.type.equals("stick")){
                    doesWinAgainst = true;
                }
                break;

            case "scissors":
                if (this.type.equals("rock") ||
                    this.type.equals("stick")){
                    doesWinAgainst = true;
                }
                break;

            case " ":
                doesWinAgainst = true;
                break;

            case "stick":
                doesWinAgainst = false;
                break;
        }
        return doesWinAgainst;
    }

    public String getType(){
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getX(){
        return x;
    }

    public void setOutOFBounds(){
        x = -50;
        y = -50;
    }

    public void setColumn(int column){
        x = 150 + (column * 50) + 10;
    }

    public void setRow(int row){
        y = (row * 50) + 10;
    }

    public int getY(){
        return y;
    }

    public int getCollisionBorders(){
        return (SIZE + 20);
    }
}
