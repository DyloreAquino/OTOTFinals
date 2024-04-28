
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class GameFrame {
    private JFrame f;
    private JPanel cp;
    private GameCanvas gc;

    private Player player;
    private ArrayList<Wall> walls;

    private String direction;

    public GameFrame() {
        f = new JFrame();
        gc = new GameCanvas(1200, 600);
        cp = (JPanel) f.getContentPane();
        cp.setFocusable(true);

        direction = "T";

        player = gc.getPlayer();
        walls = gc.getWalls();
    }

    public void setUpGUI() {

        gc.setPreferredSize(new Dimension(1200, 600));
        cp.add(gc, BorderLayout.CENTER);

        f.setTitle("Rock Paper Scissors");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
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

        

        am.put("mLeft", moveLeft);
        am.put("mRight", moveRight);
        am.put("mDown", moveDown);
        am.put("mUp", moveUp);

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "mLeft");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "mRight");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "mDown");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "mUp");
    }

    private void checkCollisions(){

        /*
         * maybe like add some local bool vars here
         */
        if (player.checkBorderCollision()) {
            direction = " ";
            System.out.println("colliding!");
            /*
             * then make that var ture here
             */
        }
        
        for (Wall obj: walls){
            if (player.checkWallCollision(obj)){
                direction = " ";
                System.out.println("colliding! to walls");
                /*
                * make that var ture here also
                */
            }
        }
    }

    public void setUpTimeListen() {
        class TimeListener implements ActionListener {

            

            @Override
            public void actionPerformed(ActionEvent ae){
                
                switch (direction) {
                    case "L":
                        if (player.getSpeed() == 0){
                            player.setX(player.getX() + 1);
                        }
                        player.moveLeft();
                        checkCollisions();
                        /*
                         * yo what if we add a boolean var so that if its true, it sets conditions that can't move towards the original direction
                         */
                        if (player.getSpeed() == 0){
                            player.setX(player.getX() + 1);
                        }
                        break;

                    case "R":
                        if (player.getSpeed() == 0){
                            player.setX(player.getX() - 1);
                        }
                        player.moveRight();
                        checkCollisions();
                        /*
                         * yo what if we add a boolean var so that if its true, it sets conditions that can't move towards the original direction
                         */
                        if (player.getSpeed() == 0){
                            player.setX(player.getX() - 1);
                        }
                        break;

                    case "D":
                        if (player.getSpeed() == 0){
                            player.setY(player.getY() + 1);
                        }
                        player.moveDown();
                        checkCollisions();
                        /*
                         * yo what if we add a boolean var so that if its true, it sets conditions that can't move towards the original direction
                         */
                        if (player.getSpeed() == 0){
                            player.setY(player.getY() + 1);
                        }
                        break;

                    case "U":
                        if (player.getSpeed() == 0){
                            player.setY(player.getY() - 1);
                        }
                        player.moveUp(); 
                        checkCollisions();
                        /*
                         * yo what if we add a boolean var so that if its true, it sets conditions that can't move towards the original direction
                         */
                        if (player.getSpeed() == 0){
                            player.setY(player.getY() - 1);
                        }
                        break;
                    case " ":
                        System.out.println("im in case nothing now");
                        player.moveNowhere();
                        break;
                    default:
                        break;
                }
                gc.repaint();
            }
        }

        ActionListener timeListener = new TimeListener();
        Timer timer = new Timer(3, timeListener);
        timer.start();
    }
}

