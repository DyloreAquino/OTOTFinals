import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

public class BlobIconScreen extends Screen {
    
    private int x, y, width, height;

    private ImageIcon blobDisplay;
    private String blobFileName;
    private boolean isLeft;

    public BlobIconScreen(int x, int y, boolean isLeft){
        this.x = x;
        this.y = y;

        width = 150;
        height = 150;

        this.isLeft = isLeft;

        blobFileName = " ";
    }

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
    
    @Override
    public void draw(Graphics2D g2d) {
        blobDisplay = new ImageIcon(blobFileName);
        Image toPrint = blobDisplay.getImage();
        g2d.drawImage(toPrint, x, y, width, height, null);
    }

}
