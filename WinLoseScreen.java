import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

public class WinLoseScreen extends Screen{
    
    private int x, y;

    private ImageIcon resultDisplay;
    private String resultFilename;

    public WinLoseScreen(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void changeState(String playerBlobType, String opponentBlobType){
        switch (playerBlobType) {
            case "rock":
                if (opponentBlobType == "rock") {
                    resultFilename = "rock tie.png";
                } else if (opponentBlobType == "paper") {
                    resultFilename = "rock paper lose.png";
                } else if (opponentBlobType == "scissors") {
                    resultFilename = "rock scissors win.png";
                } else if (opponentBlobType == "stick") {
                    resultFilename = "rock stick lose.png";
                }
                break;
            case "paper":
                if (opponentBlobType == "rock") {
                    resultFilename = "paper rock win.png";
                } else if (opponentBlobType == "paper") {
                    resultFilename = "paper tie.png";
                } else if (opponentBlobType == "scissors") {
                    resultFilename = "paper scissors lose.png";
                } else if (opponentBlobType == "stick") {
                    resultFilename = "paper stick lose.png";
                }
                break;
            
            case "scissors":
                if (opponentBlobType == "rock") {
                    resultFilename = "scissors rock lose.png";
                } else if (opponentBlobType == "paper") {
                    resultFilename = "scissors paper win.png";
                } else if (opponentBlobType == "scissors") {
                    resultFilename = "scissors tie.png";
                } else if (opponentBlobType == "stick") {
                    resultFilename = "scissors stick lose.png";
                }
                break;

            case "stick":
                if (opponentBlobType == "rock") {
                    resultFilename = "stick rock win.png";
                } else if (opponentBlobType == "paper") {
                    resultFilename = "stick paper win.png";
                } else if (opponentBlobType == "scissors") {
                    resultFilename = "stick scissors.png";
                } else if (opponentBlobType == "stick") {
                    resultFilename = "stick tie.png";
                }
                break;
            default:
                break;
        }
        resultDisplay = new ImageIcon(resultFilename);
        super.updateImage(resultDisplay);
    }
    
    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
    }
}
