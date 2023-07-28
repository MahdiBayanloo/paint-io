import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int screen_width = 600;
    static final int screen_hight = 600;
    static final int unit_size = 25;
    static final int Game_units = (screen_width * screen_hight) / unit_size;
    static final int Delay = 275;
    final int[] x = new int[Game_units];
    final int[] y = new int[Game_units];
    final int[] xp = new int[Game_units];
    final int[] yp = new int[Game_units];

    int bodyParts = 0;
    int startPX = 11 * unit_size;
    int startPY = 8 * unit_size;

    char direction = 'L';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel() {

        random = new Random();
        this.setPreferredSize(new Dimension(screen_width, screen_hight));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdepter());
        startGame();
    }

    public void startGame() {
        //newSpace();
        running = true;
        timer = new Timer(Delay, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        draw(g);
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        bodyParts++;
        System.out.println(bodyParts);
        switch (direction) {
            case 'U':
                y[0] = y[0] - unit_size;
                break;
            case 'D':
                y[0] = y[0] + unit_size;
                break;
            case 'L':
                x[0] = x[0] - unit_size;
                break;
            case 'R':
                x[0] = x[0] + unit_size;
                break;
        }

    }

    public void GameOver(Graphics g) {

    }

    public void draw(Graphics g) {
        for (int i = 0; i < screen_hight / unit_size; i++) {
            g.drawLine(i * unit_size, 0, i * unit_size, screen_hight);
            g.drawLine(0, i * unit_size, screen_width, i * unit_size);
        }

        for (int i = 0; i < bodyParts; i++) {
            if (i == 0) {
                g.setColor(Color.green);
                g.fillRect(x[i] + startPX, y[i] + startPY, unit_size, unit_size);
            } else {
                g.setColor(new Color(45, 120, 20));
                g.fillRect(x[i] + startPX, y[i] + startPY, unit_size, unit_size);
            }
            g.setColor(Color.green);
            g.fillRect(startPX, startPY, unit_size * 5, unit_size * 5);
            for (int j = startPX; j < startPX + 6; j++) {
                x[j] = 1;
                y[j] = 1;
            }
        }

    }

    /*public void newSpace(){
        SpaceX = screen_width/2;
        SpaceY = screen_hight/2;
        //random.nextInt((int)(screen_width/unit_size))*unit_size;

    }*/

    public void checkCollisions() {
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
                break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (running) {
            move();
            checkCollisions();
        }
        repaint();

    }

    public class MyKeyAdepter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
