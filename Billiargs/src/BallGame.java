
import java.awt.*;
import javax.swing.*;

public class BallGame extends JFrame {

    Image ball = Toolkit.getDefaultToolkit().getImage("image/ball.png");
    Image desk = Toolkit.getDefaultToolkit().getImage("image/desk.jpg");

    double x = 100;
    double y = 100;
    boolean right = true;

    //画窗口
    public void paint(Graphics g) {
        System.out.println("painted");
        g.drawImage(desk, 0, 0, null);
        g.drawImage(ball, (int)x, (int) y, null);

        //速度变化
        if (right) {
            x = x + 10;
        }
        else {
            x = x - 10;
        }

        if (x > 856-70) {
            right = false;
        }

        if (x < 40) {
            right = true;
        }
    }

    //窗口加载
    void launchFrame() {
        setSize(856, 500);
        setLocation(50, 50);
        setVisible(true);

        //重画窗口
        while (true) {
            try {
                repaint();
                Thread.sleep(40);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] arg) {
        BallGame game = new BallGame();
        game.launchFrame();
    }
}
