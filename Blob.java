import java.awt.*;
import java.awt.geom.*;

public class Blob implements Drawable{

    private int x, y;
    private int column, row;
    private String type;

    private int SIZE = 30;

    private Ellipse2D.Double blobSprite;
    private Color blobColor;
    
    public Blob(int column, int row, String type){
        this.column = column;
        this.row = row;

        setColumn(column);
        setRow(row);

        this.type = type;
    }

    public void draw(Graphics2D g2d){
        blobColor = Color.GREEN;
        blobSprite = new Ellipse2D.Double(x, y, SIZE, SIZE);
        g2d.setColor(blobColor);
        g2d.fill(blobSprite);
    }

    public boolean doesItWinAgainst(Blob blob){
        boolean doesWinAgainst = false;
        switch (blob.getType()) {
            case "rock":
                if (this.type == "paper"){
                    doesWinAgainst = true;
                }
                break;
            case "paper":
                if (this.type == "scissors"){
                    doesWinAgainst = true;
                }
                break;
            case "scissors":
                if (this.type == "rock"){
                    doesWinAgainst = true;
                }
                break;
            default:
                doesWinAgainst = false;
                break;
        }
        return doesWinAgainst;
    }

    public String getType(){
        return type;
    }

    public int getX(){
        return x;
    }

    public void setOutOFBounds(){
        x = -50;
        y = -50;
    }

    public void setColumn(int column){
        x = 150 + (column * 50) + 20;
    }

    public void setRow(int row){
        y = (row * 50) + 20;
    }

    public int getY(){
        return y;
    }

    public int getCollisionBorders(){
        return (SIZE + 20);
    }
}
