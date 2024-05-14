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

        resultFilename = " ";
    }

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
    
    @Override
    public void draw(Graphics2D g2d) {
        resultDisplay = new ImageIcon(resultFilename);
        super.updateImage(resultDisplay);
        super.draw(g2d);
    }
}
