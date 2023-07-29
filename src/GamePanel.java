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
    //final int[] xp = new int[Game_units];
    //final int[] yp = new int[Game_units];
    int[][] panelMtx = new int[screen_width / unit_size][screen_hight / unit_size];
    int bodyParts = 0;
    int startPX = ((screen_width / unit_size) - 4) / 2;
    int startPY = ((screen_hight / unit_size) - 4) / 2;
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

    public void primaryPaint() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                panelMtx[startPX + i][startPY + j] = 2;
            }
        }
    }

    public void chap() {
        for (int i = 0; i < screen_width / unit_size; i++) {
            for (int j = 0; j < screen_hight / unit_size; j++) {
                System.out.print(panelMtx[i][j] + " ");
                if (j == ((screen_hight / unit_size) - 1))
                    System.out.println();
            }
        }
    }

    public void startGame() {
        running = true;
        primaryPaint();
        timer = new Timer(Delay, this);
        timer.start();
        chap();
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
        //System.out.println(bodyParts);
        switch (direction) {
            case 'U' -> y[0] = y[0] - unit_size;
            case 'D' -> y[0] = y[0] + unit_size;
            case 'L' -> x[0] = x[0] - unit_size;
            case 'R' -> x[0] = x[0] + unit_size;
        }

    }

    public void fill() {
        for (int i = 0; i < screen_width / unit_size; i++) {
            for (int j = 0; j < screen_hight / unit_size; j++) {
                if (panelMtx[i][j] > 0)
                    panelMtx[i][j] = 2;
            }
        }
    }

    public void draw(Graphics g) {
        for (int i = 0; i < screen_hight / unit_size; i++) {
            g.drawLine(i * unit_size, 0, i * unit_size, screen_hight);
            g.drawLine(0, i * unit_size, screen_width, i * unit_size);
        }

        for (int i = 0; i < bodyParts; i++) {
            if (i == 0) {
                g.setColor(Color.green);
                g.fillRect(x[i] + startPX * unit_size, y[i] + startPY * unit_size, unit_size, unit_size);
            } else {
                g.setColor(new Color(45, 120, 20));
                g.fillRect(x[i] + startPX * unit_size, y[i] + startPY * unit_size, unit_size, unit_size);
                if (panelMtx[y[i] / unit_size + startPY][x[i] / unit_size + startPX] == 0)
                    panelMtx[y[i] / unit_size + startPY][x[i] / unit_size + startPX] = 1;
                else if (panelMtx[y[i] / unit_size + startPY][x[i] / unit_size + startPX] == 2) {
                    fill();
                    break;
                }

            }
            g.setColor(Color.green);
            for (int k = 0; k < screen_width / unit_size; k++) {
                for (int j = 0; j < screen_hight / unit_size; j++) {
                    if (panelMtx[k][j] == 2)
                        g.fillRect(j * unit_size, k * unit_size, unit_size, unit_size);
                }
            }

        }

    }

    public void checkCollisions() {
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                System.out.println("x[" + i + "]: " + x[i]);
                System.out.println("y[" + i + "]: " + y[i]);
                chap();
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
                case KeyEvent.VK_LEFT -> {
                    if (direction != 'R') {
                        direction = 'L';
                    }
                }
                case KeyEvent.VK_RIGHT -> {
                    if (direction != 'L') {
                        direction = 'R';
                    }
                }
                case KeyEvent.VK_UP -> {
                    if (direction != 'D') {
                        direction = 'U';
                    }
                }
                case KeyEvent.VK_DOWN -> {
                    if (direction != 'U') {
                        direction = 'D';
                    }
                }
            }
        }
    }
}
