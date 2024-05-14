/**
	
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

import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.TreeUI;
import java.util.Random;
import java.net.*;
import java.io.*;


public class GameServer {
    
    private ServerSocket ss;
    private int numPlayers;
    private int turnsMade;
    private int maxTurns;

    private int serverTime;

    private Socket p1Socket;
    private Socket p2Socket;

    private ReadFromClient p1ReadRunnable;
    private ReadFromClient p2ReadRunnable;
    private WriteToClient p1WriteRunnable;
    private WriteToClient p2WriteRunnable;

    private ServerTimerThread stimeThread;

    private String p1BlobType;
    private String p2BlobType;
    private int p1X;
    private int p1Y;
    private int p2X;
    private int p2Y;
    private String p1Direction;
    private String p2Direction;
    private boolean p1EatBlob;
    private boolean p2EatBlob;
    private boolean p1VomitBlob;
    private boolean p2VomitBlob;
    private int p1Points;
    private int p2Points;

    private boolean stopServerTimerp1;
    private boolean stopServerTimerp2;

    private int levelNum;
    private int levelAmt = 2;

    private Random rand;

    public GameServer() {
        numPlayers = 0;
        serverTime = 0;

        resetEverything();

        levelNum = 1;

        rand = new Random();
        try {
            ss = new ServerSocket(port);
        } catch (IOException ex) {
            System.out.println("IOException from GameServer constructor");
        }
    }

    /** resets all variables */
    private void resetEverything() {
        p1BlobType = " ";
        p2BlobType = " ";
        p1Direction = " ";
        p2Direction = " ";
        p1EatBlob = false;
        p2EatBlob = false;
        p1VomitBlob = false;
        p2VomitBlob = false;
        p1Points = 0;
        p2Points = 0;
        stopServerTimerp1 = false;
        stopServerTimerp2 = false;
    }

    /** accepts connections from client */
    public void acceptConnections() {
        try {
            System.out.println("Waiting for connections");
            while (numPlayers < 2) {
                Socket s = ss.accept();
                DataInputStream in = new DataInputStream(s.getInputStream());
                DataOutputStream out = new DataOutputStream(s.getOutputStream());

                numPlayers++;
                out.writeInt(numPlayers);
                System.out.println("Player #" + numPlayers + " has connected.");

                ReadFromClient rfc = new ReadFromClient(numPlayers, in);
                WriteToClient wtc = new WriteToClient(numPlayers, out);

                stimeThread = new ServerTimerThread();

                if (numPlayers == 1){
                    p1Socket = s;
                    p1ReadRunnable = rfc;
                    p1WriteRunnable = wtc;
                } else {
                    p2Socket = s;
                    p2ReadRunnable = rfc;
                    p2WriteRunnable = wtc;

                    Thread readThread1 = new Thread(p1ReadRunnable);
                    Thread readThread2 = new Thread(p2ReadRunnable);
                    readThread1.start();
                    readThread2.start();

                    Thread writeThread1 = new Thread(p1WriteRunnable);
                    Thread writeThread2 = new Thread(p2WriteRunnable);
                    writeThread1.start();
                    writeThread2.start();

                    Thread serverTimeThread = new Thread(stimeThread);
                    serverTimeThread.start();
                }

            }
            System.out.println("Two players. No connections allowed now.");
        } catch (IOException ex) {
            System.out.println("IOException from acceptConnections()");
        }
    }

    /** thread for server time action */
    private class ServerTimerThread implements Runnable {

        /** method on what the server timer will do */
        public void run() {
            try {
                while (true) {
                    levelNum = rand.nextInt(levelAmt) + 1;
                    if (stopServerTimerp1 == false && stopServerTimerp2 == false) {
                        if (serverTime < 18) { 
                            serverTime++;
                        } else {
                            serverTime = 3;
                        }
                    } else if (stopServerTimerp1 && stopServerTimerp2) {
                        if (serverTime < 24) { 
                            serverTime++;
                        } else {
                            resetEverything();
                            serverTime = 3;
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        System.out.println("InterruptedException in run() while loop in ServerTimerThread");
                    }
                    System.out.println(serverTime);
                }
            } catch (Exception e) {
                System.out.println("Exception in run() ServerTimerThread");
            }
            
        }
    }

    /** class for server read from client */
    private class ReadFromClient implements Runnable {
        private int playerID;
        private DataInputStream dataIn;

        /**
         * instantiates objects for the client to read
         * @param pid
         * @param in
         */
        public ReadFromClient(int pid, DataInputStream in){
            playerID = pid;
            dataIn = in;
            System.out.println("Read from Client " + playerID + " created.");
        }

        /** executes how the read will do */
        public void run() {
            try {
                while (true) {
                    if (playerID == 1){
                        p1BlobType = dataIn.readUTF();
                        p1X = dataIn.readInt();
                        p1Y = dataIn.readInt();
                        p1Direction = dataIn.readUTF();
                        p1EatBlob = dataIn.readBoolean();
                        p1VomitBlob = dataIn.readBoolean();
                        p1Points = dataIn.readInt();
                        stopServerTimerp1 = dataIn.readBoolean();
                    } else if (playerID == 2){
                        p2BlobType = dataIn.readUTF();
                        p2X = dataIn.readInt();
                        p2Y = dataIn.readInt();
                        p2Direction = dataIn.readUTF();
                        p2EatBlob = dataIn.readBoolean();
                        p2VomitBlob = dataIn.readBoolean();
                        p2Points = dataIn.readInt();
                        stopServerTimerp2 = dataIn.readBoolean();
                    }
                    System.out.println(p1Direction);
                    System.out.println(p2Direction);
                }
            } catch (IOException ex) {
                System.out.println("IOException at WTS run()");
            }
        }
    }

    /** class for writing to client */
    private class WriteToClient implements Runnable {
        private int playerID;
        private DataOutputStream dataOut;

        /**
         * instantiates objects to write to client
         * @param pid
         * @param out
         */
        public WriteToClient(int pid, DataOutputStream out){
            playerID = pid;
            dataOut = out;
            System.out.println("Write to Client " + playerID + " created.");
        }
        /** executes how the server will write to client */
        public void run() {
            try {
                while (true) {
                    dataOut.writeInt(serverTime);
                    if (playerID == 1){
                        dataOut.writeUTF(p2BlobType);
                        dataOut.writeInt(p2X);
                        dataOut.writeInt(p2Y);
                        dataOut.writeUTF(p2Direction);
                        dataOut.writeBoolean(p2EatBlob);
                        dataOut.writeBoolean(p2VomitBlob);
                        dataOut.writeInt(p2Points);
                    } else if (playerID == 2) {
                        dataOut.writeUTF(p1BlobType);
                        dataOut.writeInt(p1X);
                        dataOut.writeInt(p1Y);
                        dataOut.writeUTF(p1Direction);
                        dataOut.writeBoolean(p1EatBlob);
                        dataOut.writeBoolean(p1VomitBlob);
                        dataOut.writeInt(p1Points);
                    }
                    dataOut.writeInt(levelNum);
                    dataOut.flush();
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException ex) {
                        System.out.println("InterruptedException in run() while loop in ServerTimerThread");
                    }
                }
                
            } catch (IOException ex) {
                System.out.println("IOException from WTS run() with player" + playerID);
            }
        }
    }

    /**
     * main method for the server to run
     * @param args
     */
    public static void main(String[] args) {
        int portToSend = Integer.parseInt(args[0]);
        GameServer gs = new GameServer(portToSend);
        gs.acceptConnections();
    }
}
