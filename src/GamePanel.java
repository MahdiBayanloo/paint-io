import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener {
    private final int Delay = 375;
    private final int unit_size = 25;
    private final int scale = 3;
    private final int screen_width = scale * 600;
    private final int camera_width = screen_width / scale;
    private final int startPX = ((camera_width / unit_size) - 4) / 2;
    private final int screen_hight = scale * 600;
    private final int camera_hight = screen_hight / scale;
    private final int startPY = ((camera_hight / unit_size) - 4) / 2;
    private final int Game_units = (screen_width * screen_hight) / unit_size;
    private final int[] x = new int[Game_units];
    private final int[] y = new int[Game_units];
    private final int[][] panelMtx = new int[screen_width / unit_size][screen_hight / unit_size];
    private int bodyParts = 0;
    char direction = 'L';
    boolean running = false;
    Timer timer;
    Draw dd = new Draw(screen_hight, screen_width, unit_size, startPY, startPX, x, y, panelMtx, bodyParts);

    GamePanel() {
        this.setPreferredSize(new Dimension(camera_width, camera_hight));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdepter());
        this.addMouseMotionListener(new MyMouseAdepter());
        startGame();
    }

    public void startGame() {
        running = true;
        dd.primaryPaint();
        timer = new Timer(Delay, this);
        timer.start();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        dd.drawer(g);
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
                System.arraycopy(panelMtx[screen_width / unit_size - 1], 1, panelMtx[0], 1, screen_hight / unit_size - 1);
                // Shift each element in each column down by the shiftAmount
                for (int i = screen_hight / unit_size - 1; i > 0; i--) {
                    System.arraycopy(panelMtx[i - 1], 0, panelMtx[i], 0, screen_width / unit_size);
                }
                break;
            case 'D':
                //y[0] = y[0] + unit_size;
                // Add a row of zeros at the top
                System.arraycopy(panelMtx[0], 0, panelMtx[screen_width / unit_size - 1], 0, screen_hight / unit_size);
                // Shift each element in each column down by the shiftAmount
                for (int i = 0; i < screen_hight / unit_size - 1; i++) {
                    System.arraycopy(panelMtx[i + 1], 0, panelMtx[i], 0, screen_width / unit_size);
                }
                break;
            case 'L':
                //x[0] = x[0] - unit_size;
                for (int i = 0; i < screen_width / unit_size; i++) {
                    int firstElement = panelMtx[i][screen_hight / unit_size - 1];
                    for (int j = screen_hight / unit_size - 1; j > 0; j--) {
                        panelMtx[i][j] = panelMtx[i][j - 1];

                    }
                    panelMtx[i][0] = firstElement;
                }
                break;
            case 'R':
                //x[0] = x[0] + unit_size;
                for (int i = 0; i < screen_width / unit_size; i++) {
                    int firstElement = panelMtx[i][0];
                    for (int j = 0; j < screen_hight / unit_size - 1; j++) {
                        panelMtx[i][j] = panelMtx[i][j + 1];
                    }
                    panelMtx[i][screen_hight / unit_size - 1] = firstElement;
                }
                break;
        }

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

    public class MyMouseAdepter extends MouseAdapter {
        private int characterX = 0;
        private int characterY = 0;

        @Override
        public void mouseMoved(MouseEvent e) {
            int mouseX = e.getX();
            int mouseY = e.getY();

            // Calculate the movement direction based on mouse position
            int dx = mouseX - characterX;
            int dy = mouseY - characterY;

            // Update character position
            characterX = mouseX;
            characterY = mouseY;

            // Perform character movement based on direction
            if (dx > 0) {
                // Move right
                direction = 'R';
            } else if (dx < 0) {
                // Move left
                direction = 'L';
            }

            if (dy > 0) {
                // Move down
                direction = 'D';
            } else if (dy < 0) {
                // Move up
                direction = 'U';
            }
        }
    }

    /*public void chap() {
        for (int i = 0; i < screen_width / unit_size; i++) {
            for (int j = 0; j < screen_hight / unit_size; j++) {
                System.out.print(panelMtx[i][j] + " ");
                if (j == ((screen_hight / unit_size) - 1))
                    System.out.println();
            }
        }
    }*/

}
