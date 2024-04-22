
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;

public class GameFrame {
    private JFrame f;
    private JPanel cp;
    private GameCanvas gc;

    private Player player;

    private String direction;

    public GameFrame() {
        f = new JFrame();
        gc = new GameCanvas(1200, 600);
        cp = (JPanel) f.getContentPane();
        cp.setFocusable(true);

        direction = " ";

        player = gc.getPlayer();
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

    public void setUpTimeListen() {
        class TimeListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent ae){
                gc.repaint();

                player.checkWallCollision();

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

                    default:
                        player.moveIdle();
                        break;
                }
            }
        }

        ActionListener timeListener = new TimeListener();
        Timer timer = new Timer(10, timeListener);
        timer.start();
    }
}

