
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
    private Player opponent;
    private ArrayList<Wall> walls;
    private ArrayList<Blob> blobs;
    private WaitingScreen waitingScreen;

    private String direction;
    private String opponentDirection;
    private int playerSpeed;

    private boolean isEatingBlob;
    private boolean isCollidingBlob;
    private boolean hasEatenBlob;
    private boolean opponentEatsBlob;
    private boolean hasVomitBlob;
    private boolean opponentVomitBlob;

    private int opponentX;
    private int opponentY;
    private int playerX;
    private int playerY;

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
    private boolean canConnect;

    private BlobChecker blobChecker;

    public GameFrame() {
        f = new JFrame();
        cp = (JPanel) f.getContentPane();
        cp.setFocusable(true);

        direction = " ";
        opponentDirection = " ";
        turn = " ";
        playerSpeed = 5;
        playerBlobType = " ";
        
        opponentBlobType = " ";
        canIncrement = false;

        clientTime = 0;
        canConnect = false;

    }

    public void setUpGUI() {
        gc = new GameCanvas(1200, 600, playerID);

        gc.setPreferredSize(new Dimension(1200, 600));
        cp.add(gc, BorderLayout.CENTER);

        player = gc.getPlayer();
        opponent = gc.getOpponent();
        playerX = player.getX();
        playerY = player.getY();
        opponentX = opponent.getX();
        opponentY = opponent.getY();
        walls = gc.getWalls();
        blobs = gc.getBlobs();
        waitingScreen = gc.getWaitingScreen();
        hasEatenBlob = false;
        opponentEatsBlob = false;
        hasVomitBlob = false;
        opponentVomitBlob = false;

        f.setTitle("Rock Paper Scissors. You are Player #" + playerID);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);

        blobChecker = new BlobChecker();
        Thread blobThread = new Thread(blobChecker);
        blobThread.start();
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
                    opponentDirection = dataIn.readUTF();
                    opponentEatsBlob = dataIn.readBoolean();
                    opponentVomitBlob = dataIn.readBoolean();
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
                    dataOut.writeUTF(direction);
                    dataOut.writeBoolean(hasEatenBlob);
                    dataOut.writeBoolean(hasVomitBlob);
                    

                    dataOut.flush();
                    try {
                        Thread.sleep(10);
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
                    blobs.add(player.vomitBlob());
                    playerBlobType = " ";
                    hasVomitBlob = true;
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

        if (opponent.checkBorderCollision()){
            opponentDirection = " ";
        }
        
        for (Wall obj: walls){
            if (player.checkWallCollision(obj)){
                direction = " ";
            }
            if (opponent.checkWallCollision(obj)){
                opponentDirection = " ";
            }
        }

        
    }

    private void checkBlobBehavior(){
        for (Blob obj: blobs){
            if (player.checkBlobCollision(obj)){
                isCollidingBlob = true;
            } else {
                isCollidingBlob = false;
            }

            if (isCollidingBlob && isEatingBlob){
                player.eatBlob(obj);
                playerBlobType = player.getBlob().getType();
                hasEatenBlob = true;
            }

            if (opponent.checkBlobCollision(obj)){
                if (opponentEatsBlob){
                    opponent.eatBlob(obj);
                }
            }
        }
        
        if (opponentVomitBlob){
            if (opponent.checkHasBlob()){
                blobs.add(opponent.vomitBlob());
            }
        }
    }

    private void turnManager() {
        if (clientTime == 0){
            turn = "waitingMenu";
        } else if (clientTime < 10) {
            turn = "fightRound";
        } else if (clientTime < 15) {
            turn = "decidingTurn";
        }
    }

    private void updateCoordinates(){
        playerX = player.getX();
        playerY = player.getY();
        opponentX = opponent.getX();
        opponentY = opponent.getY();
    }

    private void setUpTurnChanges() {
        switch (turn) {
            case "waitingMenu":
                waitingScreen.setVisible();
                player.setSpeed(0);
                opponent.setSpeed(0);
                canIncrement = true;
                break;
            case "fightRound":
                waitingScreen.setInvisible();
                player.setSpeed(playerSpeed);
                opponent.setSpeed(playerSpeed);
                break;
            case "decidingTurn":
                waitingScreen.setVisible();
                player.setSpeed(0);
                opponent.setSpeed(0);
                whoWonRound();
                break;
            default:
                break;
        }
    }

    private void whoWonRound() {
        if (player.checkHasBlob()){
            if (player.getBlob().doesItWinAgainst(opponentBlobType)){
                if (canIncrement) {
                    player.incrementPoints();
                    canIncrement = false;
                }
            } else {
    
            }
        } else {
            
        }
        
    }

    private void checkPlayerDirection(){
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
        player.changeSpriteState(direction);
    }

    private void checkOpponentDirection(){
        switch (opponentDirection) {
            case "L":
                opponent.moveLeft();
                break;

            case "R":
                opponent.moveRight();
                break;

            case "D":
                opponent.moveDown();
                break;

            case "U":
                opponent.moveUp(); 
                break;

            case " ":
                break;

            default:
                break;
        }

        opponent.changeSpriteState(opponentDirection);
    }

    public void setUpTimeListen() {
        class TimeListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent ae){

                gc.repaint();

                checkPlayerDirection();
                checkOpponentDirection();

                checkCollisions();

                updateCoordinates();
                checkBlobBehavior();

                turnManager();
                setUpTurnChanges();
            }
        }

        ActionListener timeListener = new TimeListener();
        Timer timer = new Timer(5, timeListener);
        timer.start();
    }

    private class BlobChecker implements Runnable {
        public void run(){
            try {
                while (true) {
                    if (hasEatenBlob) {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException ex) {
                            System.out.println("InterruptedException in run() while loop in ServerTimerThread");
                        }
                        hasEatenBlob = false;
                    }
                    if (hasVomitBlob) {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException ex) {
                            System.out.println("InterruptedException in run() while loop in ServerTimerThread");
                        }
                        hasVomitBlob = false;
                    }
                }
            } catch (Exception e) {
                System.out.println("Exception in BlobChecker");
            }
        }
    }

    public int getPlayerID(){
        return playerID;
    }
}

