import javax.swing.*;
import java.awt.*;
import javax.swing.Timer;
import java.awt.event.*;

public class board extends JPanel implements ActionListener {

    private Image apple;
    private Image dot;
    private Image head;
    private int dots;

    private int speed = 100;

    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;

    private int RANDOM_POSTION = 30;
    private int apple_x;
    private int apple_y;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;

    private boolean inGame = true;

    private Timer timer;

    private final int x[] = new int[900];
    private final int y[] = new int[900];


    board() {
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(300, 300));

        setFocusable(true);
        loadImage();
        initGame();
    }

    public void loadImage() {
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons\\apple.png"));
        apple = i1.getImage();

        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("icons\\dot.png"));
        dot = i2.getImage();

        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("icons\\head.png"));
        head = i3.getImage();
    }

    public void initGame() {
        dots = 3;

        for (int i = 0; i < dots; i++) {
            x[i] = 50 - i * DOT_SIZE;
            y[i] = 50;
        }

        locateApple();

        timer = new Timer(speed, this);
        timer.start();
    }

    public void locateApple() {
        // Random r = new Random();
        // apple_x = (r.nextInt(300) / 10) * 10;
        // apple_x = (r.nextInt(300) / 10) * 10;
        int r = (int)(Math.random() * RANDOM_POSTION);
        apple_x = r* DOT_SIZE;
        r = (int)(Math.random() * RANDOM_POSTION);
        apple_y = r* DOT_SIZE;
        speed--;
        System.out.println(speed);
    }

    public void checkApple() {
        for(int z = dots; z > 0; z--){
            if(x[z] == apple_x && y[z] == apple_y)
                locateApple();
        }
        if (x[0] == apple_x && y[0] == apple_y) {
            dots++;
            locateApple();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (inGame) {
            g.drawImage(apple, apple_x, apple_y, this);
            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(dot, x[z], y[z], this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }else{
            gameOver(g);
        }
    }

    public void gameOver(Graphics g){
        String msg = "Game Over! Press 'R' to Restart";
        Font font = new Font("SAN_SERIF", Font.BOLD, 14);
        FontMetrics metrices = getFontMetrics(font);

        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(msg, (300 - metrices.stringWidth(msg))/2, 150);
    }

    public void checkCollision() {
        for (int z = dots; z > 0; z--) {
            if ((z > 3) && (x[0] == x[z]) && (y[0] == y[z]))
                inGame = false;
        }
        /******************************************************************** */
                              // For Boundary Collision
        /******************************************************************** */
        // if (y[0] > 290 || x[0] > 290 || y[0] < 0 || x[0] < 0) {
        //     inGame = false;
        // }
        /******************************************************************** */
                              // For continuous Movement
        /******************************************************************** */
        if (x[0] > 290) {
            x[0] = 0;
        }
        if (y[0] > 290){
            y[0] = 0;
        }
        if (x[0] < 0) {
            x[0] = 300;
        }
        if (y[0] < 0){
            y[0] = 300;
        }
        /******************************************************************** */
        if (!inGame) {
            timer.stop();
        }
    }

    public void move() {

        for (int k = dots; k > 0; k--) {
            x[k] = x[k - 1];
            y[k] = y[k - 1];
        }
        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }
        if (rightDirection) {
            x[0] += DOT_SIZE;
        }
        if (upDirection) {
            y[0] -= DOT_SIZE;
        }
        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }

    private class TAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent ke) {
            int key = ke.getKeyCode();

            if (key == ke.VK_LEFT && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if (key == ke.VK_RIGHT && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if (key == ke.VK_UP && (!downDirection)) {
                leftDirection = false;
                upDirection = true;
                rightDirection = false;
            }
            if (key == ke.VK_DOWN && (!upDirection)) {
                leftDirection = false;
                downDirection = true;
                rightDirection = false;
            }
            if (key == ke.VK_R){
                leftDirection = false;
                downDirection = false;
                upDirection = false;
                rightDirection = true;
                speed = 100;
                initGame();
                inGame = true;
                repaint();
            }
        }
    }
}
