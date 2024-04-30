
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import java.net.*;
import java.io.*;

public class GameFrame {
    private JFrame f;
    private JPanel cp;
    private GameCanvas gc;

    private Player player;
    private ArrayList<Wall> walls;
    private ArrayList<Blob> blobs;

    private String direction;

    private boolean isEatingBlob;
    private boolean isCollidingBlob;

    private ClientSideConnection csc;

    private int playerID;
    private int otherPlayer;

    public GameFrame() {
        f = new JFrame();
        cp = (JPanel) f.getContentPane();
        cp.setFocusable(true);

        direction = " ";
    }

    public void setUpGUI() {
        gc = new GameCanvas(1200, 600, playerID);

        gc.setPreferredSize(new Dimension(1200, 600));
        cp.add(gc, BorderLayout.CENTER);

        player = gc.getPlayer();
        walls = gc.getWalls();
        blobs = gc.getBlobs();

        f.setTitle("Rock Paper Scissors. You are Player #" + playerID);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }

    public void connectToServer() {
        csc = new ClientSideConnection();
    }

    //Client Connection
    private class ClientSideConnection {
        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;

        public ClientSideConnection() {
            System.out.println("Client");
            try {
                socket = new Socket("localhost", 44444);
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());

                playerID = dataIn.readInt();
                System.out.println("Connected to Server as Player #" + playerID);
            } catch (IOException ex) {
                System.out.println("IOException from CSC constructor");
            }
        }
    }

    public void addKeyBindings() {
        ActionMap am = cp.getActionMap();
        InputMap im = cp.getInputMap();

        AbstractAction moveLeft = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                direction = "L";
            }
        };

        AbstractAction moveRight = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                direction = "R";
            }
        };

        AbstractAction moveDown = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                direction = "D";
            }
        };

        AbstractAction moveUp = new AbstractAction() {
            public void actionPerformed(ActionEvent ae) {
                direction = "U";
            }
        };

        AbstractAction eatBlob = new AbstractAction() {
            public void actionPerformed(ActionEvent ae){
                if (player.checkHasBlob()){
                    player.vomitBlob();
                } else {
                    isEatingBlob = true;
                }
            }
        };

        AbstractAction notEatBlob = new AbstractAction() {
            public void actionPerformed(ActionEvent ae){
                isEatingBlob = false;
            }
        };

        am.put("mLeft", moveLeft);
        am.put("mRight", moveRight);
        am.put("mDown", moveDown);
        am.put("mUp", moveUp);
        am.put("eatBlob", eatBlob);
        am.put("notEatBlob", notEatBlob);

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "mLeft");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "mRight");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "mDown");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "mUp");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "eatBlob");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "notEatBlob");
    }

    private void checkCollisions(){

        if (player.checkBorderCollision()) {
            direction = " ";
        }
        
        for (Wall obj: walls){
            if (player.checkWallCollision(obj)){
                direction = " ";
            }
        }
    }

    private void checkBlobCollisionWithPlayer(){
        for (Blob obj: blobs){
            if (player.checkBlobCollision(obj)){
                isCollidingBlob = true;
            } else {
                isCollidingBlob = false;
            }

            if (isCollidingBlob && isEatingBlob){
                player.eatBlob(obj);
            }
        }
    }

    public void setUpTimeListen() {
        class TimeListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent ae){

                switch (direction) {
                    case "L":
                        player.moveLeft();
                        break;

                    case "R":
                        player.moveRight();
                        break;

                    case "D":
                        player.moveDown();
                        break;

                    case "U":
                        player.moveUp(); 
                        break;

                    case " ":
                        break;

                    default:
                        break;
                }

                checkBlobCollisionWithPlayer();

                checkCollisions();

                gc.repaint();
            }
        }

        ActionListener timeListener = new TimeListener();
        Timer timer = new Timer(3, timeListener);
        timer.start();
    }

    public int getPlayerID(){
        return playerID;
    }
}

