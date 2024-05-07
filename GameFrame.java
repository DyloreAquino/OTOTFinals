
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
    private WaitingScreen waitingScreen;

    private String direction;
    private int playerSpeed;

    private boolean isEatingBlob;
    private boolean isCollidingBlob;

    private int playerID;
    private int otherPlayer;

    private int turnsMade;
    private int maxTurns;

    private int myPoints;
    private int enemyPoints;

    private int clientTime;

    private Socket socket;
    private ReadFromServer rfsRunnable;
    private WriteToServer wtsRunnable;

    private String turn;
    private String playerBlobType;
    private String opponentBlobType;

    private boolean canIncrement;



    public GameFrame() {
        f = new JFrame();
        cp = (JPanel) f.getContentPane();
        cp.setFocusable(true);

        direction = " ";
        turn = " ";
        playerSpeed = 5;
        playerBlobType = " ";
        opponentBlobType = " ";
        canIncrement = false;

        clientTime = 0;


    }

    public void setUpGUI() {
        gc = new GameCanvas(1200, 600, playerID);

        gc.setPreferredSize(new Dimension(1200, 600));
        cp.add(gc, BorderLayout.CENTER);

        player = gc.getPlayer();
        walls = gc.getWalls();
        blobs = gc.getBlobs();
        waitingScreen = gc.getWaitingScreen();

        f.setTitle("Rock Paper Scissors. You are Player #" + playerID);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }

    public void connectToServer() {
        try {
            socket = new Socket("localhost", 44444);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            playerID = in.readInt();

            rfsRunnable = new ReadFromServer(in);
            wtsRunnable = new WriteToServer(out);

            Thread readThread = new Thread(rfsRunnable);
            Thread writeThread = new Thread(wtsRunnable);

            readThread.start();
            writeThread.start();
        } catch (IOException ex) {
            System.out.println("IOException from connectToServer()");
        }
    }

    private class ReadFromServer implements Runnable {

        private DataInputStream dataIn;

        public ReadFromServer(DataInputStream in) {
            dataIn = in;
            System.out.println("Read From Server " + playerID + " created.");
        }

        public void run(){
            try {
                while (true) {
                    clientTime = dataIn.readInt();
                    opponentBlobType = dataIn.readUTF();
                }
            } catch (IOException ex) {
                System.out.println("IOException from RFC run()");
            }
        }

    }

    private class WriteToServer implements Runnable {

        private DataOutputStream dataOut;

        public WriteToServer(DataOutputStream out) {
            dataOut = out;
            System.out.println("Write To Server " + playerID + " created.");
        }

        public void run(){
            try {
                while (true) {
                    dataOut.writeUTF(playerBlobType);
                    dataOut.flush();
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException ex) {
                        System.out.println("InterruptedException at WTS run()");
                    }
                }
            } catch (IOException ex) {
                System.out.println("IOException at WTS run()");
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
                    playerBlobType = " ";
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
                playerBlobType = player.getBlob().getType();
            }
        }
    }

    private void turnManager() {
        if (clientTime < 10) {
            turn = "player1Turn";
        } else if (clientTime < 20) {
            turn = "player2Turn";
        } else if (clientTime < 25) {
            turn = "decidingTurn";
        }
    }

    private void setUpTurnChanges() {
        switch (turn) {
            case "player1Turn":
                if (playerID == 1) {
                    waitingScreen.setInvisible();
                    player.setSpeed(playerSpeed);
                } else {
                    waitingScreen.setVisible();
                    player.setSpeed(0);
                }
                canIncrement = true;
                break;
            case "player2Turn":
                if (playerID == 1) {
                    waitingScreen.setVisible();
                    player.setSpeed(0);
                } else {
                    waitingScreen.setInvisible();
                    player.setSpeed(playerSpeed);
                }
                break;
            case "decidingTurn":
                waitingScreen.setVisible();
                player.setSpeed(0);
                whoWonRound();
                System.out.println(player.getPoints());
                break;
            default:
                break;
        }
    }

    //Problem: I am still yet to find a solution where the points only increment once when they win a round
    // since this is called in the timer too, then the points increment every millisecond grrrrr
    private void whoWonRound() {
        if (player.checkHasBlob()){
            if (player.getBlob().doesItWinAgainst(opponentBlobType)){
                System.out.println("You win!!!");
                if (canIncrement) {
                    player.incrementPoints();
                    canIncrement = false;
                }
            } else {
                System.out.println("Nope. Try Again.");
            }
        } else {
            System.out.println("You don't even have a blob!!!");
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

                turnManager();

                System.out.println(clientTime);

                //System.out.println(clientTime);
                setUpTurnChanges();
            }
        }

        ActionListener timeListener = new TimeListener();
        Timer timer = new Timer(1, timeListener);
        timer.start();
    }

    public int getPlayerID(){
        return playerID;
    }
}

