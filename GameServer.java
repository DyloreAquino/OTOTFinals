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

    public GameServer() {
        numPlayers = 0;
        turnsMade = 0;
        maxTurns = 9;
        serverTime = 0;
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
            class ServerTimer implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent ae){
                    serverTime++;
                }
            }
            ActionListener serverTimeListener = new ServerTimer();
            Timer timer = new Timer(1000, serverTimeListener);
            timer.start();
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
                    dataOut.flush();
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
