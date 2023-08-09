import java.awt.*;

public class Draw {
    private final int screen_hight;
    private final int screen_width;
    private final int unit_size;
    private final int startPY;
    private final int startPX;
    private final int[] x;
    private final int[] y;
    private int bodyParts;
    private final int[][] panelMtx;


    public Draw(int screenHight, int screenWidth, int unitSize, int startpy, int startpx, int[] X, int[] Y, int[][] panelmtx, int bodyparts) {
        screen_hight = screenHight;
        screen_width = screenWidth;
        bodyParts = bodyparts;
        unit_size = unitSize;
        startPY = startpy;
        startPX = startpx;
        x = X;
        y = Y;
        panelMtx = panelmtx;
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

    public void checkCollisions() {
        MatrixConverter converter = new MatrixConverter(panelMtx);
        converter.convertZeros();
    }

    public void drawer(Graphics g) {
        for (int i = 0; i < screen_hight / unit_size; i++) {
            g.setColor(Color.black);
            g.drawLine(i * unit_size, 0, i * unit_size, screen_hight);
            g.drawLine(0, i * unit_size, screen_width, i * unit_size);
        }

        for (int i = 0; i <= bodyParts; i++) {
            if (i == 0) {
                g.setColor(Color.green);
                g.fillRect(x[i] + startPX * unit_size, y[i] + startPY * unit_size, unit_size, unit_size);
                if (panelMtx[y[i] / unit_size + startPY][x[i] / unit_size + startPX] == 2) {
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


}
