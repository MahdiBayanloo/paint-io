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
        switch (direction) {
            case 'U':
                //y[0] = y[0] - unit_size;
                for (int i = screen_hight / unit_size - 1; i > 0; i--) {
                    panelMtx[screen_width / unit_size - 1][i] = 0;
                }
                // Shift each element in each column down by the shiftAmount
                for (int i = screen_hight / unit_size - 1; i > 0; i--) {
                    System.arraycopy(panelMtx[i - 1], 0, panelMtx[i], 0, screen_width / unit_size);
                }
                break;
            case 'D':
                //y[0] = y[0] + unit_size;
                // Add a row of zeros at the top
                for (int i = 0; i < screen_hight / unit_size; i++) {
                    panelMtx[screen_width / unit_size - 1][i] = 0;
                }
                // Shift each element in each column down by the shiftAmount
                for (int i = 0; i < screen_hight / unit_size - 1; i++) {
                    System.arraycopy(panelMtx[i + 1], 0, panelMtx[i], 0, screen_width / unit_size);
                }
                break;
            case 'L':
                //x[0] = x[0] - unit_size;
                for (int i = 0; i < screen_width / unit_size; i++) {
                    int firstElement = panelMtx[i][0];
                    for (int j = screen_hight / unit_size - 1; j > 0; j--) {
                        panelMtx[i][j] = panelMtx[i][j - 1];
                    }
                    panelMtx[i][screen_hight / unit_size - 1] = 0;
                }
                break;
            case 'R':
                //x[0] = x[0] + unit_size;
                for (int i = 0; i < screen_width / unit_size; i++) {
                    int firstElement = panelMtx[i][0];
                    for (int j = 0; j < screen_hight / unit_size - 1; j++) {
                        panelMtx[i][j] = panelMtx[i][j + 1];
                    }
                    panelMtx[i][screen_hight / unit_size - 1] = 0;
                }
                break;
        }

    }


    public void primaryPaint() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                panelMtx[startPX + i][startPY + j] = 2;
            }
        }
    }

    public void fill() {
        for (int i = 0; i < screen_width / unit_size; i++) {
            for (int j = 0; j < screen_hight / unit_size; j++) {
                if (panelMtx[i][j] > 0)
                    panelMtx[i][j] = 2;
            }
        }
        bodyParts = 1;

    }

    public void draw(Graphics g) {
        for (int i = 0; i < screen_hight / unit_size; i++) {
            g.setColor(Color.WHITE);
            g.drawLine(i * unit_size, 0, i * unit_size, screen_hight);
            g.drawLine(0, i * unit_size, screen_width, i * unit_size);
        }

        for (int i = 0; i <= bodyParts; i++) {
            if (i == 0) {
                g.setColor(Color.green);
                g.fillRect(x[i] + startPX * unit_size, y[i] + startPY * unit_size, unit_size, unit_size);
                if (panelMtx[y[i] / unit_size + startPY][x[i] / unit_size + startPX] == 2 && bodyParts > 1) {
                    fill();
                    checkCollisions();
                }
                panelMtx[y[i] / unit_size + startPY][x[i] / unit_size + startPX] = 1;
            }
        }
        for (int k = 0; k < screen_width / unit_size; k++) {
            for (int j = 0; j < screen_hight / unit_size; j++) {
                if (panelMtx[k][j] == 2) {
                    g.setColor(Color.green);
                    g.fillRect(j * unit_size, k * unit_size, unit_size, unit_size);
                }
                g.setColor(new Color(45, 180, 10, 100));
                if (panelMtx[k][j] == 1) {
                    g.fillRect(j * unit_size, k * unit_size, unit_size, unit_size);
                }

            }
        }

    }

    /*public void checkCollisions() {
        for (int i = bodyParts; i > 0; i--) {
            for (int j = i - 1; j > 0; j--) {
                if (x[i] == x[j]) {
                    for (int k = j; k <= i; k++) {
                        panelMtx[y[k] / unit_size + startPY][x[j] / unit_size + startPX] = 2;
                    }
                }
                if (y[i] == y[j]) {
                    for (int k = j; k <= i; k++) {
                        panelMtx[y[j] / unit_size + startPY][x[k] / unit_size + startPX] = 2;
                    }
                }
                if (y[i] == y[j] && x[i] == x[j]) {
                    for (int k = j; k <= i; k++) {
                        panelMtx[y[k] / unit_size + startPY][x[k] / unit_size + startPX] = 2;
                    }
                }
            }
        }
        bodyParts = 0;
        chap();
        System.out.println("dfdfdfdsfsss");
    }
    public void checkCollisions() {
        for (int i = 1; i < screen_width / unit_size - 1; i++) {
            int sum = 0;
            for (int j = 1; j < screen_hight / unit_size - 1; j++) {
                sum = sum + panelMtx[i][j];
                int sum1 = sum + panelMtx[i][j + 1];
                if (sum1 == 6)
                    sum = 0;
                if (sum >= 4) {
                    int k = j - 1;
                    while (panelMtx[i][k] == 0) {
                        panelMtx[i][k] = 2;
                        k--;
                        if (k < 0)
                            break;
                    }
                    sum = 0;
                }
            }
        }
    }*/
    public void checkCollisions() {
        MatrixConverter converter = new MatrixConverter(panelMtx);
        converter.convertZeros();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (running) {
            move();
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
