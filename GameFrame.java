/**
<<<<<<< HEAD
	GameFrame class takes care of all the game logic, and draws out the animations
=======
>>>>>>> 09fa670037b4d23ce9c7c6f206e520812096caaf
	
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


import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.Random;

public class GameFrame {
    private JFrame f;
    private JPanel cp;
    private GameCanvas gc;

    private Player player;
    private Player opponent;
    private ArrayList<Wall> walls;
    private ArrayList<Blob> blobs;

    private WaitingScreen waitingScreen;
    private WaitingForOtherPlayerScreen waitingForOtherPlayerScreen;
    private WinLoseScreen winLoseScreen;
    private GetReadyScreen getReadyScreen;
    private WinLoseGameScreen winLoseGameScreen;
    private TimerScreen timerScreen;
    private BlobIconScreen myBlobIconScreen;
    private BlobIconScreen theirBlobIconScreen;
    private PointsTextScreen pointsTextScreen;

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
    private boolean canSpawn;

    private BlobChecker blobChecker;

    private boolean hasPlayerWon;
    private boolean stopServerTimer;

    private int playerPoints;
    private int opponentPoints;

    private Audio roundResultSFX;
    private boolean canPlayRoundAudio;

    private Audio getReadySFX;
    private boolean canPlayReadyAudio;

    private Audio gameResultSFX;
    private boolean canPlayGameAudio;

    private Random rand;
    private int levelNum;

    /**
     * Constructor for GameFrame
     */
    public GameFrame() {
        f = new JFrame();
        cp = (JPanel) f.getContentPane();
        cp.setFocusable(true);
        levelNum = 1;
        resetEverything();
        rand = new Random();
        
    }
    
    /**
     * Resets all the variables
     * Useful for resetting Game
     */
    private void resetEverything(){
        direction = " ";
        opponentDirection = " ";
        turn = " ";
        playerSpeed = 5;
        playerBlobType = " ";
        
        opponentBlobType = " ";
        canIncrement = false;

        clientTime = 0;
        canConnect = false;
        canSpawn = true;

        hasPlayerWon = false;

        hasEatenBlob = false;
        opponentEatsBlob = false;
        hasVomitBlob = false;
        opponentVomitBlob = false;

        stopServerTimer = false;
        playerPoints = 0;
        opponentPoints = 0;

        roundResultSFX = new Audio();
        canPlayRoundAudio = false;

        getReadySFX = new Audio();
        canPlayReadyAudio = false;

        gameResultSFX = new Audio();
        canPlayGameAudio = false;
    }

    /**
     * Setting up Frame and Game Canvas
     */
    public void setUpGUI() {
        gc = new GameCanvas(1200, 600, playerID);

        gc.setPreferredSize(new Dimension(1200, 600));
        cp.add(gc, BorderLayout.CENTER);

        spawnLevel(levelNum);

        waitingScreen = gc.getWaitingScreen();
        waitingForOtherPlayerScreen = gc.getWaitingForOtherPlayerScreen();
        winLoseScreen = gc.getWinLoseScreen();
        getReadyScreen = gc.getReadyScreen();
        winLoseGameScreen = gc.getWinLoseGameScreen();
        timerScreen = gc.getTimerScreen();
        myBlobIconScreen = gc.getMyBlobIconScreen();
        theirBlobIconScreen = gc.getTheirBlobIconScreen();
        pointsTextScreen = gc.getPointsTextScreen();

        playerPoints = player.getPoints();

        f.setTitle("Rock Paper Scissors. You are Player #" + playerID);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
        
        blobChecker = new BlobChecker();
        Thread blobThread = new Thread(blobChecker);
        blobThread.start();
    }

    /**
     * Spawns a level
     * @param level which level to do
     */
    private void spawnLevel(int level){
        gc.clearLevel();
        gc.setUpLevel(level);
        player = gc.getPlayer();
        player.setPoints(playerPoints);
        opponent = gc.getOpponent();
        playerX = player.getX();
        playerY = player.getY();
        opponentX = opponent.getX();
        opponentY = opponent.getY();
        walls = gc.getWalls();
        blobs = gc.getBlobs();
        player.clearBlob();
        opponent.clearBlob();
    }

    /**
     * Connects to the server
     * @param host
     * @param port
     */
    public void connectToServer(String host, int port) {
        try {
            socket = new Socket(host, port);
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

    /**
     * Reads all information from the server
     */
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
                    opponentX = dataIn.readInt();
                    opponentY = dataIn.readInt();
                    opponentDirection = dataIn.readUTF();
                    opponentEatsBlob = dataIn.readBoolean();
                    opponentVomitBlob = dataIn.readBoolean();
                    opponentPoints = dataIn.readInt();
                    levelNum = dataIn.readInt();
                }
            } catch (IOException ex) {
                System.out.println("IOException from RFS run()");
            }
        }

    }

    /**
     * Writes all information from the server
     */
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
                    dataOut.writeInt(playerX);
                    dataOut.writeInt(playerY);
                    dataOut.writeUTF(direction);
                    dataOut.writeBoolean(hasEatenBlob);
                    dataOut.writeBoolean(hasVomitBlob);
                    dataOut.writeInt(playerPoints);
                    dataOut.writeBoolean(stopServerTimer);
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
    
    /**
     * Adds Key Bindings to the Game Frame
     * Useful for keyboard actions
     */
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

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "mLeft");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "mRight");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "mDown");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "mUp");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "eatBlob");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "notEatBlob");
    }

    /**
     * Checked every frame, checks the collision of the players with walls and borders
     */
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

    /**
     * Checked every frame, checks the collision of the Blobs with player and opponent
     * Eats when both isCollidingBlob and isEatingBlob is true
     */
    private void checkBlobBehavior(){
        for (Blob obj: blobs){
            if (player.checkBlobCollision(obj)){
                isCollidingBlob = true;
            } else {
                isCollidingBlob = false;
            }

            if (isCollidingBlob && isEatingBlob){
                player.eatBlob(obj);
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

    /**
     * Sets a String to a certain thing when Time is at a certain amount
     */
    private void turnManager() {
        if (clientTime == 0) {
            turn = "waitingMenu";
        } else if (clientTime < 3) {
            turn = "gameStartingMenu";
        } else if (clientTime < 6) {
            turn = "countDownMenu";
        } else if (clientTime < 16) {
            turn = "fightRound";
        } else if (clientTime < 19) {
            turn = "decidingTurn";
        } else if (clientTime < 22) {
            turn = "finishMenu";
        } else if (clientTime > 22) {
            turn = "restart";
        }
    }


    /**
     * Sets up all the required logic when the Time is at a certain amount
     */
    private void setUpTurnChanges() {
        switch (turn) {
            case "waitingMenu":
                gc.removeScreens();
                waitingForOtherPlayerScreen.setVisible();
                player.setSpeed(0);
                opponent.setSpeed(0);
                break;
            case "gameStartingMenu":
                gc.removeScreens();
                waitingScreen.setVisible();
                player.setSpeed(0);
                opponent.setSpeed(0);
                canPlayGameAudio = true;
                canPlayReadyAudio = true;
                break;
            case "countDownMenu":

                stopServerTimer = false;
                if (canSpawn){
                    
                    spawnLevel(levelNum);
                    canSpawn = false;
                }
                gc.removeScreens();
                getReadyScreen.setVisible();
                player.setSpeed(0);
                opponent.setSpeed(0);

                if (canPlayReadyAudio){
                    getReadySFX.setFile("rock paper scissors shoot.wav");
                    getReadySFX.play();
                    canPlayReadyAudio = false;
                }
                
                break;
            case "fightRound":
                stopServerTimer = false;
                gc.removeScreens();

                timerScreen.changeState(clientTime);
                timerScreen.setVisible();

                myBlobIconScreen.changeState(playerBlobType, checkBlobWinning(player, opponentBlobType));
                theirBlobIconScreen.changeState(opponentBlobType, checkBlobWinning(opponent, playerBlobType));

                myBlobIconScreen.setVisible();
                theirBlobIconScreen.setVisible();

                pointsTextScreen.changePoints(playerPoints, opponentPoints);
                pointsTextScreen.setVisible();

                player.setSpeed(playerSpeed);
                opponent.setSpeed(playerSpeed);
                canIncrement = true;
                canPlayRoundAudio = true;
                canPlayReadyAudio = true;
                break;
            case "decidingTurn":
                gc.removeScreens();
                winLoseScreen.changeState(playerBlobType, opponentBlobType);
                winLoseScreen.setVisible();
                player.setSpeed(0);
                opponent.setSpeed(0);
                whoWonRound();
                canSpawn = true;
                pointsTextScreen.changePoints(playerPoints, opponentPoints);
                break;
            case "finishMenu":
                gc.removeScreens();
                winLoseGameScreen.changeState(hasPlayerWon);
                winLoseGameScreen.setVisible();
                player.setSpeed(0);
                opponent.setSpeed(0);
                player.setPoints(0);
                player.vomitBlob();
                opponent.vomitBlob();

                if (canPlayGameAudio) {
                    if (hasPlayerWon){
                        gameResultSFX.setFile("win sfx.wav");
                    } else {
                        gameResultSFX.setFile("lose sfx.wav");
                    }
                    gameResultSFX.play();
                    canPlayGameAudio = false;
                }

                break;
            case "restart":
                resetEverything(); 
                break;
            default:
                break;
        }
    }

    /**
     * Helper method for checking if a blob is winning
     * @param me a Player
     * @param blobType the Blob type to check if the player is winning against it
     * @return true if the player wins against the Blob
     */
    private boolean checkBlobWinning(Player me, String blobType) {
        boolean itWins = false;
        if (me.checkHasBlob()) {
            itWins = me.getBlob().doesItWinAgainst(blobType);
        } 
        return itWins;
    }

    /**
     * Helper method to check who wins the round
     * Plays the SFX
     * Increments points
     * Stops the Server Timer
     */
    private void whoWonRound() {
        if (player.checkHasBlob()){
            if (player.getBlob().doesItWinAgainst(opponentBlobType)){
                if (canIncrement) {
                    player.incrementPoints();
                    canIncrement = false;
                }
                if (canPlayRoundAudio){
                    roundResultSFX.setFile("congratulations sfx.wav");
                    roundResultSFX.play();
                    canPlayRoundAudio = false;
                }
            } else {
                if (canPlayRoundAudio){
                    roundResultSFX.setFile("try again sfx.wav");
                    roundResultSFX.play();
                    canPlayRoundAudio = false;
                }
            }
        } else if (playerBlobType.equals(" ")){
            if (canPlayRoundAudio){
                roundResultSFX.setFile("huh.wav");
                roundResultSFX.play();
                canPlayRoundAudio = false;
            }
        }
        if ((playerPoints >= 3 || opponentPoints >= 3) && clientTime == 18){
            if (playerPoints >= 3) {
                hasPlayerWon = true;
            }
            stopServerTimer = true;
        }

    }

    /**
     * Checked every frame, checks which direction the player is going and moves them
     */
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

    /**
     * Checked every frame, checks which direction the opponent is going and moves them
     */
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

    /**
     * The main Game Loop or Game Timer
     * All methods to be checked and run every frame is located here
     * All variables to be updated are located here as well
     */
    public void setUpTimeListen() {
        class TimeListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent ae){

                playerPoints = player.getPoints();
                playerX = player.getX();
                playerY = player.getY();
                opponent.setX(opponentX);
                opponent.setY(opponentY);

                if (player.checkHasBlob()){
                    playerBlobType = player.getBlob().getType();
                } else {
                    playerBlobType = " ";
                }

                if (opponent.checkHasBlob()){
                    opponent.getBlob().setType(opponentBlobType);
                } else {
                    opponentBlobType = " ";
                }

                checkPlayerDirection();
                checkOpponentDirection();

                checkCollisions();

                checkBlobBehavior();

                turnManager();
                setUpTurnChanges();

                gc.repaint();
            }
        }

        ActionListener timeListener = new TimeListener();
        Timer timer = new Timer(5, timeListener);
        timer.start();
    }

    /**
     * Helper class, a runnable, which basically waits for a few seconds before turning booleans into false
     * This gives enough time for the server to recognize the change 
     */
    private class BlobChecker implements Runnable {
        public void run(){
            try {
                while (true) {
                    if (hasEatenBlob) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            System.out.println("InterruptedException in run() while loop in ServerTimerThread");
                        }
                        hasEatenBlob = false;
                        
                    }
                    if (hasVomitBlob) {
                        try {
                            Thread.sleep(100);
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

    /**
     * Gets the player ID
     * @return the player ID
     */
    public int getPlayerID(){
        return playerID;
    }
}

