import java.awt.event.*;
import javax.swing.*;
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

    public GameServer() {
        numPlayers = 0;
        turnsMade = 0;
        maxTurns = 9;
        serverTime = 0;

        p1BlobType = " ";
        p2BlobType = " ";
        p1Direction = " ";
        p2Direction = " ";
        p1EatBlob = false;
        p2EatBlob = false;
        p1VomitBlob = false;
        p2VomitBlob = false;
        try {
            ss = new ServerSocket(44444);
        } catch (IOException ex) {
            System.out.println("IOException from GameServer constructor");
        }
    }

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

    private class ServerTimerThread implements Runnable {

        public void run() {
            try {
                while (true) {
                    if (serverTime < 15) { // what if we turn this into 24? since the threads are kind of having a hard time checking if 26 is alrdy out of the limit
                        serverTime++;
                    } else {
                        serverTime = 1;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        System.out.println("InterruptedException in run() while loop in ServerTimerThread");
                    }
                }
            } catch (Exception e) {
                System.out.println("Exception in run() ServerTimerThread");
            }
            
        }
    }

    private class ReadFromClient implements Runnable {
        private int playerID;
        private DataInputStream dataIn;

        public ReadFromClient(int pid, DataInputStream in){
            playerID = pid;
            dataIn = in;
            System.out.println("Read from Client " + playerID + " created.");
        }

        public void run() {
            try {
                while (true) {
                    if (playerID == 1){
                        p1BlobType = dataIn.readUTF();
                        p1Direction = dataIn.readUTF();
                        p1EatBlob = dataIn.readBoolean();
                        p1VomitBlob = dataIn.readBoolean();
                    } else if (playerID == 2){
                        p2BlobType = dataIn.readUTF();
                        p2Direction = dataIn.readUTF();
                        p2EatBlob = dataIn.readBoolean();
                        p2VomitBlob = dataIn.readBoolean();
                    }
                }
            } catch (IOException ex) {
                System.out.println("IOException at WTS run()");
            }
        }
    }

    private class WriteToClient implements Runnable {
        private int playerID;
        private DataOutputStream dataOut;

        public WriteToClient(int pid, DataOutputStream out){
            playerID = pid;
            dataOut = out;
            System.out.println("Write to Client " + playerID + " created.");
        }

        public void run() {
            try {
                while (true) {
                    dataOut.writeInt(serverTime);
                    if (playerID == 1){
                        dataOut.writeUTF(p2BlobType);
                        dataOut.writeUTF(p2Direction);
                        dataOut.writeBoolean(p2EatBlob);
                        dataOut.writeBoolean(p2VomitBlob);
                    } else if (playerID == 2) {
                        dataOut.writeUTF(p1BlobType);
                        dataOut.writeUTF(p1Direction);
                        dataOut.writeBoolean(p1EatBlob);
                        dataOut.writeBoolean(p1VomitBlob);
                    }
                    
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

    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }
}
