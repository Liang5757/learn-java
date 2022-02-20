
import java.awt.*;
import javax.swing.*;

public class BallGame2 extends JFrame {

    Image ball = Toolkit.getDefaultToolkit().getImage("Billiargs/image/ball.png");
    Image desk = Toolkit.getDefaultToolkit().getImage("Billiargs/image/desk.jpg");

    double x = 100;
    double y = 100;

    //弧度
    double degree = 3.14/3;

    //画窗口
    public void paint(Graphics g) {
        System.out.println("painted");
        g.drawImage(desk, 0, 0, null);
        g.drawImage(ball, (int)x, (int) y, null);

        x = x + 10 * Math.cos(degree);
        y = y + 10 * Math.sin(degree);

        //碰到上下边界
        if (y > 500-70 || y < 80) {
            degree = -degree;
        }
        //碰到左右边界
        if (x < 40 || x > 856-70) {
            degree = 3.14 - degree;
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
        BallGame2 game = new BallGame2();
        game.launchFrame();
    }
}
