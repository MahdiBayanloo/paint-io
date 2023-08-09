public class Move {
    private final int screen_hight;
    private final int screen_width;
    private final int unit_size;
    private final int startPY;
    private final int startPX;
    private final int[] x;
    private final int[] y;
    private final int[][] panelMtx;
    private int bodyParts;

    public Move(int screenHight, int screenWidth, int unitSize, int startPY, int startPX, int[] x, int[] y, int[][] panelMtx) {
        screen_hight = screenHight;
        screen_width = screenWidth;
        unit_size = unitSize;
        this.startPY = startPY;
        this.startPX = startPX;
        this.x = x;
        this.y = y;
        this.panelMtx = panelMtx;
    }


    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        bodyParts++;
        switch (GamePanel.direction) {
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


}
